package dev.mod_id.datagen.compat.create;

import static dev.mod_id.Constants.MOD_ID;
import static dev.mod_id.Constants.resourceLocation;

import dev.mod_id.datagen.util.ItemUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class CreateRecipeBuilder extends RecipeProvider {

    private final BiFunction<RecipeOutput, Set<String>, RecipeOutput> conditionWrapper;

    protected CreateRecipeBuilder(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput, BiFunction<RecipeOutput, Set<String>, RecipeOutput> conditionWrapper) {
        super(recipeOutput, advancementOutput);
        this.conditionWrapper = conditionWrapper;
    }

    protected void createSandpaperPolishing(ItemLike input, ItemLike result) {
        final RecipeOutput recipeOutput = getModLoadConditions(input, result);
        final String recipePrefix = getItemName(result);
        final String recipeSuffix = getItemName(input);
        SandpaperRecipeBuilder.polishing(input, result)
            .unlockedBy(getHasName(input), has(input))
            .save(recipeOutput, ResourceKey.create(Registries.RECIPE, resourceLocation("create", "sandpaper_polishing/" + recipePrefix + "_from_" + recipeSuffix)));
    }

    protected void createCrushing(ItemLike input, int processingTime, CrushingResult... results) {
        final RecipeOutput recipeOutput = getModLoadConditions(input, results);
        CrushingRecipeBuilder builder = CrushingRecipeBuilder.crushing(input, processingTime);
        for (CrushingResult result : results) {
            builder.result(result.item(), result.count(), result.chance());
        }
        builder.unlockedBy(getHasName(input), has(input));
        builder.save(recipeOutput, ResourceKey.create(Registries.RECIPE, resourceLocation("create", "crushing/" + getItemName(input))));
    }

    protected record CrushingResult(ItemLike item, int count, float chance) {
    }

    private RecipeOutput getModLoadConditions(ItemLike input, CrushingResult[] results) {
        ItemLike[] items = new ItemLike[results.length + 1];
        items[0] = input;
        for (int i = 0; i < results.length; i++) {
            items[i + 1] = results[i].item();
        }
        return getModLoadConditions(items);
    }

    private RecipeOutput getModLoadConditions(ItemLike... items) {
        Set<String> requiredMods = new HashSet<>();
        Arrays.stream(items).map(ItemUtils::getNamespace).forEach(requiredMods::add);
        requiredMods.remove("minecraft");
        requiredMods.remove(MOD_ID);
        requiredMods.add("create");
        return conditionWrapper.apply(output, requiredMods);
    }
}

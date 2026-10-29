package dev.mod_id.datagen.compat.create;

import static dev.mod_id.util.Constants.MOD_ID;
import static dev.mod_id.util.IdentifierUtils.resourceLocation;

import dev.mod_id.datagen.util.ItemUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class CreateRecipeBuilder extends RecipeProvider {

    private final Function<Set<String>, RecipeOutput> conditionedOutput;

    protected CreateRecipeBuilder(HolderLookup.Provider registries, RecipeOutput output, Function<Set<String>, RecipeOutput> conditionedOutput) {
        super(registries, output);
        this.conditionedOutput = conditionedOutput;
    }

    protected void createSandpaperPolishing(ItemLike input, ItemLike result) {
        final RecipeOutput recipeOutput = getModLoadConditions(input, result);
        final String recipePrefix = getItemName(result);
        final String recipeSuffix = getItemName(input);
        SandpaperRecipeBuilder.polishing(input, result)
            .unlockedBy(getHasName(input), has(input))
            .save(recipeOutput, ResourceKey.create(Registries.RECIPE, resourceLocation("create", "sandpaper_polishing/" + recipePrefix + "_from_" + recipeSuffix)));
    }

    private RecipeOutput getModLoadConditions(ItemLike... items) {
        Set<String> requiredMods = new HashSet<>();
        Arrays.stream(items).map(ItemUtils::getNamespace).forEach(requiredMods::add);
        requiredMods.remove("minecraft");
        requiredMods.remove(MOD_ID);
        requiredMods.add("create");
        return conditionedOutput.apply(requiredMods);
    }
}

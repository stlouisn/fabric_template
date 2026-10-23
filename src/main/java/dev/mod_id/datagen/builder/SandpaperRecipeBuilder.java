package dev.mod_id.datagen.builder;

import com.zurrtum.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class SandpaperRecipeBuilder implements RecipeBuilder {

    private final ItemStackTemplate result;
    private final Ingredient ingredient;

    private final RecipeUnlockAdvancementBuilder advancementBuilder;

    private SandpaperRecipeBuilder(ItemLike input, ItemLike result) {
        this.advancementBuilder = new RecipeUnlockAdvancementBuilder();
        this.ingredient = Ingredient.of(input);
        this.result = new ItemStackTemplate(result.asItem());
    }

    public static SandpaperRecipeBuilder polishing(ItemLike input, ItemLike result) {
        return new SandpaperRecipeBuilder(input, result);
    }

    public SandpaperRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public SandpaperRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        SandPaperPolishingRecipe recipe = new SandPaperPolishingRecipe(result, ingredient);
        output.accept(id, recipe, this.advancementBuilder.build(output, id, RecipeCategory.MISC));
    }
}

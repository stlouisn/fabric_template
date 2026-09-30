package dev.palisadewalls.datagen.provider;

import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.ItemLike;

import static net.minecraft.data.recipes.RecipeProvider.getConversionRecipeName;

public final class DatagenRecipeBuilder {

    private final HolderLookup.Provider registries;
    private final RecipeOutput output;

    public DatagenRecipeBuilder(HolderLookup.Provider registries, RecipeOutput output) {
        this.registries = registries;
        this.output = output;
    }

    public void oneToOneConversion(ItemLike output, ItemLike input, RecipeCategory recipeCategory, String recipeGroup) {
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), recipeCategory, output)
            .group(recipeGroup)
            .requires(input)
            .unlockedBy(RecipeProvider.getHasName(input), RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), input)))
            .save(this.output, getConversionRecipeName(output, input));
    }

    public void palisade(ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, output, 6)
            .group("palisade")
            .define('#', input)
            .pattern("###")
            .pattern("###")
            .unlockedBy(RecipeProvider.getHasName(input), RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), input)))
            .save(this.output);
    }
}

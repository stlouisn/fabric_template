package dev.ores.datagen.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NullMarked;

@SuppressWarnings("SameParameterValue")
@NullMarked
public abstract class DatagenRecipeHelper extends RecipeProvider {

    protected DatagenRecipeHelper(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    protected void createNineBlockStorage(ItemLike unpackedForm, ItemLike packedForm) {
        final String unpackedRecipeGroup = getItemName(unpackedForm);
        final String packedRecipeGroup = getItemName(packedForm);
        createNineBlockStorage(unpackedForm, unpackedRecipeGroup, packedForm, packedRecipeGroup);
    }

    protected void createNineBlockStorage(ItemLike unpackedForm, ItemLike packedForm, RecipeCategory recipeCategory) {
        final String unpackedRecipeGroup = getItemName(unpackedForm);
        final String packedRecipeGroup = getItemName(packedForm);
        createNineBlockStorage(unpackedForm, unpackedRecipeGroup, packedForm, packedRecipeGroup, recipeCategory);
    }

    protected void createNineBlockStorage(ItemLike unpackedForm, String unpackedRecipeGroup, ItemLike packedForm, String packedRecipeGroup) {
        final RecipeCategory recipeCategory = RecipeCategory.MISC;
        createNineBlockStorage(unpackedForm, unpackedRecipeGroup, packedForm, packedRecipeGroup, recipeCategory);
    }

    protected void createNineBlockStorage(ItemLike unpackedForm, String unpackedRecipeGroup, ItemLike packedForm, String packedRecipeGroup, RecipeCategory recipeCategory) {
        shapeless(recipeCategory, unpackedForm, 9)
            .requires(packedForm)
            .group(unpackedRecipeGroup)
            .unlockedBy(getHasName(packedForm), has(packedForm))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.parse(getConversionRecipeName(unpackedForm, packedForm))));
        shaped(recipeCategory, packedForm)
            .define('#', unpackedForm)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .group(packedRecipeGroup)
            .unlockedBy(getHasName(unpackedForm), has(unpackedForm))
            .save(output, ResourceKey.create(Registries.RECIPE, Identifier.parse(getConversionRecipeName(packedForm, unpackedForm))));
    }

    protected void createTwoByTwoPacker(ItemLike unpackedForm, ItemLike packedForm) {
        final String recipeGroup = getItemName(packedForm);
        createTwoByTwoPacker(unpackedForm, packedForm, recipeGroup);
    }

    protected void createTwoByTwoPacker(ItemLike unpackedForm, ItemLike packedForm, RecipeCategory recipeCategory) {
        final String recipeGroup = getItemName(packedForm);
        createTwoByTwoPacker(unpackedForm, packedForm, recipeGroup, recipeCategory);
    }

    protected void createTwoByTwoPacker(ItemLike unpackedForm, ItemLike packedForm, String recipeGroup) {
        final RecipeCategory recipeCategory = RecipeCategory.MISC;
        createTwoByTwoPacker(unpackedForm, packedForm, recipeGroup, recipeCategory);
    }

    protected void createTwoByTwoPacker(ItemLike unpackedForm, ItemLike packedForm, String recipeGroup, RecipeCategory recipeCategory) {
        shaped(recipeCategory, packedForm, 1)
            .define('#', unpackedForm)
            .pattern("##")
            .pattern("##")
            .group(recipeGroup)
            .unlockedBy(getHasName(unpackedForm), has(unpackedForm))
            .save(output);
    }

    protected void createOneToOneConversion(ItemLike input, ItemLike result) {
        final RecipeCategory recipeCategory = RecipeCategory.MISC;
        createOneToOneConversion(input, result, recipeCategory);
    }

    protected void createOneToOneConversion(ItemLike input, ItemLike result, RecipeCategory recipeCategory) {
        final String recipeGroup = getItemName(result);
        createOneToOneConversion(input, result, recipeGroup, recipeCategory);
    }

    protected void createOneToOneConversion(ItemLike input, ItemLike result, String recipeGroup, RecipeCategory recipeCategory) {
        shapeless(recipeCategory, result, 1)
            .requires(input, 1)
            .group(recipeGroup)
            .unlockedBy(getHasName(input), has(input))
            .save(output, getItemName(result) + "_from" + getItemName(input));
    }

    protected void createOreProcessingRecipe(TagKey<Item> input, ItemLike result, float experience) {
        final String recipeGroup = getItemName(result);
        createOreProcessingRecipe(input, result, recipeGroup, experience);
    }

    protected void createOreProcessingRecipe(TagKey<Item> input, ItemLike result, String recipeGroup, float experience) {
        createOreProcessingRecipe(input, result, recipeGroup, experience, true);
    }

    protected void createOreProcessingRecipe(TagKey<Item> input, ItemLike result, String recipeGroup, float experience, Boolean useRecipeSuffix) {
        final String recipeSuffix;
        if (useRecipeSuffix) {
            recipeSuffix = "_" + input.location().getPath();
        } else {
            recipeSuffix = "";
        }
        createOreProcessingRecipe(input, result, recipeGroup, experience, recipeSuffix);
    }

    protected void createOreProcessingRecipe(TagKey<Item> input, ItemLike result, float experience, String recipeSuffix) {
        final String recipeGroup = getItemName(result);
        createOreProcessingRecipe(input, result, recipeGroup, experience, recipeSuffix);
    }

    protected void createOreProcessingRecipe(TagKey<Item> input, ItemLike result, String recipeGroup, float experience, String recipeSuffix) {
        final Ingredient ingredient = Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(input));
        createOreProcessingRecipe(ingredient, result, recipeGroup, experience, recipeSuffix);
    }

    protected void createOreProcessingRecipe(ItemLike input, ItemLike result, float experience) {
        final String recipeGroup = getItemName(result);
        createOreProcessingRecipe(input, result, recipeGroup, experience);
    }

    protected void createOreProcessingRecipe(ItemLike input, ItemLike result, String recipeGroup, float experience) {
        createOreProcessingRecipe(input, result, recipeGroup, experience, true);
    }

    protected void createOreProcessingRecipe(ItemLike input, ItemLike result, String recipeGroup, float experience, Boolean useRecipeSuffix) {
        final String recipeSuffix;
        if (useRecipeSuffix) {
            recipeSuffix = "_" + getItemName(input);
        } else {
            recipeSuffix = "";
        }
        createOreProcessingRecipe(input, result, recipeGroup, experience, recipeSuffix);
    }

    protected void createOreProcessingRecipe(ItemLike input, ItemLike result, String recipeGroup, float experience, String recipeSuffix) {
        final Ingredient ingredient = Ingredient.of(input.asItem());;
        createOreProcessingRecipe(ingredient, result, recipeGroup, experience, recipeSuffix);
    }

    protected void createOreProcessingRecipe(Ingredient ingredient, ItemLike result, String recipeGroup, float experience, String recipeSuffix) {
        final RecipeCategory craftingCategory = RecipeCategory.MISC;
        final CookingBookCategory cookingCategory = CookingBookCategory.MISC;
        SimpleCookingRecipeBuilder.generic(
                ingredient,
                craftingCategory,
                cookingCategory,
                result,
                experience,
                200,
                SmeltingRecipe::new)
            .group(recipeGroup)
            .unlockedBy(getHasName(result), has(result))
            .save(output, getItemName(result) + "_from_smelting" + recipeSuffix);
        SimpleCookingRecipeBuilder.generic(
                ingredient,
                craftingCategory,
                cookingCategory,
                result,
                experience,
                100,
                BlastingRecipe::new)
            .group(recipeGroup)
            .unlockedBy(getHasName(result), has(result))
            .save(output, getItemName(result) + "_from_blasting" + recipeSuffix);
    }
}

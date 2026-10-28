package dev.mod_id.datagen.builder;

import static dev.mod_id.util.Constants.MOD_ID;

import dev.mod_id.datagen.util.ItemUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
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
import org.jspecify.annotations.Nullable;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenRecipeBuilder extends RecipeProvider {

    protected final RecipeOutput output;
    private final Function<Set<String>, RecipeOutput> conditionedOutput;

    protected DatagenRecipeBuilder(HolderLookup.Provider registries, RecipeOutput output, Function<Set<String>, RecipeOutput> conditionedOutput) {
        super(registries, output);
        this.output = output;
        this.conditionedOutput = conditionedOutput;
    }

    protected void createShapelessRecipe(ItemLike input, ItemLike result) {
        createShapelessRecipe(input, result, RecipeCategory.MISC);
    }

    protected void createShapelessRecipe(ItemLike input, ItemLike result, RecipeCategory recipeCategory) {
        createShapelessRecipe(List.of(input), result, recipeCategory);
    }

    protected void createShapelessRecipe(List<ItemLike> inputs, ItemLike result) {
        createShapelessRecipe(inputs, result, RecipeCategory.MISC);
    }

    protected void createShapelessRecipe(List<ItemLike> inputs, ItemLike result, RecipeCategory recipeCategory) {
        final RecipeOutput recipeOutput = getModLoadConditions(Stream.concat(Stream.of(result), inputs.stream()).toArray(ItemLike[]::new));
        final String recipeGroup = getItemName(result);
        final ShapelessRecipeBuilder builder = shapeless(recipeCategory, result, 1);
        for (ItemLike item : inputs) {
            builder.requires(item);
            builder.unlockedBy(getHasName(item), has(item));
        }
        builder.group(recipeGroup);
        builder.save(recipeOutput);
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
        final RecipeOutput recipeOutput = getModLoadConditions(unpackedForm, packedForm);
        shapeless(recipeCategory, unpackedForm, 9)
            .requires(packedForm)
            .group(unpackedRecipeGroup)
            .unlockedBy(getHasName(packedForm), has(packedForm))
            .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.parse(getConversionRecipeName(unpackedForm, packedForm))));
        shaped(recipeCategory, packedForm)
            .define('#', unpackedForm)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .group(packedRecipeGroup)
            .unlockedBy(getHasName(unpackedForm), has(unpackedForm))
            .save(recipeOutput, ResourceKey.create(Registries.RECIPE, Identifier.parse(getConversionRecipeName(packedForm, unpackedForm))));
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
        final RecipeOutput recipeOutput = getModLoadConditions(unpackedForm, packedForm);
        shaped(recipeCategory, packedForm, 1)
            .define('#', unpackedForm)
            .pattern("##")
            .pattern("##")
            .group(recipeGroup)
            .unlockedBy(getHasName(unpackedForm), has(unpackedForm))
            .save(recipeOutput);
    }

    protected void createThreeByThreePacker(ItemLike unpackedForm, ItemLike packedForm) {
        final String recipeGroup = getItemName(packedForm);
        createThreeByThreePacker(unpackedForm, packedForm, recipeGroup);
    }

    protected void createThreeByThreePacker(ItemLike unpackedForm, ItemLike packedForm, RecipeCategory recipeCategory) {
        final String recipeGroup = getItemName(packedForm);
        createThreeByThreePacker(unpackedForm, packedForm, recipeGroup, recipeCategory);
    }

    protected void createThreeByThreePacker(ItemLike unpackedForm, ItemLike packedForm, String recipeGroup) {
        final RecipeCategory recipeCategory = RecipeCategory.MISC;
        createThreeByThreePacker(unpackedForm, packedForm, recipeGroup, recipeCategory);
    }

    protected void createThreeByThreePacker(ItemLike unpackedForm, ItemLike packedForm, String recipeGroup, RecipeCategory recipeCategory) {
        final RecipeOutput recipeOutput = getModLoadConditions(unpackedForm, packedForm);
        shaped(recipeCategory, packedForm, 1)
            .define('#', unpackedForm)
            .pattern("###")
            .pattern("###")
            .pattern("###")
            .group(recipeGroup)
            .unlockedBy(getHasName(unpackedForm), has(unpackedForm))
            .save(recipeOutput);
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
        final RecipeOutput recipeOutput = getModLoadConditions(input, result);
        shapeless(recipeCategory, result, 1)
            .requires(input, 1)
            .group(recipeGroup)
            .unlockedBy(getHasName(input), has(input))
            .save(recipeOutput, getItemName(result) + "_from" + getItemName(input));
    }

    protected void createCrossPatternRecipe(ItemLike ingredient, ItemLike center, ItemLike result, RecipeCategory recipeCategory) {
        createCrossPatternRecipe(ingredient, center, result, 1, recipeCategory);
    }

    protected void createCrossPatternRecipe(ItemLike ingredient, ItemLike center, ItemLike result, String recipeGroup, RecipeCategory recipeCategory) {
        createCrossPatternRecipe(ingredient, center, result, 1, recipeGroup, recipeCategory);
    }

    protected void createCrossPatternRecipe(ItemLike ingredient, ItemLike center, ItemLike result, int count, RecipeCategory recipeCategory) {
        final String recipeGroup = getItemName(result);
        createCrossPatternRecipe(ingredient, center, result, count, recipeGroup, recipeCategory);
    }

    protected void createCrossPatternRecipe(ItemLike outerInput, ItemLike centerInput, ItemLike result, int count, String recipeGroup, RecipeCategory recipeCategory) {
        final RecipeOutput recipeOutput = getModLoadConditions(outerInput, centerInput, result);
        shaped(recipeCategory, result, count)
            .define('#', outerInput)
            .define('X', centerInput)
            .pattern(" # ")
            .pattern("#X#")
            .pattern(" # ")
            .group(recipeGroup)
            .unlockedBy(getHasName(outerInput), has(outerInput))
            .save(recipeOutput);
    }

    protected void createOreProcessingRecipe(TagKey<Item> input, ItemLike result, String recipeGroup, float experience, boolean useRecipeSuffix) {
        createOreProcessingRecipe(input, result, recipeGroup, experience, useRecipeSuffix, null);
    }

    protected void createOreProcessingRecipe(TagKey<Item> input, ItemLike result, String recipeGroup, float experience, boolean useRecipeSuffix, @Nullable Set<String> requiredMods) {
        final String recipeSuffix;
        if (useRecipeSuffix) {
            recipeSuffix = "_" + input.location().getPath();
        } else {
            recipeSuffix = "";
        }
        createOreProcessingRecipe(input, result, recipeGroup, experience, recipeSuffix, requiredMods);
    }

    protected void createOreProcessingRecipe(TagKey<Item> input, ItemLike result, float experience, String recipeSuffix) {
        createOreProcessingRecipe(input, result, experience, recipeSuffix, null);
    }

    protected void createOreProcessingRecipe(TagKey<Item> input, ItemLike result, float experience, String recipeSuffix, @Nullable Set<String> requiredMods) {
        final String recipeGroup = getItemName(result);
        createOreProcessingRecipe(input, result, recipeGroup, experience, recipeSuffix, requiredMods);
    }

    protected void createOreProcessingRecipe(TagKey<Item> input, ItemLike result, String recipeGroup, float experience, String recipeSuffix) {
        createOreProcessingRecipe(input, result, recipeGroup, experience, recipeSuffix, null);
    }

    protected void createOreProcessingRecipe(TagKey<Item> input, ItemLike result, String recipeGroup, float experience, String recipeSuffix, @Nullable Set<String> requiredMods) {
        final RecipeOutput recipeOutput = getModLoadConditions(requiredMods, result);
        final RecipeCategory craftingCategory = RecipeCategory.MISC;
        final CookingBookCategory cookingCategory = CookingBookCategory.MISC;
        final Ingredient ingredient = Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(input));
        SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, result, experience, 200, SmeltingRecipe::new)
            .group(recipeGroup)
            .unlockedBy(getHasName(result), has(result))
            .save(recipeOutput, getItemName(result) + "_from_smelting" + recipeSuffix);
        SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, result, experience, 100, BlastingRecipe::new)
            .group(recipeGroup)
            .unlockedBy(getHasName(result), has(result))
            .save(recipeOutput, getItemName(result) + "_from_blasting" + recipeSuffix);
    }

    protected void createOreProcessingRecipe(ItemLike input, ItemLike result, float experience) {
        final String recipeGroup = getItemName(result);
        createOreProcessingRecipe(input, result, recipeGroup, experience);
    }

    protected void createOreProcessingRecipe(ItemLike input, ItemLike result, String recipeGroup, float experience) {
        createOreProcessingRecipe(input, result, recipeGroup, experience, true);
    }

    protected void createOreProcessingRecipe(ItemLike input, ItemLike result, String recipeGroup, float experience, boolean useRecipeSuffix) {
        final String recipeSuffix;
        if (useRecipeSuffix) {
            recipeSuffix = "_" + getItemName(input);
        } else {
            recipeSuffix = "";
        }
        createOreProcessingRecipe(input, result, recipeGroup, experience, recipeSuffix);
    }

    protected void createOreProcessingRecipe(ItemLike input, ItemLike result, String recipeGroup, float experience, String recipeSuffix) {
        final RecipeOutput recipeOutput = getModLoadConditions(input, result);
        final RecipeCategory craftingCategory = RecipeCategory.MISC;
        final CookingBookCategory cookingCategory = CookingBookCategory.MISC;
        final Ingredient ingredient = Ingredient.of(input);
        final String recipePrefix = getItemName(result);
        SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, result, experience, 200, SmeltingRecipe::new)
            .group(recipeGroup)
            .unlockedBy(getHasName(result), has(result))
            .save(recipeOutput, recipePrefix + "_from_smelting" + recipeSuffix);
        SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, result, experience, 100, BlastingRecipe::new)
            .group(recipeGroup)
            .unlockedBy(getHasName(result), has(result))
            .save(recipeOutput, recipePrefix + "_from_blasting" + recipeSuffix);
    }

    private RecipeOutput getModLoadConditions(ItemLike... items) {
        final Set<String> requiredMods = new HashSet<>();
        Arrays.stream(items).map(ItemUtils::getNamespace).forEach(requiredMods::add);
        requiredMods.remove("minecraft");
        requiredMods.remove(MOD_ID);
        if (requiredMods.isEmpty()) {
            return output;
        }
        return conditionedOutput.apply(requiredMods);
    }

    private RecipeOutput getModLoadConditions(@Nullable Set<String> additionalRequiredMods, ItemLike... items) {
        final Set<String> requiredMods;
        if (additionalRequiredMods == null) {
            requiredMods = new HashSet<>();
        } else {
            requiredMods = new HashSet<>(additionalRequiredMods);
        }
        Arrays.stream(items).map(ItemUtils::getNamespace).forEach(requiredMods::add);
        requiredMods.remove("minecraft");
        requiredMods.remove(MOD_ID);
        if (requiredMods.isEmpty()) {
            return output;
        }
        return conditionedOutput.apply(requiredMods);
    }
}

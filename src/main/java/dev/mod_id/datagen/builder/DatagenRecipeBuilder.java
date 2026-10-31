package dev.mod_id.datagen.builder;

import static dev.mod_id.Constants.MOD_ID;

import dev.mod_id.datagen.util.ItemUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
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

    private final RecipeOutput output;
    private final Function<Set<String>, RecipeOutput> conditionedOutput;

    protected DatagenRecipeBuilder(HolderLookup.Provider registries, RecipeOutput output, Function<Set<String>, RecipeOutput> conditionedOutput) {
        super(registries, output);
        this.output = output;
        this.conditionedOutput = conditionedOutput;
    }

    protected void createShapelessRecipe(ItemLike input, ItemLike result) {
        createShapelessRecipe(List.of(input), result, 1,RecipeCategory.MISC);
    }

    protected void createShapelessRecipe(ItemLike input, ItemLike result, RecipeCategory recipeCategory) {
        createShapelessRecipe(List.of(input), result, 1, recipeCategory);
    }

    protected void createShapelessRecipe(ItemLike input, ItemLike result, int count) {
        createShapelessRecipe(List.of(input), result, count, RecipeCategory.MISC);
    }

    protected void createShapelessRecipe(ItemLike input, ItemLike result, int count, RecipeCategory recipeCategory) {
        createShapelessRecipe(List.of(input), result, count, recipeCategory);
    }

    protected void createShapelessRecipe(List<ItemLike> inputs, ItemLike result) {
        createShapelessRecipe(inputs, result, 1, RecipeCategory.MISC);
    }

    protected void createShapelessRecipe(List<ItemLike> inputs, ItemLike result, RecipeCategory recipeCategory) {
        createShapelessRecipe(inputs, result, 1, recipeCategory);
    }

    protected void createShapelessRecipe(List<ItemLike> inputs, ItemLike result, int count) {
        createShapelessRecipe(inputs, result, count, RecipeCategory.MISC);
    }

    protected void createShapelessRecipe(List<ItemLike> inputs, ItemLike result, int count, RecipeCategory recipeCategory) {
        final ShapelessRecipeBuilder builder = shapeless(recipeCategory, result, count);
        final RecipeOutput recipeOutput = getModLoadConditions(Stream.concat(Stream.of(result), inputs.stream()).toArray(ItemLike[]::new));
        final String recipeGroup = getItemName(result);
        for (ItemLike item : inputs) {
            builder.requires(item);
            builder.unlockedBy(getHasName(item), has(item));
        }
        builder.group(recipeGroup);
        builder.save(recipeOutput);
    }

    protected void createShapedRecipe(ItemLike result, Consumer<ShapedRecipeContext> consumer) {
        createShapedRecipe(result, 1, RecipeCategory.MISC, consumer);
    }

    protected void createShapedRecipe(ItemLike result, RecipeCategory recipeCategory, Consumer<ShapedRecipeContext> consumer) {
        createShapedRecipe(result, 1, recipeCategory, consumer);
    }

    protected void createShapedRecipe(ItemLike result, int count, Consumer<ShapedRecipeContext> consumer) {
        createShapedRecipe(result, count, RecipeCategory.MISC, consumer);
    }

    protected void createShapedRecipe(ItemLike result, int count, RecipeCategory recipeCategory, Consumer<ShapedRecipeContext> consumer) {
        final ShapedRecipeBuilder builder = shaped(recipeCategory, result, count);
        final ShapedRecipeContext recipeContext = new ShapedRecipeContext(builder);
        consumer.accept(recipeContext);
        final Set<ItemLike> inputs = recipeContext.getDefinedItems();
        final RecipeOutput recipeOutput = getModLoadConditions(Stream.concat(Stream.of(result), inputs.stream()).toArray(ItemLike[]::new));
        final String recipeGroup = getItemName(result);
        for (ItemLike item : inputs) {
            builder.unlockedBy(getHasName(item), has(item));
        }
        builder.group(recipeGroup);
        builder.save(recipeOutput);
    }

    protected void createWall(ItemLike input, ItemLike result) {
        createWall(input, result, RecipeCategory.MISC);
    }

    protected void createWall(ItemLike input, ItemLike result, RecipeCategory recipeCategory) {
        final RecipeOutput recipeOutput = getModLoadConditions(input, result);
        final String recipeGroup = getItemName(result);
        shaped(recipeCategory, result, 6)
            .group(recipeGroup)
            .define('#', input)
            .pattern("###")
            .pattern("###")
            .unlockedBy(getHasName(input), has(input))
            .save(recipeOutput);
    }

    protected void createNineBlockStorage(ItemLike unpackedForm, ItemLike packedForm) {
        createNineBlockStorage(unpackedForm, packedForm, RecipeCategory.MISC);
    }

    protected void createNineBlockStorage(ItemLike unpackedForm, ItemLike packedForm, RecipeCategory recipeCategory) {
        final RecipeOutput recipeOutput = getModLoadConditions(unpackedForm, packedForm);
        final String unpackedRecipeGroup = getItemName(unpackedForm);
        final String packedRecipeGroup = getItemName(packedForm);
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
        createTwoByTwoPacker(unpackedForm, packedForm, RecipeCategory.MISC);
    }

    protected void createTwoByTwoPacker(ItemLike unpackedForm, ItemLike packedForm, RecipeCategory recipeCategory) {
        final RecipeOutput recipeOutput = getModLoadConditions(unpackedForm, packedForm);
        final String recipeGroup = getItemName(packedForm);
        shaped(recipeCategory, packedForm, 1)
            .define('#', unpackedForm)
            .pattern("##")
            .pattern("##")
            .group(recipeGroup)
            .unlockedBy(getHasName(unpackedForm), has(unpackedForm))
            .save(recipeOutput);
    }

    protected void createThreeByThreePacker(ItemLike unpackedForm, ItemLike packedForm) {
        createThreeByThreePacker(unpackedForm, packedForm, RecipeCategory.MISC);
    }

    protected void createThreeByThreePacker(ItemLike unpackedForm, ItemLike packedForm, RecipeCategory recipeCategory) {
        final RecipeOutput recipeOutput = getModLoadConditions(unpackedForm, packedForm);
        final String recipeGroup = getItemName(packedForm);
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
        createOneToOneConversion(input, result, RecipeCategory.MISC);
    }

    protected void createOneToOneConversion(ItemLike input, ItemLike result, RecipeCategory recipeCategory) {
        final RecipeOutput recipeOutput = getModLoadConditions(input, result);
        final String recipeGroup = getItemName(result);
        shapeless(recipeCategory, result, 1)
            .requires(input, 1)
            .group(recipeGroup)
            .unlockedBy(getHasName(input), has(input))
            .save(recipeOutput, getItemName(result) + "_from" + getItemName(input));
    }

    protected void createCrossPatternRecipe(ItemLike outer, ItemLike center, ItemLike result, RecipeCategory recipeCategory) {
        createCrossPatternRecipe(outer, center, result, 1, recipeCategory);
    }

    protected void createCrossPatternRecipe(ItemLike outer, ItemLike center, ItemLike result, int count, RecipeCategory recipeCategory) {
        final RecipeOutput recipeOutput = getModLoadConditions(outer, center, result);
        final String recipeGroup = getItemName(result);
        shaped(recipeCategory, result, count)
            .define('#', outer)
            .define('X', center)
            .pattern(" # ")
            .pattern("#X#")
            .pattern(" # ")
            .group(recipeGroup)
            .unlockedBy(getHasName(outer), has(outer))
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
        final Ingredient ingredient = Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(input));
        SimpleCookingRecipeBuilder.generic(ingredient, RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, 200, SmeltingRecipe::new)
            .group(recipeGroup)
            .unlockedBy(getHasName(result), has(result))
            .save(recipeOutput, getItemName(result) + "_from_smelting" + recipeSuffix);
        SimpleCookingRecipeBuilder.generic(ingredient, RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, 100, BlastingRecipe::new)
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
        final Ingredient ingredient = Ingredient.of(input);
        final String recipePrefix = getItemName(result);
        SimpleCookingRecipeBuilder.generic(ingredient, RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, 200, SmeltingRecipe::new)
            .group(recipeGroup)
            .unlockedBy(getHasName(result), has(result))
            .save(recipeOutput, recipePrefix + "_from_smelting" + recipeSuffix);
        SimpleCookingRecipeBuilder.generic(ingredient, RecipeCategory.MISC, CookingBookCategory.MISC, result, experience, 100, BlastingRecipe::new)
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

    @SuppressWarnings("UnusedReturnValue")
    protected static final class ShapedRecipeContext {

        private static final Set<ItemLike> DEFINED_ITEMS = new LinkedHashSet<>();

        private final ShapedRecipeBuilder delegate;

        public ShapedRecipeContext(ShapedRecipeBuilder delegate) {
            this.delegate = delegate;
        }

        public ShapedRecipeContext pattern(String patternString) {
            delegate.pattern(patternString);
            return this;
        }

        public ShapedRecipeContext define(char symbol, ItemLike item) {
            delegate.define(symbol, item);
            DEFINED_ITEMS.add(item);
            return this;
        }

        public ShapedRecipeContext define(char symbol, TagKey<Item> tag) {
            delegate.define(symbol, tag);
            return this;
        }

        public ShapedRecipeContext define(char symbol, Ingredient ingredient) {
            delegate.define(symbol, ingredient);
            return this;
        }

        public Set<ItemLike> getDefinedItems() {
            return DEFINED_ITEMS;
        }
    }
}

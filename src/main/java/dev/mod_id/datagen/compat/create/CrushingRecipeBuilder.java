package dev.mod_id.datagen.compat.create;

import com.zurrtum.create.content.kinetics.crusher.CrushingRecipe;
import com.zurrtum.create.content.processing.recipe.ProcessingOutput;
import java.util.ArrayList;
import java.util.List;
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
import org.jspecify.annotations.Nullable;

public final class CrushingRecipeBuilder implements RecipeBuilder {

    private final RecipeUnlockAdvancementBuilder advancementBuilder;
    private final Ingredient ingredient;
    private final int processingTime;

    private final List<ProcessingOutput> results = new ArrayList<>();

    private CrushingRecipeBuilder(ItemLike input, int processingTime) {
        if (processingTime <= 0) {
            throw new IllegalArgumentException("Processing time must be positive");
        }
        this.advancementBuilder = new RecipeUnlockAdvancementBuilder();
        this.ingredient = Ingredient.of(input);
        this.processingTime = processingTime;
    }

    public static CrushingRecipeBuilder crushing(ItemLike input, int processingTime) {
        return new CrushingRecipeBuilder(input, processingTime);
    }

    public CrushingRecipeBuilder result(ItemLike item, int count, float chance) {
        if (results.size() >= 7) {
            throw new IllegalStateException("Crushing recipes can have at most 7 results");
        }
        if (count < 1 || count > 99) {
            throw new IllegalArgumentException("Result count must be between 1 and 99");
        }
        if (!Float.isFinite(chance) || chance < 0.0f || chance > 1.0f) {
            throw new IllegalArgumentException("Result chance must be between 0 and 1");
        }
        results.add(new ProcessingOutput(item.asItem(), count, chance));
        return this;
    }

    public CrushingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public CrushingRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        if (results.isEmpty()) {
            throw new IllegalStateException("A crushing recipe must have at least one result");
        }
        return RecipeBuilder.getDefaultRecipeId(new ItemStackTemplate(results.getFirst().item().value()));
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        if (results.isEmpty()) {
            throw new IllegalStateException("A crushing recipe must have at least one result");
        }
        CrushingRecipe recipe = new CrushingRecipe(processingTime, List.copyOf(results), ingredient);
        output.accept(id, recipe, advancementBuilder.build(output, id, RecipeCategory.MISC));
    }
}

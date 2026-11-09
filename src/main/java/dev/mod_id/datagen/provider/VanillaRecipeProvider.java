package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterItems;
import dev.mod_id.datagen.builder.DatagenRecipeBuilder;
import java.util.Set;
import java.util.function.BiFunction;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

public final class VanillaRecipeProvider extends DatagenRecipeBuilder {

    public VanillaRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput, BiFunction<RecipeOutput, Set<String>, RecipeOutput> conditionWrapper) {
        super(recipeOutput, advancementOutput, conditionWrapper);
    }

    @Override
    public void buildRecipes() {

        createShapedRecipe(RegisterItems.TURTLE_LEGGINGS, RecipeCategory.COMBAT, builder -> builder
            .pattern("###")
            .pattern("# #")
            .pattern("# #")
            .define('#', Items.TURTLE_SCUTE));

        createShapedRecipe(RegisterItems.TURTLE_CHESTPLATE, RecipeCategory.COMBAT, builder -> builder
            .pattern("# #")
            .pattern("###")
            .pattern("###")
            .define('#', Items.TURTLE_SCUTE));

        createShapedRecipe(RegisterItems.TURTLE_BOOTS, RecipeCategory.COMBAT, builder -> builder
            .pattern("# #")
            .pattern("# #")
            .define('#', Items.TURTLE_SCUTE));
    }
}

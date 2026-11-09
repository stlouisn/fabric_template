package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterItems;
import dev.mod_id.datagen.builder.DatagenRecipeBuilder;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

public final class DatagenRecipeProvider extends DatagenRecipeBuilder {

    public DatagenRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput, BiFunction<RecipeOutput, Set<String>, RecipeOutput> conditionWrapper) {
        super(recipeOutput, advancementOutput, conditionWrapper);
    }

    @Override
    public void buildRecipes() {

        createNineBlockStorage(Items.CHARCOAL, RegisterBlockItems.CHARCOAL_BLOCK);
        createNineBlockStorage(RegisterItems.CHARCOAL_NUGGET, Items.CHARCOAL);
        createNineBlockStorage(RegisterItems.COAL_NUGGET, Items.COAL);
        createNineBlockStorage(RegisterItems.RAW_NETHERITE, RegisterBlockItems.RAW_NETHERITE_BLOCK);
        createNineBlockStorage(RegisterItems.NETHERITE_NUGGET, Items.NETHERITE_INGOT);

        createTwoByTwoPacker(RegisterItems.ROSE_QUARTZ_SHARD, RegisterBlockItems.ROSE_QUARTZ_BLOCK, RecipeCategory.BUILDING_BLOCKS);

        createCrossPatternRecipe(Items.REDSTONE, RegisterBlockItems.POLISHED_ROSE_QUARTZ, AllBlocks.ROSE_QUARTZ_LAMP, RecipeCategory.REDSTONE);
    }
}

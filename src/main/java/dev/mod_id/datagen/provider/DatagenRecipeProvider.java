package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterBlocks;
import dev.mod_id.datagen.builder.DatagenRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

public final class DatagenRecipeProvider extends DatagenRecipeBuilder {

    public DatagenRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes() {

        createNineBlockStorage(Items.CHARCOAL, RegisterBlocks.CHARCOAL_BLOCK);
        createTwoByTwoPacker(RegisterItems.ROSE_QUARTZ_SHARD, RegisterBlocks.ROSE_QUARTZ_BLOCK, RecipeCategory.BUILDING_BLOCKS);
        createCrossPatternRecipe(Items.IRON_INGOT, Items.STICK, RegisterBlocks.CROSSING_RAIL, 6, RecipeCategory.TRANSPORTATION);
    }
}

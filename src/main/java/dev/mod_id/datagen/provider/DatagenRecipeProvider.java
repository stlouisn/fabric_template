package dev.ores.datagen.provider;

import dev.ores.content.registry.RegisterBlocks;
import dev.ores.content.registry.RegisterItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenRecipeProvider extends DatagenRecipeHelper {

    public DatagenRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes() {

        createNineBlockStorage(Items.CHARCOAL, RegisterBlocks.CHARCOAL_BLOCK);
        createNineBlockStorage(RegisterItems.CHARCOAL_NUGGET, Items.CHARCOAL);
        createNineBlockStorage(RegisterItems.COAL_NUGGET, Items.COAL);
        createNineBlockStorage(RegisterItems.RAW_NETHERITE, RegisterBlocks.RAW_NETHERITE_BLOCK);
        createNineBlockStorage(RegisterItems.NETHERITE_NUGGET, Items.NETHERITE_INGOT);

        createTwoByTwoPacker(RegisterItems.ROSE_QUARTZ_SHARD, RegisterBlocks.ROSE_QUARTZ_BLOCK, RecipeCategory.BUILDING_BLOCKS);

        createSandpaperPolishing(RegisterBlocks.ROSE_QUARTZ_BLOCK, RegisterBlocks.POLISHED_ROSE_QUARTZ);
    }
}

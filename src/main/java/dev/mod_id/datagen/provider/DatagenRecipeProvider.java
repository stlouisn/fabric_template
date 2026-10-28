package dev.mod_id.datagen.provider;

import com.zurrtum.create.AllBlocks;
import dev.mod_id.content.registry.RegisterBlocks;
import dev.mod_id.content.registry.RegisterItems;
import dev.mod_id.datagen.builder.DatagenRecipeBuilder;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

public final class DatagenRecipeProvider extends DatagenRecipeBuilder {

    public DatagenRecipeProvider(HolderLookup.Provider registries, RecipeOutput output, Function<Set<String>, RecipeOutput> conditionedOutput) {
        super(registries, output, conditionedOutput);
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

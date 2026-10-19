package dev.ores.datagen.provider;

import dev.ores.content.registry.RegisterBlocks;
import dev.ores.content.registry.RegisterItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenModelProvider extends FabricModelProvider {

    public DatagenModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators modelGenerator) {
        modelGenerator.createTrivialCube(RegisterBlocks.CHARCOAL_BLOCK);
        modelGenerator.createTrivialCube(RegisterBlocks.RAW_NETHERITE_BLOCK);
        modelGenerator.createTrivialCube(RegisterBlocks.ROSE_QUARTZ_BLOCK);
        modelGenerator.createTrivialCube(RegisterBlocks.BUDDING_ROSE_QUARTZ);
        modelGenerator.createAmethystCluster(RegisterBlocks.ROSE_QUARTZ_CLUSTER);
        modelGenerator.createAmethystCluster(RegisterBlocks.SMALL_ROSE_QUARTZ_BUD);
        modelGenerator.createAmethystCluster(RegisterBlocks.MEDIUM_ROSE_QUARTZ_BUD);
        modelGenerator.createAmethystCluster(RegisterBlocks.LARGE_ROSE_QUARTZ_BUD);
        modelGenerator.createTrivialCube(RegisterBlocks.POLISHED_ROSE_QUARTZ);
    }

    @Override
    public void generateItemModels(ItemModelGenerators modelGenerator) {
        modelGenerator.declareCustomModelItem(RegisterBlocks.ROSE_QUARTZ_CLUSTER.asItem());
        modelGenerator.declareCustomModelItem(RegisterBlocks.SMALL_ROSE_QUARTZ_BUD.asItem());
        modelGenerator.declareCustomModelItem(RegisterBlocks.MEDIUM_ROSE_QUARTZ_BUD.asItem());
        modelGenerator.declareCustomModelItem(RegisterBlocks.LARGE_ROSE_QUARTZ_BUD.asItem());
        modelGenerator.generateFlatItem(RegisterItems.CHARCOAL_NUGGET, ModelTemplates.FLAT_ITEM);
        modelGenerator.generateFlatItem(RegisterItems.COAL_NUGGET, ModelTemplates.FLAT_ITEM);
        modelGenerator.generateFlatItem(RegisterItems.NETHERITE_NUGGET, ModelTemplates.FLAT_ITEM);
        modelGenerator.generateFlatItem(RegisterItems.RAW_NETHERITE, ModelTemplates.FLAT_ITEM);
        modelGenerator.generateFlatItem(RegisterItems.ROSE_QUARTZ_SHARD, ModelTemplates.FLAT_ITEM);
    }
}

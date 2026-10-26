package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterBlocks;
import dev.mod_id.content.registry.RegisterItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public final class DatagenModelProvider extends FabricModelProvider {

    public DatagenModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators modelGenerator) {
        modelGenerator.createTrivialCube(RegisterBlockItems.CHARCOAL_BLOCK);
        modelGenerator.createTrivialCube(RegisterBlockItems.RAW_NETHERITE_BLOCK);
        modelGenerator.createTrivialCube(RegisterBlockItems.ROSE_QUARTZ_BLOCK);
        modelGenerator.createTrivialCube(RegisterBlockItems.BUDDING_ROSE_QUARTZ);
        modelGenerator.createAmethystCluster(RegisterBlockItems.ROSE_QUARTZ_CLUSTER);
        modelGenerator.createAmethystCluster(RegisterBlockItems.SMALL_ROSE_QUARTZ_BUD);
        modelGenerator.createAmethystCluster(RegisterBlockItems.MEDIUM_ROSE_QUARTZ_BUD);
        modelGenerator.createAmethystCluster(RegisterBlockItems.LARGE_ROSE_QUARTZ_BUD);
        modelGenerator.createTrivialCube(RegisterBlockItems.POLISHED_ROSE_QUARTZ);
    }

    @Override
    public void generateItemModels(ItemModelGenerators modelGenerator) {
        modelGenerator.declareCustomModelItem(RegisterBlockItems.ROSE_QUARTZ_CLUSTER.asItem());
        modelGenerator.declareCustomModelItem(RegisterBlockItems.SMALL_ROSE_QUARTZ_BUD.asItem());
        modelGenerator.declareCustomModelItem(RegisterBlockItems.MEDIUM_ROSE_QUARTZ_BUD.asItem());
        modelGenerator.declareCustomModelItem(RegisterBlockItems.LARGE_ROSE_QUARTZ_BUD.asItem());
        modelGenerator.generateFlatItem(RegisterItems.CHARCOAL_NUGGET, ModelTemplates.FLAT_ITEM);
        modelGenerator.generateFlatItem(RegisterItems.COAL_NUGGET, ModelTemplates.FLAT_ITEM);
        modelGenerator.generateFlatItem(RegisterItems.NETHERITE_NUGGET, ModelTemplates.FLAT_ITEM);
        modelGenerator.generateFlatItem(RegisterItems.RAW_NETHERITE, ModelTemplates.FLAT_ITEM);
        modelGenerator.generateFlatItem(RegisterItems.ROSE_QUARTZ_SHARD, ModelTemplates.FLAT_ITEM);
    }
}

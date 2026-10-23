package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterBlocks;
import dev.mod_id.content.registry.RegisterItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

import static dev.mod_id.datagen.builder.DatagenModelBuilder.createSimpleFlatRail;

public final class DatagenModelProvider extends FabricModelProvider {

    public DatagenModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerator) {

        createSimpleFlatRail(blockModelGenerator, RegisterBlocks.CROSSING_RAIL);
        modelGenerator.createTrivialCube(RegisterBlocks.CHARCOAL_BLOCK);
        modelGenerator.createAmethystCluster(RegisterBlocks.SMALL_ROSE_QUARTZ_BUD);
    }

    public void generateItemModels(ItemModelGenerators modelGenerator) {

        modelGenerator.declareCustomModelItem(RegisterBlocks.SMALL_ROSE_QUARTZ_BUD.asItem());
        modelGenerator.generateFlatItem(RegisterItems.CHARCOAL_NUGGET, ModelTemplates.FLAT_ITEM);
    }
}

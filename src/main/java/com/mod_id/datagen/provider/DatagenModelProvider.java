package com.mod_id.datagen.provider;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;


public final class DatagenModelProvider extends FabricModelProvider {

    public DatagenModelProvider(final FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(final BlockModelGenerators generators) {
    }

    @Override
    public void generateItemModels(final ItemModelGenerators generators) {
    }
}

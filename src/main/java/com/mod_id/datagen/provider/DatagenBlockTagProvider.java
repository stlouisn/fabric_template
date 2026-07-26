package com.mod_id.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public final class DatagenBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

    private static final String PROVIDER_NAME = "Block Tags Provider";

    public DatagenBlockTagProvider(final FabricPackOutput output, final CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(final HolderLookup.Provider provider) {
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}

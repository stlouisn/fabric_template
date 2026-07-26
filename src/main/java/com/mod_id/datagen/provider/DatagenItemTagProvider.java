package com.mod_id.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public final class DatagenItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    private static final String PROVIDER_NAME = "Item Tags Provider";

    public DatagenItemTagProvider(final FabricPackOutput output, final CompletableFuture<HolderLookup.Provider> completableFuture, final DatagenBlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void addTags(final HolderLookup.Provider provider) {
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}

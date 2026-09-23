package com.mod_id.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public final class DatagenDynamicRegistryProvider extends FabricDynamicRegistryProvider {

    private static final String PROVIDER_NAME = "Dynamic Registry Provider";

    public DatagenDynamicRegistryProvider(final FabricPackOutput output, final CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(final HolderLookup.Provider provider, final Entries entries) {
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}


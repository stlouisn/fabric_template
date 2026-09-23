package com.mod_id.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public final class DatagenLanguageProvider extends FabricLanguageProvider {

    private static final String PROVIDER_NAME = "Language Provider";

    public DatagenLanguageProvider(final FabricPackOutput output, final CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateTranslations(final HolderLookup.Provider registries, final TranslationBuilder builder) {
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}

package dev.mod_id.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.CompletableFuture;

@NullMarked
public final class DatagenTrimMaterialProvider extends FabricDynamicRegistryProvider {

    private static final String PROVIDER_NAME = "Trim Material Provider";

    public DatagenTrimMaterialProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries) {
        entries.addAll(provider.lookupOrThrow(Registries.TRIM_MATERIAL));
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}

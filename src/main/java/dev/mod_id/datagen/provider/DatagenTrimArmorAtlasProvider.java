package dev.mod_id.datagen.provider;

import dev.mod_id.content.key.KeyTrimMaterials;
import dev.mod_id.datagen.builder.DatagenTrimArmorAtlasBuilder;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;

public final class DatagenTrimArmorAtlasProvider extends DatagenTrimArmorAtlasBuilder {

    public DatagenTrimArmorAtlasProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void generateTrimMaterials() {
        addTrimMaterial(KeyTrimMaterials.ROSE_QUARTZ);
    }
}

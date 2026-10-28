package dev.mod_id.datagen.provider;

import dev.mod_id.content.key.KeyTrimMaterials;
import dev.mod_id.datagen.builder.DatagenTrimItemsAtlasBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

public final class DatagenTrimItemsAtlasProvider extends DatagenTrimItemsAtlasBuilder {

    public DatagenTrimItemsAtlasProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    protected void generateTrimMaterials() {
        addTrimMaterial(KeyTrimMaterials.ROSE_QUARTZ);
    }
}

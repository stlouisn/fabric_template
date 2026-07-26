package com.mod_id.datagen;

import com.mod_id.datagen.provider.DatagenBlockTagProvider;
import com.mod_id.datagen.provider.DatagenItemTagProvider;
import com.mod_id.datagen.provider.DatagenLanguageProvider;
import com.mod_id.datagen.provider.DatagenModelProvider;
import com.mod_id.datagen.provider.DatagenPackMetadataGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class ModNameDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(final FabricDataGenerator fabricDataGenerator) {

        final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(DatagenPackMetadataGenerator::new);
        pack.addProvider(DatagenLanguageProvider::new);
        pack.addProvider(DatagenModelProvider::new);

        final DatagenBlockTagProvider blockTagProvider = pack.addProvider(DatagenBlockTagProvider::new);
        pack.addProvider((output, registries) -> new DatagenItemTagProvider(output, registries, blockTagProvider));
    }
}

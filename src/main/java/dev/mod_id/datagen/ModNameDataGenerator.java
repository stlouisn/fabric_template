package com.mod_id.datagen;

import dev.mod_id.content.registry.RegisterDamageTypes;
import dev.mod_id.datagen.adapter.DatagenRecipeAdapter;
import dev.mod_id.datagen.java.GenerateKeyBlockItems;
import dev.mod_id.datagen.java.GenerateRegisterBlockItems;
import dev.mod_id.datagen.java.GenerateRegisterCreativeModeTabs;
import dev.mod_id.datagen.java.GenerateRegisterFlammables;
import dev.mod_id.datagen.java.GenerateRegisterStrippables;
import dev.mod_id.datagen.provider.DatagenBlockLootSubProvider;
import dev.mod_id.datagen.provider.DatagenBlockTagProvider;
import dev.mod_id.datagen.provider.DatagenDamageTypeProvider;
import dev.mod_id.datagen.provider.DatagenDamageTypeTagProvider;
import dev.mod_id.datagen.provider.DatagenItemTagProvider;
import dev.mod_id.datagen.provider.DatagenLanguageProvider;
import dev.mod_id.datagen.provider.DatagenModelProvider;
import dev.mod_id.datagen.provider.DatagenPackMetadataProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ModNameDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(final FabricDataGenerator fabricDataGenerator) {

        GenerateKeyBlockItems.run();
        GenerateRegisterBlockItems.run();
        GenerateRegisterFlammables.run();
        GenerateRegisterStrippables.run();
        GenerateRegisterCreativeModeTabs.run();

        final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(DatagenPackMetadataGenerator::new);
        pack.addProvider(DatagenLanguageProvider::new);
        pack.addProvider(DatagenModelProvider::new);

        final DatagenBlockTagProvider blockTagProvider = pack.addProvider(DatagenBlockTagProvider::new);
        pack.addProvider((output, registries) -> new DatagenItemTagProvider(output, registries, blockTagProvider));

        pack.addProvider(DatagenBlockLootProvider::new);
        pack.addProvider(DatagenRecipeAdapter::new);
        pack.addProvider(CreateRecipeAdapter::new);

        pack.addProvider(DatagenDamageTypeProvider::new);
        pack.addProvider(DatagenDamageTypeTagProvider::new);

        @Override
        public void buildRegistry(RegistrySetBuilder registryBuilder) {
            registryBuilder.add(Registries.DAMAGE_TYPE, RegisterDamageTypes::bootstrap);
        }
    }
}

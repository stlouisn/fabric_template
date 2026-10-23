package dev.mod_id.datagen.provider;

import dev.mod_id.content.key.KeyTrimMaterials;
import dev.mod_id.content.registry.RegisterBlocks;
import dev.mod_id.content.registry.RegisterItems;
import dev.mod_id.content.tag.TagBlocks;
import dev.mod_id.content.tag.TagItems;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.Util;

public final class DatagenLanguageProvider extends FabricLanguageProvider {

    private static final String PROVIDER_NAME = "Language Provider";

    public DatagenLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registries, TranslationBuilder builder) {

        // Blocks
        builder.add(RegisterBlocks.CHARCOAL_BLOCK, "Block of Charcoal");

        // Items
        builder.add(RegisterItems.CHARCOAL_NUGGET, "Charcoal Nugget");

        // Trims
        builder.add(Util.makeDescriptionId("trim_material", KeyTrimMaterials.ROSE_QUARTZ.identifier()), "Rose Quartz Material");

        // Block Tags
        builder.add(TagBlocks.STORAGE_BLOCKS_CHARCOAL, "Charcoal Storage Blocks");

        // Item Tags
        builder.add(TagItems.CHARCOAL_NUGGET, "Charcoal Nuggets");
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}

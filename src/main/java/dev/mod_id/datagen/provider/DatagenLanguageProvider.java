package dev.ores.datagen.provider;

import dev.ores.content.key.KeyTrimMaterials;
import dev.ores.content.registry.RegisterBlocks;
import dev.ores.content.registry.RegisterItems;
import dev.ores.content.tag.TagBlocks;
import dev.ores.content.tag.TagItems;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.Util;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenLanguageProvider extends FabricLanguageProvider {

    private static final String PROVIDER_NAME = "Language Provider";

    public DatagenLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registries, TranslationBuilder builder) {

        // Blocks
        builder.add(RegisterBlocks.CHARCOAL_BLOCK, "Block of Charcoal");
        builder.add(RegisterBlocks.RAW_NETHERITE_BLOCK, "Block of Raw Netherite");
        builder.add(RegisterBlocks.ROSE_QUARTZ_BLOCK, "Block of Rose Quartz");
        builder.add(RegisterBlocks.BUDDING_ROSE_QUARTZ, "Budding Rose Quartz");
        builder.add(RegisterBlocks.ROSE_QUARTZ_CLUSTER, "Rose Quartz Cluster");
        builder.add(RegisterBlocks.LARGE_ROSE_QUARTZ_BUD, "Large Rose Quartz Bud");
        builder.add(RegisterBlocks.MEDIUM_ROSE_QUARTZ_BUD, "Medium Rose Quartz Bud");
        builder.add(RegisterBlocks.SMALL_ROSE_QUARTZ_BUD, "Small Rose Quartz Bud");
        builder.add(RegisterBlocks.POLISHED_ROSE_QUARTZ, "Polished Rose Quartz");

        // Items
        builder.add(RegisterItems.CHARCOAL_NUGGET, "Charcoal Nugget");
        builder.add(RegisterItems.COAL_NUGGET, "Coal Nugget");
        builder.add(RegisterItems.NETHERITE_NUGGET, "Netherite Nugget");
        builder.add(RegisterItems.RAW_NETHERITE, "Raw Netherite");
        builder.add(RegisterItems.ROSE_QUARTZ_SHARD, "Rose Quartz Shard");

        // Trims
        builder.add(Util.makeDescriptionId("trim_material", KeyTrimMaterials.ROSE_QUARTZ.identifier()), "Rose Quartz Material");

        // Block Tags
        builder.add(TagBlocks.STORAGE_BLOCKS_CHARCOAL, "Charcoal Storage Blocks");
        builder.add(TagBlocks.STORAGE_BLOCKS_RAW_NETHERITE, "Raw Netherite Storage Blocks");

        // Item Tags
        builder.add(TagItems.CHARCOAL_NUGGET, "Charcoal Nuggets");
        builder.add(TagItems.COAL_NUGGET, "Coal Nuggets");
        builder.add(TagItems.NETHERITE_NUGGET, "Netherite Nuggets");
        builder.add(TagItems.NETHERITE_RAW_MATERIALS, "Raw Netherite Materials");
        builder.add(TagItems.ROSE_QUARTZ_GEMS, "Rose Quartz Gems");
        builder.add(TagItems.STORAGE_BLOCKS_CHARCOAL, "Charcoal Storage Blocks");
        builder.add(TagItems.STORAGE_BLOCKS_RAW_NETHERITE, "Raw Netherite Storage Blocks");
        builder.add(TagItems.COAL_NUGGETS, "Coal Nuggets");
        builder.add(TagItems.SMELTS_TO_COPPER_NUGGETS, "Smelts to Copper Nuggets");
        builder.add(TagItems.SMELTS_TO_GOLD_NUGGETS, "Smelts to Gold Nuggets");
        builder.add(TagItems.SMELTS_TO_IRON_NUGGETS, "Smelts to Iron Nuggets");
        builder.add(TagItems.SMELTS_TO_NETHERITE_NUGGETS, "Smelts to Netherite Nuggets");
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}

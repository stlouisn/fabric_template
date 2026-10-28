package dev.mod_id.datagen.provider;

import dev.crossing_rail.content.registry.RegisterBlocks;
import dev.crossing_rail.datagen.builder.DatagenLanguageBuilder;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;

public final class DatagenLanguageProvider extends DatagenLanguageBuilder {

    public DatagenLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registries, TranslationBuilder builder) {

        // Blocks
        builder.add(RegisterBlockItems.CHARCOAL_BLOCK, "Block of Charcoal");
        builder.add(RegisterBlockItems.RAW_NETHERITE_BLOCK, "Block of Raw Netherite");
        builder.add(RegisterBlockItems.ROSE_QUARTZ_BLOCK, "Block of Rose Quartz");
        builder.add(RegisterBlockItems.BUDDING_ROSE_QUARTZ, "Budding Rose Quartz");
        builder.add(RegisterBlockItems.ROSE_QUARTZ_CLUSTER, "Rose Quartz Cluster");
        builder.add(RegisterBlockItems.LARGE_ROSE_QUARTZ_BUD, "Large Rose Quartz Bud");
        builder.add(RegisterBlockItems.MEDIUM_ROSE_QUARTZ_BUD, "Medium Rose Quartz Bud");
        builder.add(RegisterBlockItems.SMALL_ROSE_QUARTZ_BUD, "Small Rose Quartz Bud");
        builder.add(RegisterBlockItems.POLISHED_ROSE_QUARTZ, "Polished Rose Quartz");

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

        // Messages
        builder.add("notification.friendlyfire.protected", "The %s is protected. Crouch to bypass.");

        // Config
        addConfig(builder, "title", MOD_NAME);
        addConfig(builder, "enablePetProtection", "Enable Pet Protection");
        addConfig(builder, "enablePetProtection.tooltip", "Prevent players and their pets from damaging friendly pets");
        addConfig(builder, "enableBabyProtection", "Enable Baby Protection");
        addConfig(builder, "enableBabyProtection.tooltip", "Prevent players and their pets from damaging baby animals");
        addConfig(builder, "displayHitWarning", "Display Hit Warning");
        addConfig(builder, "displayHitWarning.tooltip", "Show a warning when hitting protected entities");
    }
}

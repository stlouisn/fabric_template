package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterBlocks;
import dev.mod_id.content.registry.RegisterItems;
import dev.mod_id.datagen.builder.DatagenBlockLootBuilder;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;

public final class DatagenBlockLootProvider extends DatagenBlockLootBuilder {

    public DatagenBlockLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        createSimpleDrop(RegisterBlockItems.CHARCOAL_BLOCK);
        createSimpleDrop(RegisterBlockItems.RAW_NETHERITE_BLOCK);
        createSimpleDrop(RegisterBlockItems.ROSE_QUARTZ_BLOCK);
        createSimpleDrop(RegisterBlockItems.BUDDING_ROSE_QUARTZ);
        createGeodeClusterDrop(RegisterBlockItems.ROSE_QUARTZ_CLUSTER, RegisterItems.ROSE_QUARTZ_SHARD);
        createSilkTouchDrop(RegisterBlockItems.LARGE_ROSE_QUARTZ_BUD);
        createSilkTouchDrop(RegisterBlockItems.MEDIUM_ROSE_QUARTZ_BUD);
        createSilkTouchDrop(RegisterBlockItems.SMALL_ROSE_QUARTZ_BUD);
        createSimpleDrop(RegisterBlockItems.POLISHED_ROSE_QUARTZ);
        createRawOreDrop(Blocks.ANCIENT_DEBRIS, RegisterItems.RAW_NETHERITE);
    }
}

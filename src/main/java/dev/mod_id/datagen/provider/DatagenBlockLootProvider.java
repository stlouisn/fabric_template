package dev.mod_id.datagen.provider;

import java.util.concurrent.CompletableFuture;

import dev.mod_id.content.registry.RegisterBlocks;
import dev.mod_id.datagen.builder.DatagenBlockLootBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;

public final class DatagenBlockLootProvider extends DatagenBlockLootBuilder {

    public DatagenBlockLootProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        createSelfDrop(RegisterBlocks.CHARCOAL_BLOCK);
        createGeodeClusterDrop(RegisterBlocks.ROSE_QUARTZ_CLUSTER, RegisterItems.ROSE_QUARTZ_SHARD);
        createSilkTouchDrop(RegisterBlocks.SMALL_ROSE_QUARTZ_BUD);
        createRawOreDrop(Blocks.ANCIENT_DEBRIS, RegisterItems.RAW_NETHERITE);
    }
}

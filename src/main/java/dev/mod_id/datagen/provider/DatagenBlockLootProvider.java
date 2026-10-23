package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterBlocks;
import dev.mod_id.content.registry.RegisterItems;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenBlockLootProvider extends DatagenBlockLootHelper {

    public DatagenBlockLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
        createSelfDrop(RegisterBlocks.CHARCOAL_BLOCK);
        createSelfDrop(RegisterBlocks.RAW_NETHERITE_BLOCK);
        createSelfDrop(RegisterBlocks.ROSE_QUARTZ_BLOCK);
        createSelfDrop(RegisterBlocks.BUDDING_ROSE_QUARTZ);
        createGeodeClusterDrop(RegisterBlocks.ROSE_QUARTZ_CLUSTER, RegisterItems.ROSE_QUARTZ_SHARD);
        createSilkTouchDrop(RegisterBlocks.LARGE_ROSE_QUARTZ_BUD);
        createSilkTouchDrop(RegisterBlocks.MEDIUM_ROSE_QUARTZ_BUD);
        createSilkTouchDrop(RegisterBlocks.SMALL_ROSE_QUARTZ_BUD);
        createSelfDrop(RegisterBlocks.POLISHED_ROSE_QUARTZ);
        createRawOreDrop(Blocks.ANCIENT_DEBRIS, RegisterItems.RAW_NETHERITE);
    }
}

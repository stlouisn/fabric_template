package dev.mod_id.datagen.model;

import java.util.concurrent.CompletableFuture;
import net.minecraft.data.CachedOutput;
import net.minecraft.world.level.block.Block;

public interface ModelWriter {
    CompletableFuture<?> write(CachedOutput cache, Block block);
}

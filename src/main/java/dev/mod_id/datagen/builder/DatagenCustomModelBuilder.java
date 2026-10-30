package dev.mod_id.datagen.builder;

import dev.mod_id.datagen.model.ModelWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.world.level.block.Block;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenCustomModelBuilder implements DataProvider {

    private static final List<ModelEntry> entries = new ArrayList<>();

    private record ModelEntry(Block block, ModelWriter writer) {
    }

    protected void add(Block block, ModelWriter writer) {
        entries.add(new ModelEntry(block, writer));
    }

    @Override
    public final CompletableFuture<?> run(CachedOutput cache) {
        CompletableFuture<?>[] writes = entries.stream().map(entry -> entry.writer().write(cache, entry.block())).toArray(CompletableFuture[]::new);
        return CompletableFuture.allOf(writes);
    }

    @Override
    public String getName() {
        return "Custom Model Provider";
    }
}

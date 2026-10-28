package dev.mod_id.datagen.builder;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenBlockTagBuilder extends FabricTagsProvider.BlockTagsProvider {

    public DatagenBlockTagBuilder(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    public String getName() {
        return "Block Tags Provider";
    }
}

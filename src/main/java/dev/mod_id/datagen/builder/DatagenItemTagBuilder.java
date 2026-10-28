package dev.mod_id.datagen.builder;

import dev.mod_id.datagen.provider.DatagenBlockTagProvider;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenItemTagBuilder extends FabricTagsProvider.ItemTagsProvider {

    public DatagenItemTagBuilder(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, DatagenBlockTagBuilder blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    public String getName() {
        return "Item Tags Provider";
    }
}

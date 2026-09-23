package dev.turtle_armour.datagen.provider;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

    private static final String PROVIDER_NAME = "Block Tags Provider";

    public DatagenBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}

package dev.mod_id.datagen.provider;

import dev.mod_id.content.key.KeyItems;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

public final class DatagenItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    private static final String PROVIDER_NAME = "Item Tags Provider";

    public DatagenItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, DatagenBlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        copy(BlockTags.RAILS, ItemTags.RAILS);

        builder(ItemTags.CHEST_ARMOR).add(KeyItems.TURTLE_CHESTPLATE);
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}

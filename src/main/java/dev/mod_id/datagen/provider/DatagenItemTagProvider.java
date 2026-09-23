package dev.turtle_armour.datagen.provider;

import dev.turtle_armour.content.key.KeyItems;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    private static final String PROVIDER_NAME = "Item Tags Provider";

    public DatagenItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, DatagenBlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        builder(ItemTags.FOOT_ARMOR).add(KeyItems.TURTLE_BOOTS);

        builder(ItemTags.CHEST_ARMOR).add(KeyItems.TURTLE_CHESTPLATE);

        builder(ItemTags.LEG_ARMOR).add(KeyItems.TURTLE_LEGGINGS);
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}

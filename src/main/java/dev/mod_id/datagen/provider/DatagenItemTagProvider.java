package dev.mod_id.datagen.provider;

import dev.mod_id.content.key.KeyItems;
import dev.mod_id.datagen.builder.DatagenItemTagBuilder;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

public final class DatagenItemTagProvider extends DatagenItemTagBuilder {

    public DatagenItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, DatagenBlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        copy(ConventionalBlockTags.BUDDING_BLOCKS, ConventionalItemTags.BUDDING_BLOCKS);

        copy(ConventionalBlockTags.BUDS, ConventionalItemTags.BUDS);

        copy(ConventionalBlockTags.CLUSTERS, ConventionalItemTags.CLUSTERS);

        copy(TagBlocks.STORAGE_BLOCKS_CHARCOAL, TagItems.STORAGE_BLOCKS_CHARCOAL);

        copy(TagBlocks.STORAGE_BLOCKS_RAW_NETHERITE, TagItems.STORAGE_BLOCKS_RAW_NETHERITE);

        copy(ConventionalBlockTags.STORAGE_BLOCKS, ConventionalItemTags.STORAGE_BLOCKS);

        builder(TagItems.ROSE_QUARTZ_GEMS).add(KeyItems.ROSE_QUARTZ_SHARD);

        builder(ConventionalItemTags.GEMS).addTag(TagItems.ROSE_QUARTZ_GEMS);

        builder(TagItems.CHARCOAL_NUGGET).add(KeyItems.CHARCOAL_NUGGET);

        builder(TagItems.COAL_NUGGET).add(KeyItems.COAL_NUGGET);

        builder(TagItems.NETHERITE_NUGGET).add(KeyItems.NETHERITE_NUGGET);

        builder(ItemTags.METAL_NUGGETS).add(KeyItems.NETHERITE_NUGGET);

        builder(ConventionalItemTags.NUGGETS)
            .addTag(TagItems.CHARCOAL_NUGGET)
            .addTag(TagItems.COAL_NUGGET)
            .addTag(TagItems.NETHERITE_NUGGET);

        builder(TagItems.COAL_NUGGETS)
            .addTag(TagItems.COAL_NUGGET)
            .addTag(TagItems.CHARCOAL_NUGGET);

        builder(TagItems.NETHERITE_RAW_MATERIALS).add(KeyItems.RAW_NETHERITE);

        builder(ConventionalItemTags.RAW_MATERIALS).addTag(TagItems.NETHERITE_RAW_MATERIALS);

        builder(ItemTags.TRIM_MATERIALS).add(KeyItems.ROSE_QUARTZ_SHARD);

        builder(ItemTags.SULFUR_CUBE_SWALLOWABLE).add(
            KeyBlockItems.CHARCOAL_BLOCK,
            KeyBlockItems.ROSE_QUARTZ_BLOCK,
            KeyBlockItems.RAW_NETHERITE_BLOCK,
            KeyBlockItems.POLISHED_ROSE_QUARTZ);

        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_REGULAR).add(KeyBlockItems.CHARCOAL_BLOCK);

        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY).add(
            KeyBlockItems.ROSE_QUARTZ_BLOCK,
            KeyBlockItems.POLISHED_ROSE_QUARTZ);

        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_FLAT).add(KeyBlockItems.RAW_NETHERITE_BLOCK);
    }
}

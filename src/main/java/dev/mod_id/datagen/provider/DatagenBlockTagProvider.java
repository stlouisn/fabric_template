package dev.mod_id.datagen.provider;

import dev.mod_id.datagen.builder.DatagenBlockTagBuilder;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;

public final class DatagenBlockTagProvider extends DatagenBlockTagBuilder {


    public DatagenBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        builder(ConventionalBlockTags.BUDDING_BLOCKS).add(KeyBlockItems.BUDDING_ROSE_QUARTZ);

        builder(ConventionalBlockTags.BUDS).add(
                    KeyBlockItems.SMALL_ROSE_QUARTZ_BUD,
                    KeyBlockItems.MEDIUM_ROSE_QUARTZ_BUD,
                    KeyBlockItems.LARGE_ROSE_QUARTZ_BUD);

        builder(ConventionalBlockTags.CLUSTERS).add(KeyBlockItems.ROSE_QUARTZ_CLUSTER);

        builder(TagBlocks.STORAGE_BLOCKS_CHARCOAL).add(KeyBlockItems.CHARCOAL_BLOCK);

        builder(TagBlocks.STORAGE_BLOCKS_RAW_NETHERITE).add(KeyBlockItems.RAW_NETHERITE_BLOCK);

        builder(ConventionalBlockTags.STORAGE_BLOCKS)
                    .addTag(TagBlocks.STORAGE_BLOCKS_CHARCOAL)
                    .addTag(TagBlocks.STORAGE_BLOCKS_RAW_NETHERITE);

        builder(BlockTags.MINEABLE_WITH_PICKAXE).add(
                    KeyBlockItems.CHARCOAL_BLOCK,
                    KeyBlockItems.RAW_NETHERITE_BLOCK,
                    KeyBlockItems.ROSE_QUARTZ_BLOCK,
                    KeyBlockItems.BUDDING_ROSE_QUARTZ,
                    KeyBlockItems.SMALL_ROSE_QUARTZ_BUD,
                    KeyBlockItems.MEDIUM_ROSE_QUARTZ_BUD,
                    KeyBlockItems.LARGE_ROSE_QUARTZ_BUD,
                    KeyBlockItems.ROSE_QUARTZ_CLUSTER,
                    KeyBlockItems.POLISHED_ROSE_QUARTZ);

        builder(BlockTags.CRYSTAL_SOUND_BLOCKS).add(
                    KeyBlockItems.ROSE_QUARTZ_BLOCK,
                    KeyBlockItems.BUDDING_ROSE_QUARTZ,
                    KeyBlockItems.POLISHED_ROSE_QUARTZ);

        builder(BlockTags.VIBRATION_RESONATORS).add(
                    KeyBlockItems.ROSE_QUARTZ_BLOCK,
                    KeyBlockItems.POLISHED_ROSE_QUARTZ);

        builder(BlockTags.NEEDS_DIAMOND_TOOL).add(KeyBlockItems.RAW_NETHERITE_BLOCK);

        builder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(KeyBlockItems.SMALL_ROSE_QUARTZ_BUD);
    }

    @Override
    public String getName() {
        return "Block Tags Provider";
    }
}

package com.cypresswood.datagen.builder;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenItemTagBuilder extends FabricTagsProvider.ItemTagsProvider {

    private static final Set<TagKey<Block>> IGNORED_BLOCK_TAGS = Set.of(
        BlockTags.CEILING_HANGING_SIGNS,
        BlockTags.CRYSTAL_SOUND_BLOCKS,
        BlockTags.FLOWER_POTS,
        BlockTags.INSIDE_STEP_SOUND_BLOCKS,
        BlockTags.MINEABLE_WITH_AXE,
        BlockTags.MINEABLE_WITH_HOE,
        BlockTags.MINEABLE_WITH_PICKAXE,
        BlockTags.MINEABLE_WITH_SHOVEL,
        BlockTags.NEEDS_DIAMOND_TOOL,
        BlockTags.NEEDS_IRON_TOOL,
        BlockTags.NEEDS_STONE_TOOL,
        BlockTags.OVERWORLD_NATURAL_LOGS,
        BlockTags.STANDING_SIGNS,
        BlockTags.VIBRATION_RESONATORS,
        BlockTags.WALL_HANGING_SIGNS,
        BlockTags.WALL_SIGNS
    );

    private final DatagenBlockTagBuilder blockTagProvider;

    protected DatagenItemTagBuilder(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, DatagenBlockTagBuilder blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
        this.blockTagProvider = blockTagProvider;
    }

    protected abstract void addItemTags(HolderLookup.Provider provider);

    @Override
    protected final void addTags(HolderLookup.Provider provider) {
        for (TagKey<Block> blockTag : blockTagProvider.declaredTags()) {
            if (IGNORED_BLOCK_TAGS.contains(blockTag)) {
                continue;
            }
            final TagKey<Item> itemTag = TagKey.create(Registries.ITEM, blockTag.location());
            copy(blockTag, itemTag);
        }
        addItemTags(provider);
    }

    @Override
    protected CompletableFuture<HolderLookup.Provider> createContentsProvider() {
        return blockTagProvider.contentsGetter().thenCompose(_ -> super.createContentsProvider());
    }

    @Override
    public String getName() {
        return "Item Tags Provider";
    }
}

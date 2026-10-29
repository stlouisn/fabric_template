package dev.mod_id.datagen.builder;

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
        BlockTags.CRYSTAL_SOUND_BLOCKS,
        BlockTags.INSIDE_STEP_SOUND_BLOCKS,
        BlockTags.MINEABLE_WITH_AXE,
        BlockTags.MINEABLE_WITH_HOE,
        BlockTags.MINEABLE_WITH_PICKAXE,
        BlockTags.MINEABLE_WITH_SHOVEL,
        BlockTags.NEEDS_DIAMOND_TOOL,
        BlockTags.NEEDS_IRON_TOOL,
        BlockTags.NEEDS_STONE_TOOL,
        BlockTags.VIBRATION_RESONATORS
    );

    private final DatagenBlockTagBuilder blockTagProvider;

    public DatagenItemTagBuilder(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, DatagenBlockTagBuilder blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
        this.blockTagProvider = blockTagProvider;
    }

    @Override
    protected CompletableFuture<HolderLookup.Provider> createContentsProvider() {
        return blockTagProvider.contentsGetter().thenCompose(_ -> super.createContentsProvider());
    }

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

    protected abstract void addItemTags(HolderLookup.Provider provider);

    @Override
    public String getName() {
        return "Item Tags Provider";
    }
}

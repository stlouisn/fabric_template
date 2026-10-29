package dev.mod_id.datagen.builder;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenBlockTagBuilder extends FabricTagsProvider.BlockTagsProvider {

    private static final Set<TagKey<Block>> declaredTags = new LinkedHashSet<>();

    protected DatagenBlockTagBuilder(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    public Set<TagKey<Block>> declaredTags() {
        return Collections.unmodifiableSet(declaredTags);
    }

    @Override
    protected TagBuilder getOrCreateRawBuilder(TagKey<Block> tag) {
        declaredTags.add(tag);
        return super.getOrCreateRawBuilder(tag);
    }

    @Override
    public String getName() {
        return "Block Tags Provider";
    }
}

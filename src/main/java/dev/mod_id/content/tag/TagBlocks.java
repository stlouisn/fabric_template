package dev.mod_id.content.tag;

import static dev.mod_id.Constants.resourceLocation;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class TagBlocks {

    // Conventional Tags
    public static final TagKey<Block> STORAGE_BLOCKS_WITHERED_BONE_MEAL = create("c", "storage_blocks/withered_bone_meal");

    /**
     * Creates a block tag key for the given path.
     */
    private static TagKey<Block> create(String path) {
        return TagKey.create(Registries.BLOCK, resourceLocation(path));
    }

    /**
     * Creates a block tag key for the given namespace and path.
     */
    private static TagKey<Block> create(String namespace, String path) {
        return TagKey.create(Registries.BLOCK, resourceLocation(namespace, path));
    }
}

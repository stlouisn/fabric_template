package dev.mod_id.content.key;

import static dev.mod_id.api.ResourceLocation.resourceLocation;

import net.minecraft.references.BlockItemId;

public final class KeyBlockItems {

    public static final BlockItemId WITHERED_BONE_BLOCK = create("withered_bone_block");

    /**
     * Creates a BlockItemId using the mod's resource location for the given path.
     */
    private static BlockItemId create(String path) {
        return BlockItemId.create(resourceLocation(path), resourceLocation(path));
    }

    /**
     * Creates a BlockItemId using the mod's resource location for the given namespace and path.
     */
    private static BlockItemId create(String namespace, String path) {
        return BlockItemId.create(resourceLocation(path), resourceLocation(namespace, path));
    }
}

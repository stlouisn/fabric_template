package dev.mod_id.content.tag;

import static dev.mod_id.api.ResourceLocation.resourceLocation;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class TagItems {

    // Conventional Tags
    public static final TagKey<Item> STORAGE_BLOCKS_WITHERED_BONE_MEAL = create("c", "storage_blocks/withered_bone_meal");
    public static final TagKey<Item> WITHERED_FERTILIZERS = create("c", "withered_fertilizers");

    /**
     * Creates an item tag key for the given path.
     */
    private static TagKey<Item> create(String path) {
        return TagKey.create(Registries.ITEM, resourceLocation(path));
    }

    /**
     * Creates an item tag key for the given namespace and path.
     */
    private static TagKey<Item> create(String namespace, String path) {
        return TagKey.create(Registries.ITEM, resourceLocation(namespace, path));
    }
}

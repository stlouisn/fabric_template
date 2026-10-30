package dev.mod_id.datagen.util;

import static dev.mod_id.Constants.MOD_ID;
import static dev.mod_id.api.ResourceLocation.resourceLocation;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class TagUtils {

    /**
     * Returns the identifier of the given tag.
     */
    public static Identifier getKey(TagKey<?> tag) {
        return tag.location();
    }

    /**
     * Returns the namespace of the given tag.
     */
    public static String getNamespace(TagKey<?> tag) {
        return tag.location().getNamespace();
    }

    /**
     * Returns the path of the given tag.
     */
    public static String getPath(TagKey<?> tag) {
        return tag.location().getPath();
    }

    /**
     * Returns the item tag using the default mod ID and the specified path.
     */
    public static TagKey<Item> getItemTag(String path) {
        return getItemTag(MOD_ID, path);
    }

    /**
     * Returns the item tag using the specified namespace and path.
     */
    public static TagKey<Item> getItemTag(String namespace, String path) {
        return getItemTag(resourceLocation(namespace, path));
    }

    /**
     * Returns the item tag using the specified identifier.
     */
    public static TagKey<Item> getItemTag(Identifier key) {
        return TagKey.create(Registries.ITEM, key);
    }

    /**
     * Returns the block tag using the default mod ID and the specified path.
     */
    public static TagKey<Block> getBlockTag(String path) {
        return getBlockTag(MOD_ID, path);
    }

    /**
     * Returns the block tag using the specified namespace and path.
     */
    public static TagKey<Block> getBlockTag(String namespace, String path) {
        return getBlockTag(resourceLocation(namespace, path));
    }

    /**
     * Returns the block tag using the specified identifier.
     */
    public static TagKey<Block> getBlockTag(Identifier key) {
        return TagKey.create(Registries.BLOCK, key);
    }
}

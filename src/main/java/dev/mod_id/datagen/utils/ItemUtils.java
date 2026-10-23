package dev.mod_id.datagen.utils;

import static dev.mod_id.util.Constants.MOD_ID;
import static dev.mod_id.util.Constants.resourceLocation;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("unused")
public final class ItemUtils {

    /**
     * Returns the identifier of the given item.
     */
    public static Identifier getKey(@NonNull ItemLike item) {
        return getKey(item.asItem());
    }

    /**
     * Returns the identifier of the given item.
     */
    public static Identifier getKey(@NonNull Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    /**
     * Returns the namespace of the given item.
     */
    public static String getNamespace(@NonNull ItemLike item) {
        return getNamespace(item.asItem());
    }

    /**
     * Returns the namespace of the given item.
     */
    public static String getNamespace(@NonNull Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getNamespace();
    }

    /**
     * Returns the resource key of the given item.
     */
    @Nullable
    public static ResourceKey<Item> getResourceKey(@NonNull ItemLike item) {
        return getResourceKey(item.asItem());
    }

    /**
     * Returns the resource key of the given item.
     */
    @Nullable
    public static ResourceKey<Item> getResourceKey(@NonNull Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).orElse(null);
    }

    /**
     * Returns the path of the given item.
     */
    public static String getPath(@NonNull ItemLike item) {
        return getPath(item.asItem());
    }

    /**
     * Returns the path of the given item.
     */
    public static String getPath(@NonNull Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
    }

    /**
     * Returns the item using the default mod ID and the specified path.
     */
    @Nullable
    public static Item getItem(@NonNull String path) {
        return getItem(MOD_ID, path);
    }

    /**
     * Returns the item using the specified namespace and path.
     */
    @Nullable
    public static Item getItem(@NonNull String namespace, @NonNull String path) {
        return getItem(resourceLocation(namespace, path));
    }

    /**
     * Returns the item using the specified identifier.
     */
    @Nullable
    public static Item getItem(Identifier key) {
        return BuiltInRegistries.ITEM.get(key).map(Holder::value).orElse(null);
    }

    /**
     * Checks whether an item exists in the registry using the default mod ID and the specified path.
     */
    public static boolean doesExist(@NonNull String path) {
        return doesExist(resourceLocation(MOD_ID, path));
    }

    /**
     * Checks whether an item exists in the registry using the specified namespace and path.
     */
    public static boolean doesExist(@NonNull String namespace, @NonNull String path) {
        return doesExist(resourceLocation(namespace, path));
    }

    /**
     * Checks whether an item exists in the registry using the specified identifier.
     */
    public static boolean doesExist(@NonNull Identifier key) {
        return BuiltInRegistries.ITEM.containsKey(key);
    }
}

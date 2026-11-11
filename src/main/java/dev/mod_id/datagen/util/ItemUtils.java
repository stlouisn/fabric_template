package dev.mod_id.datagen.util;

import static dev.mod_id.Constants.MOD_ID;
import static dev.mod_id.api.ResourceLocation.resourceLocation;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class ItemUtils {

    /**
     * Returns the namespace of the given item.
     */
    public static String getNamespace(ItemLike itemLike) {
        return getIdentifier(itemLike).getNamespace();
    }

    /**
     * Returns the namespace of the given item.
     */
    public static String getNamespace(Item item) {
        return getIdentifier(item).getNamespace();
    }

    /**
     * Returns the path of the given item.
     */
    public static String getPath(ItemLike itemLike) {
        return getIdentifier(itemLike).getPath();
    }

    /**
     * Returns the path of the given item.
     */
    public static String getPath(Item item) {
        return getIdentifier(item).getPath();
    }

    /**
     * Returns the identifier of the given item.
     */
    public static Identifier getIdentifier(ItemLike itemLike) {
        return getIdentifier(itemLike.asItem());
    }

    /**
     * Returns the identifier of the given item.
     */
    public static Identifier getIdentifier(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    /**
     * Returns the resource key of the given item.
     */
    @Nullable
    public static ResourceKey<Item> getResourceKey(ItemLike itemLike) {
        return getResourceKey(itemLike.asItem());
    }

    /**
     * Returns the resource key of the given item.
     */
    @Nullable
    public static ResourceKey<Item> getResourceKey(Item item) {
        return BuiltInRegistries.ITEM.getResourceKey(item).orElse(null);
    }

    /**
     * Returns the resource key of the given item identifier.
     */
    @Nullable
    public static ResourceKey<Item> getResourceKey(Identifier id) {
        return BuiltInRegistries.ITEM.get(id).map(Holder.Reference::key).orElse(null);
    }

    /**
     * Returns the item using the default mod ID and the specified path.
     */
    @Nullable
    public static Item getItem(String path) {
        return getItem(MOD_ID, path);
    }

    /**
     * Returns the item using the specified namespace and path.
     */
    @Nullable
    public static Item getItem(String namespace, String path) {
        return getItem(resourceLocation(namespace, path));
    }

    /**
     * Returns the item using the specified identifier.
     */
    @Nullable
    public static Item getItem(Identifier id) {
        return BuiltInRegistries.ITEM.get(id).map(Holder::value).orElse(null);
    }
}

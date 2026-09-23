package dev.turtle_armour.content.key;

import static dev.turtle_armour.util.Constants.resourceLocation;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public final class KeyItems {

    public static final ResourceKey<Item> TURTLE_CHESTPLATE = create("turtle_chestplate");
    public static final ResourceKey<Item> TURTLE_LEGGINGS = create("turtle_leggings");
    public static final ResourceKey<Item> TURTLE_BOOTS = create("turtle_boots");

    /**
     * Creates an Item ResourceKey using the mod's resource location for the given path.
     */
    private static ResourceKey<Item> create(String path) {
        return ResourceKey.create(Registries.ITEM, resourceLocation(path));
    }

    /**
     * Creates an Item ResourceKey for the given namespace and path.
     */
    @SuppressWarnings("unused")
    private static ResourceKey<Item> create(String namespace, String path) {
        return ResourceKey.create(Registries.ITEM, resourceLocation(namespace, path));
    }
}

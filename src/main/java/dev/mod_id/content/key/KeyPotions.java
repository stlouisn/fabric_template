package dev.mod_id.content.key;

import static dev.mod_id.Constants.resourceLocation;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.alchemy.Potion;

public final class KeyPotions {

    public static final ResourceKey<Potion> DECAY = create("potion_of_decay");

    public static final ResourceKey<Potion> LONG_DECAY = create("long_potion_of_decay");

    public static final ResourceKey<Potion> STRONG_DECAY = create("strong_potion_of_decay");

    /**
     * Creates a Potion ResourceKey using the mod's resource location for the given path.
     */
    private static ResourceKey<Potion> create(String path) {
        return ResourceKey.create(Registries.POTION, resourceLocation(path));
    }

    /**
     * Creates a Potion ResourceKey using the mod's resource location for the given namespace and path.
     */
    private static ResourceKey<Potion> create(String namespace, String path) {
        return ResourceKey.create(Registries.POTION, resourceLocation(namespace, path));
    }
}

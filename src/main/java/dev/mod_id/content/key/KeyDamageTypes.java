package dev.mod_id.content.key;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import static dev.mod_id.Constants.resourceLocation;

public class KeyDamageTypes {

    public static final ResourceKey<DamageType> PALISADE = create("palisade");

    /**
     * Creates a DamageType ResourceKey using the mod's resource location for the given path.
     */
    private static ResourceKey<DamageType> create(String path) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, resourceLocation(path));
    }

    /**
     * Creates a DamageType ResourceKey using the mod's resource location for the given namespace and path.
     */
    private static ResourceKey<DamageType> create(String namespace, String path) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, resourceLocation(namespace, path));
    }
}

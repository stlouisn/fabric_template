package dev.mod_id.content.key;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;

import static dev.mod_id.Constants.resourceLocation;

public class KeySoundEvents {

    public static final ResourceKey<SoundEvent> PALISADE_CARVE = create("item.palisade.carve");

    /**
     * Creates a SoundEvent ResourceKey using the mod's resource location for the given path.
     */
    private static ResourceKey<SoundEvent> create(String path) {
        return ResourceKey.create(Registries.SOUND_EVENT, resourceLocation(path));
    }

    /**
     * Creates a SoundEvent ResourceKey using the mod's resource location for the given namespace and path.
     */
    private static ResourceKey<SoundEvent> create(String namespace, String path) {
        return ResourceKey.create(Registries.SOUND_EVENT, resourceLocation(namespace, path));
    }
}

package dev.mod_id.content.registry;

import dev.mod_id.content.key.KeySoundEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;

public final class RegisterSoundEvents {

    public static final SoundEvent PALISADE_CARVE = register(KeySoundEvents.PALISADE_CARVE);

    public static void init() {
    }

    /**
     * Creates and registers a variable-range sound event for the given key.
     *
     * @param key the key to register the sound event under
     * @return the registered sound event
     */
    private static SoundEvent register(ResourceKey<SoundEvent> key) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, key, SoundEvent.createVariableRangeEvent(key.identifier()));
    }
}

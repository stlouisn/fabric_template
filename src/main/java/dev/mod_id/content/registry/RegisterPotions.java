package dev.mod_id.content.registry;

import dev.mod_id.content.key.KeyPotions;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

public class RegisterPotions {

    private static final int DECAY_TICK_DURATION = 24 * 20;
    private static final int LONG_DECAY_TICK_DURATION = 40 * 20;
    private static final int STRONG_DECAY_TICK_DURATION = 16 * 20;

    public static final Holder<Potion> DECAY = register(KeyPotions.DECAY, new Potion("decay", new MobEffectInstance(MobEffects.WITHER, DECAY_TICK_DURATION)));
    public static final Holder<Potion> LONG_DECAY = register(KeyPotions.LONG_DECAY, new Potion("decay", new MobEffectInstance(MobEffects.WITHER, LONG_DECAY_TICK_DURATION)));
    public static final Holder<Potion> STRONG_DECAY = register(KeyPotions.STRONG_DECAY, new Potion("decay", new MobEffectInstance(MobEffects.WITHER, STRONG_DECAY_TICK_DURATION, 0)));

    public static void init() {
    }

    private static Holder<Potion> register(ResourceKey<Potion> key, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, key, potion);
    }
}

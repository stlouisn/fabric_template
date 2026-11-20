package dev.mod_id.content.registry;

import dev.mod_id.content.key.KeyDamageTypes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public final class RegisterDamageTypes {

    public static void bootstrap(BootstrapContext<DamageType> context) {
        final DamageType damageType = new DamageType(KeyDamageTypes.SPIKED_PALISADE.identifier().toLanguageKey(), DamageScaling.ALWAYS, 0.1F);
        context.register(KeyDamageTypes.SPIKED_PALISADE, damageType);
    }
}

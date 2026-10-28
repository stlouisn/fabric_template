package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterItems;
import dev.mod_id.datagen.builder.VanillaLanguageBuilder;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;

public final class VanillaLanguageProvider extends VanillaLanguageBuilder {

    public VanillaLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registries, TranslationBuilder builder) {

        // Advancements
        builder.add("advancements.nether.obtain_ancient_debris.description", "Obtain Raw Netherite");

        // Potion Effects
        addPotionTranslations(builder, RegisterPotions.DECAY, "Decay");
    }
}

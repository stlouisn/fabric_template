package dev.mod_id.datagen.provider;

import static dev.mod_id.util.IdentifierUtils.resourceLocation;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

public final class VanillaLanguageProvider extends FabricLanguageProvider {

    private static final String PROVIDER_NAME = "Vanilla Language Provider";

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

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    protected Path getLangFilePath(String code) {
        return packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "lang").json(resourceLocation("minecraft", code));
    }

    /**
     * Generates translations for Potion, Splash Potion, Lingering Potion, and Tipped Arrow using the base name defined in
     * the Potion instance.
     */
    @SuppressWarnings("SameParameterValue")
    private static void addPotionTranslations(TranslationBuilder builder, Holder<Potion> potionHolder, String displayName) {
        final String baseName = potionHolder.value().name();
        builder.add("item.minecraft.potion.effect." + baseName, "Potion of " + displayName);
        builder.add("item.minecraft.splash_potion.effect." + baseName, "Splash Potion of " + displayName);
        builder.add("item.minecraft.lingering_potion.effect." + baseName, "Lingering Potion of " + displayName);
        builder.add("item.minecraft.tipped_arrow.effect." + baseName, "Arrow of " + displayName);
    }
}

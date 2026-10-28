package dev.mod_id.datagen.builder;

import static dev.mod_id.util.IdentifierUtils.resourceLocation;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.alchemy.Potion;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class VanillaLanguageBuilder extends FabricLanguageProvider {

    public VanillaLanguageBuilder(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected Path getLangFilePath(String code) {
        return packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "lang").json(resourceLocation("minecraft", code));
    }

    @Override
    public String getName() {
        return "Vanilla Language Provider";
    }

    /**
     * Generates translations for Potion, Splash Potion, Lingering Potion, and Tipped Arrow.
     */
    public static void addPotionTranslations(TranslationBuilder builder, Holder<Potion> potionHolder, String displayName) {
        final String baseName = potionHolder.value().name();
        builder.add("item.minecraft.potion.effect." + baseName, "Potion of " + displayName);
        builder.add("item.minecraft.splash_potion.effect." + baseName, "Splash Potion of " + displayName);
        builder.add("item.minecraft.lingering_potion.effect." + baseName, "Lingering Potion of " + displayName);
        builder.add("item.minecraft.tipped_arrow.effect." + baseName, "Arrow of " + displayName);
    }
}

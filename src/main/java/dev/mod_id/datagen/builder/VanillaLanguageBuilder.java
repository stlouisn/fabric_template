package dev.mod_id.datagen.builder;

import static dev.mod_id.api.ResourceLocation.resourceLocation;

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

    protected VanillaLanguageBuilder(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    /**
     * Adds translations for potion, splash potion, lingering potion, and tipped arrow variants associated with the given
     * potion effect.
     *
     * @param builder the builder to add the translations to
     * @param potionHolder the potion whose translation-key name is used to build the keys
     * @param displayName the potion effect's display name
     */
    protected static void addPotionTranslations(TranslationBuilder builder, Holder<Potion> potionHolder, String displayName) {
        final String baseName = potionHolder.value().name();
        builder.add("item.minecraft.potion.effect." + baseName, "Potion of " + displayName);
        builder.add("item.minecraft.splash_potion.effect." + baseName, "Splash Potion of " + displayName);
        builder.add("item.minecraft.lingering_potion.effect." + baseName, "Lingering Potion of " + displayName);
        builder.add("item.minecraft.tipped_arrow.effect." + baseName, "Arrow of " + displayName);
    }

    @Override
    protected Path getLangFilePath(String code) {
        return packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "lang").json(resourceLocation("minecraft", code));
    }

    @Override
    public String getName() {
        return "Vanilla Language Provider";
    }
}

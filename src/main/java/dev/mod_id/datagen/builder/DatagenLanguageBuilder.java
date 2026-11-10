package dev.better_withered_mobs.datagen.builder;

import static dev.better_withered_mobs.Constants.MOD_ID;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.alchemy.Potion;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenLanguageBuilder extends FabricLanguageProvider {

    protected DatagenLanguageBuilder(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    /**
     * Adds a translation for a MidnightConfig option using this mod's translation-key prefix.
     *
     * @param builder the builder to add the translation to
     * @param translationKey the config option's translation-key suffix
     * @param value the translated text
     */
    protected static void midnightConfig(TranslationBuilder builder, String translationKey, String value) {
        builder.add(MOD_ID + ".midnightconfig." + translationKey, value);
    }

    /**
     * Adds a translation for a MidnightConfig option using this mod's translation-key prefix.
     *
     * @param builder the builder to add the translation to
     * @param translationKey the config option's translation-key suffix
     * @param value the translated text
     * @param tooltip the translated tooltip
     */
    protected static void midnightConfig(TranslationBuilder builder, String translationKey, String value, String tooltip) {
        builder.add(MOD_ID + ".midnightconfig." + translationKey, value);
        builder.add(MOD_ID + ".midnightconfig." + translationKey + ".tooltip", tooltip);
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
    public String getName() {
        return "Language Provider";
    }
}

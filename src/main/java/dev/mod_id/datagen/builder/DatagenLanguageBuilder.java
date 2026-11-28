package dev.mod_id.datagen.builder;

import static dev.mod_id.Constants.MOD_ID;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;

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
        midnightConfig(builder, translationKey, value);
        builder.add(MOD_ID + ".midnightconfig." + translationKey + ".tooltip", tooltip);
    }

    /**
     * Adds a translation for a custom option using this mod's translation-key prefix.
     *
     * @param builder the builder to add the translation to
     * @param translationKey the option's translation-key suffix
     * @param value the translated text
     */
    protected static void option(TranslationBuilder builder, String translationKey, String value) {
        builder.add(MOD_ID + ".option." + translationKey, value);
    }

    /**
     * Adds a translation for a custom option using this mod's translation-key prefix.
     *
     * @param builder the builder to add the translation to
     * @param translationKey the option's translation-key suffix
     * @param value the translated text
     * @param description the translated description
     */
    protected static void option(TranslationBuilder builder, String translationKey, String value, String description) {
        option(builder, translationKey, value);
        builder.add(MOD_ID + ".option." + translationKey + ".desc", description);
    }

    /**
     * Adds a colored translation for a custom option using this mod's translation-key prefix.
     *
     * @param builder the builder to add the translation to
     * @param translationKey the option's translation-key suffix
     * @param value the translated text
     * @param color the text color
     */
    protected static void option(TranslationBuilder builder, String translationKey, String value, ChatFormatting color) {
        builder.add(MOD_ID + ".option." + translationKey, color + value + ChatFormatting.RESET);
    }

    /**
     * Adds a translation for a custom option using this mod's translation-key prefix.
     *
     * @param builder the builder to add the translation to
     * @param translationKey the option's translation-key suffix
     * @param value the translated text
     * @param description the translated description
     * @param color the text color
     */
    protected static void option(TranslationBuilder builder, String translationKey, String value, String description, ChatFormatting color) {
        option(builder, translationKey, value, color);
        builder.add(MOD_ID + ".option." + translationKey + ".desc", description);
    }

    @Override
    public String getName() {
        return "Language Provider";
    }
}

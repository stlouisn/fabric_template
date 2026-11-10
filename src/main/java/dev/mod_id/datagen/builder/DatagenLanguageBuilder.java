package dev.mod_id.datagen.builder;

import static dev.mod_id.Constants.MOD_ID;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
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
        builder.add(MOD_ID + ".midnightconfig." + translationKey, value);
        builder.add(MOD_ID + ".midnightconfig." + translationKey + ".tooltip", tooltip);
    }

    @Override
    public String getName() {
        return "Language Provider";
    }
}

package dev.turtle_armour.datagen.provider;

import dev.turtle_armour.content.registry.RegisterItems;
import dev.turtle_armour.util.Constants;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class VanillaLanguageProvider extends FabricLanguageProvider {

    private static final String PROVIDER_NAME = "Vanilla Language Provider";

    public VanillaLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registries, TranslationBuilder builder) {

        builder.add(Items.TURTLE_HELMET, "Turtle Helmet");

        builder.add(RegisterItems.TURTLE_BOOTS, "Turtle Chestplate");

        builder.add(RegisterItems.TURTLE_CHESTPLATE, "Turtle Leggings");

        builder.add(RegisterItems.TURTLE_LEGGINGS, "Turtle Boots");
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    protected Path getLangFilePath(String code) {
        return packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "lang").json(Constants.resourceLocation("minecraft", code));
    }
}

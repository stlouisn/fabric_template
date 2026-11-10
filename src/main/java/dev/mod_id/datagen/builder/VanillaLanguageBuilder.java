package dev.mod_id.datagen.builder;

import static dev.mod_id.api.ResourceLocation.resourceLocation;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class VanillaLanguageBuilder extends FabricLanguageProvider {

    protected VanillaLanguageBuilder(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
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
}

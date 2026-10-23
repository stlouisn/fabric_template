package dev.mod_id.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;

import static dev.mod_id.util.Constants.resourceLocation;

public final class VanillaRecipeAdapter extends FabricRecipeProvider {

    private static final String PROVIDER_NAME = "Vanilla Recipe Provider";

    public VanillaRecipeAdapter(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new VanillaRecipeProvider(registries, output);
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public Identifier getRecipeIdentifier(Identifier identifier) {
        return resourceLocation("minecraft", identifier.getPath());
    }
}

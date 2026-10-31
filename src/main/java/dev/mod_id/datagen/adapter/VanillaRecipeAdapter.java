package dev.mod_id.datagen.adapter;

import static dev.mod_id.api.ResourceLocation.resourceLocation;

import dev.mod_id.datagen.provider.VanillaRecipeProvider;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;

public final class VanillaRecipeAdapter extends FabricRecipeProvider {

    public VanillaRecipeAdapter(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new VanillaRecipeProvider(registries, output, mods -> withConditions(output, ResourceConditions.allModsLoaded(mods.toArray(String[]::new))));
    }

    @Override
    public Identifier getRecipeIdentifier(Identifier identifier) {
        return resourceLocation("minecraft", identifier.getPath());
    }

    @Override
    public String getName() {
        return "Vanilla Recipe Provider";
    }
}

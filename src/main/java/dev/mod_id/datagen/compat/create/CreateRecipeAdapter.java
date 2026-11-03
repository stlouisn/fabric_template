package dev.mod_id.datagen.compat.create;

import static dev.mod_id.api.ResourceLocation.resourceLocation;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;

public final class CreateRecipeAdapter extends FabricRecipeProvider {

    public CreateRecipeAdapter(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new CreateRecipeProvider(registries, output, mods -> withConditions(output, ResourceConditions.allModsLoaded(mods.toArray(String[]::new))));
    }

    @Override
    public String getName() {
        return "Create Recipe Provider";
    }

    @Override
    public Identifier getRecipeIdentifier(Identifier identifier) {
        return resourceLocation("create", identifier.getPath());
    }
}

package dev.mod_id.datagen.adapter;

import dev.mod_id.datagen.provider.DatagenRecipeProvider;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

public final class DatagenRecipeAdapter extends FabricRecipeProvider {

    public DatagenRecipeAdapter(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new DatagenRecipeProvider(registries, output, mods -> withConditions(output, ResourceConditions.allModsLoaded(mods.toArray(String[]::new))));
    }

    @Override
    public String getName() {
        return "Recipe Provider";
    }
}

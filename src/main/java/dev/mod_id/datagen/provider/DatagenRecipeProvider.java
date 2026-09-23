package com.mod_id.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public final class DatagenRecipeProvider extends FabricRecipeProvider {

    private static final String PROVIDER_NAME = "Recipe Provider";

    public DatagenRecipeProvider(final FabricPackOutput output, final CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(final HolderLookup.Provider registries, final RecipeOutput recipeOutput) {
        return new RecipeProvider(registries, recipeOutput) {

            @Override
            public void buildRecipes() {

                final HolderGetter<Item> itemRegistry = registries.lookupOrThrow(Registries.ITEM);

            }
        };
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}

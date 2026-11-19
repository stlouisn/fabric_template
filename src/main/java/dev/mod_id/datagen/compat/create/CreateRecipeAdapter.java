package dev.mod_id.datagen.compat.create;

import static dev.mod_id.Constants.resourceLocation;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;

public final class CreateRecipeAdapter extends FabricRecipeProvider {

    public CreateRecipeAdapter(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new CreateRecipeProvider(recipes, advancements, (recipeOutput, mods) -> withConditions(recipeOutput, ResourceConditions.allModsLoaded(mods.toArray(String[]::new))));
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

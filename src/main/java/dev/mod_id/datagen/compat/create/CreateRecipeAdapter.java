package dev.mod_id.datagen.compat.create;

import static dev.mod_id.util.IdentifierUtils.resourceLocation;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class CreateRecipeAdapter extends FabricRecipeProvider {

    private static final String PROVIDER_NAME = "Create Recipe Provider";

    public CreateRecipeAdapter(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new CreateRecipeProvider(registries, output, mods -> withConditions(output, ResourceConditions.allModsLoaded(mods.toArray(String[]::new))));
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public Identifier getRecipeIdentifier(Identifier identifier) {
        return resourceLocation("create", identifier.getPath());
    }
}

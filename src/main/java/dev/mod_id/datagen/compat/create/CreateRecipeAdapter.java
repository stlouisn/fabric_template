package dev.ores.datagen.compat.create;

import dev.ores.datagen.provider.VanillaRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

import java.util.concurrent.CompletableFuture;

import static dev.ores.util.Constants.resourceLocation;

@NullMarked
public final class CreateRecipeAdapter extends FabricRecipeProvider {

    private static final String PROVIDER_NAME = "Create Recipe Provider";

    public CreateRecipeAdapter(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new CreateRecipeProvider(registries, output);
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

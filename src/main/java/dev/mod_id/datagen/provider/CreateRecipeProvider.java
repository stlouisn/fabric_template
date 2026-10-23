package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterBlockItems;
import dev.mod_id.datagen.compat.create.CreateRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class CreateRecipeProvider extends CreateRecipeBuilder {

    CreateRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes() {

        createSandpaperPolishing(RegisterBlockItems.ROSE_QUARTZ_BLOCK, RegisterBlockItems.POLISHED_ROSE_QUARTZ);
    }
}

package dev.mod_id.datagen.compat.create;

import dev.mod_id.content.registry.RegisterBlocks;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class CreateRecipeProvider extends CreateRecipeBuilder {

    CreateRecipeProvider(HolderLookup.Provider registries, RecipeOutput output, Function<Set<String>, RecipeOutput> conditionedOutput) {
        super(registries, output, conditionedOutput);
    }

    @Override
    public void buildRecipes() {

        createSandpaperPolishing(RegisterBlockItems.ROSE_QUARTZ_BLOCK, RegisterBlockItems.POLISHED_ROSE_QUARTZ);
    }
}

package dev.mod_id.datagen.compat.create;

import dev.mod_id.content.registry.RegisterBlockItems;
import dev.mod_id.datagen.utils.ItemUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;

import static dev.mod_id.util.Constants.resourceLocation;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class CreateRecipeProvider extends RecipeProvider {

    public CreateRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes() {
        createSandpaperPolishing(RegisterBlockItems.ROSE_QUARTZ_BLOCK, RegisterBlockItems.POLISHED_ROSE_QUARTZ);
    }

    private void createSandpaperPolishing(ItemLike input, ItemLike result) {
        final String namespace = ItemUtils.getNamespace(result);
        SandpaperRecipeBuilder.polishing(input, result)
            .unlockedBy(getHasName(input), has(input))
            .save(output, ResourceKey.create(Registries.RECIPE, resourceLocation(namespace,"sandpaper_polishing/" + getItemName(result))));
    }
}

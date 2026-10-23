package dev.mod_id.datagen.compat.create;

import dev.mod_id.datagen.builder.SandpaperRecipeBuilder;
import dev.mod_id.datagen.utils.ItemUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;

import static dev.mod_id.util.Constants.resourceLocation;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class CreateRecipeBuilder extends RecipeProvider {

    protected CreateRecipeBuilder(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    protected void createSandpaperPolishing(ItemLike input, ItemLike result) {
        final String namespace = ItemUtils.getNamespace(result);
        SandpaperRecipeBuilder.polishing(input, result)
            .unlockedBy(getHasName(input), has(input))
            .save(output, ResourceKey.create(Registries.RECIPE, resourceLocation(namespace,"sandpaper_polishing/" + getItemName(result))));
    }
}

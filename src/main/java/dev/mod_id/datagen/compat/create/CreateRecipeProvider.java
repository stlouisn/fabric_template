package dev.mod_id.datagen.compat.create;

import dev.mod_id.content.registry.RegisterBlockItems;
import dev.mod_id.content.registry.RegisterItems;
import java.util.Set;
import java.util.function.BiFunction;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class CreateRecipeProvider extends CreateRecipeBuilder {

    public CreateRecipeProvider(HolderLookup.Provider registries, RecipeOutput output, Function<Set<String>, RecipeOutput> conditionedOutput) {
        super(registries, output, conditionedOutput);
    }

    @Override
    public void buildRecipes() {

        createSandpaperPolishing(RegisterBlockItems.ROSE_QUARTZ_BLOCK, RegisterBlockItems.POLISHED_ROSE_QUARTZ);

        createCrushing(
            RegisterBlockItems.ROSE_QUARTZ_BLOCK,
            150,
            new CrushingResult(RegisterItems.ROSE_QUARTZ_SHARD, 3, 1.0F),
            new CrushingResult(RegisterItems.ROSE_QUARTZ_SHARD, 1, 0.5F)
        );

        createCrushing(
            RegisterBlockItems.ROSE_QUARTZ_CLUSTER,
            150,
            new CrushingResult(RegisterItems.ROSE_QUARTZ_SHARD, 7, 1.0F),
            new CrushingResult(RegisterItems.ROSE_QUARTZ_SHARD, 2, 0.5F)
        );
    }
}

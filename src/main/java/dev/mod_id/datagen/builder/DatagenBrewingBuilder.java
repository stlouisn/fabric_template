package dev.mod_id.datagen.builder;

import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBrewingProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Holder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.Nullable;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenBrewingBuilder extends RecipeProvider {

    protected final BrewingBuilder brewingBuilder;

    protected DatagenBrewingBuilder(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
        this.brewingBuilder = new BrewingBuilder(this.output);
    }

    protected abstract void buildMixes(BrewingBuilder brewing);

    @Override
    public final void buildRecipes() {
        this.brewingBuilder.setMixCallback(this::buildMixes);
        this.brewingBuilder.buildRecipes();
    }

    public static class BrewingBuilder extends FabricBrewingProvider {

        @Nullable
        private Consumer<BrewingBuilder> mixCallback;

        public BrewingBuilder(RecipeOutput output) {
            super(output);
        }

        public void setMixCallback(Consumer<BrewingBuilder> mixCallback) {
            this.mixCallback = mixCallback;
        }

        @Override
        protected void buildMixes() {
            if (mixCallback != null) {
                mixCallback.accept(this);
            }
        }

        public void registerMix(Holder<Potion> input, Item reagent, Holder<Potion> output) {
            this.buildMix(input, reagent, output);
        }
    }
}

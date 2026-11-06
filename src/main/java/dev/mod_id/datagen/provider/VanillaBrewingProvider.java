package dev.mod_id.datagen.provider;

import dev.mod_id.datagen.builder.DatagenBrewingBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Recipe;

public final class VanillaBrewingProvider extends DatagenBrewingBuilder {

    public VanillaBrewingProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    protected void buildMixes(BrewingBuilder brewing) {
        brewing.registerMix(Potions.AWKWARD, Items.TURTLE_SCUTE, Potions.TURTLE_MASTER);
    }
}

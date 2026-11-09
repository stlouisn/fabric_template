package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterPotions;
import dev.mod_id.datagen.builder.DatagenBrewingBuilder;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Recipe;

public final class DatagenBrewingProvider extends DatagenBrewingBuilder {

    public DatagenBrewingProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    protected void buildMixes(DatagenBrewingBuilder.BrewingBuilder brewing) {
        brewing.registerMix(Potions.AWKWARD, Items.WITHER_ROSE, RegisterPotions.DECAY);
        brewing.registerMix(RegisterPotions.DECAY, Items.REDSTONE, RegisterPotions.LONG_DECAY);
        brewing.registerMix(RegisterPotions.DECAY, Items.GLOWSTONE_DUST, RegisterPotions.STRONG_DECAY);
    }
}

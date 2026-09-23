package dev.turtle_armour.content.registry;

import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

public class RegisterBrewingRecipes {

    private static void build(PotionBrewing.Builder builder) {
        builder.addMix(Potions.AWKWARD, Items.TURTLE_SCUTE, Potions.TURTLE_MASTER);
    }

    public static void init() {
        FabricPotionBrewingBuilder.BUILD.register(RegisterBrewingRecipes::build);
    }
}

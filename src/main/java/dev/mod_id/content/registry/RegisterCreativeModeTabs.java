package dev.turtle_armour.content.registry;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

public final class RegisterCreativeModeTabs {

    private static void addCombat(FabricCreativeModeTabOutput output) {
        output.insertAfter(Items.TURTLE_HELMET, RegisterItems.TURTLE_CHESTPLATE);
        output.insertAfter(RegisterItems.TURTLE_CHESTPLATE, RegisterItems.TURTLE_LEGGINGS);
        output.insertAfter(RegisterItems.TURTLE_LEGGINGS, RegisterItems.TURTLE_BOOTS);
    }

    public static void init() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(RegisterCreativeModeTabs::addCombat);
    }
}

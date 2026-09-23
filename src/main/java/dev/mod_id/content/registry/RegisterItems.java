package dev.turtle_armour.content.registry;

import dev.turtle_armour.content.key.KeyItems;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;

public final class RegisterItems {

    public static final Item TURTLE_CHESTPLATE = register(KeyItems.TURTLE_CHESTPLATE, Item::new, new Item.Properties().humanoidArmor(ArmorMaterials.TURTLE_SCUTE, ArmorType.HELMET).durability(100));

    public static final Item TURTLE_LEGGINGS = register(KeyItems.TURTLE_LEGGINGS, Item::new, new Item.Properties().humanoidArmor(ArmorMaterials.TURTLE_SCUTE, ArmorType.HELMET).durability(25));

    public static final Item TURTLE_BOOTS = register(KeyItems.TURTLE_BOOTS, Item::new, new Item.Properties().humanoidArmor(ArmorMaterials.TURTLE_SCUTE, ArmorType.HELMET).durability(25));

    /**
     * Registers an item using the given key, factory, and properties.
     */
    private static Item register(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties properties) {
        Item item = factory.apply(properties.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    public static void init() {
    }
}

package dev.better_withered_mobs.content.registry;

import dev.better_withered_mobs.content.key.KeyBlockItems;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public final class RegisterBlockItems {

    public static final Block WITHERED_BONE_BLOCK = register(KeyBlockItems.WITHERED_BONE_BLOCK, RotatedPillarBlock::new, BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.XYLOPHONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK).strength(2.0F));

    public static void init() {
    }

    /**
     * Registers a block and its corresponding BlockItem using its BlockItemId, factory, and block properties.
     */
    private static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties blockProperties) {
        final Item.Properties itemProperties = new Item.Properties().useBlockDescriptionPrefix();
        return register(id, factory, blockProperties, itemProperties);
    }

    /**
     * Registers a block and its corresponding BlockItem using its BlockItemId, factory, block properties, and item properties.
     */
    private static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties blockProperties, Item.Properties itemProperties) {
        final Block block = registerBlock(id.block(), factory, blockProperties);
        registerItem(id.item(), properties -> new BlockItem(block, properties), itemProperties);
        return block;
    }

    /**
     * Registers a block using the given key, factory, and block properties.
     */
    private static Block registerBlock(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        return Blocks.register(key, factory, properties);
    }

    /**
     * Registers an item using the given key, factory, and item properties.
     */
    private static void registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties properties) {
        final Item item = factory.apply(properties.setId(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }
        Registry.register(BuiltInRegistries.ITEM, key, item);
    }
}

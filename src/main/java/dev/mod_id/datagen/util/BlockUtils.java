package dev.mod_id.datagen.util;

import static dev.mod_id.Constants.MOD_ID;
import static dev.mod_id.api.ResourceLocation.resourceLocation;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class BlockUtils {

    /**
     * Returns the identifier of the given block.
     */
    public static Identifier getKey(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    /**
     * Returns the namespace of the given block.
     */
    public static String getNamespace(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getNamespace();
    }

    /**
     * Returns the resource key of the given block.
     */
    @Nullable
    public static ResourceKey<Block> getResourceKey(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).orElse(null);
    }

    /**
     * Returns the path of the given block.
     */
    public static String getPath(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    /**
     * Returns the block using the default mod ID and the specified path.
     */
    @Nullable
    public static Block getBlock(String path) {
        return getBlock(MOD_ID, path);
    }

    /**
     * Returns the block using the specified namespace and path.
     */
    @Nullable
    public static Block getBlock(String namespace, String path) {
        return getBlock(resourceLocation(namespace, path));
    }

    /**
     * Returns the block using the specified identifier.
     */
    @Nullable
    public static Block getBlock(Identifier key) {
        return BuiltInRegistries.BLOCK.get(key).map(Holder::value).orElse(null);
    }

    /**
     * Checks whether a block exists in the registry using the default mod ID and the specified path.
     */
    public static boolean doesExist(String path) {
        return doesExist(MOD_ID, path);
    }

    /**
     * Checks whether a block exists in the registry using the specified namespace and path.
     */
    public static boolean doesExist(String namespace, String path) {
        return doesExist(resourceLocation(namespace, path));
    }

    /**
     * Checks whether a block exists in the registry using the specified identifier.
     */
    public static boolean doesExist(Identifier key) {
        return BuiltInRegistries.BLOCK.containsKey(key);
    }
}

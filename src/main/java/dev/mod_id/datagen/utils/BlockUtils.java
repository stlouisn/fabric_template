package dev.mod_id.datagen.utils;

import static dev.mod_id.util.Constants.MOD_ID;
import static dev.mod_id.util.Constants.resourceLocation;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("unused")
public final class BlockUtils {

    /**
     * Returns the identifier of the given block.
     */
    public static Identifier getKey(@NonNull Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    /**
     * Returns the namespace of the given block.
     */
    public static String getNamespace(@NonNull Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getNamespace();
    }

    /**
     * Returns the resource key of the given block.
     */
    @Nullable
    public static ResourceKey<Block> getResourceKey(@NonNull Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).orElse(null);
    }

    /**
     * Returns the path of the given block.
     */
    public static String getPath(@NonNull Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    /**
     * Returns the block using the default mod ID and the specified path.
     */
    @Nullable
    public static Block getBlock(@NonNull String path) {
        return getBlock(MOD_ID, path);
    }

    /**
     * Returns the block using the specified namespace and path.
     */
    @Nullable
    public static Block getBlock(@NonNull String namespace, @NonNull String path) {
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
    public static boolean doesExist(@NonNull String path) {
        return doesExist(MOD_ID, path);
    }

    /**
     * Checks whether a block exists in the registry using the specified namespace and path.
     */
    public static boolean doesExist(@NonNull String namespace, @NonNull String path) {
        return doesExist(resourceLocation(namespace, path));
    }

    /**
     * Checks whether a block exists in the registry using the specified identifier.
     */
    public static boolean doesExist(@NonNull Identifier key) {
        return BuiltInRegistries.BLOCK.containsKey(key);
    }
}

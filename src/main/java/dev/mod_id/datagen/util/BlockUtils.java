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
    public static Identifier getIdentifier(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    /**
     * Returns the resource key of the given block.
     */
    @Nullable
    public static ResourceKey<Block> getResourceKey(Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block).orElse(null);
    }

    /**
     * Returns the resource key of the given block identifier.
     */
    @Nullable
    public static ResourceKey<Block> getResourceKey(Identifier id) {
        final Block block = getBlock(id);
        if (block == null) {
            return null;
        }
        return BuiltInRegistries.BLOCK.getResourceKey(block).orElse(null);
    }

    /**
     * Returns the namespace of the given block.
     */
    public static String getNamespace(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getNamespace();
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
    public static Block getBlock(Identifier id) {
        return BuiltInRegistries.BLOCK.get(id).map(Holder::value).orElse(null);
    }
}

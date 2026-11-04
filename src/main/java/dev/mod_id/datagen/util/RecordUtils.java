package dev.mod_id.datagen.util;

import static dev.mod_id.datagen.data.BlockOverrides.checkForPathOverrides;
import static dev.mod_id.datagen.util.BlockUtils.getBlock;
import static dev.mod_id.datagen.util.RegistryUtils.getBlockIdentifier;

import dev.mod_id.datagen.data.BlockExclusions;
import dev.mod_id.datagen.data.BlockType;
import dev.mod_id.datagen.data.BlockVariant;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

public final class RecordUtils {

    /**
     * Returns the path using the specified prefix, block type, and suffix.
     */
    public static String buildPath(@Nullable String prefix, BlockType blockType, @Nullable String suffix) {
        String path = blockType.material();
        if (prefix != null && !prefix.isEmpty()) {
            path = prefix + "_" + path;
        }
        if (suffix != null && !suffix.isEmpty()) {
            path = path + "_" + suffix;
        }
        return path;
    }

    /**
     * Returns the block using the specified prefix, block type, and suffix.
     */
    public static Block getBlockWithBlockType(@Nullable String prefix, BlockType blockType, @Nullable String suffix) {
        final String path = checkForPathOverrides(buildPath(prefix, blockType, suffix));
        final Identifier key = getBlockIdentifier(path);
        if (key == null) {
            throw new IllegalStateException("Could not find identifier for '" + path + "' in registry.");
        }
        final Block block = getBlock(key);
        if (block == null) {
            throw new IllegalStateException("Could not find block for '" + key + "' in registry.");
        }
        return block;
    }

    @Nullable
    public static Identifier getPreviousBlockIdentifier(BlockType blockType, BlockVariant blockVariant) {
        return getPreviousBlockIdentifier(blockType, null, blockVariant, null);
    }

    @Nullable
    public static Identifier getPreviousBlockIdentifier(BlockType blockType, @Nullable Predicate<BlockType> typeFilter, BlockVariant blockVariant) {
        return getPreviousBlockIdentifier(blockType, typeFilter, blockVariant, null);
    }

    @Nullable
    public static Identifier getPreviousBlockIdentifier(BlockType blockType, BlockVariant blockVariant, @Nullable Predicate<BlockVariant> variantFilter) {
        return getPreviousBlockIdentifier(blockType, null, blockVariant, variantFilter);
    }

    @Nullable
    public static Identifier getPreviousBlockIdentifier(BlockType blockType, @Nullable Predicate<BlockType> typeFilter, BlockVariant blockVariant, @Nullable Predicate<BlockVariant> variantFilter) {

        // Apply the optional type filter before searching
        List<BlockType> filteredTypes;
        if (typeFilter != null) {
            filteredTypes = BlockType.BLOCK_TYPES.stream().filter(typeFilter).toList();
        } else {
            filteredTypes = BlockType.BLOCK_TYPES;
        }

        // Find the current type and its variant in the search order
        int typeIndex = filteredTypes.indexOf(blockType);
        if (typeIndex == -1) {
            return null;
        }
        List<BlockVariant> variants = blockType.blockVariants();
        int variantIndex = variants.indexOf(blockVariant);

        // Search backward through variants in the same type.
        for (int i = variantIndex - 1; i >= 0; i--) {
            BlockVariant previousVariant = variants.get(i);
            if (variantFilter != null && !variantFilter.test(previousVariant)) {
                continue;
            }
            final String buildPath = checkForPathOverrides(buildPath(previousVariant.prefix(), blockType, previousVariant.suffix()));
            final Identifier id = getBlockIdentifier(buildPath);
            Identifier identifier;
            if (id != null) {
                identifier = getBlockIdentifier(id.getPath(), candidate -> candidate.getNamespace().equals(id.getNamespace()), BlockExclusions::collisionRules);
            } else {
                String path = checkForPathOverrides(buildPath);
                identifier = getBlockIdentifier(path, null, BlockExclusions::collisionRules);
            }
            if (identifier != null && getBlock(identifier) != null) {
                return identifier;
            }
        }

        // Search backward through previous filtered types.
        for (int i = typeIndex - 1; i >= 0; i--) {
            BlockType previousType = filteredTypes.get(i);
            List<BlockVariant> previousVariants = previousType.blockVariants();
            for (int j = previousVariants.size() - 1; j >= 0; j--) {
                BlockVariant previousVariant = previousVariants.get(j);
                if (variantFilter != null && !variantFilter.test(previousVariant)) {
                    continue;
                }
                String buildPath = checkForPathOverrides(buildPath(previousVariant.prefix(), previousType, previousVariant.suffix()));
                final Identifier id = getBlockIdentifier(buildPath);
                Identifier identifier;
                if (id != null) {
                    identifier = getBlockIdentifier(id.getPath(), candidate -> candidate.getNamespace().equals(id.getNamespace()), BlockExclusions::collisionRules);
                } else {
                    String path = checkForPathOverrides(buildPath);
                    identifier = getBlockIdentifier(path, null, BlockExclusions::collisionRules);
                }
                if (identifier != null && getBlock(identifier) != null) {
                    return identifier;
                }
            }
        }

        // No matching previous block found
        return null;
    }
}

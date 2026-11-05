package dev.mod_id.datagen.util;

import static dev.mod_id.Constants.MOD_ID;
import static dev.mod_id.datagen.data.BlockOverrides.checkForPathOverrides;
import static dev.mod_id.datagen.util.RegistryUtils.getBlockIdentifier;

import dev.mod_id.datagen.data.BlockExclusions;
import dev.mod_id.datagen.data.BlockType;
import dev.mod_id.datagen.data.BlockVariant;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.Nullable;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class RecordUtils {

    /**
     * Returns the block path using the specified prefix, block type, and suffix.
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
     * Returns the block identifier using the specified prefix, block type, and suffix.
     */
    public static Identifier getIdentifier(@Nullable String prefix, BlockType blockType, @Nullable String suffix) {
        final String path = checkForPathOverrides(buildPath(prefix, blockType, suffix));
        final Identifier id = getBlockIdentifier(path);
        if (id == null) {
            throw new IllegalStateException("Could not find identifier for '" + path + "' in registry.");
        }
        return id;
    }

    /**
     * Returns the block using the specified prefix, block type, and suffix.
     */
    public static Block getBlock(@Nullable String prefix, BlockType blockType, @Nullable String suffix) {
        final Identifier id = getIdentifier(prefix, blockType, suffix);
        final Block block = BlockUtils.getBlock(id);
        if (block == null) {
            throw new IllegalStateException("Could not find block for '" + id + "' in registry.");
        }
        return block;
    }

    /**
     * Checks whether a block already exists using the specified prefix, block type, and suffix.
     */
    public static boolean doesBlockAlreadyExist(@Nullable String prefix, BlockType blockType, @Nullable String suffix) {
        final String path = checkForPathOverrides(buildPath(prefix, blockType, suffix));
        final Predicate<Identifier> filter = identifier -> !identifier.getNamespace().equals(MOD_ID);
        return RegistryUtils.doesBlockExist(path, filter, BlockExclusions::collisionRules);
    }

    /**
     * Returns the identifier of the previous block matching the specified block type and variant.
     */
    @Nullable
    public static Identifier getPreviousBlockIdentifier(BlockType blockType, BlockVariant blockVariant) {
        return getPreviousBlockIdentifier(blockType, null, blockVariant, null);
    }

    /**
     * Returns the identifier of the previous block matching the specified block type, optional type filter, and variant.
     */
    @Nullable
    public static Identifier getPreviousBlockIdentifier(BlockType blockType, @Nullable Predicate<BlockType> typeFilter, BlockVariant blockVariant) {
        return getPreviousBlockIdentifier(blockType, typeFilter, blockVariant, null);
    }

    /**
     * Returns the identifier of the previous block matching the specified block type, variant, and optional variant filter.
     */
    @Nullable
    public static Identifier getPreviousBlockIdentifier(BlockType blockType, BlockVariant blockVariant, @Nullable Predicate<BlockVariant> variantFilter) {
        return getPreviousBlockIdentifier(blockType, null, blockVariant, variantFilter);
    }

    /**
     * Returns the identifier of the previous block matching the specified block type, optional type filter, variant, and
     * optional variant filter.
     */
    @Nullable
    public static Identifier getPreviousBlockIdentifier(BlockType blockType, @Nullable Predicate<BlockType> typeFilter, BlockVariant blockVariant, @Nullable Predicate<BlockVariant> variantFilter) {

        // Apply the optional type filter before searching
        final List<BlockType> filteredTypes;
        if (typeFilter != null) {
            filteredTypes = BlockType.BLOCK_TYPES.stream().filter(typeFilter).toList();
        } else {
            filteredTypes = BlockType.BLOCK_TYPES;
        }

        // Find the current type in the search order
        final int typeIndex = filteredTypes.indexOf(blockType);

        // Guard against missing or filtered block types
        if (typeIndex < 0) {
            return null;
        }

        // Retrieve all block variants associated with the current block type
        final List<BlockVariant> variants = blockType.blockVariants();

        // Find the current variant in the search order
        int variantIndex = variants.indexOf(blockVariant);

        // Search backward through variants in the same type.
        for (int i = variantIndex - 1; i >= 0; i--) {
            final BlockVariant previousVariant = variants.get(i);
            if (variantFilter != null && !variantFilter.test(previousVariant)) {
                continue;
            }
            final String previousPath = checkForPathOverrides(buildPath(previousVariant.prefix(), blockType, previousVariant.suffix()));
            final Identifier previousIdentifier = getBlockIdentifier(previousPath, null, BlockExclusions::collisionRules);
            if (previousIdentifier != null) {
                return previousIdentifier;
            }
        }

        // Search backward through previous filtered types.
        for (int i = typeIndex - 1; i >= 0; i--) {
            final BlockType previousBlockType = filteredTypes.get(i);
            final List<BlockVariant> previousVariants = previousBlockType.blockVariants();
            for (int j = previousVariants.size() - 1; j >= 0; j--) {
                final BlockVariant previousVariant = previousVariants.get(j);
                if (variantFilter != null && !variantFilter.test(previousVariant)) {
                    continue;
                }
                final String previousPath = checkForPathOverrides(buildPath(previousVariant.prefix(), previousBlockType, previousVariant.suffix()));
                final Identifier previousIdentifier = getBlockIdentifier(previousPath, null, BlockExclusions::collisionRules);
                if (previousIdentifier != null) {
                    return previousIdentifier;
                }
            }
        }

        // No previous block found
        return null;
    }
}

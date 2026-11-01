package dev.mod_id.datagen.util;

import static dev.mod_id.datagen.data.BlockOverrides.checkForBlockOverrides;
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

    @Nullable
    public static Block getPreviousBlock(BlockType blockType, BlockVariant blockVariant) {
        return getPreviousBlock(blockType, null, blockVariant, null);
    }

    @Nullable
    public static Block getPreviousBlock(BlockType blockType, @Nullable Predicate<BlockType> typeFilter, BlockVariant blockVariant) {
        return getPreviousBlock(blockType, typeFilter, blockVariant, null);
    }

    @Nullable
    public static Block getPreviousBlock(BlockType blockType, BlockVariant blockVariant, @Nullable Predicate<BlockVariant> variantFilter) {
        return getPreviousBlock(blockType, null, blockVariant, variantFilter);
    }

    @Nullable
    public static Block getPreviousBlock(BlockType blockType, @Nullable Predicate<BlockType> typeFilter, BlockVariant blockVariant, @Nullable Predicate<BlockVariant> variantFilter) {

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

        // Step backward through variants in the same type
        for (int i = variantIndex - 1; i >= 0; i--) {
            BlockVariant previousVariant = variants.get(i);
            if (variantFilter != null && !variantFilter.test(previousVariant)) {
                continue;
            }
            String buildPath = BlockType.buildPath(previousVariant.prefix(), blockType, previousVariant.suffix());
            Identifier id = checkForBlockOverrides(buildPath);
            Block block;
            if (id != null) {
                block = getBlock(getBlockIdentifier(id.getPath(), identifier -> identifier.getNamespace().equals(id.getNamespace()), BlockExclusions::collisionRules));
            } else {
                String path = checkForPathOverrides(buildPath);
                block = getBlock(getBlockIdentifier(path, null, BlockExclusions::collisionRules));
            }
            if (block != null) {
                return block;
            }
        }

        // Step backward through previous filtered types
        for (int i = typeIndex - 1; i >= 0; i--) {
            BlockType previousType = filteredTypes.get(i);
            List<BlockVariant> previousVariants = previousType.blockVariants();
            for (int j = previousVariants.size() - 1; j >= 0; j--) {
                BlockVariant previousVariant = previousVariants.get(j);
                if (variantFilter != null && !variantFilter.test(previousVariant)) {
                    continue;
                }
                String buildPath = BlockType.buildPath(previousVariant.prefix(), previousType, previousVariant.suffix());
                Identifier id = checkForBlockOverrides(buildPath);
                Block block;
                if (id != null) {
                    block = getBlock(getBlockIdentifier(id.getPath(), identifier -> identifier.getNamespace().equals(id.getNamespace()), BlockExclusions::collisionRules));
                } else {
                    String path = checkForPathOverrides(buildPath);
                    block = getBlock(getBlockIdentifier(path, null, BlockExclusions::collisionRules));
                }
                if (block != null) {
                    return block;
                }
            }
        }

        // No matching previous block found
        return null;
    }
}

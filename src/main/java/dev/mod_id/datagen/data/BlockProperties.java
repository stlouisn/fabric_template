package dev.mod_id.datagen.data;

import org.jspecify.annotations.Nullable;

@SuppressWarnings({"ConstantValue", "unused", "SwitchStatementWithTooFewBranches"})
public final class BlockProperties {

    /**
     * Returns the block properties expression associated with the specified block name.
     */
    @Nullable
    public static String getBlockProperties(String block) {

        return switch (block) {
            default -> null;
        };
    }
}

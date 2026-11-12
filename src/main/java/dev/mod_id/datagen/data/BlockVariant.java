package dev.mod_id.datagen.data;

import java.util.List;
import org.jspecify.annotations.Nullable;

public record BlockVariant(
    @Nullable String prefix,
    @Nullable String suffix,
    boolean useFactoryPlaceholder,
    String factory,
    @Nullable String previousPrefix,
    @Nullable String previousSuffix,
    boolean strippable
) {

    public static final String placeholder = "PLACEHOLDER";

    public BlockVariant(String prefix, String suffix, boolean hasPlaceholder, String factory) {
        this(prefix, suffix, hasPlaceholder, factory, null, null, false);
    }

    public BlockVariant(String prefix, String suffix, boolean hasPlaceholder, String factory, boolean isStrippable) {
        this(prefix, suffix, hasPlaceholder, factory, null, null, isStrippable);
    }

    public BlockVariant(String suffix, boolean hasPlaceholder, String factory, String previousSuffix, boolean isStrippable) {
        this(null, suffix, hasPlaceholder, factory, null, previousSuffix, isStrippable);
    }

    public static final BlockVariant _PALISADE = new BlockVariant(
        "palisade",
        true,
        "properties -> new PalisadeBlock(() -> RegisterBlockItems.SPIKED_PLACEHOLDER_PALISADE, () -> RegisterBlockItems.STRIPPED_PLACEHOLDER_PALISADE, properties)",
        "slab",
        true
    );

    public static final BlockVariant STRIPPED__PALISADE = new BlockVariant(
        "stripped",
        "palisade",
        true,
        "properties -> new PalisadeBlock(() -> RegisterBlockItems.STRIPPED_SPIKED_PLACEHOLDER_PALISADE, null, properties)");

    public static final BlockVariant SPIKED__PALISADE = new BlockVariant(
        "spiked",
        "palisade",
        true,
        "properties -> new SpikedPalisadeBlock(() -> RegisterBlockItems.STRIPPED_SPIKED_PLACEHOLDER_PALISADE, properties)",
        true
    );

    public static final BlockVariant STRIPPED_SPIKED__PALISADE = new BlockVariant(
        "stripped_spiked",
        "palisade",
        false,
        "properties -> new SpikedPalisadeBlock(null, properties)");

    public static final List<BlockVariant> COMPLETE = List.of(
        _PALISADE,
        STRIPPED__PALISADE,
        SPIKED__PALISADE,
        STRIPPED_SPIKED__PALISADE
    );
}

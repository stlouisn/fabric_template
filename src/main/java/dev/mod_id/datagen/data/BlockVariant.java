package dev.mod_id.datagen.data;

import org.jspecify.annotations.Nullable;

import java.util.List;

public record BlockVariant(
    @Nullable String prefix,
    @Nullable String suffix,
    boolean useFactoryPlaceholder,
    String factory,
    @Nullable String previousPrefix,
    @Nullable String previousSuffix
) {

    public static final String placeholder = "OAK";

    private BlockVariant(String prefix, String suffix, Factory factory) {
        this(prefix, suffix, factory.usePlaceholder, factory.factory, null, null);
    }

    private BlockVariant(String suffix, Factory factory, PreviousBlock previousBlock) {
        this(null, suffix, factory.usePlaceholder, factory.factory, previousBlock.prefix, previousBlock.suffix);
    }

    public static final BlockVariant _PALISADE = new BlockVariant(
        "palisade",
        new Factory(true, "properties -> new PalisadeBlock(() -> RegisterBlockItems.SPIKED_OAK_PALISADE, () -> RegisterBlockItems.STRIPPED_OAK_PALISADE, properties)"),
        new PreviousBlock("slab")
    );

    public static final BlockVariant STRIPPED__PALISADE = new BlockVariant(
        "stripped",
        "palisade",
        new Factory (true, "properties -> new PalisadeBlock(() -> RegisterBlockItems.STRIPPED_SPIKED_OAK_PALISADE, null, properties)")
    );

    public static final BlockVariant SPIKED__PALISADE = new BlockVariant(
        "spiked",
        "palisade",
        new Factory(true, "properties -> new SpikedPalisadeBlock(() -> RegisterBlockItems.STRIPPED_SPIKED_OAK_PALISADE, properties)")
    );

    public static final BlockVariant STRIPED_SPIKED__PALISADE = new BlockVariant(
        "stripped_spiked",
        "palisade",
        new Factory(true, "properties -> new SpikedPalisadeBlock(null, properties)")
    );

    public static final List<BlockVariant> DEFAULT = List.of(
        _PALISADE,STRIPPED__PALISADE,SPIKED__PALISADE,STRIPED_SPIKED__PALISADE
    );

    private record Factory(boolean usePlaceholder, String factory) {
        private Factory(String factory) {
            this(false, factory);
        }
    }

    private record PreviousBlock(@Nullable String prefix, @Nullable String suffix) {
        private PreviousBlock(String suffix) {
            this(null, suffix);
        }
    }
}

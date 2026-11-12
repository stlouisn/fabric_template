package dev.mod_id.datagen.data;

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
}

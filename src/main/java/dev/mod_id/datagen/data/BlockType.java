package dev.mod_id.datagen.data;

import java.util.List;

public record BlockType(
    String material,
    List<BlockVariant> blockVariants,
    String mapColor,
    String soundType,
    String noteBlockInstrument,
    String strength,
    boolean flammable
) {

    public static final List<BlockType> BLOCK_TYPES = List.of(
    );
}

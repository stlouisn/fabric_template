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

    public BlockType(String material, String mapColor) {
        this(material, BlockVariant.COMPLETE, mapColor, "WOOD", "BASS", "2.0F", true);
    }

    public BlockType(String material, String mapColor, String soundType) {
        this(material, BlockVariant.COMPLETE, mapColor, soundType, "BASS", "2.0F", true);
    }

    public BlockType(String material, String mapColor, String soundType, boolean isFlammable) {
        this(material, BlockVariant.COMPLETE, mapColor, soundType, "BASS", "2.0F", isFlammable);
    }

    public static final List<BlockType> BLOCK_TYPES = List.of(

        new BlockType("oak", "WOOD"),
        new BlockType("spruce", "PODZOL"),
        new BlockType("birch", "SAND"),
        new BlockType("jungle", "DIRT"),
        new BlockType("acacia", "COLOR_ORANGE"),
        new BlockType("dark_oak", "COLOR_BROWN"),
        new BlockType("mangrove", "COLOR_RED"),
        new BlockType("cherry", "TERRACOTTA_WHITE", "CHERRY_WOOD"),
        new BlockType("pale_oak", "QUARTZ"),
        new BlockType("poplar", "COLOR_LIGHT_GRAY"),
        new BlockType("bamboo", "COLOR_YELLOW", "BAMBOO_WOOD"),
        new BlockType("crimson", "CRIMSON_STEM", "STEM", false),
        new BlockType("warped", "WARPED_STEM", "STEM", false)

    );
}

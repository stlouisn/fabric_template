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

    private BlockType(String material, String mapColor) {
        this(material, BlockVariant.DEFAULT,  mapColor, "SoundType.WOOD", "NoteBlockInstrument.BASS","2.0F", true);
    }

    private BlockType(String material, String mapColor, String soundType) {
        this(material, BlockVariant.DEFAULT,  mapColor, soundType, "NoteBlockInstrument.BASS","2.0F", true);
    }

    private BlockType(String material, String mapColor, String soundType, boolean flammable) {
        this(material, BlockVariant.DEFAULT,  mapColor, soundType, "NoteBlockInstrument.BASS","2.0F", flammable);
    }

    public static final List<BlockType> BLOCK_TYPES = List.of(

        // Vanilla
        new BlockType("oak", "MapColor.WOOD"),
        new BlockType("spruce", "MapColor.PODZOL"),
        new BlockType("birch", "MapColor.SAND"),
        new BlockType("jungle", "MapColor.DIRT"),
        new BlockType("acacia", "MapColor.COLOR_ORANGE"),
        new BlockType("dark_oak", "MapColor.COLOR_BROWN"),
        new BlockType("mangrove", "MapColor.COLOR_RED"),
//        new BlockType("cypress", "MapColor.COLOR_PURPLE"),
        new BlockType("cherry", "MapColor.TERRACOTTA_WHITE", "SoundType.CHERRY_WOOD"),
        new BlockType("pale_oak", "MapColor.QUARTZ"),
//        new BlockType("bamboo", "MapColor.COLOR_YELLOW", "SoundType.BAMBOO_WOOD"),
        new BlockType("crimson", "MapColor.CRIMSON_STEM", "SoundType.STEM", false),
        new BlockType("warped", "MapColor.WARPED_STEM", "SoundType.STEM", false)

        // Ecologics
//        new BlockType("coconut", "MapColor.WOOD"),
//        new BlockType("maple", "MapColor.WOOD"),
//        new BlockType("walnut", "MapColor.WOOD"),
//        new BlockType("azalea", "MapColor.WOOD")
    );
}

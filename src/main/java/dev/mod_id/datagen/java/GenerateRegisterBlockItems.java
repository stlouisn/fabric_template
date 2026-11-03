package dev.mod_id.datagen.java;

import static dev.mod_id.Constants.MOD_ID;
import static dev.mod_id.datagen.data.BlockOverrides.checkForPathOverrides;
import static dev.mod_id.datagen.util.CreateJavaFile.createJavaFile;
import static dev.mod_id.datagen.util.RegistryUtils.doesBlockExist;
import static dev.mod_id.datagen.util.RegistryUtils.getBlockIdentifier;

import dev.mod_id.datagen.data.BlockExclusions;
import dev.mod_id.datagen.data.BlockType;
import dev.mod_id.datagen.data.BlockVariant;
import dev.mod_id.datagen.util.RecordUtils;
import net.minecraft.resources.Identifier;

@SuppressWarnings("ConstantValue")
public final class GenerateRegisterBlockItems {

    private static final String AUTO_GENERATED_WARNING = "// DATA-GENERATED FILE — DO NOT EDIT";

    private static final String PACKAGE_NAME = "dev.mod_id.content.registry";

    private static final String CLASS_ANNOTATION = "";

    private static final String CLASS_DECLARATION = "public final class";

    private static final String CLASS_NAME = "RegisterBlockItems";

    private static final String IMPORTS = """
            import dev.mod_id.content.block.PalisadeBlock;
            import dev.mod_id.content.block.SpikedPalisadeBlock;
            import dev.mod_id.content.key.KeyBlockItems;
            import java.util.function.Function;
            import net.minecraft.core.Registry;
            import net.minecraft.core.registries.BuiltInRegistries;
            import net.minecraft.references.BlockItemId;
            import net.minecraft.resources.ResourceKey;
            import net.minecraft.world.item.BlockItem;
            import net.minecraft.world.item.Item;
            import net.minecraft.world.level.block.Block;
            import net.minecraft.world.level.block.Blocks;
            import net.minecraft.world.level.block.SoundType;
            import net.minecraft.world.level.block.state.BlockBehaviour;
            import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
            import net.minecraft.world.level.material.MapColor;
            """;

    private static final String CONSTANTS = "";

    private static final String INIT = """
                public static void init() {
                }
            """;

    private static final String PRIVATE_METHODS = """
                /**
                 * Registers a block and its corresponding BlockItem using its BlockItemId, factory, and properties.
                 */
                private static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
                    Block block = registerBlock(id.block(), factory, properties);
                    registerItem(id.item(), itemProperties -> new BlockItem(block, itemProperties), new Item.Properties().useBlockDescriptionPrefix());
                    return block;
                }

                /**
                 * Registers a block using the given key, factory, and properties.
                 */
                private static Block registerBlock(ResourceKey<Block> key, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
                    return Blocks.register(key, factory, properties);
                }

                /**
                 * Registers an item using the given key, factory, and properties.
                 */
                private static void registerItem(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties properties) {
                    Item item = factory.apply(properties.setId(key));
                    if (item instanceof BlockItem blockItem) {
                        blockItem.registerBlocks(Item.BY_BLOCK, item);
                    }
                    Registry.register(BuiltInRegistries.ITEM, key, item);
                }
            """;

    public static void run() {

        StringBuilder sourceCode = new StringBuilder();

        // Header
        sourceCode.append(AUTO_GENERATED_WARNING + "\n");
        sourceCode.append("\n");
        sourceCode.append("package " + PACKAGE_NAME + ";" + "\n");
        sourceCode.append("\n");
        if (IMPORTS != null && !IMPORTS.isEmpty()) {
            sourceCode.append(IMPORTS + "\n");
        }
        if (CLASS_ANNOTATION != null && !CLASS_ANNOTATION.isEmpty()) {
            sourceCode.append(CLASS_ANNOTATION + "\n");
        }
        sourceCode.append(CLASS_DECLARATION + " " + CLASS_NAME + " {" + "\n");
        sourceCode.append("\n");

        // Constants
        if (CONSTANTS != null && !CONSTANTS.isEmpty()) {
            sourceCode.append(CONSTANTS);
            sourceCode.append("\n");
        }

        // Registration
        processBlocks(sourceCode);

        // Init
        if (INIT != null && !INIT.isEmpty()) {
            sourceCode.append(INIT);
            sourceCode.append("\n");
        }

        // Footer
        if (PRIVATE_METHODS != null && !PRIVATE_METHODS.isEmpty()) {
            sourceCode.append(PRIVATE_METHODS);
        }
        sourceCode.append("}" + "\n");

        // Create File
        createJavaFile(sourceCode, "src/main/java/" + PACKAGE_NAME.replace(".", "/"), CLASS_NAME + ".java");
    }

    private static void processBlocks(StringBuilder sourceCode) {

        for (BlockType blockType : BlockType.BLOCK_TYPES) {

            for (BlockVariant blockVariant : blockType.blockVariants()) {

                final String buildPath = checkForPathOverrides(RecordUtils.buildPath(blockVariant.prefix(), blockType, blockVariant.suffix()));
                final Identifier buildIdentifier = getBlockIdentifier(buildPath);

                // Skip if the matching block identifier resolves to a block from another namespace.
                if (buildIdentifier != null && !buildIdentifier.getNamespace().equals(MOD_ID)) {
                    continue;
                }

                // Skip if another namespace has a block with this path that isn't excluded by the collision rules
                if (doesBlockExist(buildPath, identifier -> !identifier.getNamespace().equals(MOD_ID), BlockExclusions::collisionRules)) {
                    continue;
                }

                final String blockFactory = getFactory(blockType, blockVariant);
                final String blockProperties = getProperties(blockType);

                sourceCode
                    .append("    public static final Block ")
                    .append(buildPath.toUpperCase())
                    .append(" = register(KeyBlockItems.")
                    .append(buildPath.toUpperCase())
                    .append(", ")
                    .append(blockFactory)
                    .append(", ")
                    .append(blockProperties)
                    .append(");")
                    .append("\n");
            }

            sourceCode.append("\n");
        }
    }

    private static String getFactory(BlockType blockType, BlockVariant blockVariant) {
        String factory = blockVariant.factory();
        if (factory == null || factory.isEmpty()) {
            throw new IllegalStateException("Factory missing for: " + blockVariant);
        }
        if (blockVariant.useFactoryPlaceholder()) {
            factory = factory.replace(BlockVariant.placeholder, blockType.material().toUpperCase());
        }
        return factory;
    }

    private static String getProperties(BlockType blockType) {
        final String mapColor = blockType.mapColor();
        final String noteBlockInstrument = blockType.noteBlockInstrument();
        final String strength = blockType.strength();
        final String soundType = blockType.soundType();
        if (mapColor == null || mapColor.isEmpty()) {
            throw new IllegalStateException("Properties mapColor missing for: " + blockType);
        }
        if (noteBlockInstrument == null || noteBlockInstrument.isEmpty()) {
            throw new IllegalStateException("Properties noteBlockInstrument missing for: " + blockType);
        }
        if (strength == null || strength.isEmpty()) {
            throw new IllegalStateException("Properties strength missing for: " + blockType);
        }
        if (soundType == null || soundType.isEmpty()) {
            throw new IllegalStateException("Properties soundType missing for: " + blockType);
        }
        StringBuilder properties = new StringBuilder();
        properties
            .append("BlockBehaviour.Properties.of()")
            .append(".mapColor(")
            .append(mapColor)
            .append(").instrument(")
            .append(noteBlockInstrument)
            .append(").strength(")
            .append(strength)
            .append(").sound(")
            .append(soundType)
            .append(")");
        if (blockType.flammable()) {
            properties.append(".ignitedByLava()");
        }
        return properties.toString();
    }
}

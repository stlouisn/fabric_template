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
public final class GenerateRegisterCreativeModeTabs {

    private static final String AUTO_GENERATED_WARNING = "// DATA-GENERATED FILE — DO NOT EDIT";

    private static final String PACKAGE_NAME = "dev.mod_id.content.registry";

    private static final String CLASS_ANNOTATION = "";

    private static final String CLASS_DECLARATION = "public final class";

    private static final String CLASS_NAME = "RegisterCreativeModeTabs";

    private static final String IMPORTS = """
            import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
            import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
            import net.minecraft.world.item.CreativeModeTabs;
            import net.minecraft.world.level.block.Blocks;
            """;

    private static final String CONSTANTS = "";

    private static final String INIT = """
                public static void init() {
                    CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(RegisterCreativeModeTabs::addBuildingBlocks);
                }
            """;

    private static final String PRIVATE_METHODS = "";

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

        // Building Blocks
        addBuildingBlocks(sourceCode);

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

    private static void addBuildingBlocks(StringBuilder sourceCode) {

        sourceCode.append("    private static void addBuildingBlocks(FabricCreativeModeTabOutput output) {" + "\n");

        for (BlockType blockType : BlockType.BLOCK_TYPES) {

            sourceCode.append("\n");

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

                final Identifier previousIdentifier;
                final boolean hasPreviousPrefix = blockVariant.previousPrefix() != null && !blockVariant.previousPrefix().isEmpty();
                final boolean hasPreviousSuffix = blockVariant.previousSuffix() != null && !blockVariant.previousSuffix().isEmpty();
                if (hasPreviousPrefix || hasPreviousSuffix) {
                    final String previousPath = checkForPathOverrides(RecordUtils.buildPath(blockVariant.previousPrefix(), blockType, blockVariant.previousSuffix()));
                    previousIdentifier = getBlockIdentifier(previousPath);
                } else {
                    previousIdentifier = RecordUtils.getPreviousBlockIdentifier(blockType, blockVariant);
                }
                if (previousIdentifier == null) {
                    throw new IllegalStateException("Previous block '" + previousIdentifier + "' does not exist.");
                }

                final String previous = getPrevious(previousIdentifier);

                sourceCode
                    .append("        output.insertAfter(")
                    .append(previous)
                    .append(", RegisterBlockItems.")
                    .append(buildPath.toUpperCase())
                    .append(");")
                    .append("\n");
            }
        }

        sourceCode.append("    }" + "\n");
    }

    private static String getPrevious(Identifier id) {
        if (id.getNamespace().equals("minecraft")) {
            return "Blocks." + id.getPath().toUpperCase();
        } else if (id.getNamespace().equals(MOD_ID)) {
            return "RegisterBlockItems." + id.getPath().toUpperCase();
        } else {
            throw new IllegalArgumentException("Unsupported namespace: " + id.getNamespace());
        }
    }
}

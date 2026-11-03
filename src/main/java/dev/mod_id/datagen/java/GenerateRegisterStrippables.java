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
public final class GenerateRegisterStrippables {

    private static final String AUTO_GENERATED_WARNING = "// DATA-GENERATED FILE — DO NOT EDIT";

    private static final String PACKAGE_NAME = "dev.mod_id.content.registry";

    private static final String CLASS_ANNOTATION = "";

    private static final String CLASS_DECLARATION = "public final class";

    private static final String CLASS_NAME = "RegisterStrippables";

    private static final String IMPORTS = """
            import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
            """;

    private static final String CONSTANTS = "";

    private static final String INIT = "";

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

        sourceCode
            .append("    public static void init() {")
            .append("\n");

        for (BlockType blockType : BlockType.BLOCK_TYPES) {

            sourceCode.append("\n");

            for (BlockVariant blockVariant : blockType.blockVariants()) {

                // Skip non-strippable block variants
                if (blockVariant.equals(BlockVariant.STRIPPED__PALISADE) || blockVariant.equals(BlockVariant.STRIPED_SPIKED__PALISADE)) {
                    continue;
                }

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

                sourceCode
                    .append("        StrippableBlockRegistry.registerCopyState(RegisterBlockItems.")
                    .append(buildPath.toUpperCase())
                    .append(", RegisterBlockItems.STRIPPED_")
                    .append(buildPath.toUpperCase())
                    .append(");")
                    .append("\n");
            }
        }

        sourceCode
            .append("    }")
            .append("\n");
    }
}

package dev.mod_id.datagen.java;

import static dev.mod_id.Constants.MOD_ID;
import static dev.mod_id.datagen.data.BlockOverrides.checkForPathOverrides;
import static dev.mod_id.datagen.util.CreateJavaFile.createJavaFile;
import static dev.mod_id.datagen.util.RegistryUtils.doesBlockExist;
import static dev.mod_id.datagen.util.RegistryUtils.getBlockIdentifier;

import com.google.common.base.Strings;
import dev.mod_id.datagen.data.BlockExclusions;
import dev.mod_id.datagen.data.BlockType;
import dev.mod_id.datagen.data.BlockVariant;
import dev.mod_id.datagen.util.RecordUtils;
import net.minecraft.resources.Identifier;

public final class GenerateKeyBlockItems {

    private static final String AUTO_GENERATED_WARNING = "// DATA-GENERATED FILE — DO NOT EDIT";

    private static final String PACKAGE_NAME = "dev.mod_id.content.key";

    private static final String CLASS_ANNOTATION = "";

    private static final String CLASS_DECLARATION = "public final class";

    private static final String CLASS_NAME = "KeyBlockItems";

    private static final String IMPORTS = """
            import static dev.mod_id.Constants.resourceLocation;

            import net.minecraft.references.BlockItemId;
            """;

    private static final String CONSTANTS = "";

    private static final String INIT = "";

    private static final String PRIVATE_METHODS = """
                /**
                 * Creates a BlockItemId using the mod's resource location for the given path.
                 */
                private static BlockItemId create(String path) {
                    return BlockItemId.create(resourceLocation(path), resourceLocation(path));
                }
            """;

    public static void run() {

        StringBuilder sourceCode = new StringBuilder();

        // Header
        sourceCode.append(AUTO_GENERATED_WARNING + "\n");
        sourceCode.append("\n");
        sourceCode.append("package " + PACKAGE_NAME + ";" + "\n");
        if (!Strings.isNullOrEmpty(IMPORTS)) {
            sourceCode.append("\n");
            sourceCode.append(IMPORTS);
        }

        // Class
        sourceCode.append("\n");
        if (!Strings.isNullOrEmpty(CLASS_ANNOTATION)) {
            sourceCode.append(CLASS_ANNOTATION + "\n");
        }
        sourceCode.append(CLASS_DECLARATION + " " + CLASS_NAME + " {" + "\n");

        // Constants
        if (!Strings.isNullOrEmpty(CONSTANTS)) {
            sourceCode.append("\n");
            sourceCode.append(CONSTANTS);
        }

        // BlockItemIds
        processBlocks(sourceCode);

        // Init
        if (!Strings.isNullOrEmpty(INIT)) {
            sourceCode.append("\n");
            sourceCode.append(INIT);
        }

        // Footer
        if (!Strings.isNullOrEmpty(PRIVATE_METHODS)) {
            sourceCode.append("\n");
            sourceCode.append(PRIVATE_METHODS);
        }
        sourceCode.append("}" + "\n");

        // Create File
        createJavaFile(sourceCode, "src/main/java/" + PACKAGE_NAME.replace(".", "/"), CLASS_NAME + ".java");
    }

    private static void processBlocks(StringBuilder sourceCode) {

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

                sourceCode
                    .append("    public static final BlockItemId ")
                    .append(buildPath.toUpperCase())
                    .append(" = create(\"")
                    .append(buildPath.toLowerCase())
                    .append("\");")
                    .append("\n");
            }
        }
    }
}

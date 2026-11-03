package dev.mod_id.datagen.java;

import static dev.mod_id.Constants.MOD_ID;
import static dev.mod_id.datagen.data.BlockOverrides.checkForBlockOverrides;
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
public final class GenerateRegisterFlammables {

    private static final String AUTO_GENERATED_WARNING = "// DATA-GENERATED FILE — DO NOT EDIT";

    private static final String PACKAGE_NAME = "dev.mod_id.content.registry";

    private static final String CLASS_ANNOTATION = "";

    private static final String CLASS_DECLARATION = "public final class";

    private static final String CLASS_NAME = "RegisterFlammables";

    private static final String IMPORTS = """
            import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
            """;

    private static final String CONSTANTS = """
                private static final FlammableBlockRegistry FLAMMABLES = FlammableBlockRegistry.getDefaultInstance();
            """;

    public static void run() {

        StringBuilder sourceCode = new StringBuilder();

        // Header
        sourceCode.append(AUTO_GENERATED_WARNING).append("\n");
        sourceCode.append("\n");
        sourceCode.append("package " + PACKAGE_NAME + ";").append("\n");
        sourceCode.append("\n");
        if (IMPORTS != null && !IMPORTS.isEmpty()) {
            sourceCode.append(IMPORTS).append("\n");
        }
        if (CLASS_ANNOTATION != null && !CLASS_ANNOTATION.isEmpty()) {
            sourceCode.append(CLASS_ANNOTATION).append("\n");
        }
        sourceCode.append(CLASS_DECLARATION + " " + CLASS_NAME + " {").append("\n");
        sourceCode.append("\n");

        // Constants
        sourceCode.append(CONSTANTS);
        sourceCode.append("\n");

        // Block Registration
        processBlocks(sourceCode);

        sourceCode.append("}").append("\n");

        // Create File
        createJavaFile(sourceCode, "src/main/java/" + PACKAGE_NAME.replace(".", "/"), CLASS_NAME + ".java");
    }

    private static void processBlocks(StringBuilder sourceCode) {

        sourceCode.append("    public static void init() {").append("\n");

        for (BlockType blockType : BlockType.BLOCK_TYPES) {

            // Skip non-flammable block types
            if (!blockType.flammable()) {
                continue;
            }

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
                    .append("        FLAMMABLES.add(RegisterBlockItems.")
                    .append(buildPath.toUpperCase())
                    .append(", 5, 20);")
                    .append("\n");
            }
        }

        sourceCode.append("    }").append("\n");
    }
}

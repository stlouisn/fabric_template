package dev.mod_id.datagen.util;

import static dev.mod_id.Constants.MOD_ID;

import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class CreateJavaFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void createJavaFile(StringBuilder sourceCode, String filePath, String fileName) {
        final Path directory = Path.of("../../" + filePath);
        final Path sourceFile = directory.resolve(fileName);
        try {
            Files.createDirectories(directory);
            Files.writeString(sourceFile, sourceCode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Java file: " + sourceFile.toAbsolutePath(), e);
        }
    }
}

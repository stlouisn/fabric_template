package dev.mod_id.datagen.utils;

import static dev.mod_id.util.Constants.MOD_ID;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public final class CreateJavaFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void createJavaFile(@NonNull StringBuilder sourceCode, @NonNull String filePath, @NonNull String fileName) {
        Path path = Path.of("../../" + filePath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                LOGGER.error("ERROR: Failed to create directory: : {}", filePath);
                throw new RuntimeException(e);
            }
        }
        try {
            File sourceFile = new File("../../" + filePath + "/" + fileName);
            try (FileWriter writer = new FileWriter(sourceFile)) {
                writer.write(sourceCode.toString());
            }
        } catch (IOException e) {
            LOGGER.error("ERROR: Failed to create file: {}/{}", filePath, fileName);
            throw new RuntimeException(e);
        }
    }
}

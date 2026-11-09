package dev.mod_id.compat.reliable;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

import static dev.mod_id.Constants.MOD_ID;

public final class ReliableRecipes {

    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("reliable_recipes");

    private static final Path FILE = PATH.resolve(MOD_ID + "_mod_overrides.json");

    private static final String JSON = """
                [
                  {
                    "action": "remove_recipe",
                    "id": [
                      "minecraft:tipped_arrow"
                      ]
                  }
                ]
                """;

    public static void generate() {

        StringBuilder sourceCode = new StringBuilder();
        sourceCode.append(JSON);

        try {
            Files.createDirectories(PATH);
            Files.writeString(FILE, sourceCode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Reliable Recipe file: " + FILE.toAbsolutePath(), e);
        }
    }
}

package dev.mod_id.datagen.data;

public final class BlockOverrides {

    public static String checkForPathOverrides(String path) {

        return switch (path) {
            case "crimson_log" -> "crimson_stem";
            case "warped_log" -> "warped_stem";
            case "stripped_crimson_log" -> "stripped_crimson_stem";
            case "stripped_warped_log" -> "stripped_warped_stem";
            default -> path;
        };
    }
}

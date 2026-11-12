package dev.mod_id.datagen.data;

public final class BlockOverrides {

    public static String checkForPathOverrides(String path) {

        if (path.contains("crimson_log")) {
            return path.replace("crimson_log", "crimson_stem");
        }
        if (path.contains("warped_log")) {
            return path.replace("warped_log", "warped_stem");
        }
        return path;
    }
}

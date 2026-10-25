package dev.mod_id.datagen.util;

import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings({"SameParameterValue", "unused"})
public class StringUtils {

    public static String formatName(String name) {
        return Arrays.stream(name.split("_")).map(word -> word.isEmpty() ? "" : Character.toUpperCase(word.charAt(0)) + word.substring(1)).collect(Collectors.joining(" "));
    }
}

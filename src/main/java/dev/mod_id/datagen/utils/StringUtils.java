package dev.ores.datagen.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class StringUtils {

    public static String formatName(String name) {
        return Arrays.stream(name.split("_")).map(word -> word.isEmpty() ? "" : Character.toUpperCase(word.charAt(0)) + word.substring(1)).collect(Collectors.joining(" "));
    }
}

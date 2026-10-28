package dev.mod_id.util;

import static dev.mod_id.util.Constants.MOD_ID;

import net.minecraft.resources.Identifier;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class IdentifierUtils {

    public static Identifier resourceLocation(final String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static Identifier resourceLocation(final String namespace, final String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }
}

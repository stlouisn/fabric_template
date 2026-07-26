package com.mod_id.util;

import net.minecraft.resources.Identifier;

public final class Constants {

    public static final String MOD_ID = "mod_id";
    public static final String MOD_NAME = "Mod Name";

    public static Identifier resourceLocation(final String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static Identifier resourceLocation(final String namespace, final String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }
}

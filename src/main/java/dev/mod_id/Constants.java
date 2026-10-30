package dev.mod_id;

import net.minecraft.resources.Identifier;

public final class Constants {

    public static final String MOD_ID = "nimble";
    public static final String MOD_NAME = "Nimble";

    public static Identifier resourceLocation(final String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static Identifier resourceLocation(final String namespace, final String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }
}

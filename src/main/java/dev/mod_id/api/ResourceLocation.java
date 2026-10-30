package dev.mod_id.api;

import static dev.mod_id.Constants.MOD_ID;

import net.minecraft.resources.Identifier;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class ResourceLocation {

    public static Identifier resourceLocation(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static Identifier resourceLocation(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }
}

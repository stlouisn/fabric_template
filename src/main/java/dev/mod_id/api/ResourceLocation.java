package dev.mod_id;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ResourceLocation {

    public static Identifier resourceLocation(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static Identifier resourceLocation(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }
}

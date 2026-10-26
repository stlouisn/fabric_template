package dev.mod_id.util;

import static dev.mod_id.util.Constants.MOD_ID;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.regex.Pattern;
import net.minecraft.resources.Identifier;
import net.minecraft.util.IdentifierPattern;
import org.jspecify.annotations.Nullable;

@SuppressWarnings({"SameParameterValue", "unused"})
public class IdentifierUtils {

    public static Identifier resourceLocation(final String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static Identifier resourceLocation(final String namespace, final String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }

    public static IdentifierPattern resourcePattern(final Pattern path) {
        return resourcePattern(null, path);
    }

    public static IdentifierPattern resourcePattern(@Nullable final Pattern namespace, @Nullable final Pattern path) {
        JsonObject json = new JsonObject();
        if (namespace != null) {
            json.addProperty("namespace", namespace.pattern());
        }
        if (path != null) {
            json.addProperty("path", path.pattern());
        }
        return IdentifierPattern.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();
    }
}

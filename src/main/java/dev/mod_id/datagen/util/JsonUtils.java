package dev.mod_id.datagen.util;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.regex.Pattern;
import net.minecraft.util.IdentifierPattern;
import org.jspecify.annotations.Nullable;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class JsonUtils {

    /**
     * Returns an identifier pattern matching any namespace with the specified path pattern.
     */
    public static IdentifierPattern resourcePattern(Pattern path) {
        return resourcePattern(null, path);
    }

    /**
     * Returns an identifier pattern matching the specified namespace and path patterns.
     */
    public static IdentifierPattern resourcePattern(@Nullable Pattern namespace, @Nullable Pattern path) {
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

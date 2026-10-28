package dev.mod_id.datagen.util;

import static dev.mod_id.util.Constants.MOD_ID;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.util.regex.Pattern;
import net.minecraft.resources.Identifier;
import net.minecraft.util.IdentifierPattern;
import org.jspecify.annotations.Nullable;

@SuppressWarnings({"SameParameterValue", "unused"})
public class IdentifierUtils {

    public static IdentifierPattern resourcePattern(Pattern path) {
        return resourcePattern(null, path);
    }

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

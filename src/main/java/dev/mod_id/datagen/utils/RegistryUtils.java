package dev.mod_id.datagen.utils;

import static dev.mod_id.util.Constants.MOD_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class RegistryUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * Checks whether a block exists in the block registry with the specified path.
     */
    public static boolean doesBlockExist(String path) {
        return getIdentifier(BuiltInRegistries.BLOCK, path, null, null) != null;
    }

    /**
     * Checks whether a block exists in the block registry matching the specified path and filter predicate.
     */
    public static boolean doesBlockExist(String path, @Nullable Predicate<Identifier> filter) {
        return getIdentifier(BuiltInRegistries.BLOCK, path, filter, null) != null;
    }

    /**
     * Checks whether a block exists in the block registry matching the specified path, filter predicate, and collision
     * rules.
     */
    public static boolean doesBlockExist(String path, @Nullable Predicate<Identifier> filter, @Nullable BiPredicate<String, String> collisionRules) {
        return getIdentifier(BuiltInRegistries.BLOCK, path, filter, collisionRules) != null;
    }

    /**
     * Checks whether an item exists in the item registry with the specified path.
     */
    public static boolean doesItemExist(String path) {
        return getIdentifier(BuiltInRegistries.ITEM, path, null, null) != null;
    }

    /**
     * Checks whether an item exists in the item registry matching the specified path and filter predicate.
     */
    public static boolean doesItemExist(String path, @Nullable Predicate<Identifier> filter) {
        return getIdentifier(BuiltInRegistries.ITEM, path, filter, null) != null;
    }

    /**
     * Checks whether an item exists in the item registry matching the specified path, filter predicate, and collision
     * rules.
     */
    public static boolean doesItemExist(String path, @Nullable Predicate<Identifier> filter, @Nullable BiPredicate<String, String> collisionRules) {
        return getIdentifier(BuiltInRegistries.ITEM, path, filter, collisionRules) != null;
    }

    /**
     * Retrieves the identifier of a block from the block registry using the specified path.
     */
    @Nullable
    public static Identifier getBlockIdentifier(String path) {
        return getIdentifier(BuiltInRegistries.BLOCK, path, null, null);
    }

    /**
     * Retrieves the identifier of a block from the block registry using the specified path and filter predicate.
     */
    @Nullable
    public static Identifier getBlockIdentifier(String path, @Nullable Predicate<Identifier> filter) {
        return getIdentifier(BuiltInRegistries.BLOCK, path, filter, null);
    }

    /**
     * Retrieves the identifier of a block from the block registry using the specified path, filter predicate, and collision
     * rules.
     */
    @Nullable
    public static Identifier getBlockIdentifier(String path, @Nullable Predicate<Identifier> filter, @Nullable BiPredicate<String, String> collisionRules) {
        return getIdentifier(BuiltInRegistries.BLOCK, path, filter, collisionRules);
    }

    /**
     * Retrieves the identifier of an item from the item registry using the specified path.
     */
    @Nullable
    public static Identifier getItemIdentifier(String path) {
        return getIdentifier(BuiltInRegistries.ITEM, path, null, null);
    }

    /**
     * Retrieves the identifier of an item from the item registry using the specified path and filter predicate.
     */
    @Nullable
    public static Identifier getItemIdentifier(String path, @Nullable Predicate<Identifier> filter) {
        return getIdentifier(BuiltInRegistries.ITEM, path, filter, null);
    }

    /**
     * Retrieves the identifier of an item from the item registry using the specified path, filter predicate, and collision
     * rules.
     */
    @Nullable
    public static Identifier getItemIdentifier(String path, @Nullable Predicate<Identifier> filter, @Nullable BiPredicate<String, String> collisionRules) {
        return getIdentifier(BuiltInRegistries.ITEM, path, filter, collisionRules);
    }

    /**
     * Searches a given registry for an identifier matching the specified path, subject to optional filtering and collision
     * rules.
     */
    @Nullable
    public static <T> Identifier getIdentifier(Registry<T> registry, String path, @Nullable Predicate<Identifier> filter, @Nullable BiPredicate<String, String> collisionRules) {
        List<Identifier> matches = new ArrayList<>();
        for (Identifier id : registry.keySet()) {
            if (filter != null && !filter.test(id)) {
                continue;
            }
            if (id.getPath().equals(path)) {
                if (collisionRules != null && collisionRules.test(id.getNamespace(), path)) {
                    LOGGER.debug("Ignoring identifier '{}' due to collision rule.", id);
                    continue;
                }
                matches.add(id);
            }
        }
        if (matches.isEmpty()) {
            return null;
        }
        if (matches.size() > 1) {
            Identifier matchId = null;
            for (Identifier id : matches) {
                if (id.getNamespace().equals(MOD_ID)) {
                    matchId = id;
                } else if (id.getNamespace().equals("minecraft") && matchId == null) {
                    matchId = id;
                }
            }
            if (matchId == null) {
                matchId = matches.getFirst();
            }
            LOGGER.warn("Multiple matches found with path: {}. Namespace collision detected among: '{}'. Falling back to: {}", path, matches, matchId);
            return matchId;
        }
        return matches.getFirst();
    }
}

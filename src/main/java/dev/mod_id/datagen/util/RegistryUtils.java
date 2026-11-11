package dev.mod_id.datagen.util;

import static dev.mod_id.Constants.MOD_ID;

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
     * Checks whether a block exists in the block registry with the specified identifier.
     */
    public static boolean doesBlockExist(Identifier id) {
        return BuiltInRegistries.BLOCK.containsKey(id);
    }

    /**
     * Checks whether a block exists in the block registry with the specified path.
     */
    public static boolean doesBlockExist(String path) {
        return doesBlockExist(path, null, null);
    }

    /**
     * Checks whether a block exists in the block registry using the specified namespace and path.
     */
    public static boolean doesBlockExist(String namespace, String path) {
        return doesBlockExist(path, identifier -> identifier.getNamespace().equals(namespace));
    }

    /**
     * Checks whether a block exists in the block registry matching the specified path and filter predicate.
     */
    public static boolean doesBlockExist(String path, @Nullable Predicate<Identifier> filter) {
        return doesBlockExist(path, filter, null);
    }

    /**
     * Checks whether a block exists in the block registry matching the specified path, filter predicate, and collision
     * rules.
     */
    public static boolean doesBlockExist(String path, @Nullable Predicate<Identifier> filter, @Nullable BiPredicate<String, String> collisionRules) {
        final Identifier id = getIdentifier(BuiltInRegistries.BLOCK, path, filter, collisionRules);
        return id != null;
    }

    /**
     * Checks whether an item exists in the item registry with the specified identifier.
     */
    public static boolean doesItemExist(Identifier id) {
        return BuiltInRegistries.ITEM.containsKey(id);
    }

    /**
     * Checks whether an item exists in the item registry with the specified path.
     */
    public static boolean doesItemExist(String path) {
        return doesItemExist(path, null, null);
    }

    /**
     * Checks whether an item exists in the item registry using the specified namespace and path.
     */
    public static boolean doesItemExist(String namespace, String path) {
        return doesItemExist(path, identifier -> identifier.getNamespace().equals(namespace));
    }

    /**
     * Checks whether an item exists in the item registry matching the specified path and filter predicate.
     */
    public static boolean doesItemExist(String path, @Nullable Predicate<Identifier> filter) {
        return doesItemExist(path, filter, null);
    }

    /**
     * Checks whether an item exists in the item registry matching the specified path, filter predicate, and collision
     * rules.
     */
    public static boolean doesItemExist(String path, @Nullable Predicate<Identifier> filter, @Nullable BiPredicate<String, String> collisionRules) {
        final Identifier id = getIdentifier(BuiltInRegistries.ITEM, path, filter, collisionRules);
        return id != null;
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
     * Searches a registry for identifiers with the specified path, applying optional filtering and collision rules.
     *
     * @param registry the registry to search
     * @param path the path to match
     * @param filter optional predicate; matching identifiers that fail it are skipped
     * @param collisionRules optional predicate; matching identifiers it rejects are skipped and logged
     * @return the preferred matching identifier, or {@code null} if none match
     */
    @Nullable
    public static <T> Identifier getIdentifier(Registry<T> registry, String path, @Nullable Predicate<Identifier> filter, @Nullable BiPredicate<String, String> collisionRules) {
        List<Identifier> matches = new ArrayList<>();
        for (Identifier id : registry.keySet()) {
            if (filter != null && !filter.test(id)) {
                continue;
            }
            if (!id.getPath().equals(path)) {
                continue;
            }
            if (collisionRules != null && collisionRules.test(id.getNamespace(), path)) {
                LOGGER.warn("Ignoring identifier '{}' due to collision rule.", id);
                continue;
            }
            matches.add(id);
        }
        if (matches.isEmpty()) {
            return null;
        }
        if (matches.size() == 1) {
            return matches.getFirst();
        }
        final Predicate<Identifier> mod_id = id -> id.getNamespace().equals(MOD_ID);
        final Predicate<Identifier> minecraft = id -> id.getNamespace().equals("minecraft");
        final Identifier match = matches.stream().filter(mod_id).findFirst().orElseGet(() -> matches.stream().filter(minecraft).findFirst().orElse(matches.getFirst()));
        LOGGER.error("Multiple matches found with path: {}. Namespace collision detected among: '{}'. Falling back to: {}", path, matches, match);
        return match;
    }
}

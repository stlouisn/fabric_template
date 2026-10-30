package dev.mod_id.datagen.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public final class ClusterModelWriter implements ModelWriter {

    private final PackOutput.PathProvider pathProvider;

    public ClusterModelWriter(PackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
    }

    public CompletableFuture<?> write(CachedOutput cache, Block block) {

        // Retrieve block identifier from the registry
        final Identifier id = BuiltInRegistries.BLOCK.getKey(block);

        // Initialize root JSON object and define parent model reference
        final JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:item/generated");

        // Configure and attach model textures object
        final JsonObject textures = new JsonObject();
        textures.addProperty("layer0", id.getNamespace() + ":block/" + id.getPath());
        root.add("textures", textures);

        // Initialize display transforms container object and add the head transform
        final JsonObject display = createDisplayTransform();

        // Attach display transforms object to root JSON
        root.add("display", display);

        // Resolve the output file path and save the stable JSON model asset
        final Path path = pathProvider.json(id);
        return DataProvider.saveStable(cache, root, path);
    }

    private static JsonObject createDisplayTransform() {

        // Initialize display transforms container object
        final JsonObject display = new JsonObject();

        // Configure head perspective translation
        final JsonArray translation = new JsonArray();
        translation.add(0);
        translation.add(14);
        translation.add(-5);

        // Initialize head perspective transform object
        final JsonObject head = new JsonObject();
        head.add("translation", translation);

        // Attach head perspective transform to display transforms
        display.add("head", head);
        return display;
    }
}

package dev.mod_id.datagen.provider;

import static dev.mod_id.util.Constants.MOD_ID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.mod_id.content.registry.RegisterBlockItems;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenRoseQuartzClusterModelProvider implements DataProvider {

    private final FabricPackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registriesFuture;

    public DatagenRoseQuartzClusterModelProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        this.output = output;
        this.registriesFuture = registriesFuture;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return this.registriesFuture.thenCompose(_ -> this.writeFile(cache, RegisterBlockItems.ROSE_QUARTZ_CLUSTER));
    }

    private Path resolveItemModelPath(Identifier id) {
        final PackOutput.PathProvider provider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
        return provider.json(id);
    }

    @SuppressWarnings("SameParameterValue")
    private CompletableFuture<?> writeFile(CachedOutput cache, Block block) {

        // Retrieve block identifier from the registry
        final Identifier id = BuiltInRegistries.BLOCK.getKey(block);

        // Initialize root JSON object and define parent model reference
        final JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:item/generated");

        // Configure and attach model textures object
        final JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":block/" + id.getPath());
        root.add("textures", textures);

        // Initialize display transforms container object
        final JsonObject display = new JsonObject();

        // Configure head camera perspective transform object
        final JsonObject head = new JsonObject();

        // Add translation coordinates for head display mode
        final JsonArray translation = new JsonArray();
        translation.add(0);
        translation.add(14);
        translation.add(-5);
        head.add("translation", translation);
        display.add("head", head);

        // Attach display transforms object to root JSON
        root.add("display", display);

        // Resolve the output file path and save the stable JSON model asset
        final Path path = resolveItemModelPath(id);
        return DataProvider.saveStable(cache, root, path);
    }

    @Override
    public String getName() {
        return "Cluster Model Definitions";
    }
}

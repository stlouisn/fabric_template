package dev.mod_id.datagen.model;

import static dev.mod_id.util.Constants.MOD_ID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.mod_id.content.registry.RegisterBlocks;
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

public final class DatagenRoseQuartzBudModelProvider implements DataProvider {

    private final FabricPackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registriesFuture;

    public DatagenRoseQuartzBudModelProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        this.output = output;
        this.registriesFuture = registriesFuture;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return this.registriesFuture.thenCompose(_ -> CompletableFuture.allOf(this.writeFile(cache, RegisterBlocks.SMALL_ROSE_QUARTZ_BUD, true, 6, 7), this.writeFile(cache, RegisterBlocks.MEDIUM_ROSE_QUARTZ_BUD, false, 0, 6), this.writeFile(cache, RegisterBlocks.LARGE_ROSE_QUARTZ_BUD, false, 0, 4)));
    }

    private Path resolveItemModelPath(Identifier id) {
        final PackOutput.PathProvider provider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
        return provider.json(id);
    }

    @SuppressWarnings("ExtractMethodRecommender")
    private CompletableFuture<?> writeFile(CachedOutput cache, Block block, boolean overrideFirstPerson, int firstPersonTranslateY, int fixedTranslateY) {

        // Retrieve block identifier from the registry
        final Identifier id = BuiltInRegistries.BLOCK.getKey(block);

        // Initialize root JSON object and define parent model reference
        final JsonObject root = new JsonObject();
        root.addProperty("parent", "minecraft:item/amethyst_bud");

        // Configure and attach model textures object
        final JsonObject textures = new JsonObject();
        textures.addProperty("layer0", MOD_ID + ":block/" + id.getPath());
        root.add("textures", textures);

        // Initialize display transforms container object
        final JsonObject display = new JsonObject();

        // Conditionally configure first-person right hand camera transform if enabled
        if (overrideFirstPerson) {
            final JsonObject firstPerson = new JsonObject();
            final JsonArray fp_rotation = new JsonArray();
            fp_rotation.add(0);
            fp_rotation.add(-90);
            fp_rotation.add(25);
            firstPerson.add("rotation", fp_rotation);
            final JsonArray fp_translation = new JsonArray();
            fp_translation.add(0);
            fp_translation.add(firstPersonTranslateY);
            fp_translation.add(0);
            firstPerson.add("translation", fp_translation);
            final JsonArray fp_scale = new JsonArray();
            fp_scale.add(0.68);
            fp_scale.add(0.68);
            fp_scale.add(0.68);
            firstPerson.add("scale", fp_scale);
            display.add("firstperson_righthand", firstPerson);
        }

        // Configure fixed camera perspective transform object
        final JsonObject fixed = new JsonObject();
        final JsonArray fixed_translation = new JsonArray();
        fixed_translation.add(0);
        fixed_translation.add(fixedTranslateY);
        fixed_translation.add(0);
        fixed.add("translation", fixed_translation);
        display.add("fixed", fixed);

        // Attach display transforms object to root JSON
        root.add("display", display);

        // Resolve the output file path and save the stable JSON model asset
        final Path path = resolveItemModelPath(id);
        return DataProvider.saveStable(cache, root, path);
    }

    @Override
    public String getName() {
        return "Bud Model Definitions";
    }
}

package dev.mod_id.datagen.model;

import static dev.mod_id.Constants.MOD_ID;

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

@SuppressWarnings({"SameParameterValue", "unused"})
public final class LargeBudModelWriter implements ModelWriter {

    private static final boolean OVERRIDE_FIRST_PERSON = false;
    private static final int FIRST_PERSON_TRANSLATE_Y = 0;
    private static final int FIXED_TRANSLATE_Y = 4;

    private final PackOutput.PathProvider pathProvider;

    public LargeBudModelWriter(PackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
    }

    public CompletableFuture<?> write(CachedOutput cache, Block block) {

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
        if (OVERRIDE_FIRST_PERSON) {
            addFirstPersonTransform(display);
        }

        // Configure fixed camera perspective transform object
        final JsonArray fixed_translation = new JsonArray();
        fixed_translation.add(0);
        fixed_translation.add(FIXED_TRANSLATE_Y);
        fixed_translation.add(0);

        // Initialize fixed camera perspective transform object
        final JsonObject fixed = new JsonObject();
        fixed.add("translation", fixed_translation);

        // Attach fixed camera perspective transform to display transforms
        display.add("fixed", fixed);

        // Attach display transforms object to root JSON
        root.add("display", display);

        // Resolve the output file path and save the stable JSON model asset
        final Path path = pathProvider.json(id);
        return DataProvider.saveStable(cache, root, path);
    }

    private void addFirstPersonTransform(JsonObject display) {

        // Initialize right-hand first-person transform object
        final JsonObject firstPerson = new JsonObject();

        // Configure first-person right-hand rotation
        final JsonArray fp_rotation = new JsonArray();
        fp_rotation.add(0);
        fp_rotation.add(-90);
        fp_rotation.add(25);
        firstPerson.add("rotation", fp_rotation);

        // Configure first-person right-hand translation
        final JsonArray fp_translation = new JsonArray();
        fp_translation.add(0);
        fp_translation.add(FIRST_PERSON_TRANSLATE_Y);
        fp_translation.add(0);
        firstPerson.add("translation", fp_translation);

        // Configure first-person right-hand scale
        final JsonArray fp_scale = new JsonArray();
        fp_scale.add(0.68);
        fp_scale.add(0.68);
        fp_scale.add(0.68);
        firstPerson.add("scale", fp_scale);

        // Attach first-person right-hand transform to display transforms
        display.add("firstperson_righthand", firstPerson);
    }
}

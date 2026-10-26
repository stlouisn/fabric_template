package dev.mod_id.datagen.provider;

import static dev.mod_id.util.IdentifierUtils.resourceLocation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.mod_id.content.key.KeyTrimMaterials;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public class DatagenTrimItemsAtlasProvider implements DataProvider {

    private static final String ROSE_QUARTZ_NAMESPACE = KeyTrimMaterials.ROSE_QUARTZ.identifier().getNamespace().toLowerCase();
    private static final String ROSE_QUARTZ_PATH = KeyTrimMaterials.ROSE_QUARTZ.identifier().getPath().toLowerCase();

    private final FabricPackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registriesFuture;

    public DatagenTrimItemsAtlasProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        this.output = output;
        this.registriesFuture = registriesFuture;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return this.registriesFuture.thenCompose(_ -> this.writeFile(cache));
    }

    private Path resolveAtlasPath(Identifier id) {
        final PackOutput.PathProvider provider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "atlases");
        return provider.json(id);
    }

    @SuppressWarnings("ExtractMethodRecommender")
    private CompletableFuture<?> writeFile(CachedOutput cache) {

        // Define identifier for the target armour trims atlas file
        final Identifier id = resourceLocation("minecraft", "items");

        // Initialize root JSON object for the atlas definition
        final JsonObject rootJson = new JsonObject();

        // Initialize the top-level sources array container
        final JsonArray sourcesArray = new JsonArray();

        // Configure paletted permutations source for armour trim patterns
        final JsonObject patternPermutationSource = new JsonObject();
        patternPermutationSource.addProperty("type", "minecraft:paletted_permutations");

        // Set base palette key texture used for colour remapping
        patternPermutationSource.addProperty("palette_key", "minecraft:trims/color_palettes/trim_palette");

        // Define custom material colour map permutations
        final JsonObject patternPermutations = new JsonObject();
        patternPermutations.addProperty(ROSE_QUARTZ_PATH, ROSE_QUARTZ_NAMESPACE + ":trims/color_palettes/" + ROSE_QUARTZ_PATH);
        patternPermutationSource.add("permutations", patternPermutations);

        // Define list of item texture paths that will use the colour palette permutations
        final List<String> itemTextures = List.of(
            "minecraft:trims/items/helmet_trim",
            "minecraft:trims/items/chestplate_trim",
            "minecraft:trims/items/leggings_trim",
            "minecraft:trims/items/boots_trim"
        );

        // Populate texture paths array
        final JsonArray patternTextures = new JsonArray();
        for (final String texture : itemTextures) {
            patternTextures.add(texture);
        }
        patternPermutationSource.add("textures", patternTextures);

        // Push the paletted permutations source block into the sources array
        sourcesArray.add(patternPermutationSource);

        // Attach the completed sources array to the root JSON object
        rootJson.add("sources", sourcesArray);

        // Resolve the target file output path and save the stable JSON asset
        final Path path = resolveAtlasPath(id);
        return DataProvider.saveStable(cache, rootJson, path);
    }

    @Override
    public String getName() {
        return "Armor Trim Items Atlas Definitions";
    }
}

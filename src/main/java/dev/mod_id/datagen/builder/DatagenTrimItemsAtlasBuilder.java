package dev.mod_id.datagen.builder;

import static dev.mod_id.api.ResourceLocation.resourceLocation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimMaterial;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenTrimItemsAtlasBuilder implements DataProvider {

    private static final Set<ResourceKey<TrimMaterial>> trimMaterials = new HashSet<>();

    private static final String[] ITEM_TEXTURES = {
        "minecraft:trims/items/boots_trim",
        "minecraft:trims/items/chestplate_trim",
        "minecraft:trims/items/helmet_trim",
        "minecraft:trims/items/leggings_trim"
    };

    private final PackOutput.PathProvider pathProvider;

    protected DatagenTrimItemsAtlasBuilder(FabricPackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "atlases");
        this.generateTrimMaterials();
    }

    /**
     * Register this mod's trim materials here in subclasses.
     */
    protected void generateTrimMaterials() {
    }

    protected final void addTrimMaterial(ResourceKey<TrimMaterial> material) {
        if (!trimMaterials.add(material)) {
            throw new IllegalStateException("Tried to register trim material twice for id: " + material);
        }
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        final JsonObject rootJson = new JsonObject();
        final JsonArray sources = new JsonArray();
        final JsonObject permutationsSource = new JsonObject();
        permutationsSource.addProperty("type", "minecraft:paletted_permutations");
        permutationsSource.addProperty("palette_key", "minecraft:trims/color_palettes/trim_palette");
        final JsonObject permutations = new JsonObject();
        for (ResourceKey<TrimMaterial> material : trimMaterials) {
            final Identifier id = material.identifier();
            final String namespace = id.getNamespace().toLowerCase();
            final String path = id.getPath().toLowerCase();
            permutations.addProperty(path, namespace + ":trims/color_palettes/" + path);
        }
        permutationsSource.add("permutations", permutations);
        final JsonArray textures = new JsonArray();
        for (String texture : ITEM_TEXTURES) {
            textures.add(texture);
        }
        permutationsSource.add("textures", textures);
        sources.add(permutationsSource);
        rootJson.add("sources", sources);
        final Path path = pathProvider.json(resourceLocation("minecraft", "items"));
        return DataProvider.saveStable(cache, rootJson, path);
    }

    @Override
    public String getName() {
        return "Armor Trim Items Atlas Definitions";
    }
}

package dev.mod_id.datagen.builder;

import static dev.mod_id.util.IdentifierUtils.resourceLocation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimMaterial;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenTrimArmorAtlasBuilder implements DataProvider {

    private static final Set<ResourceKey<TrimMaterial>> trimMaterials = new HashSet<>();

    private final FabricPackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registriesFuture;

    protected DatagenTrimArmorAtlasBuilder(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        this.output = output;
        this.registriesFuture = registriesFuture;
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
        return registriesFuture.thenCompose(registries -> writeFile(cache, registries));
    }

    private CompletableFuture<?> writeFile(CachedOutput cache, HolderLookup.Provider registries) {
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
        final List<String> assetNames = new ArrayList<>();
        registries.lookupOrThrow(Registries.TRIM_PATTERN).listElementIds().forEach(patternKey -> registries.lookupOrThrow(Registries.TRIM_PATTERN).get(patternKey).map(Holder::value).ifPresent(pattern -> assetNames.add(pattern.assetId().getPath())));
        assetNames.sort(String::compareTo);
        for (String assetName : assetNames) {
            textures.add("minecraft:trims/entity/humanoid/" + assetName);
            textures.add("minecraft:trims/entity/humanoid_leggings/" + assetName);
        }
        permutationsSource.add("textures", textures);
        sources.add(permutationsSource);
        rootJson.add("sources", sources);
        final PackOutput.PathProvider pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "atlases");
        java.nio.file.Path path = pathProvider.json(resourceLocation("minecraft", "armor_trims"));
        return DataProvider.saveStable(cache, rootJson, path);
    }

    @Override
    public String getName() {
        return "Trim Armor Atlas Definitions";
    }
}

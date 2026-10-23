package dev.mod_id.datagen.provider;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import dev.mod_id.util.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.DetectedVersion;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.ResourceFilterSection;
import net.minecraft.util.IdentifierPattern;
import net.minecraft.util.Util;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenPackMetadataGenerator extends PackMetadataGenerator {

    private static final Gson ORDERED_GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final FabricPackOutput output;
    private final List<IdentifierPattern> filterPatterns = new ArrayList<>();
    private final PackMetadataSection packMetadataSection;

    public DatagenPackMetadataGenerator(FabricPackOutput output) {
        super(output);
        this.output = output;
        this.packMetadataSection = new PackMetadataSection(Component.literal(Constants.MOD_NAME + " Resource Pack"), DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange());

        addMinecraftFilterBlock("advancement/recipes/misc/", FILTERED_VANILLA_ADVANCEMENTS);
        addMinecraftFilterBlock("recipe/", FILTERED_VANILLA_RECIPES);
    }

    private static final List<String> FILTERED_VANILLA_ADVANCEMENTS = List.of(
        "netherite_scrap.json",
        "netherite_scrap_from_blasting.json",
        "netherite_ingot.json"
    );

    private static final List<String> FILTERED_VANILLA_RECIPES = List.of(
        "coal_from_smelting_coal_ore.json",
        "coal_from_smelting_deepslate_coal_ore.json",
        "coal_from_blasting_coal_ore.json",
        "coal_from_blasting_deepslate_coal_ore.json",
        "copper_ingot_from_smelting_deepslate_copper_ore.json",
        "copper_ingot_from_blasting_deepslate_copper_ore.json",
        "diamond_from_smelting_diamond_ore.json",
        "diamond_from_smelting_deepslate_diamond_ore.json",
        "diamond_from_blasting_diamond_ore.json",
        "diamond_from_blasting_deepslate_diamond_ore.json",
        "emerald_from_smelting_emerald_ore.json",
        "emerald_from_smelting_deepslate_emerald_ore.json",
        "emerald_from_blasting_emerald_ore.json",
        "emerald_from_blasting_deepslate_emerald_ore.json",
        "gold_ingot_from_smelting_deepslate_gold_ore.json",
        "gold_ingot_from_smelting_nether_gold_ore.json",
        "gold_ingot_from_blasting_deepslate_gold_ore.json",
        "gold_ingot_from_blasting_nether_gold_ore.json",
        "iron_ingot_from_smelting_deepslate_iron_ore.json",
        "iron_ingot_from_blasting_deepslate_iron_ore.json",
        "lapis_lazuli_from_smelting_lapis_ore.json",
        "lapis_lazuli_from_smelting_deepslate_lapis_ore.json",
        "lapis_lazuli_from_blasting_lapis_ore.json",
        "lapis_lazuli_from_blasting_deepslate_lapis_ore.json",
        "netherite_ingot.json",
        "netherite_scrap.json",
        "netherite_scrap_from_blasting.json",
        "quartz.json",
        "quartz_from_blasting.json",
        "redstone_from_blasting_deepslate_redstone_ore.json",
        "redstone_from_blasting_redstone_ore.json",
        "redstone_from_smelting_deepslate_redstone_ore.json",
        "redstone_from_smelting_redstone_ore.json"
    );

    private void addMinecraftFilterBlock(String path, List<String> filenames) {
        final Pattern namespace = Pattern.compile("minecraft");
        addFilterBlock(namespace, path, filenames);
    }

    private void addFilterBlock(Pattern namespace, String path, List<String> filenames) {
        for (String filename : filenames) {
            filterPatterns.add(new IdentifierPattern(
                Optional.of(namespace),
                Optional.of(Pattern.compile(path + filename))
            ));
        }
    }

    @Override
    public <T> DatagenPackMetadataGenerator add(MetadataSectionType<T> type, T value) {
        return this;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        final JsonObject result = new JsonObject();
        result.add("pack", PackMetadataSection.SERVER_TYPE.codec().encodeStart(JsonOps.INSTANCE, this.packMetadataSection).getOrThrow(IllegalArgumentException::new));
        if (!this.filterPatterns.isEmpty()) {
            final ResourceFilterSection filterSection = new ResourceFilterSection(List.copyOf(this.filterPatterns));
            result.add("filter", ResourceFilterSection.TYPE.codec().encodeStart(JsonOps.INSTANCE, filterSection).getOrThrow(IllegalArgumentException::new));
        }
        final Path path = this.output.getOutputFolder().resolve("pack.mcmeta");
        return CompletableFuture.runAsync(() -> {
            try {
                final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                final HashingOutputStream hashedBytes = new HashingOutputStream(Hashing.sha256(), bytes);
                try (final OutputStreamWriter writer = new OutputStreamWriter(hashedBytes, StandardCharsets.UTF_8)) {
                    ORDERED_GSON.toJson(result, writer);
                }
                cache.writeIfNeeded(path, bytes.toByteArray(), hashedBytes.hash());
            } catch (IOException e) {
                LOGGER.error("Failed to save file to {}", path, e);
            }
        }, Util.backgroundExecutor().forName("saveOrdered"));
    }
}

package dev.mod_id.datagen.builder;

import static dev.mod_id.Constants.MOD_NAME;
import static dev.mod_id.datagen.util.JsonUtils.resourcePattern;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.DetectedVersion;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.ResourceFilterSection;
import net.minecraft.util.IdentifierPattern;
import net.minecraft.util.Util;

@SuppressWarnings({"SameParameterValue", "unused"})
public class DatagenPackMetadataBuilder extends PackMetadataGenerator {

    private static final Gson ORDERED_GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final List<FilterEntry> filterEntries = new ArrayList<>();

    private final FabricPackOutput output;
    private final PackMetadataSection packMetadataSection;

    protected DatagenPackMetadataBuilder(FabricPackOutput output) {
        super(output);
        this.output = output;
        this.packMetadataSection = new PackMetadataSection(Component.literal(MOD_NAME + " Resource Pack"), DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange());
    }

    /**
     * Register filter entries here in subclasses.
     */
    protected void generateFilterBlocks(List<FilterEntry> filters) {
    }

    /**
     * Adds a filter entry for a Minecraft resource path and the specified filenames.
     *
     * @param filters the list to add the filter entry to
     * @param path the resource path to filter
     * @param filenames the filenames to include in the filter
     */
    protected void addMinecraftFilterBlock(List<FilterEntry> filters, String path, List<String> filenames) {
        filters.add(FilterEntry.minecraft(path, filenames));
    }

    /**
     * Adds a filter entry for the specified namespace pattern, resource path, and filenames.
     *
     * @param filters the list to add the filter entry to
     * @param namespace the regular-expression pattern used to match namespaces
     * @param path the resource path to filter
     * @param filenames the filenames to include in the filter
     */
    protected void addFilterBlock(List<FilterEntry> filters, String namespace, String path, List<String> filenames) {
        filters.add(new FilterEntry(Pattern.compile(namespace), path, filenames));
    }

    protected record FilterEntry(Pattern namespace, String path, List<String> filenames) {
        protected static FilterEntry minecraft(String path, List<String> filenames) {
            return new FilterEntry(Pattern.compile("minecraft"), path, filenames);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        generateFilterBlocks(filterEntries);
        final JsonObject result = new JsonObject();
        result.add("pack", PackMetadataSection.SERVER_TYPE.codec().encodeStart(JsonOps.INSTANCE, this.packMetadataSection).getOrThrow(IllegalArgumentException::new));
        if (!filterEntries.isEmpty()) {
            final List<IdentifierPattern> patterns = new ArrayList<>();
            for (FilterEntry entry : filterEntries) {
                for (String filename : entry.filenames()) {
                    patterns.add(resourcePattern(entry.namespace(), Pattern.compile(entry.path() + filename)));
                }
            }
            final ResourceFilterSection filterSection = new ResourceFilterSection(List.copyOf(patterns));
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

    @Override
    public String getName() {
        return "Pack Meta Provider";
    }
}

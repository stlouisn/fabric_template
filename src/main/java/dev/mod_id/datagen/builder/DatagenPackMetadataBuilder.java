package dev.mod_id.datagen.builder;

import static dev.mod_id.datagen.util.JsonUtils.resourcePattern;
import static dev.mod_id.util.Constants.MOD_NAME;

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

@SuppressWarnings("unused")
public class DatagenPackMetadataBuilder extends PackMetadataGenerator {

    private static final Gson ORDERED_GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final FabricPackOutput output;
    private final List<IdentifierPattern> filterPatterns = new ArrayList<>();
    private final PackMetadataSection packMetadataSection;

    public DatagenPackMetadataBuilder(FabricPackOutput output) {
        super(output);
        this.output = output;
        this.packMetadataSection = new PackMetadataSection(Component.literal(MOD_NAME + " Resource Pack"), DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange());

        // Populate filter blocks if overridden by subclass
        List<FilterEntry> customFilters = new ArrayList<>();
        this.generateFilterBlocks(customFilters);
        if (!customFilters.isEmpty()) {
            for (FilterEntry entry : customFilters) {
                addFilterBlock(entry.namespace(), entry.path(), entry.filenames());
            }
        }
    }

    /**
     * Pass filter entries into the list. If not overridden, the list remains empty and no filter section is added.
     */
    public void generateFilterBlocks(List<FilterEntry> filters) {
    }

    /**
     * Helper record to define filter blocks cleanly.
     */
    public record FilterEntry(Pattern namespace, String path, List<String> filenames) {
        public static FilterEntry minecraft(String path, List<String> filenames) {
            return new FilterEntry(Pattern.compile("minecraft"), path, filenames);
        }
    }

    protected void addMinecraftFilterBlock(List<FilterEntry> filters, String path, List<String> filenames) {
        filters.add(FilterEntry.minecraft(path, filenames));
    }

    protected void addFilterBlock(Pattern namespace, String path, List<String> filenames) {
        for (String filename : filenames) {
            filterPatterns.add(resourcePattern(namespace, Pattern.compile(path + filename)));
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public final CompletableFuture<?> run(CachedOutput cache) {
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

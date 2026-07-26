package com.mod_id.datagen.provider;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
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

import static com.mod_id.util.Constants.MOD_NAME;

@SuppressWarnings("UnstableApiUsage")
public final class DatagenPackMetadataGenerator extends PackMetadataGenerator {

    private static final Gson ORDERED_GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final FabricPackOutput output;
    private final List<IdentifierPattern> filterPatterns = new ArrayList<>();
    private final PackMetadataSection packMetadataSection;

    public DatagenPackMetadataGenerator(FabricPackOutput output) {
        super(output);
        this.output = output;

        this.packMetadataSection = new PackMetadataSection(Component.literal(MOD_NAME + " Resource Pack"), DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange());

        this.addMinecraftFilterBlocks(
        );
    }

    @Override
    public <T> DatagenPackMetadataGenerator add(final MetadataSectionType<T> type, final T value) {
        return this;
    }

    @Override
    public CompletableFuture<?> run(final CachedOutput cache) {
        JsonObject result = new JsonObject();
        result.add("pack", PackMetadataSection.SERVER_TYPE.codec().encodeStart(JsonOps.INSTANCE, this.packMetadataSection).getOrThrow(IllegalArgumentException::new));
        if (!this.filterPatterns.isEmpty()) {
            ResourceFilterSection filterSection = new ResourceFilterSection(List.copyOf(this.filterPatterns));
            result.add("filter", ResourceFilterSection.TYPE.codec().encodeStart(JsonOps.INSTANCE, filterSection).getOrThrow(IllegalArgumentException::new));
        }
        Path path = this.output.getOutputFolder().resolve("pack.mcmeta");
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                HashingOutputStream hashedBytes = new HashingOutputStream(Hashing.sha256(), bytes);

                try (OutputStreamWriter writer = new OutputStreamWriter(hashedBytes, StandardCharsets.UTF_8)) {
                    ORDERED_GSON.toJson(result, writer);
                }
                cache.writeIfNeeded(path, bytes.toByteArray(), hashedBytes.hash());
            } catch (IOException e) {
                LOGGER.error("Failed to save file to {}", path, e);
            }
        }, Util.backgroundExecutor().forName("saveOrdered"));
    }

    @SuppressWarnings("SameParameterValue")
    private void addMinecraftFilterBlocks(final String... paths) {
        addFilterBlock("minecraft", paths);
    }

    @SuppressWarnings("SameParameterValue")
    private void addFilterBlock(final String namespace, final String... paths) {
        Optional<Pattern> namespacePattern = Optional.of(Pattern.compile(namespace));
        for (String path : paths) {
            IdentifierPattern pattern = new IdentifierPattern(namespacePattern, Optional.of(Pattern.compile(path)));
            this.filterPatterns.add(pattern);
        }
    }
}

package dev.turtle_armour.datagen.provider;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import dev.turtle_armour.util.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.DetectedVersion;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.Util;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenPackMetadataGenerator extends PackMetadataGenerator {

    private static final Gson ORDERED_GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final FabricPackOutput output;
    private final PackMetadataSection packMetadataSection;

    public DatagenPackMetadataGenerator(FabricPackOutput output) {
        super(output);
        this.output = output;
        this.packMetadataSection = new PackMetadataSection(Component.literal(Constants.MOD_NAME + " Resource Pack"), DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange());
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

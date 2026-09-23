package dev.turtle_armour.datagen.provider;

import dev.turtle_armour.util.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenEquipmentAssetsProvider implements DataProvider {

    private static final String PROVIDER_NAME = "Equipment Assets Provider";

    private final PackOutput.PathProvider pathProvider;

    public DatagenEquipmentAssetsProvider(FabricPackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer) {
        consumer.accept(EquipmentAssets.TURTLE_SCUTE, EquipmentClientInfo.builder().addHumanoidLayers(Constants.resourceLocation("minecraft", "turtle_scute")).build());
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        final Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssets = new HashMap<>();
        bootstrap((id, asset) -> {
            if (equipmentAssets.putIfAbsent(id, asset) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + id);
            }
        });
        return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, this.pathProvider::json, equipmentAssets);
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }
}

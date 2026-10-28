package dev.mod_id.datagen.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

@SuppressWarnings("SameParameterValue")
public final class DatagenEquipmentAssetsProvider implements DataProvider {

    private static final Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssets = new HashMap<>();

    private final PackOutput.PathProvider pathProvider;

    public DatagenEquipmentAssetsProvider(FabricPackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        addHumanoidLayers(EquipmentAssets.TURTLE_SCUTE);
        return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, this.pathProvider::json, equipmentAssets);
    }

    @Override
    public String getName() {
        return "Equipment Assets Provider";
    }

    private static void addHumanoidLayers(ResourceKey<EquipmentAsset> asset) {
        final EquipmentClientInfo info = EquipmentClientInfo.builder().addHumanoidLayers(asset.identifier()).build();
        if (equipmentAssets.putIfAbsent(asset, info) != null) {
            throw new IllegalStateException("Tried to register equipment asset twice for id: " + asset);
        }
    }
}


package dev.mod_id.datagen.builder;

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

@SuppressWarnings({"SameParameterValue", "unused"})
public class DatagenEquipmentAssetsBuilder implements DataProvider {

    private static final Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssets = new HashMap<>();

    private final PackOutput.PathProvider pathProvider;

    protected DatagenEquipmentAssetsBuilder(FabricPackOutput output) {
        this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
        this.generateEquipmentAssets();
    }

    /**
     * Register equipment assets here in subclasses.
     */
    protected void generateEquipmentAssets() {
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return DataProvider.saveAll(cache, EquipmentClientInfo.CODEC, this.pathProvider::json, equipmentAssets);
    }

    /**
     * Registers humanoid equipment layers for the given equipment asset.
     *
     * @param asset the equipment asset to register; throws an exception if it has already been registered
     */
    public final void addHumanoidLayers(ResourceKey<EquipmentAsset> asset) {
        final EquipmentClientInfo info = EquipmentClientInfo.builder().addHumanoidLayers(asset.identifier()).build();
        if (equipmentAssets.putIfAbsent(asset, info) != null) {
            throw new IllegalStateException("Tried to register equipment asset twice for id: " + asset);
        }
    }

    @Override
    public String getName() {
        return "Equipment Assets Provider";
    }
}

package dev.mod_id.datagen.provider;

import dev.mod_id.datagen.builder.DatagenEquipmentAssetsBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.world.item.equipment.EquipmentAssets;

public final class DatagenEquipmentAssetsProvider extends DatagenEquipmentAssetsBuilder {

    public DatagenEquipmentAssetsProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateEquipmentAssets() {
        addHumanoidLayers(EquipmentAssets.TURTLE_SCUTE);
    }
}

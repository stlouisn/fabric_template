package dev.turtle_armour.datagen.provider;

import dev.turtle_armour.content.registry.RegisterItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenModelProvider extends FabricModelProvider {

    public DatagenModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {

        itemModelGenerator.generateFlatItem(RegisterItems.TURTLE_BOOTS, ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(RegisterItems.TURTLE_CHESTPLATE, ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(RegisterItems.TURTLE_LEGGINGS, ModelTemplates.FLAT_ITEM);
    }
}

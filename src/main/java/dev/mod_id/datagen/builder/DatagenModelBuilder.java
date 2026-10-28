package dev.mod_id.datagen.builder;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@SuppressWarnings({"SameParameterValue", "unused"})
public final class DatagenModelBuilder {

    /**
     * Generate flat rail model with no rotation, no elevation, no powered state.
     */
    public static void createSimpleFlatRail(BlockModelGenerators modelGenerator, Block block) {
        final TextureMapping blockTextureMap = new TextureMapping().put(TextureSlot.RAIL, TextureMapping.getBlockTexture(block));
        final Identifier blockModel = ModelTemplates.RAIL_FLAT.create(block, blockTextureMap, modelGenerator.modelOutput);
        final MultiVariant single = BlockModelGenerators.plainVariant(blockModel);
        modelGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, single));
        createSimpleFlatItem(modelGenerator, block);
    }

    /**
     * Generate flat item model.
     */
    public static void createSimpleFlatItem(BlockModelGenerators modelGenerator, Block block) {
        final TextureMapping itemTextureMap = new TextureMapping().put(TextureSlot.LAYER0, TextureMapping.getBlockTexture(block));
        final Identifier itemModel = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block.asItem()), itemTextureMap, modelGenerator.modelOutput);
        modelGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(itemModel));
    }

    /**
     * Generate flat item model with an alternate model when the component is present.
     */
    public static void createConditionalFlatItem(ItemModelGenerators modelGenerator, Item item, DataComponentType<?> component, String alternateSuffix) {
        final Identifier defaultModelId = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), modelGenerator.modelOutput);
        final Identifier alternateModelId = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item, alternateSuffix), TextureMapping.layer0(new Material(ModelLocationUtils.getModelLocation(item, alternateSuffix))), modelGenerator.modelOutput);
        final ItemModel.Unbaked defaultModel = ItemModelUtils.plainModel(defaultModelId);
        final ItemModel.Unbaked alternateModel = ItemModelUtils.plainModel(alternateModelId);
        modelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(ItemModelUtils.hasComponent(component), alternateModel, defaultModel));
    }
}

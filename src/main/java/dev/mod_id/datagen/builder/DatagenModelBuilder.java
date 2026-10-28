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
     * Generates a flat rail block model without rotation, elevation, or powered-state variants,
     * registers its block state, and generates a flat item model for the block item.
     *
     * @param modelGenerator the generator used to create and register the block and item models
     * @param block the rail block to generate models for
     */
    public static void createSimpleFlatRail(BlockModelGenerators modelGenerator, Block block) {
        final TextureMapping blockTextureMap = new TextureMapping().put(TextureSlot.RAIL, TextureMapping.getBlockTexture(block));
        final Identifier blockModel = ModelTemplates.RAIL_FLAT.create(block, blockTextureMap, modelGenerator.modelOutput);
        final MultiVariant single = BlockModelGenerators.plainVariant(blockModel);
        modelGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, single));
        createSimpleFlatItem(modelGenerator, block);
    }

    /**
     * Generates a flat item model using the block's texture and registers it for the block item.
     *
     * @param modelGenerator the generator used to create and register the item model
     * @param block the block whose item model is generated
     */
    public static void createSimpleFlatItem(BlockModelGenerators modelGenerator, Block block) {
        final TextureMapping itemTextureMap = new TextureMapping().put(TextureSlot.LAYER0, TextureMapping.getBlockTexture(block));
        final Identifier itemModel = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block.asItem()), itemTextureMap, modelGenerator.modelOutput);
        modelGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(itemModel));
    }

    /**
     * Registers the block item's model to use the block's existing model location.
     *
     * <p>Assumes that a suitable model for the block is already available or generated elsewhere.
     *
     * @param modelGenerator the generator used to register the item model
     * @param block the block whose item uses the existing model
     */
    public static void createSimpleFlatItem(ItemModelGenerators modelGenerator, Block block) {
        final Identifier itemModel = ModelLocationUtils.getModelLocation(block);
        modelGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(itemModel));
    }

    /**
     * Generates default and alternate flat item models, then registers a component-based
     * conditional model that uses the alternate model when the item has the specified component.
     *
     * @param modelGenerator the generator used to create and register the item models
     * @param item the item whose models are generated
     * @param component the component whose presence selects the alternate model
     * @param alternateSuffix the suffix used to identify the alternate model and texture
     */
    public static void createConditionalFlatItem(ItemModelGenerators modelGenerator, Item item, DataComponentType<?> component, String alternateSuffix) {
        final Identifier defaultModelId = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(item), modelGenerator.modelOutput);
        final Identifier alternateModelId = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item, alternateSuffix), TextureMapping.layer0(new Material(ModelLocationUtils.getModelLocation(item, alternateSuffix))), modelGenerator.modelOutput);
        final ItemModel.Unbaked defaultModel = ItemModelUtils.plainModel(defaultModelId);
        final ItemModel.Unbaked alternateModel = ItemModelUtils.plainModel(alternateModelId);
        modelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(ItemModelUtils.hasComponent(component), alternateModel, defaultModel));
    }
}

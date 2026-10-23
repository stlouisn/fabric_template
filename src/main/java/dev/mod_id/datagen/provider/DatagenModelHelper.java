package dev.rails.datagen.provider;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NullMarked;

@SuppressWarnings("SameParameterValue")
@NullMarked
public final class DatagenModelHelper {

    /**
     * Generate flat rail model with no rotation, no elevation, no powered state.
     */
    public static void createSimpleFlatRail(BlockModelGenerators blockModelGenerator, Block block) {
        final TextureMapping blockTextureMap = new TextureMapping().put(TextureSlot.RAIL, TextureMapping.getBlockTexture(block));
        final Identifier blockModel = ModelTemplates.RAIL_FLAT.create(block, blockTextureMap, blockModelGenerator.modelOutput);
        final MultiVariant single = BlockModelGenerators.plainVariant(blockModel);
        blockModelGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, single));
        createSimpleFlatItem(blockModelGenerator, block);
    }

    /**
     * Generate flat item model.
     */
    public static void createSimpleFlatItem(BlockModelGenerators blockModelGenerator, Block block) {
        final TextureMapping itemTextureMap = new TextureMapping().put(TextureSlot.LAYER0, TextureMapping.getBlockTexture(block));
        final Identifier itemModel = ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block.asItem()), itemTextureMap, blockModelGenerator.modelOutput);
        blockModelGenerator.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(itemModel));
    }
}

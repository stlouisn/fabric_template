package dev.mod_id.compat.rrv;

import static dev.mod_id.api.ResourceLocation.resourceLocation;
import static dev.mod_id.content.fletching.recipe.FletchingTableRecipe.FLETCHING_TABLE_ID;
import static dev.mod_id.content.fletching.textures.FletchingTableGUI.FLETCHING_TABLE_GUI_BORDERED_TEXTURE;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public final class FletchingClientRecipeType implements ReliableClientRecipeType {

    public static final FletchingClientRecipeType INSTANCE = new FletchingClientRecipeType();

    @Override
    public Component getDisplayName() {
        return Component.translatable("fletching_table.title");
    }

    @Override
    public Identifier getId() {
        return resourceLocation(FLETCHING_TABLE_ID);
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Blocks.FLETCHING_TABLE);
    }

    @Override
    public int getDisplayWidth() {
        return 122;
    }

    @Override
    public int getDisplayHeight() {
        return 60;
    }

    @Override
    public Identifier getGuiTexture() {
        return FLETCHING_TABLE_GUI_BORDERED_TEXTURE;
    }

    @Override
    public int getSlotCount() {
        return 3;
    }

    @Override
    public void placeSlots(RecipeViewMenu.SlotDefinition def) {
        def.addItemSlot(0, 4, 22);
        def.addItemSlot(1, 40, 22);
        def.addItemSlot(2, 98, 22);
    }

    @Override
    public List<ItemStack> getCraftReferences() {
        return List.of(new ItemStack(Blocks.FLETCHING_TABLE));
    }
}

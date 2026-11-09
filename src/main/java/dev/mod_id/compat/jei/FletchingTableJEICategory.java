package dev.mod_id.compat.jei;

import static dev.mod_id.api.ResourceLocation.resourceLocation;
import static dev.mod_id.content.fletching.recipe.FletchingTableRecipe.FLETCHING_TABLE_ID;
import static dev.mod_id.content.fletching.textures.FletchingTableGUI.FLETCHING_TABLE_GUI_TEXTURE;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NullMarked;

public final class FletchingTableJEICategory implements IRecipeCategory<FletchingTableJEIRecipe> {

    public static final IRecipeType<FletchingTableJEIRecipe> FLETCHING_TABLE_I_RECIPE_TYPE = IRecipeType.create(resourceLocation(FLETCHING_TABLE_ID), FletchingTableJEIRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public FletchingTableJEICategory(IGuiHelper helper) {
        this.background = helper.createDrawable(FLETCHING_TABLE_GUI_TEXTURE, 0, 0, 176, 80);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Blocks.FLETCHING_TABLE));
    }

    @NullMarked
    @Override
    public IRecipeType<FletchingTableJEIRecipe> getRecipeType() {
        return FLETCHING_TABLE_I_RECIPE_TYPE;
    }

    @NullMarked
    @Override
    public Component getTitle() {
        return Component.translatable("fletching_table.title");
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 80;
    }

    @NullMarked
    @Override
    public void draw(FletchingTableJEIRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics);
    }

    @NullMarked
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FletchingTableJEIRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 34).add(recipe.arrowInput());
        builder.addSlot(RecipeIngredientRole.INPUT, 78, 34).add(recipe.potionInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 132, 34).add(recipe.output());
    }
}

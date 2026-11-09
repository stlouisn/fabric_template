package dev.mod_id.compat.rrv;

import cc.cassian.rrv.api.ActionType;
import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import dev.mod_id.content.fletching.screen.FletchingTableBlockScreen;
import java.util.List;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public final class FletchingClientRecipe implements ReliableClientRecipe {

    private final Identifier id;

    private final SlotContent arrow;
    private final SlotContent potion;
    private final SlotContent output;

    private final List<SlotContent> ingredients;
    private final List<SlotContent> results;

    public FletchingClientRecipe(Identifier id, ItemStack arrowInput, ItemStack potionInput, ItemStack outputItem) {
        this.id = id;
        this.arrow = SlotContent.of(arrowInput);
        this.potion = SlotContent.of(potionInput);
        this.output = SlotContent.of(outputItem);
        this.ingredients = List.of(this.arrow, this.potion);
        this.results = List.of(this.output);
    }

    @Override
    public ReliableClientRecipeType getType() {
        return FletchingClientRecipeType.INSTANCE;
    }

    @Override
    public void bindSlots(RecipeViewMenu.SlotFillContext context) {
        context.bindSlot(0, arrow);
        context.bindSlot(1, potion);
        context.bindSlot(2, output);
        arrow.setType(ActionType.INPUT);
        potion.setType(ActionType.INPUT);
        output.setType(ActionType.RESULT);
        context.addAdditionalStackModifier(1, (_, tooltip) -> tooltip.add(Component.translatable("tooltip.fletching_table.rrv.potion")));
    }

    @Override
    public List<SlotContent> getIngredients() {
        return ingredients;
    }

    @Override
    public List<SlotContent> getResults() {
        return results;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public boolean supportsItemTransfer() {
        return true;
    }

    @Override
    public List<Class<? extends AbstractContainerScreen<?>>> getTransferClasses() {
        return List.of(FletchingTableBlockScreen.class);
    }

    @Override
    public void mapRecipeItems(final RecipeTransferMap map, final AbstractContainerScreen<?> screen) {
        map.linkSlots(0, 0);
        map.linkSlots(1, 1);
    }
}

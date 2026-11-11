package dev.mod_id.compat.jei;

import static dev.mod_id.Constants.resourceLocation;

import dev.mod_id.content.fletching.screen.FletchingTableBlockMenu;
import dev.mod_id.content.fletching.screen.FletchingTableBlockScreen;
import dev.mod_id.content.registry.RegisterFletchingMenu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NullMarked;

@JeiPlugin
public final class FletchingTableJEIPlugin implements IModPlugin {

    @NullMarked
    @Override
    public Identifier getPluginUid() {
        return resourceLocation("jei_fletching_table");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FletchingTableJEICategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @NullMarked
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        final List<FletchingTableJEIRecipe> jeiRecipes = new ArrayList<>();
        final ItemStack arrowDisplay = new ItemStack(Items.ARROW);
        for (final Potion potion : BuiltInRegistries.POTION) {
            final Identifier potionId = BuiltInRegistries.POTION.getKey(potion);
            if (potionId == null) {
                continue;
            }
            final ResourceKey<Potion> key = ResourceKey.create(Registries.POTION, potionId);
            final Optional<Holder.Reference<Potion>> potionHolderOpt = BuiltInRegistries.POTION.get(key);
            if (potionHolderOpt.isEmpty()) {
                continue;
            }
            final Holder.Reference<Potion> potionHolder = potionHolderOpt.get();
            final PotionContents contents = new PotionContents(Optional.of(potionHolder), Optional.empty(), Collections.emptyList(), Optional.empty());
            final ItemStack lingering = new ItemStack(Items.LINGERING_POTION);
            lingering.set(DataComponents.POTION_CONTENTS, contents);
            if (!lingering.getHoverName().getString().equals(Component.translatable("item.minecraft.lingering_potion.effect.empty").getString())) {
                final ItemStack out = new ItemStack(Items.TIPPED_ARROW);
                out.set(DataComponents.POTION_CONTENTS, contents);
                jeiRecipes.add(new FletchingTableJEIRecipe(arrowDisplay.copy(), lingering, out));
            }
        }
        registration.addRecipes(FletchingTableJEICategory.FLETCHING_TABLE_I_RECIPE_TYPE, jeiRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FletchingTableBlockScreen.class, 100, 34, 25, 20, FletchingTableJEICategory.FLETCHING_TABLE_I_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(FletchingTableJEICategory.FLETCHING_TABLE_I_RECIPE_TYPE, new ItemStack(Blocks.FLETCHING_TABLE.asItem()));
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(FletchingTableBlockMenu.class, RegisterFletchingMenu.FLETCHING_TABLE_MENU, FletchingTableJEICategory.FLETCHING_TABLE_I_RECIPE_TYPE, 0, 2, 3, 36);
    }
}

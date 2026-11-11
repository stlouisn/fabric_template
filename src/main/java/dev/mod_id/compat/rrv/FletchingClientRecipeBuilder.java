package dev.mod_id.compat.rrv;

import static dev.mod_id.Constants.resourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

public final class FletchingClientRecipeBuilder {

    public static List<FletchingClientRecipe> buildAll() {
        final List<FletchingClientRecipe> result = new ArrayList<>(BuiltInRegistries.POTION.size());
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
                final Identifier recipeId = resourceLocation("arrow/" + potionId.getPath());
                result.add(new FletchingClientRecipe(recipeId, arrowDisplay.copy(), lingering, out));
            }
        }
        return result;
    }
}

package dev.mod_id.datagen.builder;

import static dev.mod_id.util.IdentifierUtils.resourceLocation;
import static net.minecraft.advancements.triggers.InventoryChangeTrigger.TriggerInstance.hasItems;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ItemLike;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenAdvancementBuilder extends FabricAdvancementProvider {

    protected DatagenAdvancementBuilder(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    protected final AdvancementHelper advancementHelper(Consumer<AdvancementHolder> consumer) {
        return new AdvancementHelper(consumer);
    }

    protected record AdvancementHelper(Consumer<AdvancementHolder> consumer) {

        public void add(String name, String namespace, String category, String parent, ItemLike icon, ItemLike... requiredItems) {
            final Identifier id = resourceLocation(namespace, category + "/" + parent);
            final MutableComponent title = Component.translatable("advancements." + category + ".obtain_" + name + ".title");
            final MutableComponent description = Component.translatable("advancements." + category + ".obtain_" + name + ".description");
            final String path = category + "/obtain_" + name;
            Advancement.Builder.advancement()
                .parent(createPlaceholder(id))
                .display(icon, title, description, null, AdvancementType.TASK, true, true, false)
                .addCriterion(name, hasItems(requiredItems))
                .save(consumer, path);
        }
    }
}

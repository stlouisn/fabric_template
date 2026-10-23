package dev.mod_id.datagen.provider;

import static dev.mod_id.util.Constants.resourceLocation;
import static net.minecraft.advancements.triggers.InventoryChangeTrigger.TriggerInstance.hasItems;

import dev.mod_id.content.registry.RegisterItems;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenAdvancementProvider extends FabricAdvancementProvider {

    public DatagenAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registriesFuture, Consumer<AdvancementHolder> consumer) {

        var builder = new AdvancementHelper(consumer);

        builder.add("ancient_debris", "minecraft", "nether", "root", Items.ANCIENT_DEBRIS, RegisterItems.RAW_NETHERITE);
    }

    @SuppressWarnings("SameParameterValue")
    private record AdvancementHelper(Consumer<AdvancementHolder> consumer) {
        void add(String name, String namespace, String category, String parent, ItemLike icon, ItemLike... requiredItems) {
            final String title = "advancements." + category + ".obtain_" + name + ".title";
            final String description = "advancements." + category + ".obtain_" + name + ".description";
            final String path = category + "/obtain_" + name;
            Advancement.Builder.advancement()
                .parent(createPlaceholder(resourceLocation(namespace, category + "/" + parent)))
                .display(icon, Component.translatable(title), Component.translatable(description), null, AdvancementType.TASK, true, true, false)
                .addCriterion(name, hasItems(requiredItems))
                .save(consumer, path);
        }
    }
}

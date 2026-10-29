package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterItems;
import dev.mod_id.datagen.builder.DatagenAdvancementBuilder;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;

public final class DatagenAdvancementProvider extends DatagenAdvancementBuilder {

    public DatagenAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registriesFuture, Consumer<AdvancementHolder> consumer) {

        var builder = advancementHelper(consumer);

        builder.add(
            "ancient_debris",
            "minecraft",
            "nether",
            "root",
            Items.ANCIENT_DEBRIS,
            RegisterItems.RAW_NETHERITE);
    }
}

package dev.petcontracts.datagen.provider;

import dev.petcontracts.content.key.KeyVillagerTrades;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.VillagerTrade;

public final class DatagenVillagerTradesTagProvider extends FabricTagsProvider<VillagerTrade> {

    public DatagenVillagerTradesTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, Registries.VILLAGER_TRADE, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateRawBuilder(VillagerTradeTags.SHEPHERD_LEVEL_5).add(TagEntry.element(KeyVillagerTrades.SHEPHERD_5_EMERALD_PET_CONTRACT.identifier()));
    }

    @Override
    public String getName() {
        return "Villager Trade Tags Provider";
    }
}

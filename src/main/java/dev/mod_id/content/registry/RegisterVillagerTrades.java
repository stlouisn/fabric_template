package dev.mod_id.content.registry;

import dev.mod_id.content.key.KeyVillagerTrades;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;

public final class RegisterVillagerTrades {

    public static void init() {
    }

    public static void bootstrap(BootstrapContext<VillagerTrade> context) {
        VillagerTrade trade = VillagerTrade.builder(new TradeCost(Items.EMERALD, 8), new ItemStackTemplate(RegisterItems.PET_CONTRACT, 1), 2, 20, 0.05F).build();
        context.register(KeyVillagerTrades.SHEPHERD_5_EMERALD_PET_CONTRACT, trade);
    }
}

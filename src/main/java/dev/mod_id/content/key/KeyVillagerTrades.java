package dev.mod_id.content.key;

import static dev.mod_id.api.ResourceLocation.resourceLocation;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.VillagerTrade;

public final class KeyVillagerTrades {

    public static final ResourceKey<VillagerTrade> SHEPHERD_5_EMERALD_PET_CONTRACT = create("shepherd/5/emerald_pet_contract");

    /**
     * Creates a VillagerTrade ResourceKey using the mod's resource location for the given path.
     */
    @SuppressWarnings("SameParameterValue")
    private static ResourceKey<VillagerTrade> create(String path) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, resourceLocation(path));
    }

    /**
     * Creates a VillagerTrade ResourceKey for the given namespace and path.
     */
    @SuppressWarnings("unused")
    private static ResourceKey<VillagerTrade> create(String namespace, String path) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, resourceLocation(namespace, path));
    }
}

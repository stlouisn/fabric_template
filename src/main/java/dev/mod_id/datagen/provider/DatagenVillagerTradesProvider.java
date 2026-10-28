package dev.petcontracts.datagen.provider;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DatagenVillagerTradesProvider extends FabricDynamicRegistryProvider {

    public DatagenVillagerTradesProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries) {
        entries.addAll(provider.lookupOrThrow(Registries.VILLAGER_TRADE));
    }

    @Override
    public String getName() {
        return "Villager Trade Provider";
    }
}

package dev.mod_id.content.registry;

import static dev.mod_id.Constants.resourceLocation;

import dev.mod_id.content.item.PetContractData;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

public final class RegisterDataComponentTypes {

    public static final DataComponentType<PetContractData> PET_CONTRACT_DATA = register("pet_contract_data", DataComponentType.<PetContractData>builder().persistent(PetContractData.CODEC).networkSynchronized(PetContractData.NETWORK_CODEC).build());

    public static void init() {
    }

    /**
     * Registers a DataComponentType using the mod's resource location for the given path.
     */
    @SuppressWarnings("SameParameterValue")
    private static <T> DataComponentType<T> register(String path, DataComponentType<T> type) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, resourceLocation(path), type);
    }

    /**
     * Registers a DataComponentType for the given namespace and path.
     */
    @SuppressWarnings("unused")
    private static <T> DataComponentType<T> register(String namespace, String path, DataComponentType<T> type) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, resourceLocation(namespace, path), type);
    }
}

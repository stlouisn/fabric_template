package dev.mod_id.datagen.provider;

import dev.mod_id.datagen.builder.DatagenPackMetadataBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import java.util.List;

public final class DatagenPackMetadataProvider extends DatagenPackMetadataBuilder {

    private static final String PROVIDER_NAME = "Pack Metadata Provider";

    public DatagenPackMetadataProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateFilterBlocks(List<FilterEntry> filters) {
        addMinecraftFilterBlock(filters, "advancement/recipes/misc/", FILTERED_VANILLA_ADVANCEMENTS);
        addMinecraftFilterBlock(filters, "recipe/", FILTERED_VANILLA_RECIPES);
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    private static final List<String> FILTERED_VANILLA_ADVANCEMENTS = List.of(
        "netherite_scrap.json",
        "netherite_scrap_from_blasting.json",
        "netherite_ingot.json"
    );

    private static final List<String> FILTERED_VANILLA_RECIPES = List.of(
        "coal_from_smelting_coal_ore.json",
        "coal_from_smelting_deepslate_coal_ore.json",
        "coal_from_blasting_coal_ore.json",
        "coal_from_blasting_deepslate_coal_ore.json",
        "copper_ingot_from_smelting_deepslate_copper_ore.json",
        "copper_ingot_from_blasting_deepslate_copper_ore.json",
        "diamond_from_smelting_diamond_ore.json",
        "diamond_from_smelting_deepslate_diamond_ore.json",
        "diamond_from_blasting_diamond_ore.json",
        "diamond_from_blasting_deepslate_diamond_ore.json",
        "emerald_from_smelting_emerald_ore.json",
        "emerald_from_smelting_deepslate_emerald_ore.json",
        "emerald_from_blasting_emerald_ore.json",
        "emerald_from_blasting_deepslate_emerald_ore.json",
        "gold_ingot_from_smelting_deepslate_gold_ore.json",
        "gold_ingot_from_smelting_nether_gold_ore.json",
        "gold_ingot_from_blasting_deepslate_gold_ore.json",
        "gold_ingot_from_blasting_nether_gold_ore.json",
        "iron_ingot_from_smelting_deepslate_iron_ore.json",
        "iron_ingot_from_blasting_deepslate_iron_ore.json",
        "lapis_lazuli_from_smelting_lapis_ore.json",
        "lapis_lazuli_from_smelting_deepslate_lapis_ore.json",
        "lapis_lazuli_from_blasting_lapis_ore.json",
        "lapis_lazuli_from_blasting_deepslate_lapis_ore.json",
        "netherite_ingot.json",
        "netherite_scrap.json",
        "netherite_scrap_from_blasting.json",
        "quartz.json",
        "quartz_from_blasting.json",
        "redstone_from_blasting_deepslate_redstone_ore.json",
        "redstone_from_blasting_redstone_ore.json",
        "redstone_from_smelting_deepslate_redstone_ore.json",
        "redstone_from_smelting_redstone_ore.json"
    );
}

package com.mod_id.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;

import static com.mod_id.util.Constants.resourceLocation;

public final class DatagenBlockLootSubProvider extends FabricBlockLootSubProvider {

    public DatagenBlockLootSubProvider(final FabricPackOutput output, final CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate() {
    }

    private void createSelfDrop(final Block block) {
        String namespace = BuiltInRegistries.BLOCK.getKey(block).getNamespace();
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        Identifier identifier = resourceLocation(namespace, "blocks/" + path);
        LootTable.Builder builder = this.createSingleItemTable(block).setRandomSequence(identifier);
        this.add(block, builder);
    }

    private void createSilkTouchDrop(final Block block) {
        String namespace = BuiltInRegistries.BLOCK.getKey(block).getNamespace();
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        Identifier identifier = resourceLocation(namespace, "blocks/" + path);
        LootTable.Builder builder = this.createSilkTouchOnlyTable(block).setRandomSequence(identifier);
        this.add(block, builder);
    }

    @SuppressWarnings("SameParameterValue")
    private void createGeodeClusterDrop(Block block, ItemLike drops) {
        String namespace = BuiltInRegistries.BLOCK.getKey(block).getNamespace();
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        Identifier identifier = resourceLocation(namespace, "blocks/" + path);
        LootTable.Builder builder = this.createSilkTouchDispatchTable(block, LootItem.lootTableItem(drops).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(block, LootItem.lootTableItem(drops).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))).setRandomSequence(identifier);
        this.add(block, builder);
    }

    @SuppressWarnings("SameParameterValue")
    private void createRawOreDrop(final Block original, final Item drop) {
        String namespace = BuiltInRegistries.BLOCK.getKey(original).getNamespace();
        String path = BuiltInRegistries.BLOCK.getKey(original).getPath();
        Identifier identifier = resourceLocation(namespace, "blocks/" + path);
        LootTable.Builder builder = this.createSilkTouchDispatchTable(original, LootItem.lootTableItem(drop).apply(ApplyBonusCount.addOreBonusCount(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE)))).setRandomSequence(identifier);
        this.add(original, builder);
    }
}

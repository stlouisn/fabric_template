package dev.mod_id.datagen.builder;

import static dev.mod_id.util.Constants.resourceLocation;

import java.util.concurrent.CompletableFuture;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenBlockLootBuilder extends FabricBlockLootSubProvider {

    protected DatagenBlockLootBuilder(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    protected void createSelfDrop(Block block) {
        final String namespace = BuiltInRegistries.BLOCK.getKey(block).getNamespace();
        final String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        final Identifier identifier = resourceLocation(namespace, "blocks/" + path);
        final LootTable.Builder builder = this.createSingleItemTable(block).setRandomSequence(identifier);
        this.add(block, builder);
    }

    protected void createPottedContentsDrop(Block potted) {
        final String namespace = BuiltInRegistries.BLOCK.getKey(potted).getNamespace();
        final String path = BuiltInRegistries.BLOCK.getKey(potted).getPath();
        final Identifier identifier = resourceLocation(namespace, "blocks/" + path);
        final LootTable.Builder builder = this.createPotFlowerItemTable(((FlowerPotBlock) potted).getPotted()).setRandomSequence(identifier);
        this.add(potted, builder);
    }

    protected void createSilkTouchDrop(Block block) {
        final String namespace = BuiltInRegistries.BLOCK.getKey(block).getNamespace();
        final String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        final Identifier identifier = resourceLocation(namespace, "blocks/" + path);
        final LootTable.Builder builder = this.createSilkTouchOnlyTable(block).setRandomSequence(identifier);
        this.add(block, builder);
    }

    protected void createGeodeClusterDrop(Block original, Item drop) {
        final String namespace = BuiltInRegistries.BLOCK.getKey(original).getNamespace();
        final String path = BuiltInRegistries.BLOCK.getKey(original).getPath();
        final Identifier identifier = resourceLocation(namespace, "blocks/" + path);
        final LootTable.Builder builder = this.createSilkTouchDispatchTable(original, LootItem.lootTableItem(drop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))).apply(ApplyBonusCount.addOreBonusCount(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(original, LootItem.lootTableItem(drop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)))))).setRandomSequence(identifier);
        this.add(original, builder);
    }

    protected void createRawOreDrop(Block original, Item drop) {
        final String namespace = BuiltInRegistries.BLOCK.getKey(original).getNamespace();
        final String path = BuiltInRegistries.BLOCK.getKey(original).getPath();
        final Identifier identifier = resourceLocation(namespace, "blocks/" + path);
        final LootTable.Builder builder = this.createSilkTouchDispatchTable(original, LootItem.lootTableItem(drop).apply(ApplyBonusCount.addOreBonusCount(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE)))).setRandomSequence(identifier);
        this.add(original, builder);
    }
}

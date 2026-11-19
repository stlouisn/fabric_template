package dev.mod_id.datagen.builder;

import static dev.mod_id.Constants.resourceLocation;

import dev.mod_id.datagen.util.BlockUtils;
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
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

@SuppressWarnings({"SameParameterValue", "unused"})
public abstract class DatagenBlockLootBuilder extends FabricBlockLootSubProvider {

    private final CompletableFuture<HolderLookup.Provider> registriesFuture;

    protected DatagenBlockLootBuilder(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
        this.registriesFuture = registriesFuture;
    }

    protected void createNoDrop(Block block) {
        final LootTable.Builder builder = LootTable.lootTable().setParamSet(LootContextParamSets.BLOCK).setRandomSequence(getLootTableIdentifier(block));
        this.add(block, builder);
    }

    protected void createSimpleDrop(Block block) {
        final LootTable.Builder builder = this.createSingleItemTable(block).setRandomSequence(getLootTableIdentifier(block));
        this.add(block, builder);
    }

    protected void createSilkTouchDrop(Block block) {
        final LootTable.Builder builder = this.createSilkTouchOnlyTable(block).setRandomSequence(getLootTableIdentifier(block));
        this.add(block, builder);
    }

    protected void createPottedContentsDrop(Block block) {
        if (!(block instanceof FlowerPotBlock potted)) {
            throw new IllegalArgumentException("Not a flower pot block: " + BuiltInRegistries.BLOCK.getKey(block));
        }
        createPottedContentsDrop(potted);
    }

    protected void createPottedContentsDrop(FlowerPotBlock potted) {
        final LootTable.Builder builder = this.createPotFlowerItemTable(potted.getPotted()).setRandomSequence(getLootTableIdentifier(potted));
        this.add(potted, builder);
    }

    protected void createGeodeClusterDrop(Block original, Item drop) {
        final LootTable.Builder builder = this.createSilkTouchDispatchTable(original, LootItem.lootTableItem(drop).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(4))).apply(ApplyBonusCount.addOreBonusCount(this.registriesFuture.join().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(this.registriesFuture.join().lookupOrThrow(Registries.ITEM), ItemTags.CLUSTER_MAX_HARVESTABLES))).otherwise(this.applyExplosionDecay(original, LootItem.lootTableItem(drop).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2)))))).setRandomSequence(getLootTableIdentifier(original));
        this.add(original, builder);
    }

    protected void createRawOreDrop(Block original, Item drop) {
        final LootTable.Builder builder = this.createSilkTouchDispatchTable(original, this.applyExplosionDecay(original, LootItem.lootTableItem(drop).apply(ApplyBonusCount.addOreBonusCount(this.registriesFuture.join().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))))).setRandomSequence(getLootTableIdentifier(original));
        this.add(original, builder);
    }

    private static Identifier getLootTableIdentifier(Block block) {
        return resourceLocation(BlockUtils.getNamespace(block), "blocks/" + BlockUtils.getPath(block));
    }
}

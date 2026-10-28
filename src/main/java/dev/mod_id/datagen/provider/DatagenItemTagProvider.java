package dev.mod_id.datagen.provider;

import dev.mod_id.content.key.KeyBlockItems;
import dev.mod_id.content.key.KeyItems;
import dev.mod_id.content.tag.TagBlocks;
import dev.mod_id.content.tag.TagItems;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.references.ItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.WeatheringCopperCollection;

public final class DatagenItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    public DatagenItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, DatagenBlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        copy(ConventionalBlockTags.BUDDING_BLOCKS, ConventionalItemTags.BUDDING_BLOCKS);

        copy(ConventionalBlockTags.BUDS, ConventionalItemTags.BUDS);

        copy(ConventionalBlockTags.CLUSTERS, ConventionalItemTags.CLUSTERS);

        copy(TagBlocks.STORAGE_BLOCKS_CHARCOAL, TagItems.STORAGE_BLOCKS_CHARCOAL);

        copy(TagBlocks.STORAGE_BLOCKS_RAW_NETHERITE, TagItems.STORAGE_BLOCKS_RAW_NETHERITE);

        copy(ConventionalBlockTags.STORAGE_BLOCKS, ConventionalItemTags.STORAGE_BLOCKS);

        builder(TagItems.ROSE_QUARTZ_GEMS).add(KeyItems.ROSE_QUARTZ_SHARD);

        builder(ConventionalItemTags.GEMS).addTag(TagItems.ROSE_QUARTZ_GEMS);

        builder(TagItems.CHARCOAL_NUGGET).add(KeyItems.CHARCOAL_NUGGET);

        builder(TagItems.COAL_NUGGET).add(KeyItems.COAL_NUGGET);

        builder(TagItems.NETHERITE_NUGGET).add(KeyItems.NETHERITE_NUGGET);

        builder(ItemTags.METAL_NUGGETS).add(KeyItems.NETHERITE_NUGGET);

        builder(ConventionalItemTags.NUGGETS)
            .addTag(TagItems.CHARCOAL_NUGGET)
            .addTag(TagItems.COAL_NUGGET)
            .addTag(TagItems.NETHERITE_NUGGET);

        builder(TagItems.COAL_NUGGETS)
            .addTag(TagItems.COAL_NUGGET)
            .addTag(TagItems.CHARCOAL_NUGGET);

        builder(TagItems.NETHERITE_RAW_MATERIALS).add(KeyItems.RAW_NETHERITE);

        builder(ConventionalItemTags.RAW_MATERIALS).addTag(TagItems.NETHERITE_RAW_MATERIALS);

        builder(ItemTags.TRIM_MATERIALS).add(KeyItems.ROSE_QUARTZ_SHARD);

        addSmeltsToCopperNuggets();

        addSmeltsToGoldNuggets();

        addSmeltsToIronNuggets();

        addSmeltsToNetheriteNuggets();

        builder(ItemTags.SULFUR_CUBE_SWALLOWABLE).add(
            KeyBlockItems.CHARCOAL_BLOCK,
            KeyBlockItems.ROSE_QUARTZ_BLOCK,
            KeyBlockItems.RAW_NETHERITE_BLOCK,
            KeyBlockItems.POLISHED_ROSE_QUARTZ);

        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_REGULAR).add(KeyBlockItems.CHARCOAL_BLOCK);

        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY).add(
            KeyBlockItems.ROSE_QUARTZ_BLOCK,
            KeyBlockItems.POLISHED_ROSE_QUARTZ);

        builder(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_FLAT).add(KeyBlockItems.RAW_NETHERITE_BLOCK);
    }

    private void addSmeltsToCopperNuggets() {
        final TagAppender<Item> tagBuilder = builder(TagItems.SMELTS_TO_COPPER_NUGGETS);
        addCopperCollection(tagBuilder, "copper_door");
        addCopperCollection(tagBuilder, "copper_trapdoor");
        addCopperCollection(tagBuilder, "lightning_rod");
        tagBuilder.add(ItemIds.COPPER_AXE, ItemIds.COPPER_BOOTS, ItemIds.COPPER_CHESTPLATE, ItemIds.COPPER_HELMET, ItemIds.COPPER_HOE, ItemIds.COPPER_HORSE_ARMOR, ItemIds.COPPER_LEGGINGS, ItemIds.COPPER_NAUTILUS_ARMOR, ItemIds.COPPER_PICKAXE, ItemIds.COPPER_SHOVEL, ItemIds.COPPER_SPEAR, ItemIds.COPPER_SWORD);
    }

    private void addCopperCollection(TagAppender<Item> tagBuilder, String baseName) {
        final WeatheringCopperCollection<String> itemIds = WeatheringCopperCollection.create(baseName);
        final WeatheringCopperCollection<String> namespacedIds = WeatheringCopperCollection.prefixWithState(itemIds);
        namespacedIds.forEach(id -> {
            final ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace(id));
            tagBuilder.add(key);
        });
    }

    private void addSmeltsToGoldNuggets() {
        builder(TagItems.SMELTS_TO_GOLD_NUGGETS).add(ItemIds.GOLDEN_AXE, ItemIds.GOLDEN_BOOTS, ItemIds.GOLDEN_CHESTPLATE, ItemIds.GOLDEN_HELMET, ItemIds.GOLDEN_HOE, ItemIds.GOLDEN_HORSE_ARMOR, ItemIds.GOLDEN_LEGGINGS, ItemIds.GOLDEN_NAUTILUS_ARMOR, ItemIds.GOLDEN_PICKAXE, ItemIds.GOLDEN_SHOVEL, ItemIds.GOLDEN_SPEAR, ItemIds.GOLDEN_SWORD);
    }

    private void addSmeltsToIronNuggets() {
        builder(TagItems.SMELTS_TO_IRON_NUGGETS).add(ItemIds.CHAINMAIL_BOOTS, ItemIds.CHAINMAIL_CHESTPLATE, ItemIds.CHAINMAIL_HELMET, ItemIds.CHAINMAIL_LEGGINGS, ItemIds.IRON_AXE, ItemIds.IRON_BOOTS, ItemIds.IRON_CHESTPLATE, ItemIds.IRON_HELMET, ItemIds.IRON_HOE, ItemIds.IRON_HORSE_ARMOR, ItemIds.IRON_LEGGINGS, ItemIds.IRON_NAUTILUS_ARMOR, ItemIds.IRON_PICKAXE, ItemIds.IRON_SHOVEL, ItemIds.IRON_SPEAR, ItemIds.IRON_SWORD);
    }

    private void addSmeltsToNetheriteNuggets() {
        builder(TagItems.SMELTS_TO_NETHERITE_NUGGETS).add(ItemIds.NETHERITE_AXE, ItemIds.NETHERITE_BOOTS, ItemIds.NETHERITE_CHESTPLATE, ItemIds.NETHERITE_HELMET, ItemIds.NETHERITE_HOE, ItemIds.NETHERITE_LEGGINGS, ItemIds.NETHERITE_NAUTILUS_ARMOR, ItemIds.NETHERITE_PICKAXE, ItemIds.NETHERITE_SHOVEL, ItemIds.NETHERITE_SPEAR, ItemIds.NETHERITE_SWORD);
    }

    @Override
    public String getName() {
        return "Item Tags Provider";
    }
}

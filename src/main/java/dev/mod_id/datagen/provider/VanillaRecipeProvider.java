package dev.mod_id.datagen.provider;

import dev.mod_id.content.registry.RegisterBlocks;
import dev.mod_id.content.registry.RegisterItems;
import dev.mod_id.content.tag.TagItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class VanillaRecipeProvider extends DatagenRecipeHelper {

    public VanillaRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    public void buildRecipes() {

        createOreProcessingRecipe(Items.RAW_COPPER_BLOCK, Items.COPPER_BLOCK.weathering().unaffected(), 0.7F);
        createOreProcessingRecipe(Items.RAW_GOLD_BLOCK, Items.GOLD_BLOCK, 1.0F);
        createOreProcessingRecipe(Items.RAW_IRON_BLOCK, Items.IRON_BLOCK, 0.7F);
        createOreProcessingRecipe(RegisterBlocks.RAW_NETHERITE_BLOCK, Items.NETHERITE_BLOCK, 2.0F);
        createOreProcessingRecipe(Blocks.ANCIENT_DEBRIS, Items.NETHERITE_INGOT, 2.0F);
        createOreProcessingRecipe(RegisterItems.RAW_NETHERITE, Items.NETHERITE_INGOT, 2.0F);

        createOreProcessingRecipe(ItemTags.COPPER_ORES, Items.COPPER_INGOT, 0.7F, "_copper_ore");
        createOreProcessingRecipe(ItemTags.GOLD_ORES, Items.GOLD_INGOT, 1.0F, "_gold_ore");
        createOreProcessingRecipe(ItemTags.IRON_ORES, Items.IRON_INGOT, 0.7F, "_iron_ore");

        createOreProcessingRecipe(TagItems.SMELTS_TO_COPPER_NUGGETS, Items.COPPER_NUGGET, "copper_nugget", 0.1F, false);
        createOreProcessingRecipe(TagItems.SMELTS_TO_GOLD_NUGGETS, Items.GOLD_NUGGET, "gold_nugget", 0.1F, false);
        createOreProcessingRecipe(TagItems.SMELTS_TO_IRON_NUGGETS, Items.IRON_NUGGET, "iron_nugget", 0.1F, false);
        createOreProcessingRecipe(TagItems.SMELTS_TO_NETHERITE_NUGGETS, RegisterItems.NETHERITE_NUGGET, "netherite_nugget", 0.1F, false);
    }
}

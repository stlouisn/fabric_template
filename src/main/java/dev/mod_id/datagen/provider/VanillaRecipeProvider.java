package dev.turtle_armour.datagen.provider;

import dev.turtle_armour.content.registry.RegisterItems;
import dev.turtle_armour.util.Constants;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class VanillaRecipeProvider extends FabricRecipeProvider {

    private static final String PROVIDER_NAME = "Vanilla Recipe Provider";

    public VanillaRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput recipeOutput) {
        return new RecipeProvider(registries, recipeOutput) {

            @Override
            public void buildRecipes() {

                shaped(RecipeCategory.COMBAT, RegisterItems.TURTLE_LEGGINGS)
                    .pattern("###")
                    .pattern("# #")
                    .pattern("# #")
                    .define('#', Items.TURTLE_SCUTE)
                    .unlockedBy("has_item", has(Items.TURTLE_SCUTE))
                    .save(output);

                shaped(RecipeCategory.COMBAT, RegisterItems.TURTLE_CHESTPLATE)
                    .pattern("# #")
                    .pattern("###")
                    .pattern("###")
                    .define('#', Items.TURTLE_SCUTE)
                    .unlockedBy("has_item", has(Items.TURTLE_SCUTE))
                    .save(output);

                shaped(RecipeCategory.COMBAT, RegisterItems.TURTLE_BOOTS)
                    .pattern("# #")
                    .pattern("# #")
                    .define('#', Items.TURTLE_SCUTE)
                    .unlockedBy("has_item", has(Items.TURTLE_SCUTE))
                    .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    protected Identifier getRecipeIdentifier(Identifier identifier) {
        return Constants.resourceLocation("minecraft", identifier.getPath());
    }
}

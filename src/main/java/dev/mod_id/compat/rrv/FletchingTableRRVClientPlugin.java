package dev.mod_id.compat.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerClientPlugin;
import cc.cassian.rrv.api.recipe.ItemView;

import java.util.List;

public final class FletchingTableRRVClientPlugin implements ReliableRecipeViewerClientPlugin {

    @Override
    public void onIntegrationInitialize() {
        ItemView.addClientRecipeProvider(recipes -> {
            List<FletchingClientRecipe> built = FletchingClientRecipeBuilder.buildAll();
            recipes.addAll(built);
        });
    }
}

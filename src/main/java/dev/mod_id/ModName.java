package dev.mod_id;

import dev.mod_id.config.ModConfig;
import net.fabricmc.api.ModInitializer;

public final class ModName implements ModInitializer {

    @Override
    public void onInitialize() {
        MidnightConfig.init(Constants.MOD_ID, ModConfig.class);
    }
}

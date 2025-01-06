package maven_name.mod_id;

import dev.mod_id.config.ModConfig;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ModName implements ModInitializer {

  public static final String MOD_ID = "mod_id";

  @SuppressWarnings("unused")
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  public void onInitialize() {
    ModConfig.init();
  }
}

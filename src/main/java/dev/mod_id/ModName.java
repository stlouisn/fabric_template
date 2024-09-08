package maven_name.mod_id;

import dev.mod_id.config.ModConfig;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ModName implements ModInitializer {

  @SuppressWarnings("unused")
  public static final Logger LOGGER = LoggerFactory.getLogger(Constants.MOD_ID);

  public void onInitialize() {
    ModConfig.init();
  }
}

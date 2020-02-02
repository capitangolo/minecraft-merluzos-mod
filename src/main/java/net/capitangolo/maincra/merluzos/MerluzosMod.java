package net.capitangolo.maincra.merluzos;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Cadiboo
 */
@Mod(MerluzosMod.MODID)
public final class MerluzosMod {

	public static final String MODID = "merluzos";

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public MerluzosMod() {
		LOGGER.debug("Hello from Example Mod!");

		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		// Register Configs
		// modLoadingContext.registerConfig(MerluzosMod.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
		// modLoadingContext.registerConfig(MerluzosMod.Type.SERVER, ConfigHolder.SERVER_SPEC);
	}

}

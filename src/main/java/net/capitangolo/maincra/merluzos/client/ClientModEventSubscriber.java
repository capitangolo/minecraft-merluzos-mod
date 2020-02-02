package net.capitangolo.maincra.merluzos.client;

import net.capitangolo.maincra.merluzos.MerluzosMod;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Subscribe to events from the MOD EventBus that should be handled on the PHYSICAL CLIENT side in this class
 *
 * @author Cadiboo
 */
@EventBusSubscriber(modid = MerluzosMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEventSubscriber {

	private static final Logger LOGGER = LogManager.getLogger(MerluzosMod.MODID + " Client Mod Event Subscriber");

	/**
	 * This method will be called by Forge when it is time for the mod to do its client-side setup
	 * This method will always be called after the Registry events.
	 * This means that all Blocks, Items, TileEntityTypes, etc. will all have been registered already
	 *//*
	@SubscribeEvent
	public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {

		// Register TileEntity Renderers
		ClientRegistry.bindTileEntitySpecialRenderer(MiniModelTileEntity.class, new MiniModelTileEntityRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(ElectricFurnaceTileEntity.class, new ElectricFurnaceTileEntityRenderer());
		LOGGER.debug("Registered TileEntity Renderers");

		// Register Entity Renderers
//		RenderingRegistry.registerEntityRenderingHandler(YourEntity.class, YourEntityRenderer::new);
//		LOGGER.debug("Registered Entity Renderers");

		// Register ContainerType Screens
		ScreenManager.registerFactory(ModContainerTypes.HEAT_COLLECTOR, HeatCollectorScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.ELECTRIC_FURNACE, ElectricFurnaceScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.MOD_FURNACE, ModFurnaceScreen::new);
		LOGGER.debug("Registered ContainerType Screens");

	}*/

}

package net.capitangolo.maincra.merluzos;

import net.capitangolo.maincra.merluzos.init.ModItemGroups;
import com.google.common.base.Preconditions;
import net.capitangolo.maincra.merluzos.item.HamKnifeItem;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@EventBusSubscriber(modid = MerluzosMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {

	private static final Logger LOGGER = LogManager.getLogger(MerluzosMod.MODID + " Mod Event Subscriber");

	@SubscribeEvent
	public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
		// Register all your blocks inside this registerAll call
		event.getRegistry().registerAll(
				setup(new Block(Block.Properties.create(Material.WOOL).hardnessAndResistance(1.0F).sound(SoundType.CLOTH)), "hake_block"),
				setup(new Block(Block.Properties.create(Material.WOOL).hardnessAndResistance(1.0F).sound(SoundType.CLOTH)), "aparaticos_block")
    );
		LOGGER.debug("Registered Blocks");
	}

	/**
	 * This method will be called by Forge when it is time for the mod to register its Items.
	 * This method will always be called after the Block registry method.
	 */
	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		registry.registerAll(
				// This is a very simple Item. It has no special properties except for being on our creative tab.
				setup(new HamKnifeItem(ItemTier.DIAMOND, 10, -1F, (new Item.Properties()).group(ModItemGroups.MOD_ITEM_GROUP)), "ham_knife"),
				setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP).food(new Food.Builder().hunger(8).saturation(0.8F).meat().build())), "fuet")
		);

		// We need to go over the entire registry so that we include any potential Registry Overrides
		for (final Block block : ForgeRegistries.BLOCKS.getValues()) {

			final ResourceLocation blockRegistryName = block.getRegistryName();
			// An extra safe-guard against badly registered blocks
			Preconditions.checkNotNull(blockRegistryName, "Registry Name of Block \"" + block + "\" of class \"" + block.getClass().getName() + "\"is null! This is not allowed!");

			// Check that the blocks is from our mod, if not, continue to the next block
			if (!blockRegistryName.getNamespace().equals(MerluzosMod.MODID)) {
				continue;
			}

			// If you have blocks that don't have a corresponding BlockItem, uncomment this code and create
			// an Interface - or even better an Annotation - called NoAutomaticBlockItem with no methods
			// and implement it on your blocks that shouldn't have BlockItems autmatically made for them
//			if (block instanceof NoAutomaticBlockItem) {
//				continue;
//			}

			// Make the properties, and make it so that the item will be on our ItemGroup (CreativeTab)
			final Item.Properties properties = new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP);
			// Create the new BlockItem with the block and it's properties
			final BlockItem blockItem = new BlockItem(block, properties);
			// Setup the new BlockItem with the block's registry name and register it
			registry.register(setup(blockItem, blockRegistryName));
		}
		LOGGER.debug("Registered Items");
	}

	/**
	 * This method will be called by Forge when it is time for the mod to register its TileEntityType.
	 * This method will always be called after the Block and Item registry methods.
	 */
	@SubscribeEvent
	public static void onRegisterTileEntityTypes(@Nonnull final RegistryEvent.Register<TileEntityType<?>> event) {
		// Register your TileEntityTypes here if you have them
		event.getRegistry().registerAll(
				// We don't have a datafixer for our TileEntity, so we pass null into build
//				setup(TileEntityType.Builder.create(MiniModelTileEntity::new, ModBlocks.MINI_MODEL).build(null), "mini_model"),
//				setup(TileEntityType.Builder.create(HeatCollectorTileEntity::new, ModBlocks.HEAT_COLLECTOR).build(null), "heat_collector"),
//				setup(TileEntityType.Builder.create(ElectricFurnaceTileEntity::new, ModBlocks.ELECTRIC_FURNACE).build(null), "electric_furnace"),
//				setup(TileEntityType.Builder.create(ModFurnaceTileEntity::new, ModBlocks.MOD_FURNACE).build(null), "mod_furnace")
		);
		LOGGER.debug("Registered TileEntityTypes");
	}

	/**
	 * This method will be called by Forge when it is time for the mod to register its ContainerTypes.
	 * This method will always be called after the Block and Item registry methods.
	 */
	@SubscribeEvent
	public static void onRegisterContainerTypes(@Nonnull final RegistryEvent.Register<ContainerType<?>> event) {
		// Register your ContainerTypes here if you have them
		event.getRegistry().registerAll(
//				setup(IForgeContainerType.create(HeatCollectorContainer::new), "heat_collector"),
//				setup(IForgeContainerType.create(ElectricFurnaceContainer::new), "electric_furnace"),
//				setup(IForgeContainerType.create(ModFurnaceContainer::new), "mod_furnace")
		);
		LOGGER.debug("Registered ContainerTypes");
	}

	/**
	 * This method will be called by Forge when a config changes.
	 *//*
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
		final ModConfig config = event.getConfig();
		// Rebake the configs when they change
		if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
			ConfigHelper.bakeClient(config);
			LOGGER.debug("Baked client config");
		} else if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
			ConfigHelper.bakeServer(config);
			LOGGER.debug("Baked server config");
		}
	}*/

	/**
	 * Performs setup on a registry entry
	 *
	 * @param name The path of the entry's name. Used to make a name who's domain is our mod's modid
	 */
	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name) {
		Preconditions.checkNotNull(name, "Name to assign to entry cannot be null!");
		return setup(entry, new ResourceLocation(MerluzosMod.MODID, name));
	}

	/**
	 * Performs setup on a registry entry
	 *
	 * @param registryName The full registry name of the entry
	 */
	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName) {
		Preconditions.checkNotNull(entry, "Entry cannot be null!");
		Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
		entry.setRegistryName(registryName);
		return entry;
	}

}

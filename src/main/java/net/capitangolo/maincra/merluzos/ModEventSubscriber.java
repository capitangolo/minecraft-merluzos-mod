package net.capitangolo.maincra.merluzos;

import com.google.common.base.Preconditions;
import net.capitangolo.maincra.merluzos.item.HamKnifeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.item.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.resources.ResourceLocation;
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

  public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(MerluzosMod.MODID) {
    @Override
    public ItemStack makeIcon() {
      return new ItemStack(ModEventSubscriber.HAKE_BLOCK);
    }
  };

	private static final Logger LOGGER = LogManager.getLogger(MerluzosMod.MODID + " Mod Event Subscriber");

  public static final Block HAKE_BLOCK = new Block(BlockBehaviour.Properties.of(Material.WOOL).strength(1.0F).sound(SoundType.WOOL));
  public static final Block APARATICOS_BLOCK = new Block(BlockBehaviour.Properties.of(Material.WOOL).strength(1.0F).sound(SoundType.WOOL));

  public static final Item HAM_KNIFE = new HamKnifeItem(Tiers.DIAMOND, 10, -1F, (new Item.Properties().tab(ModEventSubscriber.ITEM_GROUP)));
  public static final Item FUET = new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).meat().build()).tab(ModEventSubscriber.ITEM_GROUP));

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		// Register all your blocks inside this registerAll call
		event.getRegistry().registerAll(
				setup(ModEventSubscriber.HAKE_BLOCK, "hake_block"),
				setup(ModEventSubscriber.APARATICOS_BLOCK, "aparaticos_block")
    );
		LOGGER.debug("Registered Blocks");
	}

	/**
	 * This method will be called by Forge when it is time for the mod to register its Items.
	 * This method will always be called after the Block registry method.
	 */
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();
		registry.registerAll(
				// This is a very simple Item. It has no special properties except for being on our creative tab.
				setup(ModEventSubscriber.HAM_KNIFE, "ham_knife"),
				setup(ModEventSubscriber.FUET, "fuet")
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
			final Item.Properties properties = new Item.Properties().tab(ModEventSubscriber.ITEM_GROUP);
			// Create the new BlockItem with the block and it's properties
			final BlockItem blockItem = new BlockItem(block, properties);
			// Setup the new BlockItem with the block's registry name and register it
			registry.register(setup(blockItem, blockRegistryName));
		}
		LOGGER.debug("Registered Items");
	}

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

package com.example.modid;

import com.eliaslucky.mc_dos.blocks.ICustomCreativeTab;
import com.example.modid.registry.ModDrivers;
import com.example.modid.registry.ModExecutables;
import com.mojang.logging.LogUtils;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * Example addon.
 *
 * <p>Adds a peripheral block, a driver per OS family, an example
 * terminal application, and a couple of executables. Copy this project
 * as a starting point for your own addon.
 */
@Mod(ComputersAddon.MODID)
public class ComputersAddon {
	public static final String MODID = "computersaddon";
	public static final String NAME  = "BlockOS Addon Example";
	public static final Logger LOGGER = LogUtils.getLogger();

	public ComputersAddon(FMLJavaModLoadingContext context) {
		IEventBus modBus = context.getModEventBus();

		AllCreativeModeTabs.register(modBus);
		AllBlocks.register(modBus);
		AllBlockEntities.register(modBus);
		AllItems.register(modBus);

		modBus.addListener(this::commonSetup);
		modBus.addListener(this::buildContents);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			ModDrivers.register();
			ModExecutables.register();
		});
	}

	/**
	 * Routes blocks that implement {@link ICustomCreativeTab} into the
	 * tab they requested. Same pattern the base mod uses, so a single
	 * listener handles every block without repeating registration code.
	 */
	public void buildContents(BuildCreativeModeTabContentsEvent event) {
		AllItems.ITEMS.getEntries().forEach(ro -> {
			Item item = ro.get();
			if (item instanceof BlockItem bi) {
				Block block = bi.getBlock();
				if (block instanceof ICustomCreativeTab custom) {
					if (event.getTabKey() == custom.getCreativeTab()) {
						event.accept(item);
					}
				}
			}
		});
	}
}

package com.example.modid;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Item registry. Every block that has no custom item gets a plain
 * {@link BlockItem} here.
 */
public final class AllItems {
	public static final DeferredRegister<Item> ITEMS =
			DeferredRegister.create(ForgeRegistries.ITEMS, ComputersAddon.MODID);

	public static final RegistryObject<Item> EXAMPLE_PERIPHERAL_ITEM =
			ITEMS.register("example_peripheral",
					() -> new BlockItem(AllBlocks.EXAMPLE_PERIPHERAL.get(),
							new Item.Properties()));

	private AllItems() {}

	public static void register(IEventBus modBus) {
		ITEMS.register(modBus);
	}
}

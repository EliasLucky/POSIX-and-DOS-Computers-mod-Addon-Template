package com.example.modid;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Creative tabs for the addon. If you'd rather add your blocks to the
 * base mod's tab instead, point your blocks' {@code getCreativeTab()}
 * at {@code AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey()} and delete
 * this file.
 */
public final class AllCreativeModeTabs {
	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ComputersAddon.MODID);

	public static final RegistryObject<CreativeModeTab> ADDON_TAB = TABS.register(
			"addon_tab",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup." + ComputersAddon.MODID))
					.icon(() -> new ItemStack(AllItems.EXAMPLE_PERIPHERAL_ITEM.get()))
					.build());

	private AllCreativeModeTabs() {}

	public static void register(IEventBus modBus) {
		TABS.register(modBus);
	}
}

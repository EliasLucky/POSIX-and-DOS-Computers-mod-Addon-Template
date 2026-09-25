package com.example.modid.client;

import com.eliaslucky.mc_dos.client.apps.TerminalApplicationRegistry;
import com.example.modid.ComputersAddon;
import com.example.modid.client.apps.CalcApplication;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client-only setup. Registers TUI programs with
 * {@link TerminalApplicationRegistry}.
 *
 * <p>The name given here must match the {@code NAME} portion of the
 * {@code APP_LAUNCH:NAME:...} string your server-side runner returns.
 * In this template, {@code ModExecutables} produces
 * {@code "APP_LAUNCH:CALC:..."}, and this class binds that to
 * {@link CalcApplication}.
 */
@Mod.EventBusSubscriber(modid = ComputersAddon.MODID,
						value = Dist.CLIENT,
						bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ClientSetup {
	private ClientSetup() {}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			TerminalApplicationRegistry.register("CALC", CalcApplication::new);
		});
	}
}

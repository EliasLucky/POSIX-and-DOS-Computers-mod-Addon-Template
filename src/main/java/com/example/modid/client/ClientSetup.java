package com.example.modid.client;

import com.eliaslucky.mc_dos.client.apps.TerminalApplicationRegistry;
import com.eliaslucky.mc_dos.client.apps.bios.BiosSetupRegistry;
import com.eliaslucky.mc_dos.client.tui.TuiThemes;
import com.example.modid.ComputersAddon;
import com.example.modid.bios.Pdp11BiosSetupApplication;
import com.example.modid.client.apps.ClockApplication;
import com.example.modid.client.themes.AmberTheme;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client-only setup. Runs once after the client mod list is loaded.
 *
 * <p>Three registries are populated here:
 * <ul>
 *   <li>{@link TerminalApplicationRegistry} TUI programs launched by
 *       the {@code APP_LAUNCH:} protocol</li>
 *   <li>{@link BiosSetupRegistry} BIOS SETUP screens, keyed by
 *       {@code Bios.setupScreenId()}</li>
 *   <li>{@link TuiThemes} color themes shared by every TUI screen</li>
 * </ul>
 *
 * <p>Nothing here should be touched from common setup — these classes
 * reference {@code GuiGraphics} and only exist on the physical client.
 */
@Mod.EventBusSubscriber(
        modid = ComputersAddon.MODID,
        value = Dist.CLIENT,
        bus   = Mod.EventBusSubscriber.Bus.MOD)
public final class ClientSetup {
    private ClientSetup() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Themes
            // Registered by ResourceLocation so addons can't collide
            // with each other or with the base mod's built-in themes.
            TuiThemes.register(
                    ResourceLocation.fromNamespaceAndPath(
                    		ComputersAddon.MODID, "amber"),
                    AmberTheme.THEME);

            // Terminal apps
            // The name matches the NAME portion of the string that the
            // server-side runner returns: "APP_LAUNCH:CLOCK::".
            TerminalApplicationRegistry.register("CLOCK", ClockApplication::new);

            // BIOS setup screens
            // The key matches Bios.setupScreenId(). The PDP-11's BIOS
            // returns "" (no setup), so this entry only becomes active
            // if the BIOS later opts in.
            BiosSetupRegistry.register("PDP11_SETUP",
                    (screen, config, name) ->
                            new Pdp11BiosSetupApplication(screen, config, name));
        });
    }
}

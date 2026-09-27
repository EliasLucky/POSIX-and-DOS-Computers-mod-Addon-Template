package com.example.modid;

import com.example.modid.registry.ModDrivers;
import com.example.modid.registry.ModExecutables;
import com.example.modid.registry.ModMachineTypes;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * The addon's main mod class.
 *
 * <p>Common setup (runs on both client and dedicated server) registers
 * machine types, drivers, and executables. Client-only setup — apps,
 * themes, BIOS setup screens — lives in
 * {@link com.example.computersaddon.client.ClientSetup}.
 */
@Mod(ComputersAddon.MODID)
public class ComputersAddon {
    public static final String MODID = "computersaddon";

    public ComputersAddon(FMLJavaModLoadingContext ctx) {
        IEventBus modBus = ctx.getModEventBus();

        AllCreativeModeTabs.register(modBus);
        AllBlocks.register(modBus);
        AllBlockEntities.register(modBus);
        AllItems.register(modBus);

        modBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Order matters for machine types because some drivers
            // reference the machine's command processor.
            ModMachineTypes.register();
            ModDrivers.register();
            ModExecutables.register();
        });
    }
}
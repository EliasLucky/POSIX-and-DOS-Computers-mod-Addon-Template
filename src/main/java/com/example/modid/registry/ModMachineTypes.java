package com.example.modid.registry;

import com.eliaslucky.mc_dos.blocks.computer.MachineTypeRegistry;
import com.example.modid.machines.Pdp11MachineType;

/**
 * Registers the addon's machine types.
 *
 * <p>Called once from {@code FMLCommonSetupEvent} on both sides.
 * Machine types are common code they describe the physical machine
 * and its OS-independent properties.
 */
public final class ModMachineTypes {
    private ModMachineTypes() {}

    public static void register() {
        MachineTypeRegistry.register(new Pdp11MachineType());
    }
}

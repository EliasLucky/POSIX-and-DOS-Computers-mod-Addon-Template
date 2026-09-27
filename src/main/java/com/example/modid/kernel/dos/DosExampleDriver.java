package com.example.modid.kernel.dos;

import com.eliaslucky.mc_dos.api.hardware.*;

import java.util.List;

/**
 * DOS driver for the plotter. Registered as {@code "PLOTTER"} in the
 * "dos" family. Loads when {@code CONFIG.SYS} contains a
 * {@code DEVICE=...PLOTTER.SYS} line, and registers the device name
 * {@code PLOT} in the DOS device table.
 */
public class DosPlotterDriver implements Driver {
    private Peripheral peripheral;

    @Override public String name() { return "PLOTTER.SYS"; }

    @Override
    public DriverInitResult init(DriverContext ctx) {
        int slot = Integer.parseInt(
                ctx.loadParams().getOrDefault("SLOT", "0"));

        List<PeripheralAddress> matches = ctx.bus().scan().stream()
                .filter(a -> a.deviceClass().equals("plotter"))
                .filter(a -> a.slot() == slot)
                .toList();

        if (matches.isEmpty()) {
            ctx.log("PLOTTER.SYS: no plotter at slot " + slot);
            return DriverInitResult.FAILED;
        }

        peripheral = ctx.bus().get(matches.get(0));
        if (peripheral == null) return DriverInitResult.FAILED;

        String name = ctx.registerDevice("PLOT", DeviceHandler.of(peripheral));
        if (name == null) {
            ctx.log("PLOTTER.SYS: name PLOT already taken");
            return DriverInitResult.FAILED;
        }

        ctx.log("PLOTTER.SYS installed at slot " + slot);
        return DriverInitResult.OK;
    }

    @Override public void shutdown() { peripheral = null; }
}
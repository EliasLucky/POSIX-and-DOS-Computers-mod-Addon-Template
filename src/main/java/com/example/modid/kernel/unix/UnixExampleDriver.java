package com.example.modid.kernel.unix;

import com.eliaslucky.mc_dos.api.hardware.*;

import java.util.List;

/**
 * UNIX v7 driver for the plotter. Registered as {@code "PLOTTER"} in
 * the "unix" family. Loaded automatically by the v7 kernel at boot;
 * registers {@code /dev/plotter}.
 */
public class UnixPlotterDriver implements Driver {
    private Peripheral peripheral;

    @Override public String name() { return "plotter.c"; }

    @Override
    public DriverInitResult init(DriverContext ctx) {
        List<PeripheralAddress> matches = ctx.bus().scan().stream()
                .filter(a -> a.deviceClass().equals("plotter"))
                .toList();

        if (matches.isEmpty()) {
            ctx.log("plotter: no device attached");
            return DriverInitResult.FAILED;
        }

        peripheral = ctx.bus().get(matches.get(0));
        if (peripheral == null) return DriverInitResult.FAILED;

        String path = ctx.registerDevice("plotter", DeviceHandler.of(peripheral));
        if (path == null) return DriverInitResult.FAILED;

        ctx.log("plotter: attached, " + path + " registered");
        return DriverInitResult.OK;
    }

    @Override public void shutdown() { peripheral = null; }
}
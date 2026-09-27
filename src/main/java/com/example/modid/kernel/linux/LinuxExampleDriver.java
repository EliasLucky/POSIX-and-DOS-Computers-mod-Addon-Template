package com.example.modid.kernel.linux;

import com.eliaslucky.mc_dos.api.hardware.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Linux module for the plotter. Registered as {@code "PLOTTER"} in the
 * "linux" family. Autoloaded; binds to every matching peripheral on the
 * bus and registers {@code /dev/plotter0}, {@code /dev/plotter1}, ...
 */
public class LinuxPlotterDriver implements Driver {
    private static final int MAX_DEVICES = 4;

    private final List<Peripheral> bound = new ArrayList<>();

    @Override public String name() { return "plotter.ko"; }

    @Override
    public DriverInitResult init(DriverContext ctx) {
        List<PeripheralAddress> matches = ctx.bus().scan().stream()
                .filter(a -> a.deviceClass().equals("plotter"))
                .limit(MAX_DEVICES)
                .toList();

        if (matches.isEmpty()) return DriverInitResult.FAILED;

        for (PeripheralAddress a : matches) {
            Peripheral p = ctx.bus().get(a);
            if (p == null) continue;
            String path = ctx.registerDevice("plotter", DeviceHandler.of(p));
            if (path != null) {
                bound.add(p);
                ctx.log("plotter: registered " + path);
            }
        }

        return bound.isEmpty() ? DriverInitResult.FAILED : DriverInitResult.OK;
    }

    @Override public void shutdown() { bound.clear(); }
}
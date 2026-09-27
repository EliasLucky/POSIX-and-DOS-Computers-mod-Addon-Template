package com.example.modid.registry;

import com.eliaslucky.mc_dos.api.exec.DosMZFormat;
import com.eliaslucky.mc_dos.api.exec.ExecutableRegistry;

/**
 * Registers the addon's executables.
 *
 * <p>Each entry pairs an OS family, a filename, a file format matcher,
 * a template body, and a runner. The runner may return a string
 * starting with {@code "APP_LAUNCH:"} to launch a client-side TUI,
 * or a plain string to print on the terminal.
 */
public final class ModExecutables {
    private ModExecutables() {}

    public static void register() {
        // DOS: a demo program that plots a pattern.
        ExecutableRegistry.register("dos", "PLOTDEMO.EXE", DosMZFormat.INSTANCE,
                "MZ\u0090\u0000\u0003\u0000\u0000\u0000Plotter demo\n",
                (computer, args, file) -> {
                    // The plotter device in DOS is named "PLOT".
                    // Writing to it queues lines for the peripheral.
                    return "Plotter demo: place a plotter block adjacent\n"
                         + "and type PLOT <pattern> to plot.\n";
                });
        
        ExecutableRegistry.register("dos", "CLOCK.EXE", DosMZFormat.INSTANCE,
                "MZ\u0090\u0000\u0003\u0000\u0000\u0000Clock utility\n",
                (computer, args, file) -> "APP_LAUNCH:CLOCK::");
    }
}
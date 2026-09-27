package com.example.computersaddon.registry;

import com.eliaslucky.mc_dos.api.hardware.DriverRegistry;
import com.example.modid.kernel.dos.DosPlotterDriver;
import com.example.modid.kernel.linux.LinuxPlotterDriver;
import com.example.modid.kernel.unix.UnixPlotterDriver;

/**
 * Registers the addon's device drivers.
 *
 * <p>One driver per OS family. The second argument must match:
 * <ul>
 *   <li>DOS: the {@code .SYS} basename that a {@code CONFIG.SYS}
 *       {@code DEVICE=} line names</li>
 *   <li>UNIX: the {@code .c} source basename the v7 kernel expected</li>
 *   <li>Linux: the {@code .ko} module basename</li>
 * </ul>
 */
public final class ModDrivers {
    private ModDrivers() {}

    public static void register() {
        DriverRegistry.register("dos",   "PLOTTER", DosPlotterDriver::new);
        DriverRegistry.register("unix",  "PLOTTER", UnixPlotterDriver::new);
        DriverRegistry.register("linux", "PLOTTER", LinuxPlotterDriver::new);
    }
}
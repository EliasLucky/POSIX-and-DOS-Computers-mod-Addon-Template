package com.example.modid.machines;

import com.eliaslucky.mc_dos.api.bios.Bios;
import com.eliaslucky.mc_dos.api.bios.MachineConfig;
import com.eliaslucky.mc_dos.blocks.computer.ComputerType;
import com.eliaslucky.mc_dos.blocks.computer.MachineType;
import com.eliaslucky.mc_dos.blocks.computer.processors.ICommandProcessor;
import com.example.modid.bios.Pdp11Bios;
import com.example.modid.processors.Pdp11CommandProcessor;

import java.util.List;
import java.util.function.Supplier;

/**
 * A DEC PDP-11/70 running UNIX v7.
 *
 * <p>Demonstrates every field a machine type carries: model name,
 * CPU description, BIOS, command processor, and drive bays. The
 * command processor is the OS-side brain the shell, kernel,
 * and file-name policy all come from there.
 */
public class Pdp11MachineType implements MachineType {
    @Override public String id()        { return "computersaddon:pdp11_70"; }
    @Override public String modelName() { return "DEC PDP-11/70"; }
    @Override public String cpuName()   { return "DEC PDP-11 @ 15 MHz"; }
    @Override public String osVersion() { return "UNIX v7 (Bell Labs)"; }
    @Override public String busType()   { return "UNIBUS"; }
    @Override public int    textColor() { return 0xFFFFAA00; }  // amber

    @Override
    public List<String> defaultFiles() {
        return List.of(
                "bin/", "dev/", "etc/", "usr/", "var/", "root/",
                "etc/passwd", "etc/rc", "etc/motd",
                "usr/bin/", "usr/bin/plotter_demo"
        );
    }

    @Override
    public ICommandProcessor commandProcessor() {
        return new Pdp11CommandProcessor();
    }

    @Override public String defaultPath() { return "/"; }

    @Override
    public Bios bios() { return new Pdp11Bios(); }

    @Override
    public List<ComputerType.DriveBaySpec> driveBays() {
        // PDP-11/70s shipped with RK05 disk drives, no floppies.
        // The tuple is (type, dosLetter, posixDevice, posixMountPoint).
        return List.of();
    }

    @Override
    public Supplier<MachineConfig> defaultConfig() {
        return () -> new MachineConfig(
                System.currentTimeMillis(),
                MachineConfig.FloppyType.NONE,
                MachineConfig.FloppyType.NONE,
                MachineConfig.DiskType.TYPE_3,   // RK05 drives
                MachineConfig.DiskType.NONE,
                56,                              // 56 KB of core memory
                2048,                            // 2 MB extended
                false,                           // no FPU
                MachineConfig.DisplayType.MONO);
    }
}

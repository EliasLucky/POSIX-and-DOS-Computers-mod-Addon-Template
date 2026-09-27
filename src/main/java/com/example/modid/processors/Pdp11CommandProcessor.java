package com.example.modid.processors;

import com.eliaslucky.mc_dos.api.hardware.Kernel;
import com.eliaslucky.mc_dos.api.shell.ShellDialect;
import com.eliaslucky.mc_dos.api.shell.StreamResolver;
import com.eliaslucky.mc_dos.blocks.computer.ComputerBlockEntity;
import com.eliaslucky.mc_dos.blocks.computer.fs.FileNamePolicy;
import com.eliaslucky.mc_dos.blocks.computer.fs.PosixFileNamePolicy;
import com.eliaslucky.mc_dos.blocks.computer.kernel.unix.UnixV7Kernel;
import com.eliaslucky.mc_dos.blocks.computer.shell.posix.PosixStreamResolver;
import com.eliaslucky.mc_dos.blocks.computer.shell.unix.BourneV7Dialect;
import com.eliaslucky.mc_dos.blocks.computer.processors.
import java.util.Locale;

/**
 * Minimal UNIX v7 command processor for the PDP-11 demo.
 *
 * <p>full implementation would delegate to the
 * base mod's {@code UnixV7CommandProcessor} or a subclass. The point
 * of this class is to show that a machine type supplies its own
 * processor, which supplies its own shell dialect, kernel, and
 * stream resolver.
 */
public class Pdp11CommandProcessor implements ICommandProcessor {
    @Override public String osFamily()    { return "unix"; }
    @Override public String defaultPath() { return "/bin:/usr/bin"; }
    @Override public FileNamePolicy fileNamePolicy() { return PosixFileNamePolicy.INSTANCE; }

    @Override public Kernel createKernel() { return new UnixV7Kernel(); }

    @Override
    public ShellDialect shellDialect(Kernel kernel) { return new BourneV7Dialect(); }

    @Override
    public StreamResolver createStreamResolver() { return PosixStreamResolver.INSTANCE; }

    @Override
    public String getPrompt(String currentPath) {
        return "pdp11# ";
    }

    @Override
    public String defaultFileContent(String fileName) {
        return switch (fileName) {
            case "etc/motd" -> "PDP-11/70 - UNIX v7\n";
            case "etc/passwd" -> "root::0:0:root:/root:/bin/sh\n";
            default -> null;
        };
    }

    @Override
    public String process(ComputerBlockEntity computer, String rawInput) {
        String input = rawInput.trim();
        if (input.isEmpty()) return "";

        String[] parts = input.split("\\s+", 2);
        String cmd = parts[0].toLowerCase(Locale.ROOT);
        String arg = parts.length > 1 ? parts[1].trim() : "";

        return switch (cmd) {
            case "echo" -> arg;
            case "pwd"  -> computer.getFileSystem().getCurrentPath();
            case "uname" -> "UNIX v7";
            default -> cmd + ": not found";
        };
    }
}

package com.example.modid.registry;

import com.eliaslucky.mc_dos.api.exec.DosMZFormat;
import com.eliaslucky.mc_dos.api.exec.ExecutableRegistry;
import com.eliaslucky.mc_dos.api.exec.PosixElfFormat;

/**
 * Registers example executables.
 *
 * <p>An executable is a file the shell can run. The runner receives
 * the computer, the command-line args, and the VFS node for the file.
 * Return a string to print on the terminal, or a string starting with
 * {@code "APP_LAUNCH:"} to open a client-side TUI program.
 *
 * <p>The DOS and POSIX entries here are the same program wrapped in
 * two formats. A user who copies {@code CALC.EXE} to a Linux machine
 * would get "Exec format error" — the file content doesn't start with
 * a POSIX shebang or ELF magic.
 */
public final class ModExecutables {
	private ModExecutables() {}

	public static void register() {
		// MS-DOS

		// CALC.EXE — opens the calculator TUI.
		// The APP_LAUNCH string is parsed by ComputerTerminalScreen.
		ExecutableRegistry.register("dos", "CALC.EXE", DosMZFormat.INSTANCE,
				"MZ\u0090\u0000\u0003\u0000\u0000\u0000BlockOS Calculator\n",
				(computer, args, file) -> "APP_LAUNCH:CALC:" + args + ":");

		// HELLO.EXE — prints a greeting.
		ExecutableRegistry.register("dos", "HELLO.EXE", DosMZFormat.INSTANCE,
				"MZ\u0090\u0000\u0003\u0000\u0000\u0000BlockOS Hello\n",
				(computer, args, file) ->
						"Hello, " + (args.isEmpty() ? "world" : args) + "!\n");

		// POSIX (Linux and UNIX share this family)
		ExecutableRegistry.register("posix", "/BIN/CALC", PosixElfFormat.INSTANCE,
				"\u007fELF /bin/calc\n",
				(computer, args, file) -> "APP_LAUNCH:CALC:" + args + ":");

		ExecutableRegistry.register("posix", "/BIN/HELLO", PosixElfFormat.INSTANCE,
				"\u007fELF /bin/hello\n",
				(computer, args, file) ->
						"Hello, " + (args.isEmpty() ? "world" : args) + "!\n");
	}
}

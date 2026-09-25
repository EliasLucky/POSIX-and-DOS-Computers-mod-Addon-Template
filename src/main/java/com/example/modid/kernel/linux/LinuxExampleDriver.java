package com.example.modid.kernel.linux;

import com.eliaslucky.mc_dos.api.hardware.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Linux module for the example peripheral.
 *
 * <p>Autoloaded by the kernel on boot. Binds to every matching
 * peripheral on the bus, up to a cap, and registers each as an indexed
 * device node: {@code /dev/example0}, {@code /dev/example1}, ...
 *
 * <p>User access: {@code cat /dev/example0}, {@code echo hi > /dev/example0}.
 */
public class LinuxExampleDriver implements Driver {
	/** Real Linux drivers cap their device count per class. */
	private static final int MAX_DEVICES = 8;

	private final List<Peripheral> bound = new ArrayList<>();

	@Override public String name() { return "example.ko"; }

	@Override
	public DriverInitResult init(DriverContext ctx) {
		List<PeripheralAddress> matches = ctx.bus().scan().stream()
				.filter(a -> a.deviceClass().equals("example"))
				.limit(MAX_DEVICES)
				.toList();

		if (matches.isEmpty()) {
			ctx.log("example: no matching devices");
			return DriverInitResult.FAILED;
		}

		for (PeripheralAddress addr : matches) {
			Peripheral p = ctx.bus().get(addr);
			if (p == null) continue;

			String devPath = ctx.registerDevice("example",
					DeviceHandler.of(p));
			if (devPath == null) {
				ctx.log("example: failed to register at slot " + addr.slot());
				continue;
			}

			bound.add(p);
			ctx.log("example: registered " + devPath);
		}

		return bound.isEmpty() ? DriverInitResult.FAILED : DriverInitResult.OK;
	}

	@Override public void shutdown() { bound.clear(); }
}

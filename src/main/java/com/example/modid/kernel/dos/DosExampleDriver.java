package com.example.modid.kernel.dos;

import com.eliaslucky.mc_dos.api.hardware.*;

import java.util.List;

/**
 * DOS driver for the example peripheral.
 *
 * <p>In real MS-DOS, this would ship as {@code EXAMPLE.SYS} and be
 * loaded by a line in {@code CONFIG.SYS}:
 * <pre>
 * DEVICE=C:\DRIVERS\EXAMPLE.SYS /SLOT=0
 * </pre>
 *
 * <p>The driver scans the bus for a peripheral whose device class is
 * {@code "example"}, wraps it with {@link DeviceHandler#of}, and
 * registers the name {@code EXAMPLE}. Once loaded, typing {@code EXAMPLE}
 * at the DOS prompt dispatches bytes to the peripheral.
 */
public class DosExampleDriver implements Driver {
	private Peripheral peripheral;

	@Override public String name() { return "EXAMPLE.SYS"; }

	@Override
	public DriverInitResult init(DriverContext ctx) {
		int slot = Integer.parseInt(ctx.loadParams().getOrDefault("SLOT", "0"));

		List<PeripheralAddress> matches = ctx.bus().scan().stream()
				.filter(a -> a.deviceClass().equals("example"))
				.filter(a -> a.slot() == slot)
				.toList();

		if (matches.isEmpty()) {
			ctx.log("EXAMPLE.SYS: no device at slot " + slot);
			return DriverInitResult.FAILED;
		}

		this.peripheral = ctx.bus().get(matches.get(0));
		if (peripheral == null) return DriverInitResult.FAILED;

		// Device name: DOS allows 1–8 chars, no extension.
		String devName = ctx.registerDevice("EXAMPLE",
				DeviceHandler.of(peripheral));
		if (devName == null) {
			ctx.log("EXAMPLE.SYS: name EXAMPLE already taken");
			return DriverInitResult.FAILED;
		}

		ctx.log("EXAMPLE.SYS installed at slot " + slot);
		return DriverInitResult.OK;
	}

	@Override public void shutdown() { peripheral = null; }
}

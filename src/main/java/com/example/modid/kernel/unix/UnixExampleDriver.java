package com.example.modid.kernel.unix;

import com.eliaslucky.mc_dos.api.hardware.*;

import java.util.List;

/**
 * UNIX v7 driver for the example peripheral.
 *
 * <p>Drivers in v7 were compiled into the kernel. The kernel scans the
 * bus at boot and links any driver whose registered name matches a
 * device class. The device node lands at {@code /dev/example}.
 *
 * <p>User access: {@code cat /dev/example} reads, and
 * {@code echo hi > /dev/example} writes.
 */
public class UnixExampleDriver implements Driver {
	private Peripheral peripheral;

	@Override public String name() { return "example.c"; }

	@Override
	public DriverInitResult init(DriverContext ctx) {
		List<PeripheralAddress> matches = ctx.bus().scan().stream()
				.filter(a -> a.deviceClass().equals("example"))
				.toList();

		if (matches.isEmpty()) {
			ctx.log("example: no device attached");
			return DriverInitResult.FAILED;
		}

		// v7 binds to the first matching device only.
		this.peripheral = ctx.bus().get(matches.get(0));
		if (peripheral == null) return DriverInitResult.FAILED;

		String devPath = ctx.registerDevice("example",
				DeviceHandler.of(peripheral));
		if (devPath == null) {
			ctx.log("example: could not create device node");
			return DriverInitResult.FAILED;
		}

		ctx.log("example: attached, " + devPath + " registered");
		return DriverInitResult.OK;
	}

	@Override public void shutdown() { peripheral = null; }
}

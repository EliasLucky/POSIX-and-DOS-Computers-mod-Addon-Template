package com.example.modid.blocks;

import com.eliaslucky.mc_dos.api.hardware.Peripheral;
import com.example.modid.AllBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Example peripheral: echoes back whatever it receives.
 *
 * <p>Each {@code write("hello\n")} queues an {@code "echo: hello\n"}
 * reply into the output buffer, which the next {@code read(...)} drains.
 * This is the simplest interesting peripheral — no external state, no
 * hardware, just demonstrating the byte in / byte out contract.
 */
public class ExamplePeripheralBlockEntity extends BlockEntity implements Peripheral {
	private final Deque<byte[]> pendingInput  = new ArrayDeque<>();
	private final Deque<byte[]> pendingOutput = new ArrayDeque<>();

	public ExamplePeripheralBlockEntity(BlockPos pos, BlockState state) {
		super(AllBlockEntities.EXAMPLE_PERIPHERAL.get(), pos, state);
	}

	// Peripheral
	@Override public String deviceClass() { return "example"; }
	@Override public String vendorId()	  { return "computersaddon"; }
	@Override public String productId()   { return "example_v1"; }
	@Override public String description() { return "Example echo device"; }
	@Override public boolean isReady()	  { return level != null && !level.isClientSide(); }
	@Override public boolean hasData()	  { return !pendingOutput.isEmpty(); }

	@Override
	public synchronized void write(byte[] data) {
		pendingInput.addLast(data);
	}

	@Override
	public synchronized byte[] read(int maxBytes) {
		if (pendingOutput.isEmpty()) return new byte[0];
		byte[] chunk = pendingOutput.pollFirst();
		if (chunk.length <= maxBytes) return chunk;

		byte[] clipped = new byte[maxBytes];
		System.arraycopy(chunk, 0, clipped, 0, maxBytes);
		return clipped;
	}

	@Override
	public int ioctl(int cmd, byte[] arg) {
		// Not used by this peripheral. Real hardware would implement
		// device-specific control here.
		return 0;
	}

	// Tick
	public static void tick(Level level, BlockPos pos, BlockState state, ExamplePeripheralBlockEntity be) {
		if (level.isClientSide()) return;
		be.drainInput();
	}

	private synchronized void drainInput() {
		while (!pendingInput.isEmpty()) {
			byte[] in = pendingInput.pollFirst();
			String line = new String(in, StandardCharsets.UTF_8);
			String reply = "echo: " + line;
			if (!reply.endsWith("\n")) reply += "\n";
			pendingOutput.addLast(reply.getBytes(StandardCharsets.UTF_8));
		}
	}
}

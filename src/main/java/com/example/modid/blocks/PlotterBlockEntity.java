package com.example.modid.blocks;

import com.eliaslucky.mc_dos.api.hardware.Peripheral;
import com.example.modid.AllBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A pen plotter. The peripheral contract is byte in / byte out —
 * the plotter doesn't know or care which OS is talking to it.
 *
 * <p>When it receives a line, it "plots" it by appending the line
 * to an internal buffer that the driver layer can read back. A real
 * device would move a pen; we're demonstrating the interface.
 */
public class PlotterBlockEntity extends BlockEntity implements Peripheral {
    /** Bytes waiting to be "plotted". Drained on the next tick. */
    private final Deque<String> plotQueue = new ArrayDeque<>();

    /** Output lines the peripheral has produced, waiting to be read. */
    private final Deque<String> outputQueue = new ArrayDeque<>();

    /** Total lines plotted since placement. */
    private int linesPlotted = 0;

    public PlotterBlockEntity(BlockPos pos, BlockState state) {
        super(AllBlockEntities.PLOTTER.get(), pos, state);
    }

    // Peripheral contract
    @Override public String deviceClass() { return "plotter"; }
    @Override public String vendorId()    { return ComputersAddon.MODID; }
    @Override public String productId()   { return "plotter_v1"; }
    @Override public String description() { return "Pen plotter"; }

    @Override
    public boolean isReady() {
        return level != null && !level.isClientSide();
    }

    @Override
    public boolean hasData() {
        return !outputQueue.isEmpty();
    }

    @Override
    public synchronized void write(byte[] data) {
        String text = new String(data, StandardCharsets.UTF_8);
        for (String line : text.split("\n")) {
            if (!line.isEmpty()) plotQueue.addLast(line);
        }
    }

    @Override
    public synchronized byte[] read(int maxBytes) {
        if (outputQueue.isEmpty()) return new byte[0];
        StringBuilder sb = new StringBuilder();
        while (!outputQueue.isEmpty() && sb.length() < maxBytes) {
            sb.append(outputQueue.pollFirst());
            if (!outputQueue.isEmpty()) sb.append('\n');
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public int ioctl(int cmd, byte[] arg) {
        // Command 1: query line count.
        if (cmd == 1) return linesPlotted;
        return -1;
    }

    // Server-side processing
    public static void tick(Level level, BlockPos pos, BlockState state,
                            PlotterBlockEntity be) {
        if (level.isClientSide()) return;
        be.processQueue();
    }

    private synchronized void processQueue() {
        while (!plotQueue.isEmpty()) {
            String line = plotQueue.pollFirst();
            linesPlotted++;
            outputQueue.addLast("PLOT " + linesPlotted + ": " + line);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("LinesPlotted", linesPlotted);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        linesPlotted = tag.getInt("LinesPlotted");
    }
}
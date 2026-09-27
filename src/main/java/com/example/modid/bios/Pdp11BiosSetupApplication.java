package com.example.modid.bios;

import com.eliaslucky.mc_dos.api.bios.MachineConfig;
import com.eliaslucky.mc_dos.client.ComputerTerminalScreen;
import com.eliaslucky.mc_dos.client.apps.TerminalApplication;
import com.eliaslucky.mc_dos.client.tui.*;

import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.glfw.GLFW;

/**
 * Example SETUP screen. Real PDP-11s had no interactive SETUP; this
 * is a placeholder that shows what a custom BIOS would build using
 * the TUI widgets.
 */
public class Pdp11BiosSetupApplication extends TerminalApplication {
    private final TuiTheme theme = TuiThemes.get("computersaddon:amber");
    private final MachineConfig config;
    private final String biosName;
    private final TuiScreen widgets = new TuiScreen();
    private final TuiKeyValueTable table;

    public Pdp11BiosSetupApplication(ComputerTerminalScreen screen, MachineConfig config, String biosName) {
        super(screen);
        this.config = config;
        this.biosName = biosName;

        this.table = new TuiKeyValueTable(4, 4, 60, 10);
        table.setRows(java.util.List.of(
                new TuiKeyValueTable.Row("CPU",              "PDP-11/70",
                        false, null),
                new TuiKeyValueTable.Row("Memory (KW)",
                        String.valueOf(config.baseMemoryKb() / 4),
                        false, null),
                new TuiKeyValueTable.Row("Boot Device",     "RK0",
                        false, null),
                new TuiKeyValueTable.Row("Console",         "DL11",
                        false, null)));

        widgets.add(table);
        widgets.setFocus(table);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        g.fill(0, 0, appWidth, appHeight, theme.screenBg());

        TuiBox frame = new TuiBox(2, 2, 64, 14, TuiBox.Style.SINGLE)
                .titled(biosName + " Configuration")
                .border(theme.border())
                .fill(theme.frameBg());
        frame.render(g, this);

        table.render(g, this);

        int footY = (rows() - 1) * CELL_H;
        g.fill(0, footY, appWidth, footY + CELL_H, theme.statusBg());
        drawDos(g, " ESC to exit ", 0, footY, theme.statusFg());

        widgets.render(g, this);
    }

    @Override
    public boolean keyPressed(int key, int scan, int mods) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            screen.returnToShell();
            return true;
        }
        return table.keyPressed(key, scan, mods);
    }

    @Override
    public String getTitle() { return "PDP-11 SETUP"; }
}

package com.example.modid.client.apps;

import com.eliaslucky.mc_dos.client.ComputerTerminalScreen;
import com.eliaslucky.mc_dos.client.apps.TerminalApplication;
import com.eliaslucky.mc_dos.client.tui.TuiPalette;
import com.eliaslucky.mc_dos.client.tui.TuiTheme;
import com.eliaslucky.mc_dos.client.tui.TuiThemes;

import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.glfw.GLFW;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A minimal TUI program: displays the current time, updates every
 * frame, exits on Escape.
 *
 * <p>Demonstrates the minimal surface a terminal application needs
 * a constructor, a render method, a key handler, and a title. It
 * reads its colors from the amber theme.
 */
public class ClockApplication extends TerminalApplication {
    private final TuiTheme theme = TuiThemes.get("computersaddon:amber");

    private final SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat dateFmt = new SimpleDateFormat("EEE, MMM dd yyyy");

    public ClockApplication(ComputerTerminalScreen screen, String[] args, String content) {
        super(screen);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        g.fill(0, 0, appWidth, appHeight, theme.screenBg());

        Date now = new Date();
        String time = timeFmt.format(now);
        String date = dateFmt.format(now);

        // Big centered time
        int timeCol = (cols() - time.length()) / 2;
        int timeRow = rows() / 2 - 1;
        drawDos(g, time, timeCol * CELL_W, timeRow * CELL_H, theme.titleFg());

        // Date underneath
        int dateCol = (cols() - date.length()) / 2;
        drawDos(g, date, dateCol * CELL_W, (timeRow + 2) * CELL_H, theme.screenFg());

        // Footer
        int footY = (rows() - 1) * CELL_H;
        g.fill(0, footY, appWidth, footY + CELL_H, theme.statusBg());
        drawDos(g, " ESC to exit ", 0, footY, theme.statusFg());
    }

    @Override
    public boolean keyPressed(int key, int scan, int mods) {
        if (key == GLFW.GLFW_KEY_ESCAPE) {
            return false;   // let the screen close the app
        }
        return true;
    }

    @Override
    public String getTitle() { return "Clock"; }
}
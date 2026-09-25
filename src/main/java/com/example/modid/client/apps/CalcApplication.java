package com.example.modid.client.apps;

import com.eliaslucky.mc_dos.client.ComputerTerminalScreen;
import com.eliaslucky.mc_dos.client.apps.TerminalApplication;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

/**
 * Example TUI program: a simple keyboard-driven calculator.
 *
 * <p>Demonstrates the minimum a {@link TerminalApplication} needs:
 * a constructor, a {@code render} method, key handling, and a title.
 *
 * <p>Launch:
 * <ul>
 *	 <li>DOS:	{@code CALC.EXE}</li>
 *	 <li>POSIX: {@code /bin/calc}</li>
 * </ul>
 *
 * <p>Keys: digits, {@code + - * / .}, {@code =} to evaluate, {@code C}
 * to clear, backspace to delete, ESC to quit.
 */
public class CalcApplication extends TerminalApplication {
	private final StringBuilder current = new StringBuilder("0");
	private final StringBuilder history = new StringBuilder();
	private double accumulator = 0;
	private char pendingOp = 0;
	private boolean justEvaluated = false;

	public CalcApplication(ComputerTerminalScreen screen,
						   String[] args, String content) {
		super(screen);
		if (content != null && !content.isEmpty()) {
			history.append(content);
		}
	}

	// Render
	@Override
	public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
		int w = appWidth;
		int h = appHeight;

		// Background
		g.fill(0, 0, w, h, 0xFF000000);

		var font = Minecraft.getInstance().font;
		var style = screen.getDosStyle();

		// Title bar
		g.fill(0, 0, w, CELL_H, 0xFFAAAAAA);
		g.drawString(font,
				Component.literal(" Calculator ").withStyle(style),
				4, 4, 0xFF000000, false);

		// Display area
		int dispTop = CELL_H * 2;
		int dispH = CELL_H * 4;
		g.fill(CELL_W, dispTop, w - CELL_W, dispTop + dispH, 0xFF0000AA);

		// History line (small)
		if (history.length() > 0) {
			g.drawString(font,
					Component.literal(history.toString()).withStyle(style),
					CELL_W * 2, dispTop + CELL_H, 0xFFAAAAAA, false);
		}

		// Current entry (big)
		String display = current.length() == 0 ? "0" : current.toString();
		g.drawString(font,
				Component.literal(display).withStyle(style),
				CELL_W * 2, dispTop + dispH - CELL_H - 6,
				0xFFFFFFFF, false);

		// Button hints
		int buttonsTop = dispTop + dispH + CELL_H;
		String[][] rows = {
				{"7", "8", "9", "/"},
				{"4", "5", "6", "*"},
				{"1", "2", "3", "-"},
				{"0", ".", "=", "+"}
		};
		for (int r = 0; r < rows.length; r++) {
			for (int c = 0; c < rows[r].length; c++) {
				int bx = CELL_W * (2 + c * 6);
				int by = buttonsTop + r * CELL_H;
				g.fill(bx, by, bx + CELL_W * 4, by + CELL_H - 2, 0xFF555555);
				g.drawString(font,
						Component.literal("  " + rows[r][c]).withStyle(style),
						bx, by + 4, 0xFFFFFFFF, false);
			}
		}

		// Footer
		int footY = h - CELL_H;
		g.fill(0, footY, w, h, 0xFFAAAAAA);
		g.drawString(font,
				Component.literal(" Type digits and operators.	ESC to quit. ")
						.withStyle(style),
				0, footY + 4, 0xFF000000, false);
	}

	// Input
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
			return false;	// let the screen close the app
		}
		if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
			if (current.length() > 0) {
				current.setLength(current.length() - 1);
			}
			return true;
		}
		if (keyCode == GLFW.GLFW_KEY_ENTER
				|| keyCode == GLFW.GLFW_KEY_KP_ENTER) {
			evaluate();
			return true;
		}
		return true;
	}

	@Override
	public boolean charTyped(char cp, int mods) {
		if (cp >= '0' && cp <= '9') { appendDigit(cp); return true; }
		if (cp == '.')				{ appendDot(); return true; }
		if (cp == '+' || cp == '-' || cp == '*' || cp == '/') {
			setOperator(cp);
			return true;
		}
		if (cp == '=')				{ evaluate(); return true; }
		if (cp == 'c' || cp == 'C') { clear(); return true; }
		return true;
	}

	// Calculator logic
	private void appendDigit(char d) {
		if (justEvaluated) { current.setLength(0); justEvaluated = false; }
		if (current.length() == 1 && current.charAt(0) == '0') {
			current.setLength(0);
		}
		current.append(d);
	}

	private void appendDot() {
		if (justEvaluated) { current.setLength(0); justEvaluated = false; }
		if (current.indexOf(".") < 0 && current.length() > 0) {
			current.append('.');
		}
	}

	private void setOperator(char op) {
		if (pendingOp != 0 && current.length() > 0) {
			evaluate();
		}
		accumulator = parseCurrent();
		pendingOp = op;
		history.setLength(0);
		history.append(format(accumulator)).append(' ').append(op);
		current.setLength(0);
		justEvaluated = false;
	}

	private void evaluate() {
		if (pendingOp == 0) { justEvaluated = true; return; }

		double rhs = parseCurrent();
		double result = switch (pendingOp) {
			case '+' -> accumulator + rhs;
			case '-' -> accumulator - rhs;
			case '*' -> accumulator * rhs;
			case '/' -> rhs == 0 ? Double.NaN : accumulator / rhs;
			default  -> rhs;
		};

		history.setLength(0);
		history.append(format(accumulator))
			   .append(' ').append(pendingOp).append(' ')
			   .append(format(rhs)).append(" = ");

		current.setLength(0);
		current.append(format(result));
		accumulator = result;
		pendingOp = 0;
		justEvaluated = true;
	}

	private void clear() {
		current.setLength(0);
		history.setLength(0);
		accumulator = 0;
		pendingOp = 0;
		justEvaluated = false;
	}

	private double parseCurrent() {
		if (current.length() == 0) return 0;
		try { return Double.parseDouble(current.toString()); }
		catch (NumberFormatException e) { return 0; }
	}

	private static String format(double d) {
		if (d == Math.floor(d) && !Double.isInfinite(d) && Math.abs(d) < 1e15) {
			return String.valueOf((long) d);
		}
		return String.valueOf(d);
	}

	@Override
	public String getTitle() {
		return "Calculator";
	}
}

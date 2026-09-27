package com.example.computersaddon.client.themes;

import com.eliaslucky.mc_dos.client.tui.TuiPalette;
import com.eliaslucky.mc_dos.client.tui.TuiTheme;

/**
 * Amber CRT look, common on early terminals like the DEC VT52 and
 * many 1980s monitors. Amber on black, minimal chrome.
 */
public final class AmberTheme {
    private AmberTheme() {}

    public static final TuiTheme THEME = new TuiTheme(
            TuiPalette.BLACK,        // screenBg
            TuiPalette.BROWN,        // screenFg  (amber-ish base)
            TuiPalette.BLACK,        // titleBg
            TuiPalette.YELLOW,       // titleFg   (bright amber)
            TuiPalette.BROWN,        // highlightBg
            TuiPalette.BLACK,        // highlightFg
            TuiPalette.YELLOW,       // highlightMn
            TuiPalette.BLACK,        // frameBg
            TuiPalette.BROWN,        // border
            TuiPalette.BROWN,        // statusBg
            TuiPalette.BLACK,        // statusFg
            TuiPalette.YELLOW,       // value
            TuiPalette.BROWN,        // disabled
            TuiPalette.LIGHT_RED,    // warning
            TuiPalette.LIGHT_RED,    // error
            TuiPalette.YELLOW);      // success
}

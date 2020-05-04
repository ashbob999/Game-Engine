package com.ashbob999.gameEngine2d;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ashbob999.gameEngine2d.core.Window;
import com.ashbob999.gameEngine2d.graphics.Hud;

public class SimpleHud extends Hud {

	private final String fontBold = "BOLD";

	private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public void customInit() {
		loadFont("/fonts/OpenSans-Bold.ttf", fontBold);
	}

	@Override
	public void draw(Window window) {
		// Upper ribbon
		nvgBeginPath(getVGContext());
		nvgRect(getVGContext(), 0, window.getHeight() - 100, window.getWidth(), 50);
		nvgFillColor(getVGContext(), getColour(0x23, 0xa1, 0xf1, 200));
		nvgFill(getVGContext());

		// Render hour text
		nvgFontSize(getVGContext(), 40.0f);
		nvgFontFace(getVGContext(), fontBold);
		nvgTextAlign(getVGContext(), NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
		nvgFillColor(getVGContext(), getColour(0xe6, 0xea, 0xed, 255));
		nvgText(getVGContext(), window.getWidth() - 150, window.getHeight() - 95, dateFormat.format(new Date()));
	}

	@Override
	public void customCleanup() {
	}

}

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

/**
 * 
 * <pre>
 * Copyright 2020 ashbob999
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </pre>
 *
 * @author ashbob999
 *
 */
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

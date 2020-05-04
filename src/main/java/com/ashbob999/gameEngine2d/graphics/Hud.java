package com.ashbob999.gameEngine2d.graphics;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgCreateFontMem;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.system.MemoryUtil;

import com.ashbob999.gameEngine2d.core.Window;
import com.ashbob999.gameEngine2d.core.WindowFlag;
import com.ashbob999.gameEngine2d.util.ResourceLoader;

public abstract class Hud {

	// private static final String FONT_NAME = "BOLD";

	private long vg; // nanoVG context

	private NVGColor tempColour;

	// private ByteBuffer fontBuffer;
	private Map<String, ByteBuffer> fonts;

	/**
	 * 
	 * @param window
	 * @throws Exception
	 */
	public final void init(Window window) throws Exception {
		this.vg = window.isFlagSet(WindowFlag.ANTIALIASING) ? nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES)
				: nvgCreate(NVG_STENCIL_STROKES);
		if (this.vg == NULL) {
			throw new Exception("Could not init nanovg");
		}

		tempColour = NVGColor.create();

		fonts = new HashMap<>();

		customInit();
	}

	/**
	 * Initialises code after the master {@link #init(Window)} is called
	 */
	public abstract void customInit();

	/**
	 * Loads a specified font and id into memory.<br>
	 * Returns {@code true} if the font has been successfully loaded
	 * 
	 * @param fontFilePath the path to the font file (.ttf)
	 * @param fontName     the id name of the font to be stored
	 * @return {@code true} if the font has been successfully loaded
	 */
	public final boolean loadFont(final String fontFilePath, final String fontName) {
		ByteBuffer fontBuffer = ResourceLoader.getFileAsBuffer(fontFilePath);
		if (fontBuffer != null) {
			int font = nvgCreateFontMem(vg, fontName, fontBuffer, 0);
			if (font != -1) {
				fonts.put(fontName, fontBuffer);
				return true;
			}
		}
		return false;
	}

	/**
	 * Renders the UI to the window.<br>
	 * - It begins the frame<br>
	 * - It calls {@link #draw(Window)} method<br>
	 * - It ends the frame<br>
	 * - It restores the window state
	 * 
	 * @param window
	 */
	public final void render(Window window) {
		nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);

		draw(window);

		nvgEndFrame(vg);

		// Restore state
		window.restoreState();
	}

	/**
	 * Called each time the {@link #render(Window)} method is called.<br>
	 * All nanoVg drawing will be in this method
	 * 
	 * @param window
	 */
	public abstract void draw(Window window);

	/**
	 * Sets the colour for a given {@link NVGColor} and returns the modified {@link NVGColor} object.
	 * 
	 * @param r      the Red channel, in the Range 0-255
	 * @param g      the Green channel, in the Range 0-255
	 * @param b      the Blue channel, in the Range 0-255
	 * @param a      the Alpha channel, in the Range 0-255
	 * @param colour the {@link NVGColor} to set
	 * @return the modified {@link NVGColor}
	 */
	public final NVGColor setColour(int r, int g, int b, int a, NVGColor colour) {
		colour.r(r / 255.0f);
		colour.g(g / 255.0f);
		colour.b(b / 255.0f);
		colour.a(a / 255.0f);

		return colour;
	}

	/**
	 * Returns a {@link NVGColor} object that has been set to the given rgba values (this modifies a private
	 * {@link NVGColor} object), so it should not be used to store a colour
	 * 
	 * @param r the Red channel, in the Range 0-255
	 * @param g the Green channel, in the Range 0-255
	 * @param b the Blue channel, in the Range 0-255
	 * @param a the Alpha channel, in the Range 0-255
	 * @return the modified {@link NVGColor}
	 */
	public final NVGColor getColour(int r, int g, int b, int a) {
		tempColour.r(r / 255.0f);
		tempColour.g(g / 255.0f);
		tempColour.b(b / 255.0f);
		tempColour.a(a / 255.0f);

		return tempColour;
	}

	/**
	 * Cleans Up and frees memory from the program
	 */
	public final void cleanup() {
		// frees all memory stored by the loaded fonts
		for (ByteBuffer bb : fonts.values()) {
			MemoryUtil.memFree(bb);
		}

		customCleanup();
	}

	/**
	 * Cleans Up and frees any memory set by an implementation of the {@link Hud} class.<br>
	 * Is called after the original {@link #cleanup()}
	 */
	public abstract void customCleanup();

	public final long getVGContext() {
		return vg;
	}

	/**
	 * Returns a {@link Set} of font names that have been loaded
	 * 
	 * @return a set containing the font names that have been loaded
	 */
	public final Set<String> getFontNames() {
		return fonts.keySet();
	}

	/**
	 * Returns a boolean stating whether a specified font has been loaded given its {@code fontName}
	 * 
	 * @param fontName the name given to the stored font
	 * @return {@code true} if the specified font has been loaded
	 */
	public final boolean isFontLoaded(final String fontName) {
		return fonts.containsKey(fontName);
	}
}

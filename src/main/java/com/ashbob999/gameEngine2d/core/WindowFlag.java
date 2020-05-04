package com.ashbob999.gameEngine2d.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Window Flags:
 * <ul>
 * <li>{@link #RESIZEABLE} - is the window resizable</li>
 * <li>{@link #VSYNC} - is vSync enabled</li>
 * <li>{@link #SHOW_POLYGONS} - should the polygons be shown</li>
 * <li>{@link #SHOW_FPS} - shows the FPS in the title</li>
 * <li>{@link #MAX_FPS} -lets FPS go over the target FPS (Doesn't work is vSync is enabled)</li>
 * <li>{@link #ANTIALIASING} -</li>
 * </ul>
 * 
 * @author ashbob999
 * 
 */
public enum WindowFlag {
	/**
	 * is the window resizable
	 */
	RESIZEABLE(false),
	/**
	 * is vSync enabled
	 */
	VSYNC(false),
	/**
	 * should the polygons be shown
	 */
	SHOW_POLYGONS(false),
	/**
	 * shows the FPS in the title
	 */
	SHOW_FPS(false),
	/**
	 * lets FPS go over the target FPS (Doesn't work is vSync is enabled)
	 */
	MAX_FPS(false),
	/**
	 * is antialiasing enabled
	 */
	ANTIALIASING(false);

	private final boolean defaultValue;

	private WindowFlag(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets the default value for a given flag
	 * 
	 * @return the default value of the flag
	 */
	public boolean getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * Returns a {@link Map} containing the flags and their default values respectively
	 * 
	 * @return a {@link Map} of the flag and it's default value
	 */
	public static Map<WindowFlag, Boolean> getDefaultMap() {
		Map<WindowFlag, Boolean> flagMap = new HashMap<>();

		for (WindowFlag wf : WindowFlag.values()) {
			flagMap.put(wf, wf.getDefaultValue());
		}

		return flagMap;
	}
}

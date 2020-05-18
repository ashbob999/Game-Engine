package com.ashbob999.gameEngine.core;

import java.util.HashMap;
import java.util.Map;

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

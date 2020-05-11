package com.ashbob999.gameEngine2d.core;

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
public class Timer {
	private double lastLoopTime;

	/**
	 * Initialises {@code lastLoopTime} to the current time
	 */
	public void init() {
		lastLoopTime = getTime();
	}

	/**
	 * Gets the current time in seconds
	 * 
	 * @return the current time in seconds
	 */
	public double getTime() {
		return System.nanoTime() / 1_000_000_000.0;
	}

	/**
	 * 
	 * @return the elapsed time between the current time and {@code lastLoopTime}
	 */
	public float getElapsedTime() {
		double time = getTime();
		float elapsedTime = (float) (time - lastLoopTime);
		lastLoopTime = time;
		return elapsedTime;
	}

	/**
	 * Gets the last loop time
	 * 
	 * @return the {@code lastLoopTime}
	 */
	public double getLastLoopTime() {
		return lastLoopTime;
	}
}

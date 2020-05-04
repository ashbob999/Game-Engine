package com.ashbob999.gameEngine2d.core;

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

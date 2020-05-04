package com.ashbob999.gameEngine2d.core;

public interface IGameLogic {

	/**
	 * 
	 * @throws Exception
	 */
	void init(Window window) throws Exception;

	/**
	 * 
	 * @param window
	 * @param mouseInput
	 */
	void input(Window window, MouseInput mouseInput);

	/**
	 * The window updating method
	 * 
	 * @param interval
	 * @param mouseInput
	 */
	void update(float interval, MouseInput mouseInput);

	/**
	 * The window rendering method
	 * 
	 * @param window The window object
	 */
	void render(Window window);

	void cleanup();
}

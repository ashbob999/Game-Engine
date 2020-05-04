package com.ashbob999.gameEngine2d.core;

public class GameEngine implements Runnable {
	public static final int TARGET_FPS = 60;

	public static final int TARGET_UPS = 30;

	private final Window window;

	private final Timer timer;

	private final IGameLogic gameLogic;

	private final MouseInput mouseInput;

	private double lastFps;

	private int fps;

	private String windowTitle;

	/**
	 * 
	 * @param window      Title The window title
	 * @param width       The window width
	 * @param height      The window height
	 * @param gameLogic   The game logic class
	 * @param windowFlags The {@link WindowFlag}s to be set
	 * @throws Exception
	 */
	public GameEngine(String windowTitle, int width, int height, IGameLogic gameLogic, WindowFlag... windowFlags)
			throws Exception {
		window = new Window(windowTitle, width, height, windowFlags);
		mouseInput = new MouseInput();
		this.gameLogic = gameLogic;
		timer = new Timer();
	}

	@Override
	public void run() {
		try {
			init();
			gameLoop();
		} catch (Exception excp) {
			excp.printStackTrace();
		} finally {
			cleanup();
		}
	}

	/**
	 * Initialises the window, timer and gameLogic
	 * 
	 * @throws Exception
	 */
	protected void init() throws Exception {
		window.init();
		mouseInput.init(window);
		timer.init();
		gameLogic.init(window);
	}

	/**
	 * Runs the game
	 */
	protected void gameLoop() {
		float elapsedTime;
		float accumulator = 0f;
		float interval = 1f / TARGET_UPS;

		boolean running = true;

		while (running && !window.windowShouldClose()) {
			elapsedTime = timer.getElapsedTime();
			accumulator += elapsedTime;

			input(); // handle input

			while (accumulator >= interval) {
				update(interval);
				accumulator -= interval;
			}

			render(); // render content to the screen

			if (!window.isvSync()) { // when vSync is disabled
				sync();
			}
		}
	}

	protected void cleanup() {
		gameLogic.cleanup();
	}

	/**
	 * 
	 */
	private void sync() {
		float loopSlot;
		if (window.isFlagSet(WindowFlag.MAX_FPS)) {
			loopSlot = 1f / Integer.MAX_VALUE;
		} else {
			loopSlot = 1f / TARGET_FPS;
		}
		double endTime = timer.getLastLoopTime() + loopSlot;
		while (timer.getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {
			}
		}
	}

	/**
	 * Handles input?
	 */
	protected void input() {
		mouseInput.input(window);
		gameLogic.input(window, mouseInput);
	}

	/**
	 * Updates the gameLogic
	 * 
	 * @param interval The time between each update
	 */
	protected void update(float interval) {
		gameLogic.update(interval, mouseInput);
	}

	/**
	 * Renders content to the screen
	 */
	protected void render() {
		if (window.isFlagSet(WindowFlag.SHOW_FPS) && timer.getLastLoopTime() - lastFps > 1) {
			lastFps = timer.getLastLoopTime();
			window.setWindowTitle(windowTitle + " - " + fps + " FPS");
			fps = 0;
		}
		fps++;

		gameLogic.render(window);
		window.update();
	}
}

package com.ashbob999.gameEngine2d.core;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.Map;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
	/**
	 * The title of the window
	 */
	private final String title;

	/**
	 * The window Width
	 */
	private int width;

	/**
	 * The window Height
	 */
	private int height;

	private long windowHandle;

	private boolean resized;

	private final Map<WindowFlag, Boolean> windowFlagMap = WindowFlag.getDefaultMap();

	/**
	 * 
	 * @param title       The title of the window
	 * @param width       The width of the window
	 * @param height      The height of the window
	 * @param windowFlags The {@link WindowFlag}s to be set
	 */
	public Window(String title, int width, int height, WindowFlag[] windowFlags) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.resized = false;

		for (WindowFlag wf : windowFlags) {
			windowFlagMap.put(wf, true);
		}
	}

	/**
	 * Initialises the window
	 */
	public void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialise GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GL_TRUE); // the window will stay hidden after creation
		// sets whether the window will be resizable
		glfwWindowHint(GLFW_RESIZABLE, windowFlagMap.get(WindowFlag.RESIZEABLE) ? 1 : 0);
		// glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		// glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		// glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		// glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		// Create the window
		windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
		if (windowHandle == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		// Setup resize callback
		glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
			this.width = width;
			this.height = height;
			this.setResized(true);
		});

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			}
		});

		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		// Centre our window
		glfwSetWindowPos(windowHandle, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

		// Make the OpenGL context current
		glfwMakeContextCurrent(windowHandle);

		if (isvSync()) {
			// Enable v-sync
			glfwSwapInterval(1);
		}

		// Make the window visible
		glfwShowWindow(windowHandle);

		GL.createCapabilities();

		// Set the clear colour
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_STENCIL_TEST);
		if (isFlagSet(WindowFlag.SHOW_POLYGONS)) {
			glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); // shows the polygons (mesh)
		}

		// Antialiasing
		if (isFlagSet(WindowFlag.ANTIALIASING)) {
			glfwWindowHint(GLFW_SAMPLES, 4);
		}
	}

	/**
	 * 
	 * @return
	 */
	public long getWindowHandle() {
		return windowHandle;
	}

	/**
	 * Sets the clear colour for the window <br>
	 * All params are floats between 0.0f and 1.0f
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param alpha
	 */
	public void setClearColor(float r, float g, float b, float alpha) {
		glClearColor(r, g, b, alpha);
	}

	/**
	 * Determines whether a given key is pressed
	 * 
	 * @param  keyCode The keyCode of the key
	 * 
	 * @return         whether the key is pressed
	 */
	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
	}

	/**
	 * 
	 * @return
	 */
	public boolean windowShouldClose() {
		return glfwWindowShouldClose(windowHandle);
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isResized() {
		return resized;
	}

	public void setResized(boolean resized) {
		this.resized = resized;
	}

	public boolean isvSync() {
		return windowFlagMap.get(WindowFlag.VSYNC);
	}

	public void setvSync(boolean vSync) {
		windowFlagMap.put(WindowFlag.VSYNC, vSync);
	}

	public String getWindowTitle() {
		return title;
	}

	public void setWindowTitle(String title) {
		glfwSetWindowTitle(windowHandle, title);
	}

	public void enableWindowFlag(WindowFlag flag) {
		windowFlagMap.put(flag, true);
	}

	public void disableWindowFlag(WindowFlag flag) {
		windowFlagMap.put(flag, false);
	}

	public boolean isFlagSet(WindowFlag flag) {
		return windowFlagMap.get(flag);
	}

	/**
	 * Updates the screen
	 */
	public void update() {
		glfwSwapBuffers(windowHandle);
		glfwPollEvents();
	}

	public void restoreState() {
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_STENCIL_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
}

package com.ashbob999.gameEngine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Vector3f;

import com.ashbob999.gameEngine.core.IGameLogic;
import com.ashbob999.gameEngine.core.MouseInput;
import com.ashbob999.gameEngine.core.Window;
import com.ashbob999.gameEngine.graphics.GameItem;
import com.ashbob999.gameEngine.graphics.Material;
import com.ashbob999.gameEngine.graphics.Mesh;
import com.ashbob999.gameEngine.graphics.OBJLoader;
import com.ashbob999.gameEngine.graphics.Renderer;
import com.ashbob999.gameEngine.graphics.Texture;
import com.ashbob999.gameEngine.graphics.lighting.PointLight;
import com.ashbob999.gameEngine.graphics.lighting.SpotLight;
import com.ashbob999.gameEngine.graphics2d.Camera2D;

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
public class BasicGame2D implements IGameLogic {

	private static final float MOUSE_SENSITIVITY = 0.2f;

	private final Vector3f cameraInc;

	private static final float CAMERA_POS_STEP = 0.05f;

	private final Renderer renderer;

	private final Camera2D camera;

	private GameItem[] gameItems;

	private Vector3f ambientLight;

	private SpotLight[] spotLightList;

	public BasicGame2D() {
		renderer = new Renderer(false);
		camera = new Camera2D();
		cameraInc = new Vector3f();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);

		float reflectance = 1f;

		Mesh mesh = OBJLoader.loadMesh("/models/cube.obj");
		Texture texture = new Texture("textures/grassblock.png");
		Material material = new Material(texture, reflectance);

		mesh.setMaterial(material);
		GameItem gameItem = new GameItem(mesh);
		gameItem.setScale(0.5f);
		gameItem.setPosition(0, 0, -5);
		// gameItem.setRotation(-45, 45, 0);
		gameItems = new GameItem[] { gameItem };

		ambientLight = new Vector3f(0.8f, 0.8f, 0.8f);

		Vector3f lightPosition = new Vector3f(0, 0, 1);

		// Spot Light
		lightPosition = new Vector3f(0, 0.0f, 20);
		float lightIntensity = 5.0f;
		PointLight sl_pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
		PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 0.02f);
		sl_pointLight.setAttenuation(att);
		Vector3f coneDir = new Vector3f(0, 0, -1);
		float cutoff = (float) Math.cos(Math.toRadians(240));
		SpotLight spotLight = new SpotLight(sl_pointLight, coneDir, cutoff);
		spotLightList = new SpotLight[] { spotLight, new SpotLight(spotLight) };
	}

	@Override
	public void input(Window window, MouseInput mouseInput) {
		cameraInc.set(0, 0, 0);
		if (window.isKeyPressed(GLFW_KEY_W)) {
			cameraInc.y = 1;
		} else if (window.isKeyPressed(GLFW_KEY_S)) {
			cameraInc.y = -1;
		}
		if (window.isKeyPressed(GLFW_KEY_A)) {
			cameraInc.x = -1;
		} else if (window.isKeyPressed(GLFW_KEY_D)) {
			cameraInc.x = 1;
		}
		if (window.isKeyPressed(GLFW_KEY_Z)) {
			cameraInc.z = -2;
		} else if (window.isKeyPressed(GLFW_KEY_X)) {
			cameraInc.z = 2;
		}
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		// Update camera position
		camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP);
		camera.moveZoom(cameraInc.z * CAMERA_POS_STEP);

		// Update camera based on mouse
		if (mouseInput.isRightButtonPressed()) {
			camera.moveRotation(5);
		}
	}

	@Override
	public void render(Window window) {
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		renderer.render(window, camera, gameItems, ambientLight, new PointLight[] {}, spotLightList, null);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		for (GameItem gameItem : gameItems) {
			gameItem.getMesh().cleanUp();
		}
	}
}

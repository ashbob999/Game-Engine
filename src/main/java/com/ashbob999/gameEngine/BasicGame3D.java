package com.ashbob999.gameEngine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_N;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.ashbob999.gameEngine.core.IGameLogic;
import com.ashbob999.gameEngine.core.MouseInput;
import com.ashbob999.gameEngine.core.Window;
import com.ashbob999.gameEngine.graphics.GameItem;
import com.ashbob999.gameEngine.graphics.Hud;
import com.ashbob999.gameEngine.graphics.Material;
import com.ashbob999.gameEngine.graphics.Mesh;
import com.ashbob999.gameEngine.graphics.OBJLoader;
import com.ashbob999.gameEngine.graphics.Renderer;
import com.ashbob999.gameEngine.graphics.Texture;
import com.ashbob999.gameEngine.graphics.lighting.DirectionalLight;
import com.ashbob999.gameEngine.graphics.lighting.PointLight;
import com.ashbob999.gameEngine.graphics.lighting.SpotLight;
import com.ashbob999.gameEngine.graphics3d.Camera3D;

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
public class BasicGame3D implements IGameLogic {

	private static final float MOUSE_SENSITIVITY = 0.2f;

	private final Vector3f cameraInc;

	private static final float CAMERA_POS_STEP = 0.05f;

	private final Renderer renderer;

	private final Camera3D camera;

	private Hud hud;

	private GameItem[] gameItems;

	private Vector3f ambientLight;

	private PointLight[] pointLightList;

	private SpotLight[] spotLightList;

	private DirectionalLight directionalLight;

	private float lightAngle;

	private float spotAngle = 0;

	private float spotInc = 1;

	public BasicGame3D() {
		renderer = new Renderer(true);
		hud = new SimpleHud();
		camera = new Camera3D();
		cameraInc = new Vector3f();
	}

	@Override
	public void init(Window window) throws Exception {
		hud.init(window);
		renderer.init(window);

		float reflectance = 1f;

		Mesh mesh = OBJLoader.loadMesh("/models/cube.obj");
		Texture texture = new Texture("/textures/grassblock.png");
		Material material = new Material(texture, reflectance);

		mesh.setMaterial(material);
		GameItem gameItem = new GameItem(mesh);
		gameItem.setScale(0.5f);
		gameItem.setPosition(0, 0, -2);
		// gameItem.setRotation(-45, 45, 0);
		gameItems = new GameItem[] { gameItem };

		ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);

		// Point Light
		Vector3f lightPosition = new Vector3f(0, 0, 1);
		float lightIntensity = 5.0f;
		PointLight pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
		PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
		pointLight.setAttenuation(att);
		pointLightList = new PointLight[] {};// pointLight };

		// Spot Light
		lightPosition = new Vector3f(0, 0.0f, 10);
		PointLight sl_pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
		att = new PointLight.Attenuation(0.0f, 0.0f, 0.02f);
		sl_pointLight.setAttenuation(att);
		Vector3f coneDir = new Vector3f(0, 0, -1);
		float cutoff = (float) Math.cos(Math.toRadians(240));
		SpotLight spotLight = new SpotLight(sl_pointLight, coneDir, cutoff);
		spotLightList = new SpotLight[] { spotLight, new SpotLight(spotLight) };

		lightPosition = new Vector3f(-1, 0, 0);
		directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, 1);
	}

	@Override
	public void input(Window window, MouseInput mouseInput) {
		cameraInc.set(0, 0, 0);
		if (window.isKeyPressed(GLFW_KEY_W)) {
			cameraInc.z = -1;
		} else if (window.isKeyPressed(GLFW_KEY_S)) {
			cameraInc.z = 1;
		}
		if (window.isKeyPressed(GLFW_KEY_A)) {
			cameraInc.x = -1;
		} else if (window.isKeyPressed(GLFW_KEY_D)) {
			cameraInc.x = 1;
		}
		if (window.isKeyPressed(GLFW_KEY_Z)) {
			cameraInc.y = -1;
		} else if (window.isKeyPressed(GLFW_KEY_X)) {
			cameraInc.y = 1;
		}

		float lightPos = spotLightList[0].getPointLight().getPosition().z;
		if (window.isKeyPressed(GLFW_KEY_N)) {
			this.spotLightList[0].getPointLight().getPosition().z = lightPos + 0.1f;
		} else if (window.isKeyPressed(GLFW_KEY_M)) {
			this.spotLightList[0].getPointLight().getPosition().z = lightPos - 0.1f;
		}
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		// Update camera position
		camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP,
				cameraInc.z * CAMERA_POS_STEP);

		// Update camera based on mouse
		if (mouseInput.isRightButtonPressed()) {
			Vector2f rotVec = mouseInput.getDisplVec();
			camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
		}

		// Update spot light direction
		spotAngle += spotInc * 0.05f;
		if (spotAngle > 2) {
			spotInc = -1;
		} else if (spotAngle < -2) {
			spotInc = 1;
		}
		double spotAngleRad = Math.toRadians(spotAngle);
		Vector3f coneDir = spotLightList[0].getConeDirection();
		coneDir.y = (float) Math.sin(spotAngleRad);

		// Update directional light direction, intensity and colour
		lightAngle += 1.1f;
		if (lightAngle > 90) {
			directionalLight.setIntensity(0);
			if (lightAngle >= 360) {
				lightAngle = -90;
			}
		} else if (lightAngle <= -80 || lightAngle >= 80) {
			float factor = 1 - (float) (Math.abs(lightAngle) - 80) / 10.0f;
			directionalLight.setIntensity(factor);
			directionalLight.getColor().y = Math.max(factor, 0.9f);
			directionalLight.getColor().z = Math.max(factor, 0.5f);
		} else {
			directionalLight.setIntensity(1);
			directionalLight.getColor().x = 1;
			directionalLight.getColor().y = 1;
			directionalLight.getColor().z = 1;
		}
		double angRad = Math.toRadians(lightAngle);
		directionalLight.getDirection().x = (float) Math.sin(angRad);
		directionalLight.getDirection().y = (float) Math.cos(angRad);

	}

	@Override
	public void render(Window window) {
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		renderer.render(window, camera, gameItems, ambientLight, pointLightList, spotLightList, directionalLight);
		hud.render(window);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		for (GameItem gameItem : gameItems) {
			gameItem.getMesh().cleanUp();
		}

		if (hud != null) {
			hud.cleanup();
		}
	}

}

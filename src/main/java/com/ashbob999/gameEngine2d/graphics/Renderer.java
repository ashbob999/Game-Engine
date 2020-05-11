package com.ashbob999.gameEngine2d.graphics;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.ashbob999.gameEngine2d.core.Window;
import com.ashbob999.gameEngine2d.graphics.lighting.DirectionalLight;
import com.ashbob999.gameEngine2d.graphics.lighting.PointLight;
import com.ashbob999.gameEngine2d.graphics.lighting.SpotLight;
import com.ashbob999.gameEngine2d.graphics2d.Transformation2D;
import com.ashbob999.gameEngine2d.graphics3d.Transformation3D;
import com.ashbob999.gameEngine2d.util.ResourceLoader;

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
public class Renderer {

	/**
	 * Field of View in Radians
	 */
	private static final float FOV = (float) Math.toRadians(60.0f);

	private static final float Z_NEAR = 0.01f;

	private static final float Z_FAR = 1000.f;

	private static final int MAX_POINT_LIGHTS = 5;

	private static final int MAX_SPOT_LIGHTS = 5;

	private ShaderProgram shaderProgram;

	private Transformation transformation;

	private float specularPower;

	/**
	 * 
	 */
	public Renderer(boolean is3D) {
		if (is3D) {
			transformation = new Transformation3D();
		} else {
			transformation = new Transformation2D();
		}
		specularPower = 10f;
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void init(Window window) throws Exception {
		// Create shader
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(ResourceLoader.loadResource("/shaders/vertex.vs"));
		shaderProgram.createFragmentShader(ResourceLoader.loadResource("/shaders/fragment.fs"));
		shaderProgram.link();

		// Create uniforms for modelView and projection matrices and texture
		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("modelViewMatrix");
		shaderProgram.createUniform("texture_sampler");

		// Create uniform for material
		shaderProgram.createMaterialUniform("material");
		// Create lighting related uniforms
		shaderProgram.createUniform("specularPower");
		shaderProgram.createUniform("ambientLight");
		shaderProgram.createPointLightListUniform("pointLights", MAX_POINT_LIGHTS);
		shaderProgram.createSpotLightListUniform("spotLights", MAX_SPOT_LIGHTS);
		shaderProgram.createDirectionalLightUniform("directionalLight");
	}

	/**
	 * Clears the screen <br>
	 * The colour depends on the clear colour
	 */
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	}

	/**
	 * Renders the game items
	 * 
	 * @param window The window class
	 */
	public void render(Window window, Camera camera, GameItem[] gameItems, Vector3f ambientLight,
			PointLight[] pointLightList, SpotLight[] spotLightList, DirectionalLight directionalLight) {
		clear();

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		shaderProgram.bind();

		// Update projection Matrix
		Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(),
				Z_NEAR, Z_FAR, camera);
		shaderProgram.setUniform("projectionMatrix", projectionMatrix);

		// Update view Matrix
		Matrix4f viewMatrix = transformation.getViewMatrix(camera);

		// Update Light Uniforms
		renderLights(viewMatrix, ambientLight, pointLightList, spotLightList, directionalLight);

		shaderProgram.setUniform("texture_sampler", 0);

		// Render each gameItem
		for (GameItem gameItem : gameItems) {
			Mesh mesh = gameItem.getMesh();
			// Set model view matrix for this item
			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix);
			shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
			// Render the mesh for this game item
			shaderProgram.setUniform("material", mesh.getMaterial());
			mesh.render();
		}
		shaderProgram.unbind();
	}

	private void renderLights(Matrix4f viewMatrix, Vector3f ambientLight, PointLight[] pointLightList,
			SpotLight[] spotLightList, DirectionalLight directionalLight) {

		shaderProgram.setUniform("ambientLight", ambientLight);
		shaderProgram.setUniform("specularPower", specularPower);

		// Process Point Lights
		int numLights = pointLightList != null ? pointLightList.length : 0;
		for (int i = 0; i < numLights; i++) {
			// Get a copy of the point light object and transform its position to view coordinates
			PointLight currPointLight = new PointLight(pointLightList[i]);
			Vector3f lightPos = currPointLight.getPosition();
			Vector4f aux = new Vector4f(lightPos, 1);
			aux.mul(viewMatrix);
			lightPos.x = aux.x;
			lightPos.y = aux.y;
			lightPos.z = aux.z;
			shaderProgram.setUniform("pointLights", currPointLight, i);
		}

		// Process Spot Lights
		numLights = spotLightList != null ? spotLightList.length : 0;
		for (int i = 0; i < numLights; i++) {
			// Get a copy of the spot light object and transform its position and cone direction to view coordinates
			SpotLight currSpotLight = new SpotLight(spotLightList[i]);
			Vector4f dir = new Vector4f(currSpotLight.getConeDirection(), 0);
			dir.mul(viewMatrix);
			currSpotLight.setConeDirection(new Vector3f(dir.x, dir.y, dir.z));
			Vector3f lightPos = currSpotLight.getPointLight().getPosition();

			Vector4f aux = new Vector4f(lightPos, 1);
			aux.mul(viewMatrix);
			lightPos.x = aux.x;
			lightPos.y = aux.y;
			lightPos.z = aux.z;

			shaderProgram.setUniform("spotLights", currSpotLight, i);
		}

		// Get a copy of the directional light object and transform its position to view coordinates
		if (directionalLight != null) {
			DirectionalLight currDirLight = new DirectionalLight(directionalLight);
			Vector4f dir = new Vector4f(currDirLight.getDirection(), 0);
			dir.mul(viewMatrix);
			currDirLight.setDirection(new Vector3f(dir.x, dir.y, dir.z));
			shaderProgram.setUniform("directionalLight", currDirLight);
		}
	}

	/**
	 * Frees the resource memory
	 */
	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}
}

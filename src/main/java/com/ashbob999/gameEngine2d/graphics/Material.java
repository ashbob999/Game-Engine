package com.ashbob999.gameEngine2d.graphics;

import org.joml.Vector4f;

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
public class Material {
	private static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

	private Vector4f ambientColour;

	private Vector4f diffuseColour;

	private Vector4f specularColour;

	private float reflectance;

	private Texture texture;

	public Material() {
		this.ambientColour = DEFAULT_COLOUR;
		this.diffuseColour = DEFAULT_COLOUR;
		this.specularColour = DEFAULT_COLOUR;
		this.texture = null;
		this.reflectance = 0;
	}

	public Material(Vector4f colour, float reflectance) {
		this(colour, colour, colour, null, reflectance);
	}

	public Material(Texture texture) {
		this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, 0);
	}

	public Material(Texture texture, float reflectance) {
		this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, reflectance);
	}

	public Material(Vector4f ambientColour, Vector4f diffuseColour, Vector4f specularColour, Texture texture,
			float reflectance) {
		this.ambientColour = ambientColour;
		this.diffuseColour = diffuseColour;
		this.specularColour = specularColour;
		this.texture = texture;
		this.reflectance = reflectance;
	}

	public Vector4f getAmbientColour() {
		return ambientColour;
	}

	public void setAmbientColour(Vector4f ambientColour) {
		this.ambientColour = ambientColour;
	}

	public Vector4f getDiffuseColour() {
		return diffuseColour;
	}

	public void setDiffuseColour(Vector4f diffuseColour) {
		this.diffuseColour = diffuseColour;
	}

	public Vector4f getSpecularColour() {
		return specularColour;
	}

	public void setSpecularColour(Vector4f specularColour) {
		this.specularColour = specularColour;
	}

	public float getReflectance() {
		return reflectance;
	}

	public void setReflectance(float reflectance) {
		this.reflectance = reflectance;
	}

	public boolean isTextured() {
		return this.texture != null;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}

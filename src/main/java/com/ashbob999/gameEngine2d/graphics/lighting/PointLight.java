package com.ashbob999.gameEngine2d.graphics.lighting;

import org.joml.Vector3f;

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
public class PointLight {
	private Vector3f color;

	private Vector3f position;

	protected float intensity;

	private Attenuation attenuation;

	public PointLight(Vector3f color, Vector3f position, float intensity) {
		attenuation = new Attenuation(1, 0, 0);
		this.color = color;
		this.position = position;
		this.intensity = intensity;
	}

	public PointLight(Vector3f color, Vector3f position, float intensity, Attenuation attenuation) {
		this(color, position, intensity);
		this.attenuation = attenuation;
	}

	public PointLight(PointLight pointLight) {
		this(new Vector3f(pointLight.getColor()), new Vector3f(pointLight.getPosition()), pointLight.getIntensity(),
				pointLight.getAttenuation());
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public Attenuation getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(Attenuation attenuation) {
		this.attenuation = attenuation;
	}

	public static class Attenuation {

		private float constant;

		private float linear;

		private float exponent;

		public Attenuation(float constant, float linear, float exponent) {
			this.constant = constant;
			this.linear = linear;
			this.exponent = exponent;
		}

		public float getConstant() {
			return constant;
		}

		public void setConstant(float constant) {
			this.constant = constant;
		}

		public float getLinear() {
			return linear;
		}

		public void setLinear(float linear) {
			this.linear = linear;
		}

		public float getExponent() {
			return exponent;
		}

		public void setExponent(float exponent) {
			this.exponent = exponent;
		}
	}
}

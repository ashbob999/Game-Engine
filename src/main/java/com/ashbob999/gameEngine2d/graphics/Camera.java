package com.ashbob999.gameEngine2d.graphics;

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
public class Camera {
	public final Vector3f position;

	public final Vector3f rotation;

	public Camera() {
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
	}

	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	public Vector3f getPosition() {
		return position;
	}

	// public void setPosition(float x, float y, float z) {
	// position.x = x;
	// position.y = y;
	// position.z = z;
	// }

	// public void movePosition(float offsetX, float offsetY, float offsetZ) {
	// if (offsetZ != 0) {
	// position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
	// position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offsetZ;
	// }
	// if (offsetX != 0) {
	// position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
	// position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
	// }
	// position.y += offsetY;
	// }

	public Vector3f getRotation() {
		return rotation;
	}

	// public void setRotation(float x, float y, float z) {
	// rotation.x = x;
	// rotation.y = y;
	// rotation.z = z;
	// }

	// public void moveRotation(float offsetX, float offsetY, float offsetZ) {
	// rotation.x += offsetX;
	// rotation.y += offsetY;
	// rotation.z += offsetZ;
	// }
}

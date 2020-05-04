package com.ashbob999.gameEngine2d.graphics2d;

import org.joml.Matrix4f;

import com.ashbob999.gameEngine2d.graphics.Camera;
import com.ashbob999.gameEngine2d.graphics.Transformation;

public class Transformation2D extends Transformation {

	@Override
	public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar,
			Camera camera) {
		float aspectRatio = width / height;
		projectionMatrix.identity();

		float top = (float) Math.tan(fov / 2) * (zFar + zNear) / 1000;
		float right = top * aspectRatio;

		projectionMatrix.ortho(-right, right, -top, top, zNear, zFar);

		// zoom range: 40f (small), 4f (large)
		if (camera.getPosition().z > 40f) {
			camera.position.z = 40f;
		} else if (camera.getPosition().z < -4f) {
			camera.position.z = -4f;
		}

		float cameraZ = camera.getPosition().z;

		if (cameraZ > 0) { // scale = 1 / z
			projectionMatrix.scale(1f / (cameraZ + 1));
		} else if (cameraZ < 0) { // scale = 1 * z
			projectionMatrix.scale(Math.abs(cameraZ - 1));
		}

		return projectionMatrix;
	}
}

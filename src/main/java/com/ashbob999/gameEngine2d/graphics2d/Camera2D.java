package com.ashbob999.gameEngine2d.graphics2d;

import com.ashbob999.gameEngine2d.graphics.Camera;
import com.ashbob999.gameEngine2d.util.MathsUtil;

public class Camera2D extends Camera {

	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	public void movePosition(float offsetX, float offsetY) {
		MathsUtil.moveVector(position, rotation, offsetX, offsetY, 0);
	}

	public void setZoom(float z) {
		position.z = z;
	}

	public void moveZoom(float offsetZ) {
		MathsUtil.moveVector(position, rotation, 0, 0, offsetZ);
	}

	public void setRotation(float z) {
		rotation.z = z;
	}

	public void moveRotation(float offsetZ) {
		rotation.z += offsetZ;
	}

}

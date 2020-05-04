package com.ashbob999.gameEngine2d.graphics3d;

import com.ashbob999.gameEngine2d.graphics.Camera;
import com.ashbob999.gameEngine2d.util.MathsUtil;

public class Camera3D extends Camera {

	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}

	public void movePosition(float offsetX, float offsetY, float offsetZ) {
		MathsUtil.moveVector(position, rotation, offsetX, offsetY, offsetZ);
	}

	public void setRotation(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}

	public void moveRotation(float offsetX, float offsetY, float offsetZ) {
		rotation.x += offsetX;
		rotation.y += offsetY;
		rotation.z += offsetZ;
	}

}

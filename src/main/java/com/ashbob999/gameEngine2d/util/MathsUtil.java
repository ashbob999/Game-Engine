package com.ashbob999.gameEngine2d.util;

import org.joml.Vector3f;

public class MathsUtil {

	/**
	 * Moves a position vector by the specified axis offsets and rotation vector.<br>
	 * This modifies the original position vector
	 * 
	 * @param position The position vector
	 * @param rotation The rotation vector
	 * @param offsetX  The X-axis offset
	 * @param offsetY  The Y-axis offset
	 * @param offsetZ  The Z-axis offset
	 */
	public static void moveVector(Vector3f position, Vector3f rotation, float offsetX, float offsetY, float offsetZ) {
		if (offsetZ != 0) {
			position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
			position.z += (float) Math.cos(Math.toRadians(rotation.y)) * offsetZ;
		}
		if (offsetX != 0) {
			position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
			position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
		}
		position.y += offsetY;
	}
}

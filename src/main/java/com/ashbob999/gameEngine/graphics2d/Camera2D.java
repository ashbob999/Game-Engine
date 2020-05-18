package com.ashbob999.gameEngine.graphics2d;

import com.ashbob999.gameEngine.graphics.Camera;
import com.ashbob999.gameEngine.util.MathsUtil;

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

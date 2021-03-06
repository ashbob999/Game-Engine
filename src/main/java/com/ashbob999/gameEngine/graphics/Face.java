package com.ashbob999.gameEngine.graphics;

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
public class Face {

	protected static class IdxGroup {

		public static final int NO_VALUE = -1;

		public int idxPos;

		public int idxTextCoord;

		public int idxVecNormal;

		public IdxGroup() {
			idxPos = NO_VALUE;
			idxTextCoord = NO_VALUE;
			idxVecNormal = NO_VALUE;
		}
	}

	/**
	 * List of idxGroup groups for a face triangle (3 vertices per face).
	 */
	private IdxGroup[] idxGroups = new IdxGroup[3];

	public Face(String v1, String v2, String v3) {
		idxGroups = new IdxGroup[3];
		// Parse the lines
		idxGroups[0] = parseLine(v1);
		idxGroups[1] = parseLine(v2);
		idxGroups[2] = parseLine(v3);
	}

	private IdxGroup parseLine(String line) {
		IdxGroup idxGroup = new IdxGroup();

		String[] lineTokens = line.split("/");
		int length = lineTokens.length;
		idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
		if (length > 1) {
			// It can be empty if the obj does not define text coords
			String textCoord = lineTokens[1];
			idxGroup.idxTextCoord = textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IdxGroup.NO_VALUE;
			if (length > 2) {
				idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
			}
		}

		return idxGroup;
	}

	public IdxGroup[] getFaceVertexIndices() {
		return idxGroups;
	}
}

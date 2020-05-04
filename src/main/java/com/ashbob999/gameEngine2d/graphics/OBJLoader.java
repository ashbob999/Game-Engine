package com.ashbob999.gameEngine2d.graphics;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.ashbob999.gameEngine2d.graphics.Face.IdxGroup;
import com.ashbob999.gameEngine2d.util.ResourceLoader;

public class OBJLoader {
	public static Mesh loadMesh(String fileName) throws Exception {
		List<String> lines = ResourceLoader.readAllLines(fileName);

		List<Vector3f> vertices = new ArrayList<>();
		List<Vector2f> textures = new ArrayList<>();
		List<Vector3f> normals = new ArrayList<>();
		List<Face> faces = new ArrayList<>();

		for (String line : lines) {
			String[] tokens = line.split("\\s+");
			switch (tokens[0]) {
			case "v":
				// Geometric vertex
				Vector3f vec3f = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
						Float.parseFloat(tokens[3]));
				vertices.add(vec3f);
				break;
			case "vt":
				// Texture coordinate
				Vector2f vec2f = new Vector2f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
				textures.add(vec2f);
				break;
			case "vn":
				// Vertex normal
				Vector3f vec3fNorm = new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
						Float.parseFloat(tokens[3]));
				normals.add(vec3fNorm);
				break;
			case "f":
				// Face
				Face face = new Face(tokens[1], tokens[2], tokens[3]);
				faces.add(face);
				break;
			default:
				// Ignore other lines
				break;
			}
		}
		return reorderLists(vertices, textures, normals, faces);
	}

	private static Mesh reorderLists(List<Vector3f> posList, List<Vector2f> textCoordList, List<Vector3f> normList,
			List<Face> facesList) {

		List<Integer> indices = new ArrayList<>();
		// Create position array in the order it has been declared
		float[] posArr = new float[posList.size() * 3];
		int i = 0;
		for (Vector3f pos : posList) {
			posArr[i * 3] = pos.x;
			posArr[i * 3 + 1] = pos.y;
			posArr[i * 3 + 2] = pos.z;
			i++;
		}
		float[] textCoordArr = new float[posList.size() * 2];
		float[] normArr = new float[posList.size() * 3];

		for (Face face : facesList) {
			IdxGroup[] faceVertexIndices = face.getFaceVertexIndices();
			for (IdxGroup indValue : faceVertexIndices) {
				processFaceVertex(indValue, textCoordList, normList, indices, textCoordArr, normArr);
			}
		}
		int[] indicesArr = new int[indices.size()];
		indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
		Mesh mesh = new Mesh(posArr, textCoordArr, normArr, indicesArr);
		return mesh;
	}

	private static void processFaceVertex(IdxGroup indices, List<Vector2f> textCoordList, List<Vector3f> normList,
			List<Integer> indicesList, float[] texCoordArr, float[] normArr) {

		// Set index for vertex coordinates
		int posIndex = indices.idxPos;
		indicesList.add(posIndex);

		// Reorder texture coordinates
		if (indices.idxTextCoord >= 0) {
			Vector2f textCoord = textCoordList.get(indices.idxTextCoord);
			texCoordArr[posIndex * 2] = textCoord.x;
			texCoordArr[posIndex * 2 + 1] = 1 - textCoord.y;
		}
		if (indices.idxVecNormal >= 0) {
			// Reorder vector normals
			Vector3f vecNorm = normList.get(indices.idxVecNormal);
			normArr[posIndex * 3] = vecNorm.x;
			normArr[posIndex * 3 + 1] = vecNorm.y;
			normArr[posIndex * 3 + 2] = vecNorm.z;
		}
	}
}

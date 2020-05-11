package com.ashbob999.gameEngine2d.graphics;

import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_info_from_memory;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;

import com.ashbob999.gameEngine2d.util.ResourceLoader;

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
public class Texture {
	private final int id;

	public Texture(String fileName) throws Exception {
		// TODO: sort filename handler / location
		this(loadTexture(fileName));
	}

	public Texture(int id) {
		this.id = id;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public int getId() {
		return id;
	}

	private static int loadTexture(String fileName) throws Exception {
		int width;
		int height;
		ByteBuffer buf;
		// Load Texture file
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer channels = stack.mallocInt(1);

			ByteBuffer data = ResourceLoader.getFileAsBuffer(fileName);

			if (data == null) {
				throw new Exception("Image file [" + fileName + "] not found");
			}

			if (!stbi_info_from_memory(data, w, h, channels)) {
				throw new Exception("Image file [" + fileName + "] unable to format");
			}

			buf = stbi_load_from_memory(data, w, h, channels, 4);

			if (buf == null) {
				throw new Exception("Image file [" + fileName + "] not loaded: " + stbi_failure_reason());
			}

			/* Get width and height of image */
			width = w.get();
			height = h.get();
		}

		// Create a new OpenGL texture
		int textureId = glGenTextures();
		// Bind the texture
		glBindTexture(GL_TEXTURE_2D, textureId);

		// Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		// glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		// Upload the texture data
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
		// Generate Mip Map
		glGenerateMipmap(GL_TEXTURE_2D);

		stbi_image_free(buf);

		return textureId;
	}

	public void cleanup() {
		glDeleteTextures(id);
	}
}

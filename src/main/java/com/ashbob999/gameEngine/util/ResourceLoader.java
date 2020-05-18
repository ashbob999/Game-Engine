package com.ashbob999.gameEngine.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.BufferUtils;

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
public class ResourceLoader {
	public static final String RESOURCE_PATH = "src/main/resources/";

	/**
	 * Returns the whole contents of a file as a string
	 * 
	 * @param  fileName    the name of the file
	 * 
	 * @return             a string containing the whole contents of the file
	 * 
	 * @throws IOException if the file is not found
	 */
	public static String loadResource(String fileName) throws IOException {
		String result;
		try (InputStream in = ResourceLoader.class.getResourceAsStream(fileName);
				Scanner scanner = new Scanner(in, java.nio.charset.StandardCharsets.UTF_8.name())) {
			result = scanner.useDelimiter("\\A").next(); // gets the whole data
		}
		return result;
	}

	/**
	 * Returns a list containing every line in the file
	 * 
	 * @param  fileName    the name of the file
	 * 
	 * @return             a {@link List} containing the lines of the file
	 * 
	 * @throws IOException if the file is not found
	 */
	public static List<String> readAllLines(String fileName) throws IOException {
		List<String> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(ResourceLoader.class.getResourceAsStream(fileName)))) {
			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		}
		return list;
	}

	/**
	 * Returns a {@link ByteBuffer} of the file at the given path, or {@code null} if the file does not exist
	 * 
	 * @param  fileName the name of the file
	 * 
	 * @return          the {@link ByteBuffer} of the file, or {@code null} is the file does not exist
	 */
	public static ByteBuffer getFileAsBuffer(String fileName) {
		try {
			byte[] buffer = new byte[4096];
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			InputStream in = getFileAsStream(fileName);

			int r = 0;
			while ((r = in.read(buffer)) != -1) {
				outstream.write(buffer, 0, r);
			}

			in.close();

			ByteBuffer buf = BufferUtils.createByteBuffer(outstream.size());
			byte[] out = outstream.toByteArray();
			for (int i = 0; i < out.length; i++)
				buf.put(out[i]);

			buf.flip();

			// ByteBuffer unwrappedBuffer = MemoryUtil.memAlloc(buf.capacity());
			// while (buf.hasRemaining()) {
			// unwrappedBuffer.put(buf.get());
			// }
			//
			// buf.flip();
			// unwrappedBuffer.flip();

			return buf;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a {@link InputStream} of the file at the given path, or {@code null} if the file does not exist
	 * 
	 * @param  fileName the name of the file
	 * 
	 * @return          the stream of the file, or {@code null} if the file does not exist
	 */
	public static InputStream getFileAsStream(String fileName) {
		InputStream in = ResourceLoader.class.getResourceAsStream(fileName);
		if (in == null) {
			return ResourceLoader.class.getResourceAsStream("/" + fileName);
		}
		return in;
	}
}

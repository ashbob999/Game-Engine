package com.ashbob999.gameEngine.core;

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
public interface IGameLogic {

	/**
	 * 
	 * @throws Exception
	 */
	void init(Window window) throws Exception;

	/**
	 * 
	 * @param window
	 * @param mouseInput
	 */
	void input(Window window, MouseInput mouseInput);

	/**
	 * The window updating method
	 * 
	 * @param interval
	 * @param mouseInput
	 */
	void update(float interval, MouseInput mouseInput);

	/**
	 * The window rendering method
	 * 
	 * @param window The window object
	 */
	void render(Window window);

	void cleanup();
}

package com.ashbob999.gameEngine;

import com.ashbob999.gameEngine.core.GameEngine;
import com.ashbob999.gameEngine.core.IGameLogic;
import com.ashbob999.gameEngine.core.WindowFlag;

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
public class App {
	public static void main(String[] args) {

		try {
			IGameLogic gameLogic = new BasicGame3D();

			WindowFlag[] windowFlags = new WindowFlag[] { WindowFlag.SHOW_FPS, WindowFlag.ANTIALIASING,
					WindowFlag.RESIZEABLE };

			GameEngine gameEng = new GameEngine("GAME (TEST)", 900, 480, gameLogic, windowFlags);
			gameEng.run();
		} catch (Exception excp) {
			excp.printStackTrace();
			System.exit(-1);
		}
	}
}

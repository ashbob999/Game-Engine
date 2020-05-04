package com.ashbob999.gameEngine2d;

import java.io.InputStream;

import com.ashbob999.gameEngine2d.core.GameEngine;
import com.ashbob999.gameEngine2d.core.IGameLogic;
import com.ashbob999.gameEngine2d.core.WindowFlag;
import com.ashbob999.gameEngine2d.util.ResourceLoader;

/**
 * Hello world!
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

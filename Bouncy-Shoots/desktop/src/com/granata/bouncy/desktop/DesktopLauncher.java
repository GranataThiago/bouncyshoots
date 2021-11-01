package com.granata.bouncy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.granata.bouncy.BouncyShoots;
import com.granata.bouncy.utiles.Config;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Config.NOMBRE;
		config.height = Config.ALTO;
		config.width = Config.ANCHO;
//		config.fullscreen = true;
		new LwjglApplication(new BouncyShoots(), config);
	}
}

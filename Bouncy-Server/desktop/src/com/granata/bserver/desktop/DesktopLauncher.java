package com.granata.bserver.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.granata.bserver.BouncyServer;
import com.granata.bserver.utiles.Config;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Config.NOMBRE;
		config.height = Config.ALTO;
		config.width = Config.ANCHO;
		config.fullscreen = true;
		new LwjglApplication(new BouncyServer(), config);
	}
}

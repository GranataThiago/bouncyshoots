package com.granata.bouncy.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

	public static final AssetManager manager = new AssetManager();
	
	public static final String bala = "Sin-título-2.png";
	public static final String powerups = "powerups.png";
	public static final String personajes = "personajes/bouncys.png";
	public static final String crosshair = "Crosshair.png";
	
	// Modos de juego
	public static final String luzVerde = "images/luzVerde.png";
	public static final String luzRoja = "images/luzRoja.png";
	
	public static void load() {
		manager.load(bala, Texture.class);
		manager.load(powerups, Texture.class);
		manager.load(personajes, Texture.class);
		manager.load(crosshair, Texture.class);
		
		manager.load(luzVerde, Texture.class);
		manager.load(luzRoja, Texture.class);
	}
	
	public static void dispose() {
		manager.dispose();
	}
	
}

package com.granata.bserver.utiles;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.granata.bserver.BouncyServer;

public abstract class Render {

	public static SpriteBatch sb;
	public static BouncyServer app;
	public static ArrayList<Sprite> spritesADibujar = new ArrayList<Sprite>();
	private static ArrayList<Sprite> spritesDibujandose = new ArrayList<Sprite>();
	public static Texture bala = new Texture("Sin-t?tulo-2.png");
	public static Texture estrella = new Texture("estrella.png");
	
	public static void limpiarPantallaB() {
		Gdx.gl.glClearColor(.5f, 0f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public static void limpiarPantallaN() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	
	public static void dibujarSprites() {
		// Hecho de esta manera para evitar excepciones
		for(Sprite s : spritesDibujandose) {
			s.draw(sb);
		}
		// No es la mejor forma, pero borro todos y le agrego solo los que se tienen que dibujar.
		spritesDibujandose.clear();
		spritesDibujandose.addAll(spritesADibujar);
	}
	

	
}

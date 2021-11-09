package com.granata.bouncy.modos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Render;
import com.granata.bouncy.utiles.Utiles;

public class Estrella extends JuegoBase implements EstrellaEventListener{

	private Sprite estrella;
	
	@Override
	public void start(String rutaMapa) {
		super.start(rutaMapa);
		Utiles.eListener = this;
		estrella = new Sprite(new Texture("estrella.png"));
		estrella.setSize(32 / Config.PPM, 32 / Config.PPM);
		Render.spritesADibujar.add(this.estrella);
		System.out.println("Iniciando Estrella");
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);

		
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);

	}

	@Override
	public void moverEstrella(float x, float y) {
		
		estrella.setPosition(x, y);
		
	}

	
}

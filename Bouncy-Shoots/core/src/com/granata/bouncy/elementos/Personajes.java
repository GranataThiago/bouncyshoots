package com.granata.bouncy.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.granata.bouncy.managers.Assets;
import com.granata.bouncy.utiles.Utiles;

public enum Personajes {

	AMARILLO(0),
	AZUL(1),
	ROJO(2),
	VERDE(3);
	
	private int posicion;
	
	private Personajes(int posicion){
		this.posicion = posicion;
	}

	public Sprite getPersonajeAleatorio() {
		int skinAleatoria = values()[Utiles.r.nextInt(values().length)].posicion;
		TextureRegion asd = new TextureRegion(Assets.manager.get(Assets.personajes, Texture.class));
		asd.setRegion((skinAleatoria == 0) ? 0 : 64 * skinAleatoria, 0, 64, 64);
		return new Sprite(asd);
	}
	
	public Sprite getSprite() {
		TextureRegion personaje = new TextureRegion(Assets.manager.get(Assets.personajes, Texture.class));
		personaje.setRegion((posicion == 0) ? 0 : 64 * posicion, 0, 64, 64);
		return new Sprite(personaje);
	}
	
	
}

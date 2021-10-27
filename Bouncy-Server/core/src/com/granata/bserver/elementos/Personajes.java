package com.granata.bserver.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.granata.bserver.utiles.Utiles;

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
		TextureRegion asd = new TextureRegion(new Texture("personajes/bouncys.png"));
		asd.setRegion((skinAleatoria == 0) ? 0 : 64 * skinAleatoria, 0, 64, 64);
		return new Sprite(asd);
	}
	
	public Sprite getSprite() {
		TextureRegion personaje = new TextureRegion(new Texture("personajes/bouncys.png"));
		personaje.setRegion((posicion == 0) ? 0 : 64 * posicion, 0, 64, 64);
		return new Sprite(personaje);
	}
	
	public int getPosicion() {
		return this.posicion;
	}
	
	
}

package com.granata.bouncy.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.granata.bouncy.io.KeyInput;
import com.granata.bouncy.managers.JugadorEventListener;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Render;

public class Personaje implements JugadorEventListener{
	
	
	// Relativo al personaje 
	private Sprite sprite;
	private Arma arma;
	
	// Inputs
	public KeyInput e = new KeyInput();
	private boolean controlable = false;
	
	public Personaje(Sprite s, boolean controlable) {
		this.sprite = s;
		this.controlable = controlable;
	}
	
	public void inicializarPersonaje() {
		if(controlable) Gdx.input.setInputProcessor(e);
		
		this.sprite.setSize(64 / Config.PPM, 64 / Config.PPM);
		Render.spritesADibujar.add(this.sprite);
		
		arma = new Arma();
	}
		
	public void update(float dt) {
		
		arma.update(dt);
		
	}

	
//	public void recibirDaño(float daño) {
//		this.vida -= daño;
//		if(this.vida <= 0) {
//			destruir();
//		}
//	}

	public Arma getArma() {
		return arma;
	}

	public void destruir() {
//		muerto = true;
		Render.spritesADibujar.remove(this.sprite);
}
	public Vector2 getSpritePos() {
		return new Vector2(sprite.getX(), sprite.getY());
	}
	
	@Override
	public void actualizarPosicion(float x, float y) {
		sprite.setPosition(x, y);
	}

	@Override
	public void disparar(Vector2 posDisparo, Vector3 target) {
		arma.disparar(posDisparo, target);
	}

	@Override
	public void cambiarDireccion(boolean estadoFlip) {
		sprite.flip(estadoFlip, false);
		
	}

	


	
}

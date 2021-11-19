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
	
	//
	float speed = 0, accel = 0, maxAccel = 0f, maxSpeed = 0.2f, maxDeceleration = 1f;
	
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
		controlarMovimiento(dt);
		

	}

	
	private void controlarMovimiento(float delta) {
		if(e.isRight() || e.isLeft())
	        accel += 0.1f;
	    else
	        accel -= 0.1f;

	    if(accel > maxAccel)
	        accel = maxAccel;

	    if(speed == 0.0f && accel < 0.0f)
	        accel = 0.0f;
	    else if(speed > 0.0f && accel < -maxDeceleration)
	        accel = -maxDeceleration;
	    
	    speed += accel;
	    
	    if(e.isRight()) {
	    	sprite.setPosition(sprite.getX() + speed, sprite.getY());
	    }else if(e.isLeft()) {
	    	sprite.setPosition(sprite.getX() - speed, sprite.getY());
	    }

	    if(speed >= maxSpeed)
	        speed = maxSpeed;
	    else if(speed < 0.0f)
	        speed = 0.0f;
	}

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

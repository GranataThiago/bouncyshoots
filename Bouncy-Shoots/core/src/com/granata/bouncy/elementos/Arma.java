package com.granata.bouncy.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.granata.bouncy.managers.ControladorBalas;
import com.granata.bouncy.managers.ControladorBodies;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Render;

public class Arma{
	
	// Crosshair
	private Vector3 posMouse;
	private Sprite sprite = new Sprite(new Texture("Crosshair.png")); 
	
	// Configuración Arma
	private int balas = 10;
	private int daño = 30;
	private int rebotes = 10;
	private float incDañoRebotes = 0;
	private float tiempoTranscurrido = 0, tiempoEntreDisparo = 0.25f;
	
	public Arma() {
		sprite.setSize(15 / Config.PPM, 15 / Config.PPM);
	}
	
	public void update(float dt) {

		posMouse = new Vector3(Gdx.input.getX(), (Gdx.input.getY()), 0);
		Global.cam.unproject(posMouse);
		sprite.setPosition(posMouse.x, posMouse.y);
		sprite.draw(Render.sb);
		
	}


	public void disparar(Vector2 posDisparo, Vector3 target) {

		
			Bala b = ControladorBalas.bp.obtain();
			b.crearBala(calcularPosicionDisparo(posDisparo, target), target, rebotes, incDañoRebotes, daño);
			ControladorBalas.balasActivas.add(b);
			balas--;
		
		
		
	}
	
	private Vector2 calcularPosicionDisparo(Vector2 posDisparo, Vector3 target) {
		if(posDisparo.y - target.y < -0.25f) { posDisparo.y += 0.5f; posDisparo.x -= 0.5f; }
		else if(posDisparo.y - target.y > 0.25f) { posDisparo.y -= 0.5f; posDisparo.x -= 0.5f; }

		if(posDisparo.x - target.x < -0.25f){ posDisparo.x += 0.5f; }
		else if(posDisparo.x - target.x > 0.25f) { posDisparo.x -= 0.5f; }

		return posDisparo;
	}
	
	public void setIncDañoRebotes(float incDañoRebotes) {
		this.incDañoRebotes = incDañoRebotes;
	}

	public void aumentarRebotes(float rebotes) {
		this.rebotes += rebotes;
	}

	public Vector2 getPosition() {
		return new Vector2(sprite.getX(), sprite.getY());
	}
	
	public int getBalas() {
		return balas;
	}
	
	public void setBalas(int balas) {
		this.balas = balas;
	}
	
	public void setDaño(int daño) {
		this.daño = daño;
	}

	public void dispose() {
		sprite.getTexture().dispose();
	}
	

}

package com.granata.bouncy.elementos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Render;

public class Bala extends Sprite implements Movible, Poolable{

	// Movimiento de la bala
	Vector2 direccion;

	// Configuraci�n Balas
	private float vel = 10f, da�o, incDa�oRebotes;
	private int rebotes;
	private Sprite sprite;
	
	public Bala() {
		
		this.rebotes = 10;
		this.incDa�oRebotes = 0;
		this.da�o = 30;

	}
	
	public void crearBala(Vector2 curPos, Vector3 target) {
		sprite = new Sprite(Render.bala);
		sprite.setSize(16 / Config.PPM, 16 / Config.PPM);
		Render.spritesADibujar.add(sprite);

	}
	
	public float getDa�o() {
		return this.da�o;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	
	public void rebotar() {
		this.rebotes--;
		this.da�o += incDa�oRebotes;
		if(this.rebotes <= 0) {
			destruir();
		}
	}
	
	public void actualizarPosicion(float x, float y) {
		sprite.setPosition(x, y);
	}
	
	public void destruir() {
		System.out.println("bala destruida");
		Render.spritesADibujar.remove(sprite);
	}
	
	@Override
	public void onDibujar() {
		System.out.println("dibujando bala");
	    Render.spritesADibujar.add(sprite);
	}

	@Override
	public void reset() {
		this.direccion.set(0, 0);
		
		
	}
}

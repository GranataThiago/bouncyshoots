package com.granata.bouncy.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.granata.bouncy.managers.ControladorBalas;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Render;

public class Arma{
	
	// Crosshair
	private Vector3 posMouse;
	private Sprite sprite = new Sprite(new Texture("Crosshair.png")); 

	
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
			b.crearBala(calcularPosicionDisparo(posDisparo, target), target);
			ControladorBalas.balasActivas.add(b);
			System.out.println(ControladorBalas.balasActivas.size);
			//balas--;
		
	}
	
	private Vector2 calcularPosicionDisparo(Vector2 posDisparo, Vector3 target) {
		if(posDisparo.y - target.y < -0.25f) { posDisparo.y += 0.5f; posDisparo.x -= 0.5f; }
		else if(posDisparo.y - target.y > 0.25f) { posDisparo.y -= 0.5f; posDisparo.x -= 0.5f; }

		if(posDisparo.x - target.x < -0.25f){ posDisparo.x += 0.5f; }
		else if(posDisparo.x - target.x > 0.25f) { posDisparo.x -= 0.5f; }

		return posDisparo;
	}
	

	public Vector2 getPosition() {
		return new Vector2(sprite.getX(), sprite.getY());
	}


	public void dispose() {
		sprite.getTexture().dispose();
	}
	

}

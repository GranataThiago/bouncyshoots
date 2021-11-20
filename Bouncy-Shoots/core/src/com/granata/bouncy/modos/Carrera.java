package com.granata.bouncy.modos;

import com.badlogic.gdx.math.Vector2;
import com.granata.bouncy.powerups.OneShot;
import com.granata.bouncy.powerups.Powerup;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Render;

public class Carrera extends JuegoBase{
	
	private float puntoFinal = 28f;
	private float velCamara = 3f;
	
	@Override
	public void start(String rutaMapa) {
		super.start(rutaMapa);
		System.out.println("Iniciando carrera...");
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		if(Global.cam.position.x <= puntoFinal) {
			Global.cam.position.x += velCamara * delta;
		}
		Global.cam.update();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		Render.sb.setProjectionMatrix(Global.cam.combined);
	}



	@Override
	public void spawnPickup(int nroPowerup, int posPowerup) {
		if(posPowerup < spawners.size()) {
			Powerup p = new OneShot();
			powerups.add(p);
			Vector2 coords = spawners.get(posPowerup);
			
			Render.spritesADibujar.add(p.getSprite());
			p.getSprite().setPosition((coords.x / Config.PPM) - (p.getSprite().getWidth() / 2), (coords.y / Config.PPM)  - (p.getSprite().getHeight() / 2));
		}
	}


}

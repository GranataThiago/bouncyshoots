package com.granata.bouncy.modos;

import com.badlogic.gdx.math.Vector2;
import com.granata.bouncy.powerups.Bullet;
import com.granata.bouncy.powerups.Powerup;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Render;

public class Colina extends JuegoBase{

	
	@Override
	public void start(String rutaMapa) {
		super.start(rutaMapa);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
//		
//		if(tiempoEntreSpawn > 0.75f && ((comprobarEspaciosVacios() == -1) ? false : true)) {
//			tiempoEntreSpawn = 0f;
//		}
		

	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
	}
	


	@Override
	public void spawnPickup(int nroPowerup, int posPowerup) {
		if(posPowerup < spawners.size()) {
			Powerup p = new Bullet();
			powerups.add(p);
			Vector2 coords = spawners.get(posPowerup);
			
			Render.spritesADibujar.add(p.getSprite());
			p.getSprite().setPosition((coords.x / Config.PPM) - (p.getSprite().getWidth() / 2), (coords.y / Config.PPM)  - (p.getSprite().getHeight() / 2));
		}
		
	}


}

package com.granata.bouncy.modos;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.granata.bouncy.powerups.OneShot;
import com.granata.bouncy.powerups.Powerup;
import com.granata.bouncy.screens.ScreenJuego;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Render;

public class Carrera extends JuegoBase{
	
	private float puntoFinal = 28f;
	private float velCamara = 4f, tiempoParaMorir = 0f, tiempoParaEmpezar = 0f;
	private ArrayList<Powerup> powerups = new ArrayList<Powerup>();

	
	@Override
	public void start(String rutaMapa, Vector2[] spawners) {
		super.start(rutaMapa, spawners);
		System.out.println("Iniciando carrera...");
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		Global.cam.update();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		Render.sb.setProjectionMatrix(Global.cam.combined);
//		if(!this.cam.frustum.pointInFrustum(p.getPosition().x, p.getPosition().y, 0) && p.getEstadoActual() != Estado.MUERTO) {
//			
//			tiempoParaMorir += delta;
//			if(tiempoParaMorir > 0.5f) {
//				p.destruir();
//			}
//			
//		}else tiempoParaMorir = 0f;
	}



	@Override
	public void spawnPickup(int nroPowerup, int posPowerup) {
		if(posPowerup < spawners.length) {
			Powerup p = new OneShot();
			powerups.add(p);
			Vector2 coords = spawners[posPowerup];
			
			Render.spritesADibujar.add(p.getSprite());
			p.getSprite().setPosition((coords.x / Config.PPM) - (p.getSprite().getWidth() / 2), (coords.y / Config.PPM)  - (p.getSprite().getHeight() / 2));
		}
	}

	@Override
	public void borrarPickup(int posicion) {
		Vector2 coords = spawners[posicion];
		int i = 0;
		boolean borrado = false;
		
		do {
			
			if(coords.x / Config.PPM - (powerups.get(i).getSprite().getWidth() / 2) == powerups.get(i).getSprite().getX() && coords.y / Config.PPM - (powerups.get(i).getSprite().getHeight() / 2) == powerups.get(i).getSprite().getY()) {
				powerups.get(i).destruir();
				powerups.remove(i);
				borrado = true;
			}
			
			i++;
		}while(!borrado && i < spawners.length);
		
	}

	@Override
	public void cambiarMapa(int mapa) {
		Render.app.setScreen(new ScreenJuego(mapa));
		
	}

}

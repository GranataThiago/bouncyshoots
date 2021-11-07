package com.granata.bouncy.modos;

import com.granata.bouncy.mapas.Mapas;
import com.granata.bouncy.screens.ScreenJuego;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void borrarPickup(int posicion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cambiarMapa(int mapa) {
		Render.app.setScreen(new ScreenJuego(mapa));
		System.out.println("Cambiando mapa..." + Mapas.values()[mapa].getRuta());
	}

	@Override
	public void moverCamaraX(float x) {
		// TODO Auto-generated method stub
		
	}

}

package com.granata.bouncy.managers;

import java.util.EventListener;

public interface JuegoEventListener extends EventListener{

	public void spawnPickup(int nroPowerup, int posPowerup);
	public void borrarPickup(int posicion);
	public void cambiarMapa(int mapa);
	public void moverCamaraX(float x);
	public void cambiarEstado(String estado);
	public void contar(float tiempo);
	
}

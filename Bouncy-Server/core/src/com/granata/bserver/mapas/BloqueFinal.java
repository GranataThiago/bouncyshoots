package com.granata.bserver.mapas;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.utiles.Render;
import com.granata.bserver.utiles.Utiles;

public class BloqueFinal implements BloqueInteractivo{

	public BloqueFinal(Rectangle rect) {
		System.out.println("Se creo la meta");
		ControladorBodies.crearBloqueInteractivo((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2), rect.getWidth(), rect.getHeight(), BodyType.StaticBody, this, true);
	}
	
	@Override
	public void onEntrar(int idJugador) {
		System.out.println("El jugador: " + idJugador + " llegó a la meta");
		Render.app.getSv().getClientes().get(idJugador).setScore(250);
		Utiles.cListener.onTerminar();
	}

	@Override
	public void onSalir() {
		// TODO Auto-generated method stub
		
	}

}

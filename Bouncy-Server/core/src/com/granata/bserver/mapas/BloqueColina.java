package com.granata.bserver.mapas;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.utiles.Render;

public class BloqueColina implements BloqueInteractivo{

	private Timer cronometro = new Timer();
	
	public BloqueColina(Rectangle rect) {
		System.out.println("Se creó un bloque colina");
		ControladorBodies.crearBloqueInteractivo((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2), rect.getWidth(), rect.getHeight(), BodyType.StaticBody, this, false);
	}
	
	@Override
	public void onEntrar(final int idJugador) {
		float tiempoEntreAumento = 1; 

		cronometro.scheduleTask(new Task(){
		    @Override
		    public void run() {
		    	if(idJugador < Render.app.getSv().getClientes().size()) {
			        Render.app.getSv().getClientes().get(idJugador).setScore(10);
		    	}
		    }
		}, 0, tiempoEntreAumento);
	}

	@Override
	public void onSalir() {
		cronometro.clear();
	}

	
	
}

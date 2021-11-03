package com.granata.bserver;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.granata.bserver.managers.ControladorNiveles;
import com.granata.bserver.red.Servidor;
import com.granata.bserver.screens.ScreenMenu;
import com.granata.bserver.utiles.Render;

public class BouncyServer extends Game {
	
	private int mapaActual = 1;
	private Servidor sv;
	
	@Override
	public void create () {
		Render.app = this;
		sv = new Servidor();
		Render.sb = new SpriteBatch();
		ControladorNiveles.generarMapas(2);
		this.setScreen(new ScreenMenu());
	}

	public int cambiarMapa() {
		int mapa = ControladorNiveles.niveles.get(mapaActual);
		System.out.println("Se le envia al cliente el mapa " + mapa + " y el servidor carga el mapa: " + mapa);
		sv.getHs().enviarMensajeGeneral("CambiarMapa!" + mapa);
		return ControladorNiveles.niveles.get(mapaActual++);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		Render.sb.dispose();
	}
	
	public Servidor getSv() {
		return this.sv;
	}
}

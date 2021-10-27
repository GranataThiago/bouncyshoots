package com.granata.bserver;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.granata.bserver.managers.ControladorNiveles;
import com.granata.bserver.red.Servidor;
import com.granata.bserver.screens.ScreenMenu;
import com.granata.bserver.utiles.Render;

public class BouncyServer extends Game {
	
	private int mapaActual = 0;
	private Servidor sv;
	
	@Override
	public void create () {
		Render.app = this;
		sv = new Servidor();
		Render.sb = new SpriteBatch();
		ControladorNiveles.generarMapas();
		this.setScreen(new ScreenMenu());
	}

	public int cambiarMapa() {
		if(mapaActual == ControladorNiveles.niveles.size() - 1) mapaActual = 0;
		else mapaActual++;

		return mapaActual;
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

package com.granata.bserver;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.granata.bserver.managers.ControladorNiveles;
import com.granata.bserver.red.HiloServidor;
import com.granata.bserver.screens.ScreenMenu;
import com.granata.bserver.utiles.Render;

public class BouncyServer extends Game {
	
	private int mapaActual = 0;
	private HiloServidor hs;
	
	@Override
	public void create () {
		hs = new HiloServidor();
		hs.start();
		
		Render.app = this;
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
}

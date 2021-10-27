package com.granata.bouncy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.granata.bouncy.managers.ControladorNiveles;
import com.granata.bouncy.red.Cliente;
import com.granata.bouncy.screens.ScreenMenu;
import com.granata.bouncy.utiles.Render;

public class BouncyShoots extends Game {
	
	private int mapaActual = 0;
	private Cliente cliente;
	
	@Override
	public void create () {
		cliente = new Cliente();
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
	
	public Cliente getCliente() {
		return this.cliente;
	}

}

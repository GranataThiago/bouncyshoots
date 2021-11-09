package com.granata.bouncy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.granata.bouncy.red.Cliente;
import com.granata.bouncy.screens.ScreenMenu;
import com.granata.bouncy.screens.ScreenResultados;
import com.granata.bouncy.utiles.Render;

public class BouncyShoots extends Game {
	
	private Cliente cliente;
	
	@Override
	public void create () {
		cliente = new Cliente();
		Render.app = this;
		Render.sb = new SpriteBatch();
		this.setScreen(new ScreenMenu());
	}

	public void mostrarResultados(final int ganador) {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				setScreen(new ScreenResultados(ganador));
			}
		});
//		this.setScreen(new ScreenResultados(ganador));
	}
	
	public void volverAlMenu() {
		cliente.getHc().enviarMensaje("DesconectarCliente!" + cliente.getId());
		Render.sb = new SpriteBatch();
		this.setScreen(new ScreenMenu());
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

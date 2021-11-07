package com.granata.bserver;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.granata.bserver.managers.ControladorNiveles;
import com.granata.bserver.red.Cliente;
import com.granata.bserver.red.Servidor;
import com.granata.bserver.screens.ScreenJuego;
import com.granata.bserver.screens.ScreenMenu;
import com.granata.bserver.screens.ScreenResultados;
import com.granata.bserver.utiles.Render;

public class BouncyServer extends Game {
	
	private int mapaActual = 1;
	private Servidor sv;
	
	@Override
	public void create () {
		Render.app = this;
		sv = new Servidor();
		Render.sb = new SpriteBatch();
		ControladorNiveles.generarMapas(4);
		this.setScreen(new ScreenMenu());
	}
	
	public Screen pasarNivel() {
		if(mapaActual < ControladorNiveles.niveles.size()) {
			int mapa = ControladorNiveles.niveles.get(mapaActual);
			sv.getHs().enviarMensajeGeneral("CambiarMapa!" + mapa);
			return new ScreenJuego(ControladorNiveles.niveles.get(mapaActual++));
		}else {
			int ganador = calcularGanador();
			sv.getHs().enviarMensajeGeneral("MostrarResultados!" + ganador);
			return new ScreenResultados(ganador);
		}
		

	}
	
	public int calcularGanador() {
		int ganador = -1;
		
		for (int i = 0; i < sv.getClientes().size(); i++) {
			if(i==0) {
				ganador = i;
			} else {
				if(sv.getClientes().get(i).getScore() > sv.getClientes().get(ganador).getScore()) {
					ganador = i;
				}
			}
		}
		
		return ganador;
	}

	public void volverAlMenu() {
		sv.limpiarServidor();
		ControladorNiveles.niveles.removeAll(ControladorNiveles.niveles);
		mapaActual = 1;
		Render.sb = new SpriteBatch();
		ControladorNiveles.generarMapas(4);
		this.setScreen(new ScreenMenu());
	}
	
	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		System.out.println("Disposée el spritebatch");
		Render.sb.dispose();
	}
	
	public Servidor getSv() {
		return this.sv;
	}
}

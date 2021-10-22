package com.granata.bouncy.screens;

import com.badlogic.gdx.Screen;
import com.granata.bouncy.mapas.Mapas;
import com.granata.bouncy.modos.JuegoBase;

public class ScreenJuego implements Screen{

	private JuegoBase juego;
	private Mapas mapa;
	
	public ScreenJuego(int numMapa) {
		mapa = Mapas.values()[numMapa];
		juego = mapa.getModo();
		System.out.println(Mapas.values()[numMapa].getModo());
	}
	
	
	@Override
	public void show() {
		juego.start(mapa.getRuta(), mapa.getSpawners());
	}
	
	public void update(float delta) {
		juego.update(delta);
	}
	
	@Override
	public void render(float delta) {
		juego.render(delta);
	}

	
	@Override
	public void resize(int width, int height) {
		juego.resize(width, height);
	}
	
	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		juego.dispose();
	}	
	


}

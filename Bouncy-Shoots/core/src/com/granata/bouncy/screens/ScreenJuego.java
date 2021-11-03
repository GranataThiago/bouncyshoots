package com.granata.bouncy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.granata.bouncy.mapas.Mapas;
import com.granata.bouncy.modos.JuegoBase;

public class ScreenJuego implements Screen{

	private JuegoBase juego;
	private Mapas mapa;
	
	public ScreenJuego(int numMapa) {
		mapa = Mapas.values()[numMapa];
		juego = mapa.getModo();
	}
	
	
	@Override
	public void show() {
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				juego.start(mapa.getRuta(), mapa.getSpawners());
			}
		});

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
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				juego.dispose();
				System.out.println("disposeado");
			}
		});
//		juego.dispose();
	}	
	


}

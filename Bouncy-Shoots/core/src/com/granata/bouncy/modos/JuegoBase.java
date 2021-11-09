package com.granata.bouncy.modos;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.granata.bouncy.elementos.Personaje;
import com.granata.bouncy.managers.JuegoEventListener;
import com.granata.bouncy.mapas.MapaTiled;
import com.granata.bouncy.powerups.Powerup;
import com.granata.bouncy.powerups.Powerups;
import com.granata.bouncy.red.Jugador;
import com.granata.bouncy.screens.ScreenJuego;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Render;
import com.granata.bouncy.utiles.Utiles;

public class JuegoBase implements JuegoEventListener{
	
	private FreeTypeFontGenerator generador;
	private FreeTypeFontParameter parametros;
	private BitmapFont fuente = new BitmapFont();
	private SpriteBatch sb = new SpriteBatch();
	
	// Personaje
	protected Personaje p;

	// Camara
	private Viewport vp;
	
	// Mapa
	private MapaTiled mapa;
	protected ArrayList<Vector2> spawners;
	
	// Cosas del nivel en si
	protected float tiempoEntreSpawn = 0f, tiempoPasado = 0f;
	protected boolean fin = false, empezo = false;
	protected int mapaSiguiente;
	protected ArrayList<Powerup> powerups = new ArrayList<Powerup>();
	
	
	public void start(String rutaMapa) {
		
		mapa = new MapaTiled(rutaMapa);
		spawners = mapa.getSpawners();
		for(Jugador j : Render.app.getCliente().getClientes()) {
			j.crearPersonaje();
		}
		p = Render.app.getCliente().getClientes().get(Render.app.getCliente().getId()).getPj();

		Utiles.juegoListener = this;
		
		Global.cam = new OrthographicCamera();
		vp = new FitViewport(Config.ANCHO / Config.PPM, Config.ALTO / Config.PPM);

		Global.cam.setToOrtho(false, vp.getWorldWidth(), vp.getWorldHeight());

	}
	
	public void render(float delta) {
		Render.limpiarPantallaN();
		if(empezo) {
			update(delta);
			
			// Renderiza el mapa
			mapa.render();
			Render.sb.setProjectionMatrix(Global.cam.combined);

			// Dibujamos al personaje y actualizamos la cámara
			Render.sb.begin();
				for(Jugador j : Render.app.getCliente().getClientes()) {
					j.getPj().update(delta);
				}
				Render.dibujarSprites();
			Render.sb.end();
			
			if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				Gdx.app.exit();
			}
			
		}else {
			sb.begin();
				fuente.draw(sb, Float.toString(tiempoPasado), Config.ANCHO / 2, Config.ALTO / 2);
			sb.end();
			tiempoPasado += delta;
		}
		
		
	}
	
	private void terminarNivel() {
		Render.app.setScreen(new ScreenJuego(mapaSiguiente));
	}

	public void update(float delta) {
		if(fin) terminarNivel();
		tiempoEntreSpawn += delta;
		mapa.update(delta, Global.cam);
	}

	
	public void resize(int width, int height) {
		vp = new FitViewport(width, height);
	}
	
	public void dispose() {
		Utiles.jugadores.clear();
		mapa.dispose();
		Render.spritesADibujar.clear();
		for(Jugador j : Render.app.getCliente().getClientes()) {
			j.getPj().getArma().dispose();
		}
	}

	@Override
	public void spawnPickup(int nroPowerup, int posPowerup) {
		Powerup p = Powerups.values()[nroPowerup].getPowerup();
		powerups.add(p);
		Vector2 coords = spawners.get(posPowerup);
		
		Render.spritesADibujar.add(p.getSprite());
		p.getSprite().setPosition((coords.x / Config.PPM) - (p.getSprite().getWidth() / 2), (coords.y / Config.PPM)  - (p.getSprite().getHeight() / 2));
		
	}

	@Override
	public void borrarPickup(int posicion) {
		Vector2 coords = spawners.get(posicion);
		int i = 0;
		boolean borrado = false;
		
		do {
			
			if(powerups.size() > posicion && coords.x / Config.PPM - (powerups.get(i).getSprite().getWidth() / 2) == powerups.get(i).getSprite().getX() && coords.y / Config.PPM - (powerups.get(i).getSprite().getHeight() / 2) == powerups.get(i).getSprite().getY()) {
				powerups.get(i).destruir();
				powerups.remove(i);
				borrado = true;
			}
			
			i++;
		}while(!borrado && i < spawners.size());
		
	}

	@Override
	public void cambiarMapa(int mapa) {
		fin = true;
		mapaSiguiente = mapa;
		
	}

	@Override
	public void moverCamaraX(float x) {
		Global.cam.position.x = x;
		
	}

	@Override
	public void cambiarEstado(String estado) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void puedeEmpezar() {
		empezo = true;
		System.out.println("Arrancó el juego en el server");
	}
	
	
	

}

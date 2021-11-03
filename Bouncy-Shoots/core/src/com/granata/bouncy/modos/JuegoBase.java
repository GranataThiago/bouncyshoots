package com.granata.bouncy.modos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.granata.bouncy.elementos.Bala;
import com.granata.bouncy.elementos.Personaje;
import com.granata.bouncy.elementos.Personaje.Estado;
import com.granata.bouncy.managers.CollisionListener;
import com.granata.bouncy.managers.ControladorBalas;
import com.granata.bouncy.managers.ControladorBodies;
import com.granata.bouncy.managers.JuegoEventListener;
import com.granata.bouncy.managers.JugadorEventListener;
import com.granata.bouncy.mapas.MapaTiled;
import com.granata.bouncy.powerups.Powerup;
import com.granata.bouncy.powerups.Powerups;
import com.granata.bouncy.red.Jugador;
import com.granata.bouncy.screens.ScreenJuego;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Render;
import com.granata.bouncy.utiles.Utiles;

public abstract class JuegoBase implements JuegoEventListener{
	
	// Personaje
	protected Personaje p;

	// Camara
	private Viewport vp;
	
	// Mapa
	private MapaTiled mapa;
	protected Vector2[] spawners;
	
	// Cosas del nivel en si
	protected float tiempoEntreSpawn = 0f;
	
	public void start(String rutaMapa, Vector2[] spawners) {
		this.spawners = spawners;
		Utiles.juegoListener = this;
		
		Global.cam = new OrthographicCamera();
		vp = new FitViewport(Config.ANCHO / Config.PPM, Config.ALTO / Config.PPM);

		
		for(Jugador j : Render.app.getCliente().getClientes()) {
			j.crearPersonaje();
		}
		p = Render.app.getCliente().getClientes().get(Render.app.getCliente().getId()).getPj();
		mapa = new MapaTiled(rutaMapa);
		Global.cam.setToOrtho(false, vp.getWorldWidth(), vp.getWorldHeight());

	}
	
	public void render(float delta) {
		update(delta);
		Render.limpiarPantallaN();
		
		// Renderiza el mapa
		mapa.render();
		Render.sb.setProjectionMatrix(Global.cam.combined);

		// Dibujamos al personaje y actualizamos la c�mara
		Render.sb.begin();
			for(Jugador j : Render.app.getCliente().getClientes()) {
				j.getPj().update(delta);
			}
			Render.dibujarSprites();
		Render.sb.end();
		
//		for(Bala b : ControladorBalas.balasActivas) {
//			b.update();
//		}

//		if(p.getEstadoActual() == Estado.MUERTO) {
//			Render.app.setScreen(new ScreenJuego(Render.app.cambiarMapa()));
//		}
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		
	}
	
	public void update(float delta) {
		tiempoEntreSpawn += delta;
		mapa.update(delta, Global.cam);
	}

	
	public void resize(int width, int height) {
		vp = new FitViewport(width, height);
	}
	
	public void dispose() {
		Utiles.jugadores.removeAll(Utiles.jugadores);
		mapa.dispose();
		Render.spritesADibujar.removeAll(Render.spritesADibujar);
		for(Jugador j : Render.app.getCliente().getClientes()) {
			j.getPj().getArma().dispose();
		}
	}
	
	
	

}

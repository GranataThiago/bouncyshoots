package com.granata.bserver.modos;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.granata.bserver.elementos.Bala;
import com.granata.bserver.elementos.Personaje;
import com.granata.bserver.elementos.Personaje.Estado;
import com.granata.bserver.managers.CollisionListener;
import com.granata.bserver.managers.ControladorBalas;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.managers.JugadorEventListener;
import com.granata.bserver.mapas.MapaTiled;
import com.granata.bserver.red.Cliente;
import com.granata.bserver.screens.ScreenJuego;
import com.granata.bserver.utiles.Config;
import com.granata.bserver.utiles.Render;
import com.granata.bserver.utiles.Utiles;

public abstract class JuegoBase {

	// Condiciones de final
	// <= 1 persona viva / -> en todos
	// Alguien llegó a la meta / -> en carrera
	// Pasó 1 minuto / -> en colina o estatua
	
	// Box2D
	private Box2DDebugRenderer b2r;
	

	// Camara
	protected OrthographicCamera cam;
	private Viewport vp;
	
	// Mapa
	protected MapaTiled mapa;
	protected ArrayList<Vector2> spawners = new ArrayList<Vector2>();
	protected boolean fin = false;
	
	// Cosas del nivel en si
	protected float tiempoEntreSpawn = 0f; 
	private float tiempoTotal = 10f, tiempoTranscurrido = 0;
	private int jugadoresMuertos = 0;

	
	public void start(String rutaMapa) {	
		cam = new OrthographicCamera();
		vp = new FitViewport(Config.ANCHO / Config.PPM, Config.ALTO / Config.PPM);
		
		// Creación del mundo necesario para Box2D
		ControladorBodies.world = new World(new Vector2(0, -16.42f), false);
		ControladorBodies.world.setContactListener(new CollisionListener());

		mapa = new MapaTiled(rutaMapa);
		spawners = mapa.getSpawners();
		
		b2r = new Box2DDebugRenderer();
		for(int i = 0; i < Render.app.getSv().getClientes().size(); i++) {
			Render.app.getSv().getClientes().get(i)
				.crearPersonaje(cam, mapa.getSpawnpoints().get(i));
		}

		cam.setToOrtho(false, vp.getWorldWidth(), vp.getWorldHeight());

	}
	
	public void render(float delta) {
			Render.limpiarPantallaN();
			update(delta);
			
			// Renderiza el mapa
			mapa.render();
			Render.sb.setProjectionMatrix(cam.combined);

			ControladorBodies.world.step(Gdx.graphics.getDeltaTime(), 6, 2);
			borrarCuerpos();
//			b2r.render(ControladorBodies.world, cam.combined);

			// Dibujamos al personaje y actualizamos la cámara
			Render.sb.begin();
				for(Cliente c : Render.app.getSv().getClientes()) {
					if(c.getPj().getEstadoActual() == Estado.MUERTO) {
						jugadoresMuertos++;
					}else {
						System.out.println(fin + " " + mapa.getNombre());
						if(!fin) c.getPj().update(delta);
					}
				}
				Render.dibujarSprites();
			Render.sb.end();
			
			int cantBalas = 0;
			for(Bala b : ControladorBalas.balasActivas) {
				b.update(cantBalas);
				cantBalas++;
			}
			
			if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				Gdx.app.exit();
			}
			
			if(fin) terminarNivel();
			
		}
	
	
	public void update(float delta) {
		chequearFinNivel();
		
		jugadoresMuertos = 0;
		tiempoEntreSpawn += delta;
		tiempoTranscurrido += delta;
		mapa.update(delta, cam);
	}
	
	protected abstract void spawnPickup();
	
	public void resize(int width, int height) {
		vp = new FitViewport(width, height);
	}
	
	public void dispose() {
		borrarCuerpos();
		Utiles.jugadores.clear();
		ControladorBodies.world.dispose();
		b2r.dispose();
		mapa.dispose();
		Render.spritesADibujar.clear();
	}

	
	public void chequearFinNivel() {
		if(jugadoresMuertos >= (Render.app.getSv().getClientes().size()-1) && !fin || tiempoTranscurrido > tiempoTotal && !fin) {
			System.out.println(jugadoresMuertos + " " + tiempoTranscurrido);
			System.out.println("SE terminó el nivel");
			fin = true;
		}
	}
	
	
	public void terminarNivel() {
		if(fin)	Render.app.setScreen(Render.app.pasarNivel());
	}
	
	protected void borrarCuerpos() {
		if(!ControladorBodies.world.isLocked()){
			for(int i = 0; i < ControladorBodies.cuerposAEliminar.size(); i++) {
				ControladorBodies.world.destroyBody(ControladorBodies.cuerposAEliminar.get(i));
				ControladorBodies.cuerposAEliminar.remove(i);
			}
		}
	}

}

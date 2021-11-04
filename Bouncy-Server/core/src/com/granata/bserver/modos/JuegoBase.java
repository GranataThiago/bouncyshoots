package com.granata.bserver.modos;

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

	// Box2D
	private Box2DDebugRenderer b2r;
	

	// Camara
	protected OrthographicCamera cam;
	private Viewport vp;
	
	// Mapa
	private MapaTiled mapa;
	protected Vector2[] spawners;
	
	// Cosas del nivel en si
	protected float tiempoEntreSpawn = 0f;
	
	public void start(String rutaMapa, Vector2[] spawners) {
		this.spawners = spawners;
		
		cam = new OrthographicCamera();
		vp = new FitViewport(Config.ANCHO / Config.PPM, Config.ALTO / Config.PPM);
		
		// Creaci�n del mundo necesario para Box2D
		ControladorBodies.world = new World(new Vector2(0, -16.42f), false);
		ControladorBodies.world.setContactListener(new CollisionListener());

		b2r = new Box2DDebugRenderer();
		for(Cliente c : Render.app.getSv().getClientes()) {
			c.crearPersonaje(cam);
		}

		mapa = new MapaTiled(rutaMapa);
		cam.setToOrtho(false, vp.getWorldWidth(), vp.getWorldHeight());

	}
	
	public void render(float delta) {

			update(delta);
			Render.limpiarPantallaN();
			
			// Renderiza el mapa
			mapa.render();
			Render.sb.setProjectionMatrix(cam.combined);

			// Como "pasa" el tiempo y el render del Box2D
			// Como primer argumento funcionaron "bien" Gdx.graphics.getDeltaTime(); o 1/60f
			ControladorBodies.world.step(Gdx.graphics.getDeltaTime(), 6, 2);
			borrarCuerpos();
			b2r.render(ControladorBodies.world, cam.combined);

			// Dibujamos al personaje y actualizamos la c�mara
			int cantVivos = 0;
			Render.sb.begin();
				for(Cliente c : Render.app.getSv().getClientes()) {
					if(c.getPj().getEstadoActual() != Estado.MUERTO) {
						c.getPj().update(delta);
						cantVivos++;
					}
				}
				Render.dibujarSprites();
			Render.sb.end();
			
			if(cantVivos <= 1) {
				Render.app.setScreen(Render.app.pasarNivel());
			}
			
			int cantBalas = 0;
			for(Bala b : ControladorBalas.balasActivas) {
				b.update(cantBalas);
				cantBalas++;
			}
			
			if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				Gdx.app.exit();
			}
		}
	
	
	public void update(float delta) {
		tiempoEntreSpawn += delta;
		
		mapa.update(delta, cam);
	}
	
	protected abstract void spawnPickup();
	
	public void resize(int width, int height) {
		vp = new FitViewport(width, height);
	}
	
	public void dispose() {
		borrarCuerpos();
		Utiles.jugadores.removeAll(Utiles.jugadores);
		ControladorBodies.world.dispose();
		b2r.dispose();
		mapa.dispose();
		Render.spritesADibujar.removeAll(Render.spritesADibujar);
//		p.getArma().dispose();
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

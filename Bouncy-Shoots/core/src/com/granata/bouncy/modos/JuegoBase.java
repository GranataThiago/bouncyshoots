package com.granata.bouncy.modos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.granata.bouncy.elementos.Bala;
import com.granata.bouncy.elementos.Personaje;
import com.granata.bouncy.elementos.Personaje.Estado;
import com.granata.bouncy.elementos.Personajes;
import com.granata.bouncy.managers.CollisionListener;
import com.granata.bouncy.managers.ControladorBalas;
import com.granata.bouncy.managers.ControladorBodies;
import com.granata.bouncy.mapas.MapaTiled;
import com.granata.bouncy.screens.ScreenJuego;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Render;
import com.granata.bouncy.utiles.Utiles;

public abstract class JuegoBase {

	// Box2D
	protected World world;
	private Box2DDebugRenderer b2r;
	
	// Personaje
	protected Personaje p;

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
		
		// Creación del mundo necesario para Box2D
		world = new World(new Vector2(0, -16.42f), false);
		world.setContactListener(new CollisionListener());

		b2r = new Box2DDebugRenderer();
		p = new Personaje(Personajes.getPersonajeAleatorio(), world, cam);

		mapa = new MapaTiled(rutaMapa, world);
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
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		borrarCuerpos();
		b2r.render(world, cam.combined);

		// Dibujamos al personaje y actualizamos la cámara
		Render.sb.begin();
		p.update(delta);
		Render.dibujarSprites();
		Render.sb.end();
		
		if(p.getEstadoActual() == Estado.MUERTO) {
			Render.app.setScreen(new ScreenJuego(Render.app.cambiarMapa()));
		}
		
		// COSAS PARA DEBUGEAR RAPIDITO JEJE
//		if(Gdx.input.isTouched()) {
//			System.out.println(new Vector2(Gdx.input.getX(), Config.ALTO - Gdx.input.getY()));
//		}
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
		
		for(Bala b : ControladorBalas.balasActivas) {
			b.update();
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
		world.dispose();
		b2r.dispose();
		mapa.dispose();
		Render.spritesADibujar.removeAll(Render.spritesADibujar);
		p.getArma().dispose();
	}
	
	protected void borrarCuerpos() {
		if(!world.isLocked()){
			for(int i = 0; i < ControladorBodies.cuerposAEliminar.size(); i++) {
				world.destroyBody(ControladorBodies.cuerposAEliminar.get(i));
				ControladorBodies.cuerposAEliminar.remove(i);
			}
		}
	}

}

package com.granata.bserver.modos;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.granata.bserver.elementos.Bala;
import com.granata.bserver.elementos.Personaje.Estado;
import com.granata.bserver.managers.CollisionListener;
import com.granata.bserver.managers.ControladorBalas;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.mapas.MapaTiled;
import com.granata.bserver.red.Cliente;
import com.granata.bserver.utiles.Config;
import com.granata.bserver.utiles.Render;
import com.granata.bserver.utiles.Utiles;

public abstract class JuegoBase {

	// Condiciones de final
	// <= 1 persona viva / -> en todos
	// Alguien llegó a la meta / -> en carrera
	// Pasó 1 minuto / -> en colina o estatua
	
	private FreeTypeFontGenerator generador;
	private FreeTypeFontParameter parametros;
	private BitmapFont fuente = new BitmapFont();
	
	// Box2D
	private Box2DDebugRenderer b2r;
	
	// Camara
	protected OrthographicCamera cam;
	private Viewport vp;
	
	// Mapa
	protected MapaTiled mapa;
	protected ArrayList<Vector2> spawners = new ArrayList<Vector2>();
//	protected boolean fin = false, empezo = false;
	protected boolean empezo = false;
	
	// Cosas del nivel en si
	protected float tiempoEntreSpawn = 0f, tiempoParaEmpezar = 3f, contador = 0f; 
	private float tiempoTotal = 70f, tiempoTranscurrido = 0, tiempoParaMorir = 0f;
	protected int jugadoresMuertos = 0, cantPu = 0;
	

	
	public void start(String rutaMapa) {	

		generador = new FreeTypeFontGenerator(Gdx.files.internal("fuentes/Acme-Regular.ttf"));
		parametros = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parametros.shadowColor = Color.BLACK;
		fuente = generador.generateFont(parametros);
		
		cam = new OrthographicCamera();
		vp = new FitViewport(Config.ANCHO / Config.PPM, Config.ALTO / Config.PPM);
		
		// Creación del mundo necesario para Box2D
		ControladorBodies.world = new World(new Vector2(0, -16.42f), false);
		ControladorBodies.world.setContactListener(new CollisionListener());

		mapa = new MapaTiled(rutaMapa);
		if(mapa.getSpawners() != null ) spawners = mapa.getSpawners();

		
		b2r = new Box2DDebugRenderer();
		for(int i = 0; i < Render.app.getSv().getClientes().size(); i++) {
			Render.app.getSv().getClientes().get(i)
				.crearPersonaje(cam, mapa.getSpawnpoints().get(i));
		}

		cam.setToOrtho(false, vp.getWorldWidth(), vp.getWorldHeight());

	}
	
	public void render(float delta) {
			Render.limpiarPantallaN();

			if(contador > tiempoParaEmpezar && !Utiles.fin) {
				if (!empezo) {
					System.out.println("Arrancó el juego en el server");
					empezo = true;
					Render.app.getSv().getHs().enviarMensajeGeneral("terminoContador");
				}
				update(delta);
				
				mapa.render();
				Render.sb.setProjectionMatrix(cam.combined);

				ControladorBodies.world.step(Gdx.graphics.getDeltaTime(), 6, 2);
				borrarCuerpos();

				Render.sb.begin();
					for(Cliente c : Render.app.getSv().getClientes()) {
						if(c.getPj().getEstadoActual() == Estado.MUERTO) {
							jugadoresMuertos++;
						}else {
							if(!Utiles.fin) {
								c.getPj().update(delta);
							}
						}
					}
					Render.dibujarSprites();
				Render.sb.end();
				

				
				if(Utiles.fin) terminarNivel();
			}else {
				dibujarContador();
				contador += delta;
			}

			
		}
	
	private void dibujarContador() {
		Render.sb.begin();
			fuente.draw(Render.sb, Float.toString(contador), Config.ANCHO / 2, Config.ALTO / 2);
		Render.sb.end();
	};
	
	public void update(float delta) {
		chequearFinNivel();
		actualizarBalas();
		verificarSiEnCamara(delta);
		
		jugadoresMuertos = 0;
		tiempoEntreSpawn += delta;
		tiempoTranscurrido += delta;
		mapa.update(delta, cam);
	}
	
	private void verificarSiEnCamara(float delta) {
		for(Cliente c : Render.app.getSv().getClientes()) {
			if(c.getPj().chequearFueraDeCamara(cam)) {
				tiempoParaMorir += delta;
				if(tiempoParaMorir > 3f) {
					c.getPj().destruir();
					tiempoParaMorir = 0;
				}
			};
		}
		
	}

	private void actualizarBalas() {
		int cantBalas = 0;
		for(Bala b : ControladorBalas.balasActivas) {
			b.update(cantBalas);
			cantBalas++;
		}
		
	}
	
	protected void sacarBalas() {
		for(Cliente c : Render.app.getSv().getClientes()) {
			c.getPj().getArma().setBalas(0);
		}
	}
	
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

	protected abstract void spawnPickup();

	
	public void chequearFinNivel() {
		if(jugadoresMuertos >= (Render.app.getSv().getClientes().size()-1) && !Utiles.fin || tiempoTranscurrido > tiempoTotal && !Utiles.fin) {
			System.out.println(jugadoresMuertos + " " + tiempoTranscurrido);
			System.out.println("SE terminó el nivel");
			Utiles.fin = true;
		}
	}
	
	
	public void terminarNivel() {
		Timer terminar = new Timer();
		if(Utiles.fin) {
			terminar.scheduleTask(new Task(){
			    @Override
			    public void run() {
					Render.app.setScreen(Render.app.pasarNivel());
			    }
			}, 3);

		}
	}
	
	protected void borrarCuerpos() {
		if(!ControladorBodies.world.isLocked()){
			for(int i = 0; i < ControladorBodies.cuerposAEliminar.size(); i++) {
				System.out.println("cuerpo a eliminar: " + ControladorBodies.cuerposAEliminar.get(i));
				ControladorBodies.world.destroyBody(ControladorBodies.cuerposAEliminar.get(i));
				ControladorBodies.cuerposAEliminar.remove(i);
			}
		}
	}

}

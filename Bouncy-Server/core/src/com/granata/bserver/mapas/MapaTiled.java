package com.granata.bserver.mapas;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.utiles.Config;

public class MapaTiled{

	private String nombre;
	private TiledMap lvl;
	private OrthogonalTiledMapRenderer renderer;
	private ArrayList<Vector2> spawners;
	private ArrayList<Vector2> spawnpoints;
	
	public MapaTiled(String mapa) {
		this.nombre = mapa;
		lvl = new TmxMapLoader().load(mapa);
		renderer = new OrthogonalTiledMapRenderer(lvl, 1 / Config.PPM);
		crearColisiones(ControladorBodies.world);
	}
	
	private void crearColisiones(World world) {

		for(MapObject object : lvl.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			ControladorBodies.crearCaja((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2), rect.getWidth(), rect.getHeight(), BodyType.StaticBody, this.getClass());
		}
		
		// Si el mapa tiene un final, osea, es del modo carrera u otros
		if(lvl.getLayers().get("Final") != null) {
			for(MapObject object : lvl.getLayers().get("Final").getObjects().getByType(RectangleMapObject.class)) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				new BloqueFinal(rect);
			}
		}
		
		// Si el mapa tiene Powerups
		if(lvl.getLayers().get("Pickups") != null) {
			spawners = new ArrayList<Vector2>();
			System.out.println("Hay Pickups");
			for(MapObject object : lvl.getLayers().get("Pickups").getObjects().getByType(RectangleMapObject.class)) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				spawners.add(new Vector2((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2)));
			}
		}

		// Si el mapa tiene spawnpoints, se supone que siempre los va a tener.
		if(lvl.getLayers().get("Spawnpoint") != null) {
			System.out.println("hay spawnpoints");
			spawnpoints = new ArrayList<Vector2>();
			for(MapObject object : lvl.getLayers().get("Spawnpoint").getObjects().getByType(RectangleMapObject.class)) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				spawnpoints.add(new Vector2((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2)));
			}
		}
		
		if(lvl.getLayers().get("Colina") != null) {
			System.out.println("hay colina");
			for(MapObject object : lvl.getLayers().get("Colina").getObjects().getByType(RectangleMapObject.class)) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				new BloqueColina(rect);
			}
		}
		
	}

	public void render() {
		renderer.render();
	}

	public void update(float dt, OrthographicCamera cam) {
		renderer.setView(cam);
	}
	
	public ArrayList<Vector2> getSpawners() {
		return this.spawners;
	};

	public String getNombre() {
		return this.nombre;
	}
	
	public ArrayList<Vector2> getSpawnpoints() {
		return this.spawnpoints;
	};

	public void dispose() {
		lvl.dispose();
		renderer.dispose();
	}

	

}

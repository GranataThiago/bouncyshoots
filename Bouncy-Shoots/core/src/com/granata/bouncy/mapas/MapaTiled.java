package com.granata.bouncy.mapas;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.granata.bouncy.managers.ControladorBodies;
import com.granata.bouncy.utiles.Config;

public class MapaTiled{

	private TiledMap lvl;
	private OrthogonalTiledMapRenderer renderer;
	private ArrayList<Body> bodiesMapa = new ArrayList<Body>();
	
	public MapaTiled(String mapa, World world) {
		lvl = new TmxMapLoader().load(mapa);
		renderer = new OrthogonalTiledMapRenderer(lvl, 1 / Config.PPM);
		crearColisiones(world);
	}
	
	private void crearColisiones(World world) {
		
		for(MapObject object : lvl.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			ControladorBodies.crearCaja(world, (rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2), rect.getWidth(), rect.getHeight(), BodyType.StaticBody, this.getClass());
		}
		
	}

	public void render() {
		renderer.render();
	}

	public void update(float dt, OrthographicCamera cam) {
		renderer.setView(cam);
	}

	public void dispose() {
		lvl.dispose();
		renderer.dispose();
//		ControladorBodies.cuerposAEliminar.add(bodiesMapa.get(0));
		System.out.println(ControladorBodies.cuerposAEliminar.size());
	}

	

}

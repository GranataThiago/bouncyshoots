package com.granata.bserver.mapas;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.utiles.Config;

public class MapaTiled{

	private TiledMap lvl;
	private OrthogonalTiledMapRenderer renderer;

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
	}

	

}

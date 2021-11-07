package com.granata.bouncy.mapas;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.granata.bouncy.utiles.Config;

public class MapaTiled{

	private TiledMap lvl;
	private OrthogonalTiledMapRenderer renderer;
	private ArrayList<Vector2> spawners;
	
	public MapaTiled(String mapa) {
		lvl = new TmxMapLoader().load(mapa);
		renderer = new OrthogonalTiledMapRenderer(lvl, 1 / Config.PPM);
		asignarCoordsPickups();
	}
	
	private void asignarCoordsPickups() {
		
		if(lvl.getLayers().get("Pickups") != null) {
			spawners = new ArrayList<Vector2>();
			System.out.println("Hay Pickups");
			for(MapObject object : lvl.getLayers().get("Pickups").getObjects().getByType(RectangleMapObject.class)) {
				Rectangle rect = ((RectangleMapObject) object).getRectangle();
				spawners.add(new Vector2((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2)));
			}
		}
		
	}

	public void render() {
		renderer.render();
	}

	public ArrayList<Vector2> getSpawners() {
		return this.spawners;
	};
	
	public void update(float dt, OrthographicCamera cam) {
		renderer.setView(cam);
	}

	public void dispose() {
		lvl.dispose();
		renderer.dispose();
	}

	

}

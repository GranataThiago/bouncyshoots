package com.granata.bouncy.mapas;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.granata.bouncy.utiles.Config;

public class MapaTiled{

	private TiledMap lvl;
	private OrthogonalTiledMapRenderer renderer;
	
	public MapaTiled(String mapa) {
		lvl = new TmxMapLoader().load(mapa);
		renderer = new OrthogonalTiledMapRenderer(lvl, 1 / Config.PPM);
//		crearFormas();
	}
	
	private void crearFormas() {
		
//		for(MapObject object : lvl.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
//			Rectangle rect = ((RectangleMapObject) object).getRectangle();
////			ControladorBodies.crearCaja((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2), rect.getWidth(), rect.getHeight(), BodyType.StaticBody, this.getClass());
//		}
		
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

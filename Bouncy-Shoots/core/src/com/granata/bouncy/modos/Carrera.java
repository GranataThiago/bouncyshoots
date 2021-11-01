package com.granata.bouncy.modos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.granata.bouncy.managers.ControladorBodies;
import com.granata.bouncy.powerups.OneShot;
import com.granata.bouncy.powerups.Powerup;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Render;

public class Carrera extends JuegoBase{
	
	private float puntoFinal = 28f;
	private float velCamara = 4f, tiempoParaMorir = 0f, tiempoParaEmpezar = 0f;
	
	@Override
	public void start(String rutaMapa, Vector2[] spawners) {
		super.start(rutaMapa, spawners);
		p.getArma().setBalas(0);
		spawnPickup();
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);

		// A -------------- B (Final)
		if(tiempoParaEmpezar > 15f && Global.cam.position.x <= puntoFinal) Global.cam.position.x += velCamara * delta;
		else {
			tiempoParaEmpezar++;
		}
		
//		if(p.getPosition().x > puntoFinal + 1f) {
//			System.out.println("Ganaste");
//		}
		
		Global.cam.update();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		Render.sb.setProjectionMatrix(Global.cam.combined);
//		if(!this.cam.frustum.pointInFrustum(p.getPosition().x, p.getPosition().y, 0) && p.getEstadoActual() != Estado.MUERTO) {
//			
//			tiempoParaMorir += delta;
//			if(tiempoParaMorir > 0.5f) {
//				p.destruir();
//			}
//			
//		}else tiempoParaMorir = 0f;
	}

	@Override
	protected void spawnPickup() {
		
		for(Vector2 coords : spawners) {
			Powerup p = new OneShot();
			Body item = ControladorBodies.crearPickup(coords.x, coords.y, 32, 32, BodyType.StaticBody, null);
			item.setUserData(p);
			Render.spritesADibujar.add(p.getSprite());
			p.getSprite().setPosition((coords.x / Config.PPM) - (p.getSprite().getWidth() / 2), (coords.y / Config.PPM)  - (p.getSprite().getHeight() / 2));
		}
		
	}

}

package com.granata.bserver.modos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.granata.bserver.elementos.Personaje.Estado;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.managers.ControladorMundo;
import com.granata.bserver.powerups.OneShot;
import com.granata.bserver.powerups.Pickupable;
import com.granata.bserver.powerups.Powerup;
import com.granata.bserver.red.Cliente;
import com.granata.bserver.utiles.Config;
import com.granata.bserver.utiles.Render;
import com.granata.bserver.utiles.Utiles;

public class Carrera extends JuegoBase implements CarreraEventListener{
	
	private float puntoFinal = 28f;
	private float velCamara = 3f;
	
	@Override
	public void start(String rutaMapa) {
		super.start(rutaMapa);
		Utiles.cListener = this;
		for(Cliente c : Render.app.getSv().getClientes()) {
			c.getPj().getArma().setBalas(0);
		}
		spawnPickup();
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);

		// A -------------- B (Final)
		if(this.cam.position.x <= puntoFinal) {
			this.cam.position.x += velCamara * delta;
			Render.app.getSv().getHs().enviarMensajeGeneral("MoverCamaraX!" + this.cam.position.x);
		}

		this.cam.update();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		Render.sb.setProjectionMatrix(cam.combined);

	}

	@Override
	protected void spawnPickup() {
		
		for(Vector2 coords : spawners) {
			Powerup p = new OneShot(cantPu);
			Body item = ControladorBodies.crearPickup(coords.x, coords.y, 32, 32, BodyType.StaticBody, null);
			Render.app.getSv().getHs().enviarMensajeGeneral("SpawnPowerup!" + cantPu++);
			item.setUserData(p);
			Render.spritesADibujar.add(p.getSprite());
			p.getSprite().setPosition((coords.x / Config.PPM) - (p.getSprite().getWidth() / 2), (coords.y / Config.PPM)  - (p.getSprite().getHeight() / 2));
		}
		
	}

	@Override
	public void onTerminar() {
		Utiles.fin = true;
	}
	

}

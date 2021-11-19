package com.granata.bserver.modos;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.granata.bserver.elementos.Bala;
import com.granata.bserver.elementos.Personaje.Estado;
import com.granata.bserver.managers.ControladorBalas;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.managers.ControladorMundo;
import com.granata.bserver.powerups.Pickupable;
import com.granata.bserver.powerups.Powerup;
import com.granata.bserver.powerups.Powerups;
import com.granata.bserver.utiles.Config;
import com.granata.bserver.utiles.Render;
import com.granata.bserver.utiles.Utiles;

public class Combate extends JuegoBase{

	private int cantPu = 0;
	
	@Override
	public void start(String rutaMapa) {
		super.start(rutaMapa);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if(tiempoEntreSpawn > 2f && ((comprobarEspaciosVacios() == -1) ? false : true) && !Utiles.fin) {
			tiempoEntreSpawn = 0f;
			spawnPickup();
		}
		

	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
	}


	
	@Override
	protected void spawnPickup() {
		int posicion = comprobarEspaciosVacios();
		
		int nroPowerup = Utiles.r.nextInt(Powerups.values().length);
		Powerup p = Powerups.values()[nroPowerup].getPowerup(posicion);
				
		Render.app.getSv().getHs().enviarMensajeGeneral("SpawnPowerup!" + nroPowerup + "!" + posicion);
		
		Vector2 coords = spawners.get(posicion);
		ocupado[posicion] = p;
		
		Body pu = ControladorBodies.crearPickup(coords.x, coords.y, 32, 32, BodyType.StaticBody, null);
		pu.setUserData(p);
		
		Render.spritesADibujar.add(p.getSprite());
		p.getSprite().setPosition((coords.x / Config.PPM) - (p.getSprite().getWidth() / 2), (coords.y / Config.PPM)  - (p.getSprite().getHeight() / 2));

	}
//	
//	@Override
//	protected void borrarCuerpos() {
//		if(!ControladorBodies.world.isLocked()){
//			for(int i = 0; i < ControladorBodies.cuerposAEliminar.size(); i++) {
//				System.out.println("cuerpo a eliminar: " + ControladorBodies.cuerposAEliminar.get(i));
//
//				ControladorBodies.world.destroyBody(ControladorBodies.cuerposAEliminar.get(i));
//				ControladorBodies.cuerposAEliminar.remove(i);
//			}
//		}
//	}
	
	
}

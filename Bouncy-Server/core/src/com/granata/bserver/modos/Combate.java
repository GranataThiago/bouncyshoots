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

	private boolean[] ocupado;
	private int cantPu = 0;
	
	@Override
	public void start(String rutaMapa, Vector2[] spawners) {
		super.start(rutaMapa, spawners);
		ocupado = new boolean[spawners.length];
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if(tiempoEntreSpawn > 0.75f && ((comprobarEspaciosVacios() == -1) ? false : true)) {
			System.out.println(cantPu++);
			tiempoEntreSpawn = 0f;
			spawnPickup();
		}
		

	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
	}
	
	private int comprobarEspaciosVacios() {
		boolean libre = false;
		int i = 0;
		int posicion = -1;
		
		do {
			if(ocupado[i] == false) {
				libre = true;
				posicion = i;
			}
			i++;
		}while(!libre && i < ocupado.length);
		
		return posicion;
	}
	
	private int comprobarEspaciosOcupados(Vector2 pos) {
		boolean eOcupado = false;
		int i = 0;
		int posicion = 0;
		
		do {
			if(Math.round(pos.x * Config.PPM) == spawners[i].x && Math.round(pos.y * Config.PPM) == spawners[i].y) {
				eOcupado = true;
				posicion = i;
			}
			i++;
		}while(!eOcupado && i < spawners.length);
		
		return posicion;
	}
	
	@Override
	protected void spawnPickup() {
		int posicion = comprobarEspaciosVacios();
		
		int nroPowerup = Utiles.r.nextInt(Powerups.values().length);
		Powerup p = Powerups.values()[nroPowerup].getPowerup();
				
		Render.app.getSv().getHs().enviarMensajeGeneral("SpawnPowerup!" + nroPowerup + "!" + posicion);
		
		Vector2 coords = spawners[posicion];
		ocupado[posicion] = true;
		
		Body pu = ControladorBodies.crearPickup(coords.x, coords.y, 32, 32, BodyType.StaticBody, null);
		pu.setUserData(p);
		
		Render.spritesADibujar.add(p.getSprite());
		p.getSprite().setPosition((coords.x / Config.PPM) - (p.getSprite().getWidth() / 2), (coords.y / Config.PPM)  - (p.getSprite().getHeight() / 2));

	}
	
	@Override
	protected void borrarCuerpos() {
		if(!ControladorBodies.world.isLocked()){
			for(int i = 0; i < ControladorBodies.cuerposAEliminar.size(); i++) {

				if(Pickupable.class.isAssignableFrom(ControladorBodies.cuerposAEliminar.get(i).getUserData().getClass())) {
					int pos = comprobarEspaciosOcupados(ControladorBodies.cuerposAEliminar.get(i).getPosition());
					Render.app.getSv().getHs().enviarMensajeGeneral("BorrarPowerup!" + pos);
					ocupado[pos] = false;
				}
				ControladorBodies.world.destroyBody(ControladorBodies.cuerposAEliminar.get(i));
				ControladorBodies.cuerposAEliminar.remove(i);
			}
		}
	}
	
	
}

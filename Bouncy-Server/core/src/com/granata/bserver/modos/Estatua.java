package com.granata.bserver.modos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.powerups.Bullet;
import com.granata.bserver.powerups.Powerup;
import com.granata.bserver.powerups.Powerups;
import com.granata.bserver.red.Cliente;
import com.granata.bserver.utiles.Config;
import com.granata.bserver.utiles.Render;
import com.granata.bserver.utiles.Utiles;

public class Estatua extends JuegoBase{
	
	private enum Modos { ESTATUA, MOVIMIENTO } 
	private Modos estadoActual = Modos.MOVIMIENTO;
	private float tiempoEntreCambio = Utiles.r.nextInt(5)+5;
	private float tiempoAcumulado = 0;
		
	private Sprite luzRoja = new Sprite(new Texture("images/luzRoja.png"));
	private Sprite luzVerde = new Sprite(new Texture("images/luzVerde.png"));

	@Override
	public void start(String rutaMapa) {
		System.out.println("Iniciando Estatua...");
		super.start(rutaMapa);
		sacarBalas();
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
				

		
		for(Cliente c : Render.app.getSv().getClientes()) {
			if(c.getPj().getBody().getLinearVelocity().x != 0f) {
				if(estadoActual == Modos.ESTATUA) c.getPj().destruir();
				else c.setScore(10);
			}
		}
	
		
		manejarEstados(delta);

		if(tiempoEntreSpawn > 1f && ((comprobarEspaciosVacios() == -1) ? false : true) && !Utiles.fin) {
			tiempoEntreSpawn = 0f;
			spawnPickup();
		}
		

		
	}
	
	private void manejarEstados(float delta) {
		tiempoAcumulado += delta;

		if(tiempoAcumulado > tiempoEntreCambio) {
			estadoActual = (estadoActual == Modos.MOVIMIENTO) ? Modos.ESTATUA : Modos.MOVIMIENTO;
			Render.app.getSv().getHs().enviarMensajeGeneral("CambiarEstado!" + estadoActual);
			tiempoAcumulado = 0;
		}
		
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		Render.sb.begin();
			if(estadoActual == Modos.ESTATUA) {
				luzRoja.setAlpha(1f);
				luzRoja.draw(Render.sb);
			}else {
				luzVerde.setAlpha(1f);
				luzVerde.draw(Render.sb);
			}
		Render.sb.end();

	}

	

	@Override
	protected void spawnPickup() {
		int posicion = comprobarEspaciosVacios();
		
		Powerup p = new Bullet(posicion);
				
		Render.app.getSv().getHs().enviarMensajeGeneral("SpawnPowerup!" + posicion);
		
		Vector2 coords = spawners.get(posicion);
		ocupado[posicion] = p;
		
		Body pu = ControladorBodies.crearPickup(coords.x, coords.y, 32, 32, BodyType.StaticBody, null);
		pu.setUserData(p);
		
		Render.spritesADibujar.add(p.getSprite());
		p.getSprite().setPosition((coords.x / Config.PPM) - (p.getSprite().getWidth() / 2), (coords.y / Config.PPM)  - (p.getSprite().getHeight() / 2));
		
	}

}

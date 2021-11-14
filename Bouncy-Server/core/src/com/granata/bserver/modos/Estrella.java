package com.granata.bserver.modos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.granata.bserver.utiles.Config;
import com.granata.bserver.utiles.Render;
import com.granata.bserver.utiles.Utiles;

public class Estrella extends JuegoBase implements EstrellaEventListener{
	
	private Sprite estrella;
	private int estrellado;
	private Vector2 posEstrellado;
	private float tiempoPasado = 0, tiempoAumentoScore = 0.5f, aumentoScore = 20;
	
	@Override
	public void start(String rutaMapa) {
		super.start(rutaMapa);
		Utiles.eListener = this;
		estrellado = Utiles.r.nextInt(Render.app.getSv().getClientes().size());
		Render.app.getSv().getClientes().get(estrellado).getPj().asignarCorona();
		estrella = new Sprite(Render.estrella);
		estrella.setSize(32 / Config.PPM, 32 / Config.PPM);
		Render.spritesADibujar.add(this.estrella);
//		ocupado = new boolean[spawners.size()];
		Render.app.getSv().getHs().enviarMensajeGeneral("AsignarEstrella!" + estrellado);
		sacarBalas();
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
//		if(tiempoEntreSpawn > 1f && ((comprobarEspaciosVacios() == -1) ? false : true) && !fin) {
//			tiempoEntreSpawn = 0f;
//			spawnPickup();
//		}
		
		tiempoPasado += delta;
		if(tiempoPasado > tiempoAumentoScore) {
			Render.app.getSv().getClientes().get(estrellado).setScore(aumentoScore);
			tiempoPasado = 0;
			System.out.println("Aumentó score");
		}

	}
	
	@Override
	public void render(float delta) {
		super.render(delta);

		if(seEstaMoviendo(estrellado)) {
			posEstrellado = Render.app.getSv().getClientes().get(estrellado).getPj().getPosition();
			estrella.setPosition(posEstrellado.x, posEstrellado.y);
			Render.app.getSv().getHs().enviarMensajeGeneral("MoverEstrella!" + posEstrellado.x + "!" + posEstrellado.y);
		}
	}
	

	
	
	private boolean seEstaMoviendo(int jugador) {
		Vector2 velocidad = Render.app.getSv().getClientes().get(estrellado).getPj().getBody().getLinearVelocity();
		if(velocidad.x != 0 || velocidad.y != 0) return true;
		return false;
	}

	@Override
	protected void spawnPickup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cambiarEstrellado(int id) {
		estrellado = id;
	}

}

package com.granata.bserver.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.granata.bserver.io.KeyInput;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.utiles.Config;
import com.granata.bserver.utiles.Render;
import com.granata.bserver.utiles.Utiles;

public class Personaje{
	
	public enum Estado { QUIETO, CORRIENDO, MUERTO, SALTANDO, CAYENDO } 
	private Estado estadoActual = Estado.QUIETO;

	
	// Relativo al personaje 
	private Sprite sprite;
	private float vel = 8f, salto = 12f, vida = 100;
	private Arma arma;
	private boolean muerto;
	
	// Box2D
	private Body pj;
	
	// Inputs
	public KeyInput e = new KeyInput();
	
	public Personaje(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void inicializarPersonaje(OrthographicCamera cam) {
//		Gdx.input.setInputProcessor(e);
		pj = ControladorBodies.crearEsfera(64, 64, 24.14f, false, 0.5f, 1f);
		pj.setUserData(this);
		pj.setAngularDamping(0);
		pj.setLinearDamping(0);

		this.sprite.setSize(64 / Config.PPM, 64 / Config.PPM);
		Render.spritesADibujar.add(this.sprite);
		arma = new Arma(pj.getPosition(), cam);
	}
		
	public void update(float dt) {

		controlarMovimiento();
		comprobarEstados();
		disparar(dt);
		arma.dibujarArma();
		
	}

	private void controlarMovimiento() {

		if(Utiles.saltando && puedeSaltar()) {
			pj.applyLinearImpulse(new Vector2(0, salto), pj.getWorldCenter(), true);
//			enviarMensajeGeneral("ModificarPosicion!" + comando[2] + "!" + getPosition());
		}
		if(Utiles.derecha && pj.getLinearVelocity().x <= 2) {
			pj.applyLinearImpulse(new Vector2(vel, 0), pj.getWorldCenter(), true);
		}
		if(Utiles.izquierda && pj.getLinearVelocity().x >= -2) {
			pj.applyLinearImpulse(new Vector2(-vel, 0), pj.getWorldCenter(), true);
		}
		sprite.setPosition(pj.getPosition().x - (sprite.getWidth() / 2), pj.getPosition().y - ( sprite.getHeight() / 2f));
	}
	
	private void disparar(float dt) {

		if(Gdx.input.justTouched() && arma.getBalas() > 0) {
			arma.disparar(pj.getPosition());
		}
	}
	
	public void recibirDaño(float daño) {
		this.vida -= daño;
		if(this.vida <= 0) {
			destruir();
		}
	}
	
	public void destruir() {
		muerto = true;
		Render.spritesADibujar.remove(this.sprite);
		ControladorBodies.cuerposAEliminar.add(this.pj);
	}
	
	public Vector2 getPosition() {
		return pj.getPosition();
	}
	
	public Body getBody() {
		return pj;
	}
	
	public void aumentarVel(float vel) {
		this.vel += vel;
	}

	public void aumentarSalto(float salto) {
		this.salto += salto;
	}

	public void aumentarVida(float vida) {
		this.vida += vida;
	}

	public Arma getArma() {
		return arma;
	}
	
	public void setArma(Arma arma) {
		this.arma = arma;
	}


	private void comprobarEstados() {
		if(muerto) estadoActual = Estado.MUERTO;
		else if(pj.getLinearVelocity().y > 0) {
			estadoActual = Estado.SALTANDO;
		}else if(pj.getLinearVelocity().y < 0) {
			estadoActual = Estado.CAYENDO;
		}else if(pj.getLinearVelocity().x != 0) {
			estadoActual = Estado.CORRIENDO;
		}else {
			estadoActual = Estado.QUIETO;
		}
		
	}
	
	public boolean puedeSaltar() {
		return (getEstadoActual() != Estado.CAYENDO && getEstadoActual() != Estado.SALTANDO);
	}

	
	public Estado getEstadoActual() {
		return estadoActual;
	}

	
}

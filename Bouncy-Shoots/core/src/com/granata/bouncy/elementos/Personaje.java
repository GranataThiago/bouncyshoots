package com.granata.bouncy.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.granata.bouncy.io.KeyInput;
import com.granata.bouncy.managers.ControladorBodies;
import com.granata.bouncy.managers.JugadorEventListener;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Render;

public class Personaje implements JugadorEventListener{
	
	public enum Estado { QUIETO, CORRIENDO, MUERTO, SALTANDO, CAYENDO } 
	private Estado estadoActual = Estado.QUIETO;
	
	// Relativo al personaje 
	private Sprite sprite;
	private float vel = 8f, salto = 12f, vida = 100;
	private Arma arma;
	private boolean muerto;
	
	// Box2D
//	private Body pj;
	
	// Inputs
	public KeyInput e = new KeyInput();
	private boolean controlable = false;
	
	public Personaje(Sprite s, boolean controlable) {
		this.sprite = s;
		this.controlable = controlable;
	}
	
	public void inicializarPersonaje() {
		if(controlable) Gdx.input.setInputProcessor(e);
//		pj = ControladorBodies.crearEsfera(64, 64, 24.14f, false, 0.5f, 1f);
//		pj.setUserData(this);
//		pj.setAngularDamping(0);
//		pj.setLinearDamping(0);
		this.sprite.setSize(64 / Config.PPM, 64 / Config.PPM);
		Render.spritesADibujar.add(this.sprite);
		
		arma = new Arma();
	}
		
	public void update(float dt) {
		
		controlarMovimiento();
		arma.update(dt);
		
	}
	
	private void controlarMovimiento() {
		
//		if(Gdx.input.isKeyJustPressed(Keys.SPACE) && getEstadoActual() != Estado.CAYENDO) {
//			pj.applyLinearImpulse(new Vector2(0, salto), pj.getWorldCenter(), true);
//		}
		
		
//		// Adaptado para funcionar con InputProcessor
//		if(e.isJumping() && puedeSaltar()) {
////			pj.applyLinearImpulse(new Vector2(0, salto), pj.getWorldCenter(), true);
//		}
//		if(e.isRight() && pj.getLinearVelocity().x <= 2) {
////			pj.applyLinearImpulse(new Vector2(vel, 0), pj.getWorldCenter(), true);
//		}
//		if(e.isLeft() && pj.getLinearVelocity().x >= -2) {
////			pj.applyLinearImpulse(new Vector2(-vel, 0), pj.getWorldCenter(), true);
//		}
	}
	
	private void disparar() {

//		if(Gdx.input.justTouched() && arma.getBalas() > 0) {
//			arma.disparar(pj.getPosition());
//		}
		
		// Adaptado para funcionar con InputProcessor
//		if(e.isClicking() && arma.getBalas() > 0) {
//			arma.disparar(pj.getPosition());
//		}
	}
	
	public void recibirDa�o(float da�o) {
		this.vida -= da�o;
		if(this.vida <= 0) {
			destruir();
		}
	}

//	public Vector2 getPosition() {
//		return pj.getPosition();
//	}
//	
//	public Body getBody() {
//		return pj;
//	}


	public Arma getArma() {
		return arma;
	}


//	private void comprobarEstados() {
//		if(muerto) estadoActual = Estado.MUERTO;
//		else if(pj.getLinearVelocity().y > 0) {
//			estadoActual = Estado.SALTANDO;
//		}else if(pj.getLinearVelocity().y < 0) {
//			estadoActual = Estado.CAYENDO;
//		}else if(pj.getLinearVelocity().x != 0) {
//			estadoActual = Estado.CORRIENDO;
//		}else {
//			estadoActual = Estado.QUIETO;
//		}
//		
//	}
	
	public boolean puedeSaltar() {
		return (getEstadoActual() != Estado.CAYENDO && getEstadoActual() != Estado.SALTANDO);
	}

	
	public Estado getEstadoActual() {
		return estadoActual;
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
	
	public void destruir() {
		muerto = true;
		Render.spritesADibujar.remove(this.sprite);

}
	
	@Override
	public void actualizarPosicion(float x, float y) {
		sprite.setPosition(x, y);
	}

	@Override
	public void disparar(Vector2 posDisparo, Vector3 target) {
		if(arma.getBalas() > 0) {
			arma.disparar(posDisparo, target);
		}
	}

	


	
}

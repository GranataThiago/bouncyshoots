package com.granata.bouncy.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.granata.bouncy.io.KeyInput;
import com.granata.bouncy.managers.ControladorBodies;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Render;

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
	
	public Personaje() {
		System.out.println("Se creó un nuevo Personaje");
	}
	
	public void inicializarPersonaje(Sprite sprite, OrthographicCamera cam) {
		Gdx.input.setInputProcessor(e);
		pj = ControladorBodies.crearEsfera(64, 64, 24.14f, false, 0.5f, 1f);
		pj.setUserData(this);
		pj.setAngularDamping(0);
		pj.setLinearDamping(0);
		this.sprite = sprite;
		this.sprite.setSize(64 / Config.PPM, 64 / Config.PPM);
		Render.spritesADibujar.add(this.sprite);
		
		arma = new Arma(pj.getPosition(), cam);
	}
		
	public void update(float dt) {

		controlarMovimiento();
		disparar(dt);
		comprobarEstados();
		arma.update(dt);
		
	}
	
	private void controlarMovimiento() {
		
//		if(Gdx.input.isKeyJustPressed(Keys.SPACE) && getEstadoActual() != Estado.CAYENDO) {
//		pj.applyLinearImpulse(new Vector2(0, salto), pj.getWorldCenter(), true);
//		}
		
		
		// Adaptado para funcionar con InputProcessor
		if(e.isJumping() && puedeSaltar()) {
			pj.applyLinearImpulse(new Vector2(0, salto), pj.getWorldCenter(), true);
		}
		if(e.isRight() && pj.getLinearVelocity().x <= 2) {
			pj.applyLinearImpulse(new Vector2(vel, 0), pj.getWorldCenter(), true);
		}
		if(e.isLeft() && pj.getLinearVelocity().x >= -2) {
			pj.applyLinearImpulse(new Vector2(-vel, 0), pj.getWorldCenter(), true);
		}
		sprite.setPosition(pj.getPosition().x - (sprite.getWidth() / 2), pj.getPosition().y - ( sprite.getHeight() / 2f));
	}
	
	private void disparar(float dt) {

//		if(Gdx.input.justTouched() && arma.getBalas() > 0) {
//			arma.disparar(pj.getPosition());
//		}
		
		// Adaptado para funcionar con InputProcessor
		if(e.isClicking() && arma.getBalas() > 0) {
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
	
//	public void setArma(Arma arma) {
//		this.arma = arma;
//	}


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

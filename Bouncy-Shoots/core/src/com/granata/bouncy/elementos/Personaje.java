package com.granata.bouncy.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.granata.bouncy.io.KeyInput;
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
		
		this.sprite.setSize(64 / Config.PPM, 64 / Config.PPM);
		Render.spritesADibujar.add(this.sprite);
		
		arma = new Arma();
	}
		
	public void update(float dt) {
		
		arma.update(dt);
		
	}

	
	public void recibirDaño(float daño) {
		this.vida -= daño;
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
	
//	public boolean puedeSaltar() {
//		return (getEstadoActual() != Estado.CAYENDO && getEstadoActual() != Estado.SALTANDO);
//	}
//
//	
//	public Estado getEstadoActual() {
//		return estadoActual;
//	}
//
//	
//	public void aumentarVel(float vel) {
//		this.vel += vel;
//	}
//
//	public void aumentarSalto(float salto) {
//		this.salto += salto;
//	}
//
//	public void aumentarVida(float vida) {
//		this.vida += vida;
//	}
	
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

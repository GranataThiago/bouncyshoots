package com.granata.bserver.elementos;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.granata.bserver.elementos.Personaje.Estado;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.managers.JugadorEventListener;
import com.granata.bserver.utiles.Config;
import com.granata.bserver.utiles.Render;

public class Personaje implements JugadorEventListener{
	
	public enum Estado { QUIETO, CORRIENDO, MUERTO, SALTANDO, CAYENDO } 
	private Estado estadoActual = Estado.QUIETO;

	
	// Relativo al personaje 
	private Sprite sprite;
	private float vel = 8f, salto = 12f, vida = 100;
	private Arma arma;
	private boolean muerto;
	private int idJugador;
	
	// Box2D
	private Body pj;

	// Movimiento
	private boolean mueveDerecha = false, mueveIzquierda = false;
	private boolean usaSalto = false, ejecutoDisparo = false, direccion = false;
	
	public Personaje(Sprite sprite, int id) {
		this.sprite = sprite;
		this.idJugador = id;
	}
	
	public void inicializarPersonaje(OrthographicCamera cam, Vector2 spawn) {
		pj = ControladorBodies.crearEsfera(spawn.x, spawn.y, 24.14f, false, 0.5f, 1f);
		pj.setUserData(this);
		pj.setAngularDamping(0);
		pj.setLinearDamping(0);

		this.sprite.setSize(64 / Config.PPM, 64 / Config.PPM);
		Render.spritesADibujar.add(this.sprite);
		arma = new Arma();
	}
		
	public void update(float dt) {

		controlarMovimiento();
		comprobarEstados();
		arma.update(dt);
		
		if((pj.getLinearVelocity().y != 0 || pj.getLinearVelocity().x != 0) && getEstadoActual() != Estado.MUERTO) {
			System.out.println("Se está moviendoooooo");
			sprite.setPosition(pj.getPosition().x - (sprite.getWidth() / 2), pj.getPosition().y - ( sprite.getHeight() / 2f));
			System.out.println("Se modificó la posición J" + idJugador);
			Render.app.getSv().getHs().enviarMensajeGeneral("ModificarPosicion!" + idJugador + "!" + (pj.getPosition().x - (sprite.getWidth() / 2)) + "!" + (pj.getPosition().y - ( sprite.getHeight() / 2f)));
		}

	}
	
	public boolean chequearFueraDeCamara(OrthographicCamera cam) {
		System.out.println("chequeando");
		if(!cam.frustum.pointInFrustum(getPosition().x, getPosition().y, 0) && getEstadoActual() != Estado.MUERTO) return true;	
		else return false;
	}

	private void controlarMovimiento() {
		if(mueveDerecha && pj.getLinearVelocity().x <= 2) {
			pj.applyLinearImpulse(new Vector2(vel, 0), pj.getWorldCenter(), true);
			flip(false);
		}else if(mueveIzquierda && pj.getLinearVelocity().x >= -2) {
			pj.applyLinearImpulse(new Vector2(-vel, 0), pj.getWorldCenter(), true);
			flip(true);
		}else if(usaSalto && puedeSaltar()) {
			pj.applyLinearImpulse(new Vector2(0, salto), pj.getWorldCenter(), true);
		}
	}
	
	public void flip (boolean x) {
		  boolean estadoFlip = false;
		  if (sprite.isFlipX() != x) {
			  estadoFlip = true;
		  }
		  sprite.flip(estadoFlip, false);
		  Render.app.getSv().getHs().enviarMensajeGeneral("ModificarDireccion!" + idJugador + "!" + estadoFlip);
		}
	
	private void disparar(Vector2 target) {
		if(ejecutoDisparo && arma.getBalas() > 0) {
			arma.disparar(pj.getPosition(), new Vector3(target.x, target.y, 0), idJugador);
		}
	}

	public void recibirDaño(float daño, int tirador) {
		this.vida -= daño;
		if(this.vida <= 0 && getEstadoActual() != Estado.MUERTO) {
			destruir(tirador);
		}
	}
	
	public void destruir(int tirador) {

		muerto = true;
		Render.app.getSv().getHs().enviarMensajeGeneral("BorrarJugador!" + idJugador);
		if(idJugador != tirador) {
			Render.app.getSv().getClientes().get(tirador).setScore(100);
		}else {
			Render.app.getSv().getClientes().get(tirador).setScore(-100);
		}

		Render.spritesADibujar.remove(this.sprite);
		ControladorBodies.cuerposAEliminar.add(getBody());

	}
	
	public void destruir() {

		muerto = true;
		Render.app.getSv().getHs().enviarMensajeGeneral("BorrarJugador!" + idJugador);
		Render.spritesADibujar.remove(this.sprite);
		ControladorBodies.cuerposAEliminar.add(getBody());

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
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getId() {
		return idJugador;
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

	@Override
	public void ejecutarMovimiento(String dir) {
		if(dir.equals("Derecha")) {
			mueveDerecha = true;
		}else if(dir.equals("Izquierda")) {
			mueveIzquierda = true;
		}else if(dir.equals("Salto")) {
			usaSalto = true;
		}
		
	}

	@Override
	public void dejarEjecutarMovimiento(String dir) {
		if(dir.equals("Derecha")) {
			mueveDerecha = false;
		}else if(dir.equals("Izquierda")) {
			mueveIzquierda = false;
		}else if(dir.equals("Salto")) {
			usaSalto = false;
		}
		
	}

	@Override
	public void ejecutarDisparo(Vector2 target) {
		ejecutoDisparo = true;
		disparar(target);
	}

	@Override
	public void dejarDisparar() {
		ejecutoDisparo = false;
	}


	
}

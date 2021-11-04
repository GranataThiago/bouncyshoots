package com.granata.bserver.elementos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.granata.bserver.managers.ControladorBalas;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.utiles.Config;
import com.granata.bserver.utiles.Render;

public class Bala extends Sprite implements Movible, Poolable{

	// Objetos del mundo
	private Body bala;

	// Movimiento de la bala
	private Vector2 direccion;

	// Configuración Balas
	private boolean destruido = false;
	private int nroBala;
	private float vel = 10f, daño, incDañoRebotes;
	private int rebotes, idTirador;
	private Sprite sprite;
	
	public Bala() {
//		this.world = world;
//		this.rebotes = rebotes;
//		this.incDañoRebotes = incDañoRebotes;
//		this.daño = daño;
//		crearBala(curPos, target);
		
		this.rebotes = 10;
		this.incDañoRebotes = 0;
		this.daño = 30;
	}
	
	public void crearBala(Vector2 curPos, Vector3 target, int rebotes, float incDañoRebotes, float daño, int nro, int idTirador) {
		sprite = new Sprite(Render.bala);
		sprite.setSize(16 / Config.PPM, 16 / Config.PPM);
		Render.spritesADibujar.add(sprite);
		
		this.nroBala = nro;
		this.rebotes = rebotes;
		this.incDañoRebotes = incDañoRebotes;
		this.daño = daño;
		this.idTirador = idTirador;
		
		bala = ControladorBodies.crearEsfera(curPos.x * Config.PPM, curPos.y  * Config.PPM, 6, false, 1, 0);
		bala.setUserData(this);
		bala.setAngularDamping(0);
		bala.setLinearDamping(0);
		bala.setGravityScale(0);
		
	    direccion = new Vector2(target.x, target.y);
	    direccion.sub(bala.getPosition());
	    direccion.nor();
	    
	    bala.setLinearVelocity(direccion.scl(vel));

	}
	
	public Vector2 getFuerzas() {
		return new Vector2(bala.getLinearVelocity());
	}
	
	public Vector2 getPosicion() {
		return bala.getPosition();
	}
	
	public Vector2 getLinearVelocity() {
		return bala.getLinearVelocity();
	}
	
	public float getDaño() {
		return this.daño;
	}
	
	public int getIdTirador() {
		return this.idTirador;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void update(int cantBalas) {
		if(!destruido) {
			sprite.setPosition(getPosicion().x - (sprite.getWidth() / 2), getPosicion().y - (sprite.getHeight() / 2));
			Render.app.getSv().getHs().enviarMensajeGeneral("ModificarPosBala!" + cantBalas + "!" + (getPosicion().x - (sprite.getWidth() / 2)) + "!" + (getPosicion().y - (sprite.getHeight() / 2)));
		}
	}
	
	public void rebotar() {
		this.rebotes--;
		this.daño += incDañoRebotes;
		if(this.rebotes <= 0) {
			destruir();
		}
	}

	public void destruir() {
		destruido = true;
		ControladorBodies.cuerposAEliminar.add(bala);
		Render.spritesADibujar.remove(sprite);
		Render.app.getSv().getHs().enviarMensajeGeneral("BorrarBala!" + nroBala);
	}
	
	@Override
	public void onDibujar() {
	    Render.spritesADibujar.add(sprite);
	}

	@Override
	public void reset() {
		this.direccion.set(0, 0);
		destruido = false;
	}
}

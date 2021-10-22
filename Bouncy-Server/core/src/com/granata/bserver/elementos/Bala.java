package com.granata.bserver.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.granata.bserver.managers.ControladorBodies;
import com.granata.bserver.utiles.Config;
import com.granata.bserver.utiles.Render;

public class Bala extends Sprite implements Movible, Poolable{

	// Objetos del mundo
	private Body bala;

	// Movimiento de la bala
	Vector2 direccion;

	// Configuraci�n Balas
	private float vel = 10f, da�o, incDa�oRebotes;
	private int rebotes;
	private Sprite sprite = new Sprite(new Texture("Sin-t�tulo-2.png"));
	
	public Bala(World world, Vector2 curPos, Vector3 target, int rebotes, float incDa�oRebotes, float da�o) {
//		this.world = world;
//		this.rebotes = rebotes;
//		this.incDa�oRebotes = incDa�oRebotes;
//		this.da�o = da�o;
//		crearBala(curPos, target);
		
		this.rebotes = 10;
		this.incDa�oRebotes = 0;
		this.da�o = 30;
		
		sprite.setSize(16 / Config.PPM, 16 / Config.PPM);
		Render.spritesADibujar.add(sprite);
	}
	
	public void crearBala(World world, Vector2 curPos, Vector3 target, int rebotes, float incDa�oRebotes, float da�o) {
		
		this.rebotes = rebotes;
		this.incDa�oRebotes = incDa�oRebotes;
		this.da�o = da�o;
		
		bala = ControladorBodies.crearEsfera(world, curPos.x * Config.PPM, curPos.y  * Config.PPM, 6, false, 1, 0);
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
	
	public float getDa�o() {
		return this.da�o;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void update() {
		sprite.setPosition(getPosicion().x - (sprite.getWidth() / 2), getPosicion().y - (sprite.getHeight() / 2));
	}
	
	public void rebotar() {
		this.rebotes--;
		this.da�o += incDa�oRebotes;
		if(this.rebotes <= 0) {
			destruir();
		}
	}

	public void destruir() {
		ControladorBodies.cuerposAEliminar.add(bala);
		Render.spritesADibujar.remove(sprite);
	}
	
	@Override
	public void onDibujar() {
	    Render.spritesADibujar.add(sprite);
	}

	@Override
	public void reset() {
		this.direccion.set(0, 0);
		
		
	}
}

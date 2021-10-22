package com.granata.bouncy.managers;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.granata.bouncy.utiles.Config;

public class ControladorBodies {

	public static ArrayList<Body> cuerposAEliminar = new ArrayList();
	
	public static Body crearCaja(World world, float posX, float posY, float width, float height, BodyType tipo, Class userData) {
		Body body;
		BodyDef bDef = new BodyDef();
		FixtureDef fDef = new FixtureDef();
		
		if(tipo == BodyType.StaticBody) {
			bDef.type = BodyType.StaticBody;
		}else if(tipo == BodyType.DynamicBody){
			bDef.type = BodyType.DynamicBody;
		}else if(tipo == BodyType.KinematicBody) {
			bDef.type = BodyType.KinematicBody;
		}
		
		bDef.position.set(posX / Config.PPM, posY / Config.PPM);
		body = world.createBody(bDef);
		if(userData != null) body.setUserData(userData);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / Config.PPM, height / 2 / Config.PPM);
		
		fDef.shape = shape;
//		fDef.density = 1.0f;
		body.createFixture(fDef);
		shape.dispose();
		return body;
	}
	
	public static Body crearEsfera(World world, float posX, float posY, float radio, boolean isEstatico, float res, float fric) {
		Body body;
		BodyDef bDef = new BodyDef();
		FixtureDef fDef = new FixtureDef();
		
		if(isEstatico) {
			bDef.type = BodyType.StaticBody;
		}else {
			bDef.type = BodyType.DynamicBody;
		}
		
		bDef.position.set(posX / Config.PPM, posY / Config.PPM);
		bDef.fixedRotation = true;
		body = world.createBody(bDef);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(radio / Config.PPM);
		
		fDef.friction = fric;
		fDef.restitution = res;
		fDef.shape = shape;
//		fDef.density = 1.0f;
		body.createFixture(fDef);
		shape.dispose();
		return body;
	}
	
	public static Body crearPickup(World world, float posX, float posY, float width, float height, BodyType tipo, Class userData) {
		Body body;
		BodyDef bDef = new BodyDef();
		FixtureDef fDef = new FixtureDef();
		
		if(tipo == BodyType.StaticBody) {
			bDef.type = BodyType.StaticBody;
		}else if(tipo == BodyType.DynamicBody){
			bDef.type = BodyType.DynamicBody;
		}else if(tipo == BodyType.KinematicBody) {
			bDef.type = BodyType.KinematicBody;
		}
		
		bDef.position.set(posX / Config.PPM, posY / Config.PPM);
		body = world.createBody(bDef);
		if(userData != null) body.setUserData(userData);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / Config.PPM, height / 2 / Config.PPM);
		
		fDef.shape = shape;
		fDef.isSensor = true;
		body.createFixture(fDef);
		shape.dispose();
		
		return body;
	}
	
}

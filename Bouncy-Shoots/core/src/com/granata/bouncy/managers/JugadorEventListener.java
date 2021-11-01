package com.granata.bouncy.managers;

import java.util.EventListener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public interface JugadorEventListener extends EventListener{

	public void actualizarPosicion(float x, float y);
	public void disparar(Vector2 posDisparo, Vector3 target);
	
}

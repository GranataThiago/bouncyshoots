package com.granata.bserver.managers;

import java.util.EventListener;

import com.badlogic.gdx.math.Vector2;

public interface JugadorEventListener extends EventListener{

	public void ejecutarMovimiento(String dir);
	public void dejarEjecutarMovimiento(String dir);
	public void ejecutarDisparo(Vector2 target);
	public void dejarDisparar();
	
}

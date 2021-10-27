package com.granata.bouncy.red;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.granata.bouncy.elementos.Personaje;
import com.granata.bouncy.elementos.Personajes;
import com.granata.bouncy.utiles.Render;

public class Jugador {

	private String nombre;
	private Personaje pj;
	private boolean esCliente = false;
	
	private int posicion;
	
	public Jugador(String nombre, int posicion, boolean esCliente) {
		this.nombre = nombre;
		this.posicion = posicion;
		this.esCliente = esCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public Personaje getPj() {
		return pj;
	}

	
	public Sprite getSprite() {
		return new Sprite(Personajes.values()[posicion].getSprite());
	}
	
	public void crearPersonaje(OrthographicCamera cam) {
		this.pj = new Personaje(new Sprite(Personajes.values()[posicion].getSprite()), esCliente);
		this.pj.inicializarPersonaje(cam);
	}
	
	
}

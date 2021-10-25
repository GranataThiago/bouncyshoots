package com.granata.bouncy.red;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.granata.bouncy.elementos.Personaje;

public class Jugador {

	private String nombre;
	private Personaje pj;
	
	public Jugador(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public Personaje getPj() {
		return pj;
	}
	
	public void crearPersonaje(Sprite s, OrthographicCamera cam) {
		this.pj = new Personaje();
		this.pj.inicializarPersonaje(s, cam);
	}
	
	
}

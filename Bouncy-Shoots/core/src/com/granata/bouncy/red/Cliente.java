package com.granata.bouncy.red;

import com.granata.bouncy.elementos.Personaje;

public class Cliente {

	private String nombre;
	private Personaje pj;
	
	public Cliente(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public Personaje getPj() {
		return pj;
	}
	
	
	
}

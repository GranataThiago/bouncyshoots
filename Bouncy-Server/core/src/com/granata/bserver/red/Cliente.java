package com.granata.bserver.red;

import java.net.InetAddress;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.granata.bserver.elementos.Personaje;
import com.granata.bserver.elementos.Personajes;
import com.granata.bserver.utiles.Render;
import com.granata.bserver.utiles.Utiles;

public class Cliente {

	// Información relacionada a la red
	private InetAddress ip;
	private int puerto;

	// Datos del Cliente/Jugador
	private String nombre;
	private int posTxt;
	private Personaje pj;
	
	public Cliente(InetAddress ip, int puerto, String nombre, int posTxt) {
		this.ip = ip;
		this.nombre = nombre;
		this.puerto = puerto;
		this.posTxt = posTxt;
	}

	public InetAddress getIp() {
		return ip;
	}

	public int getPuerto() {
		return puerto;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Sprite getSprite() {
		return new Sprite(Personajes.values()[posTxt].getSprite());
	}
	
	public int getPosTxt() {
		return posTxt;
	}
	
	public void crearPersonaje(OrthographicCamera cam) {
		this.pj = new Personaje(new Sprite(Personajes.values()[posTxt].getSprite()), posTxt);
		this.pj.inicializarPersonaje(cam);
		Utiles.jugadores.add(pj);
	}
	
	public Personaje getPj() {
		return this.pj;
	}
}

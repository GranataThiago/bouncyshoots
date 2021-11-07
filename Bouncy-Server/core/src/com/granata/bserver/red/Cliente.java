package com.granata.bserver.red;

import java.net.InetAddress;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.granata.bserver.elementos.Personaje;
import com.granata.bserver.elementos.Personajes;
import com.granata.bserver.utiles.Utiles;

public class Cliente {

	// Información relacionada a la red
	private InetAddress ip;
	private int puerto;

	// Datos del Cliente/Jugador
	private String nombre;
	private Personaje pj;
	private int posTxt;
	
	//Partida
	private float score = 0;
	
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
	
	public void crearPersonaje(OrthographicCamera cam, Vector2 spawn) {
		this.pj = new Personaje(new Sprite(Personajes.values()[posTxt].getSprite()), posTxt);
		this.pj.inicializarPersonaje(cam, spawn);
		Utiles.jugadores.add(pj);
	}
	
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score += score;
		System.out.println("Cliente: " + posTxt + " score: " + this.score);
	}

	public Personaje getPj() {
		return this.pj;
	}
}

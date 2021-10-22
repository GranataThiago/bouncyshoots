package com.granata.bouncy.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.granata.bouncy.elementos.Personaje;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Render;

public abstract class Powerup implements Pickupable{

	// Sprites powerups
	private TextureRegion sPowerups = new TextureRegion(new Texture("powerups.png"));
	
	// Todo está en unidades
	// Incrementos relativos al personaje
	private float incVel, incSalto, incVida;
	
	// Incrementos relativos al arma del personaje
	private float incRebotes, incDañoRebotes;
	private int incBalas, incDaño;
	private boolean dañoJugador;
	
	// Configuraciones generales del Powerup (en segundos)
	private float duracion;
	
	private Sprite sprite;

	public Powerup(int lugar, float incVel, float incSalto, float incVida, float incRebotes, float incDañoRebotes, boolean dañoJugador, float duracion) {
		this.incVel = incVel;
		this.incSalto = incSalto;
		this.incVida = incVida;
		this.incRebotes = incRebotes;
		this.incDañoRebotes = incDañoRebotes;
		this.dañoJugador = dañoJugador;
		this.duracion = duracion;

		inicializarSprite(lugar);
	}
	
	public Powerup(int lugar, int incBalas, int incDaño) {
		this.incVel = 0;
		this.incSalto = 0;
		this.incVida = 0;
		this.incRebotes = 0;
		this.incDañoRebotes = 0;
		this.dañoJugador = true;
		this.duracion = 0;
		
		this.incBalas = incBalas;
		this.incDaño = incDaño;
		
		inicializarSprite(lugar);
	}


	private void inicializarSprite(int lugar) {
		sPowerups.setRegion((lugar == 0) ? 0 : 64 * lugar, 0, 64, 64);
		this.sprite = new Sprite(sPowerups);
		sprite.setSize(64 / Config.PPM, 64 / Config.PPM);
		
	}

	public Sprite getSprite() {
		return sprite;
	}

	public float getDuracion() {
		return duracion;
	}


	public float getIncVel() {
		return incVel;
	}


	public float getIncSalto() {
		return incSalto;
	}


	public float getIncVida() {
		return incVida;
	}


	public float getIncRebotes() {
		return incRebotes;
	}


	public float getIncDañoRebotes() {
		return incDañoRebotes;
	}


	public boolean isDañoJugador() {
		return dañoJugador;
	}
	
	@Override
	public void onPickup(Personaje pj) {
		pj.aumentarVel(incVel);
		pj.aumentarSalto(incSalto);
		pj.aumentarVida(incVida);
		pj.getArma().aumentarRebotes(incRebotes);
		pj.getArma().setIncDañoRebotes(incDañoRebotes);
		pj.getArma().setBalas(pj.getArma().getBalas() + incBalas);
		pj.getArma().setDaño(30 + incDaño);
		Render.spritesADibujar.remove(this.sprite);
	}
	
}

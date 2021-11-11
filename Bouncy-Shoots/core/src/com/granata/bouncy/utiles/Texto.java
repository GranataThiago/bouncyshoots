package com.granata.bouncy.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Texto {

	private FreeTypeFontGenerator generador;
	private FreeTypeFontParameter parametros;
	private BitmapFont fuente = new BitmapFont();
	
	private String texto = "";
	private float x = 0, y = 0;
	
	public Texto() {
		generarTexto();
	}
	
	private void generarTexto() {
		FreeTypeFontGenerator generador = new FreeTypeFontGenerator(Gdx.files.internal("fuentes/Acme-Regular.ttf"));
		FreeTypeFontParameter parametros = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parametros.shadowColor = Color.BLACK;
		fuente = generador.generateFont(parametros);
	}
	
	public void dibujar(String texto, float x, float y) {
		fuente.draw(Render.sb, texto, x, y);
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
}

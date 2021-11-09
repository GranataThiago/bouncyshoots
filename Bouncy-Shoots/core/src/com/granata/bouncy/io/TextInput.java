package com.granata.bouncy.io;

import com.badlogic.gdx.Input.TextInputListener;
import com.granata.bouncy.utiles.Nombres;

public class TextInput implements TextInputListener {
	
	private String nombreUsuario;
			
	@Override
	public void input (String text) {
		this.nombreUsuario = text;
	}
	
	@Override
	public void canceled () {
//		this.nombreUsuario = Nombres.getNombreAleatorio();
	}
	   
	public String getNombreUsuario() {
		return this.nombreUsuario;
	}
}
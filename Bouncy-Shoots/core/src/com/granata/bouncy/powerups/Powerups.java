package com.granata.bouncy.powerups;

import java.lang.reflect.InvocationTargetException;

public enum Powerups {

	SPEEDY("Speedy"),
	KANGAROO("Kangaroo"),
	PANZER("Panzer"),
	FRIENDLYFIRENT("Friendly Firen't", "Ffnt"),
	BOUNCYDMG("Bouncy Damage", "BouncyDmg"),
	BOUNCER("Bouncer");

	private String nombre;
	private String clase;
	
	private Powerups(String nombre, String clase) {
		this.nombre = nombre;
		this.clase = clase;
	}
	
	private Powerups(String nombre) {
		this.nombre = nombre;
		this.clase = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public String getClase() {
		return clase;
	}

	public Powerup getPowerup() {
		String clase = "com.granata.bouncy.powerups." + this.clase;
		Powerup p = null;
		try {
			
			p = (Powerup) Class.forName(clase).getDeclaredConstructor().newInstance();

		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
		}
		
		return p;
	}
	
	
}

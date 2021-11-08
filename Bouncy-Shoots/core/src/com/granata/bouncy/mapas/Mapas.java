package com.granata.bouncy.mapas;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.math.Vector2;
import com.granata.bouncy.modos.JuegoBase;

public enum Mapas {

	CO1("Combate", "maps/Combate_1.tmx"),
	CO2("Combate", "maps/Combate_2.tmx"),
	CO3("Combate", "maps/Combate_3.tmx"),
	CA1("Carrera", "maps/Carrera_1.tmx"),
	CA2("Carrera", "maps/Carrera_2.tmx"),
	ES1("Estatua", "maps/Estatua_1.tmx"),
	ES2("Estatua", "maps/Estatua_2.tmx"),
	COL1("Colina", "maps/Colina_1.tmx"),
	COL2("Colina", "maps/Colina_2.tmx"),
	COL3("Colina", "maps/Colina_3.tmx");

	
	private String modo;
	private String ruta;
	
	private Mapas(String modo, String ruta) {
		this.modo = modo;
		this.ruta = ruta;
	}

	public JuegoBase getModo() {
		String clase = "com.granata.bouncy.modos." + this.modo;
		
		JuegoBase m = null;
		try {
			m = (JuegoBase) Class.forName(clase).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
		}
		
		return m;
	}

	public String getRuta() {
		return ruta;
	}
	
	
	
}

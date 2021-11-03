package com.granata.bserver.mapas;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.math.Vector2;
import com.granata.bserver.modos.JuegoBase;

public enum Mapas {

	MAPA2("Carrera", "maps/Carrera_1.tmx", new Vector2[] {new Vector2(900f,215f), new Vector2(2000f,150f), new Vector2(2300f,605f)}),
	PRUEBA("Combate", "maps/Pruebares.tmx",  new Vector2[] {new Vector2(400f,420f), new Vector2(960f,670f), new Vector2(1490f,420f), new Vector2(960f,100f)}),
	PRUEBA3("Combate", "maps/Pruebares.tmx",  new Vector2[] {new Vector2(400f,420f), new Vector2(960f,670f), new Vector2(1490f,420f), new Vector2(960f,100f)}),
	PRUEBA4("Combate", "maps/Pruebares.tmx",  new Vector2[] {new Vector2(400f,420f), new Vector2(960f,670f), new Vector2(1490f,420f), new Vector2(960f,100f)});

	
	private String modo;
	private String ruta;
	private Vector2[] spawners;
	
	private Mapas(String modo, String ruta, Vector2[] spawners) {
		this.modo = modo;
		this.ruta = ruta;
		this.spawners = spawners;
	}

	public Vector2[] getSpawners() {
		return spawners;
	}

	public JuegoBase getModo() {
		String clase = "com.granata.bserver.modos." + this.modo;
		
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

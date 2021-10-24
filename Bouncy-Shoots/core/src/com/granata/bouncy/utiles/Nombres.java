package com.granata.bouncy.utiles;

public enum Nombres {

	TITI("Titi"),
	LAUTI("Lauti"),
	ESTI("Esti"),
	DCMAT("Dcmat"),
	BKCC("Bkcc"),
	VERMI("Vermi"),
	FORTI("Forti"),
	JUANCHI("Juanchi");
	
	private String nombre;
	
	private Nombres(String nombre) {
		this.nombre = nombre;
	}
	
	public static String getNombreAleatorio() {
		return Nombres.values()[Utiles.r.nextInt(Nombres.values().length)].getNombre();
	}
	
	public String getNombre() {
		return this.nombre;
	}
}

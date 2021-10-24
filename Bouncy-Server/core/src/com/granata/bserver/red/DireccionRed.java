package com.granata.bserver.red;

import java.net.InetAddress;

public class DireccionRed {

	private InetAddress ip;
	private int puerto;
	private String nombre;
	
	public DireccionRed(InetAddress ip, int puerto, String nombre) {
		this.ip = ip;
		this.nombre = nombre;
		this.puerto = puerto;
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
}

 package com.granata.bouncy.red;

import java.util.ArrayList;

public class Cliente {

	private ArrayList<Jugador> clientes = new ArrayList<Jugador>();
	private HiloCliente hc;
	private int id;
	
	public void crearHilo() {
		hc = new HiloCliente();
		hc.start();
	}
	
	public HiloCliente getHc() {
		return this.hc;
	}
	
	public ArrayList<Jugador> getClientes(){
		return this.clientes;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
		System.out.println("Se seteo id");
	}
}

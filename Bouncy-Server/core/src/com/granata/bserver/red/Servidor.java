package com.granata.bserver.red;

import java.util.ArrayList;

public class Servidor {

	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private HiloServidor hs;
	
	public Servidor() {
		hs = new HiloServidor();
		hs.start();
	}
	
	
	public ArrayList<Cliente> getClientes() {
		return this.clientes;
	}
	
}

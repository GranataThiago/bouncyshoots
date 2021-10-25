package com.granata.bouncy.managers;

import java.util.ArrayList;

import com.granata.bouncy.red.HiloCliente;
import com.granata.bouncy.red.Jugador;

public class ControladorPartida {

	public static ArrayList<Jugador> clientes = new ArrayList<Jugador>();
	public static HiloCliente hc;
	
	public static void iniciarHilo() {
		hc = new HiloCliente();
		hc.start();
	}
	
}

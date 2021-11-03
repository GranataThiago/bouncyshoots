package com.granata.bserver.managers;

import java.util.ArrayList;

import com.granata.bserver.mapas.Mapas;
import com.granata.bserver.utiles.Utiles;

public class ControladorNiveles {

	public static ArrayList<Integer> niveles = new ArrayList<Integer>();
	
	public static void generarMapas(int cantidad) {

		while (niveles.size() < cantidad) {

		    int random = Utiles.r.nextInt(Mapas.values().length);
		    if (!niveles.contains(random)) {
		    	niveles.add(random);
		    }
		}
	}
	
	
	
}

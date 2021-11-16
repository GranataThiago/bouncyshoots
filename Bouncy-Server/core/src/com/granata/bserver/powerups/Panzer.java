package com.granata.bserver.powerups;

import com.granata.bserver.elementos.Personaje;

public class Panzer extends Powerup{

	public Panzer(int pos) {
		super(1, 0, 0, 150, 0, 0, true, 0, pos);
	}
	
	@Override
	public void onPickup(Personaje pj) {
		
	}

}
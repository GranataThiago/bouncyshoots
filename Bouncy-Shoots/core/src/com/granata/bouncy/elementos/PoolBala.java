package com.granata.bouncy.elementos;

import com.badlogic.gdx.utils.Pool;

public class PoolBala extends Pool<Bala>{

	public PoolBala(int init, int max){
		super(init, max);
	}
	
	// make pool with default 16 initial objects and no max
	public PoolBala(){
		super();
	}
	
	@Override
	protected Bala newObject() {
		// TODO Auto-generated method stub
		return new Bala(null, null, null, max, max, max);
	}

	
	
}

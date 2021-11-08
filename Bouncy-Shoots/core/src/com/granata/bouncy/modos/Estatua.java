package com.granata.bouncy.modos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.granata.bouncy.powerups.Bullet;
import com.granata.bouncy.powerups.Powerup;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Render;

public class Estatua extends JuegoBase{
	
	private enum Modos { ESTATUA, MOVIMIENTO } 
	private Modos estadoActual = Modos.MOVIMIENTO;
	
	
	private Sprite luzRoja = new Sprite(new Texture("images/luzRoja.png"));
	private Sprite luzVerde = new Sprite(new Texture("images/luzVerde.png"));
	
	@Override
	public void start(String rutaMapa) {
		super.start(rutaMapa);
		System.out.println("Iniciando estatua");
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);

		
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		Render.sb.begin();
			if(estadoActual == Modos.ESTATUA) {
				luzRoja.setAlpha(1f);
				luzRoja.draw(Render.sb);
			}else {
				luzVerde.setAlpha(1f);
				luzVerde.draw(Render.sb);
			}
		Render.sb.end();
		
	}

	@Override
	public void cambiarEstado(String estado) {
		if(estado.equals("MOVIMIENTO")) {
			estadoActual = Modos.MOVIMIENTO;
		}else if(estado.equals("ESTATUA")) {
			estadoActual = Modos.ESTATUA;
		}
	}

	@Override
	public void spawnPickup(int nroPowerup, int posPowerup) {
		if(posPowerup < spawners.size()) {
			Powerup p = new Bullet();
			powerups.add(p);
			Vector2 coords = spawners.get(posPowerup);
			
			Render.spritesADibujar.add(p.getSprite());
			p.getSprite().setPosition((coords.x / Config.PPM) - (p.getSprite().getWidth() / 2), (coords.y / Config.PPM)  - (p.getSprite().getHeight() / 2));
		}
		
	}
	
	public void dispose() {
		super.dispose();
		luzVerde.getTexture().dispose();
		luzRoja.getTexture().dispose();
	}

}

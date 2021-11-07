package com.granata.bserver.managers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.granata.bserver.elementos.Arma;
import com.granata.bserver.elementos.Bala;
import com.granata.bserver.elementos.Personaje;
import com.granata.bserver.mapas.BloqueInteractivo;
import com.granata.bserver.powerups.Pickupable;

public class CollisionListener implements ContactListener{
	
	@Override
	public void beginContact(Contact contact) {

		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		Body cuerpoA = fixA.getBody();
		Body cuerpoB = fixB.getBody();

		Object oA = cuerpoA.getUserData();
		Object oB = cuerpoB.getUserData();

		if(oA.getClass() == Bala.class && oB.getClass() == Personaje.class || oA.getClass() == Personaje.class  && oB.getClass() == Bala.class) {
			
			Body bala = ((oA.getClass() == Bala.class) ? cuerpoA : cuerpoB);
			Bala b = (Bala) bala.getUserData();
			Personaje pj =  (Personaje) ((oA.getClass() == Personaje.class) ? oA : oB);
			pj.recibirDaño(b.getDaño(), b.getIdTirador());
			b.destruir();

				
		}else if(oA.getClass() == Bala.class || oB.getClass() == Bala.class) {
			
			Bala claseBala =  (Bala) ((oA.getClass() == Bala.class) ? oA : oB);
	        claseBala.rebotar();
	        
		}else if(oA.getClass() == Personaje.class && Pickupable.class.isAssignableFrom(oB.getClass()) || Pickupable.class.isAssignableFrom(oA.getClass())  && oB.getClass() == Personaje.class) {
			Body objeto = (Pickupable.class.isAssignableFrom(oA.getClass())) ? cuerpoA : cuerpoB;
			Pickupable p = (Pickupable) objeto.getUserData();
			Personaje pj = (Personaje) ((oA.getClass() == Personaje.class) ? oA : oB);
			p.onPickup(pj);
			ControladorBodies.cuerposAEliminar.add(objeto);
		}else if(oA.getClass() == Personaje.class && BloqueInteractivo.class.isAssignableFrom(oB.getClass()) || BloqueInteractivo.class.isAssignableFrom(oA.getClass())  && oB.getClass() == Personaje.class) {
			// Código para detectar colisión con bloque interactivo
			System.out.println("Colisionando con colina");
			Personaje pj = (Personaje) ((oA.getClass() == Personaje.class) ? oA : oB);
			Body bloque = (BloqueInteractivo.class.isAssignableFrom(oA.getClass())) ? cuerpoA : cuerpoB;
			BloqueInteractivo b = (BloqueInteractivo) bloque.getUserData();
			b.onEntrar(pj.getId());
		}
	}

	@Override
	public void endContact(Contact contact) {

		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		Body cuerpoA = fixA.getBody();
		Body cuerpoB = fixB.getBody();

		Object oA = cuerpoA.getUserData();
		Object oB = cuerpoB.getUserData();
		
		if(oA.getClass() == Personaje.class && BloqueInteractivo.class.isAssignableFrom(oB.getClass()) || BloqueInteractivo.class.isAssignableFrom(oA.getClass())  && oB.getClass() == Personaje.class) {
			// Código para detectar colisión con la colina
			Personaje pj = (Personaje) ((oA.getClass() == Personaje.class) ? oA : oB);
			Body bloque = (BloqueInteractivo.class.isAssignableFrom(oA.getClass())) ? cuerpoA : cuerpoB;
			BloqueInteractivo b = (BloqueInteractivo) bloque.getUserData();
			b.onSalir();
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

}

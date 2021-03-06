package com.granata.bouncy.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Render;

public class KeyInput implements InputProcessor{

	private boolean jump = false, right = false, left = false;
	private boolean click = false;
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode==Keys.SPACE) {
			Render.app.getCliente().getHc().enviarMensaje("Ejecutar!Salto!" + Render.app.getCliente().getId());
			jump = true;
		}
		if(keycode == Keys.D) {
			Render.app.getCliente().getHc().enviarMensaje("Ejecutar!Derecha!" + Render.app.getCliente().getId());
			right = true;
		}
		if(keycode == Keys.A) {
			Render.app.getCliente().getHc().enviarMensaje("Ejecutar!Izquierda!" + Render.app.getCliente().getId());
			left = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode==Keys.SPACE) {
			jump = false;
			Render.app.getCliente().getHc().enviarMensaje("DejarEjecutar!Salto!" + Render.app.getCliente().getId());
		}
		if(keycode == Keys.D) {
			right = false;
			Render.app.getCliente().getHc().enviarMensaje("DejarEjecutar!Derecha!" + Render.app.getCliente().getId());

		}
		if(keycode == Keys.A) {
			left = false;
			Render.app.getCliente().getHc().enviarMensaje("DejarEjecutar!Izquierda!" + Render.app.getCliente().getId());

		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		click = true;
		Vector3 clickPos = Global.cam.unproject(new Vector3(screenX, screenY, 0));
		Render.app.getCliente().getHc().enviarMensaje("Ejecutar!Disparo!" + Render.app.getCliente().getId() + "!" + clickPos.x + "!" + clickPos.y);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		click = false;
		Render.app.getCliente().getHc().enviarMensaje("DejarEjecutar!Disparo!" + Render.app.getCliente().getId());
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isClicking() {
		return click;
	}
	
	public boolean isJumping() {
		return jump;
	}
	
	public boolean isRight() {
		return right;
	}
	
	public boolean isLeft() {
		return left;
	}
	
}

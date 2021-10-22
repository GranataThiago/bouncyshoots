package com.granata.bserver.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;

public class KeyInput implements InputProcessor{

	private boolean jump = false, right = false, left = false;
	private boolean click = false;
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode==Keys.SPACE) {
			jump = true;
		}
		if(keycode == Keys.D) {
			right = true;
		}
		if(keycode == Keys.A) {
			left = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode==Keys.SPACE) {
			jump = false;
		}
		if(keycode == Keys.D) {
			right = false;
		}
		if(keycode == Keys.A) {
			left = false;
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
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		click = true;
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

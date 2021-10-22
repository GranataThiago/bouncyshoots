package com.granata.bouncy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Render;

public class ScreenMenu implements Screen{

	private Stage stage;
	
	private Texture txtBg;
	
	private Texture txtJugar;
	private Button btnJugar;
	
	private Texture txtSalir;
	private Button btnSalir;
	
	public ScreenMenu() {
		this.stage = new Stage(new FitViewport(Config.ANCHO / Config.PPM, Config.ALTO / Config.PPM));
        
		txtBg = new Texture("bg_menu.png");
	
		txtJugar = new Texture("playbtn.png");
		txtSalir = new Texture("exitbtn.png");
	}
	
	@Override
	public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        
        crearBotones();
	}

	private void crearBotones() {
		btnJugar = new Button(new TextureRegionDrawable(new TextureRegion(txtJugar)));
		btnJugar.setPosition(775 / Config.PPM, 450 / Config.PPM);
		btnJugar.setSize(280 / Config.PPM, 80 / Config.PPM);
		btnJugar.setOrigin(Align.center);
		btnJugar.setTransform(true);
		btnJugar.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

				btnJugar.addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));

			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {

				btnJugar.setScale(1f);
				
			}
			
        	@Override
        	public void clicked(InputEvent e, float x, float y) {
        		Render.app.setScreen(new ScreenLobby());
        	}
        });
		
		btnSalir = new Button(new TextureRegionDrawable(new TextureRegion(txtSalir)));
		btnSalir.setPosition(775 / Config.PPM, 300 / Config.PPM);
		btnSalir.setSize(280 / Config.PPM, 80 / Config.PPM);
		btnSalir.setOrigin(Align.center);
		btnSalir.setTransform(true);
		btnSalir.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

				btnSalir.addAction(Actions.scaleTo(1.1f, 1.1f, 0.1f));

			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {

				btnSalir.setScale(1f);
				
			}
			
			
        	@Override
        	public void clicked(InputEvent e, float x, float y) {
        		Gdx.app.exit();
        	}
        });

        stage.addActor(btnJugar);
        stage.addActor(btnSalir);
	}

	@Override
	public void render(float delta) {
		Render.limpiarPantallaB();

		Render.sb.begin();
		Render.sb.draw(txtBg, 0, 0, Config.ANCHO, Config.ALTO);
		Render.sb.end();
		
        stage.act();
        stage.draw();

	}

	@Override
	public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		dispose();
	}

	@Override
	public void dispose() {
        stage.dispose();
		txtBg.dispose();
		txtJugar.dispose();
		txtSalir.dispose();
	}

}

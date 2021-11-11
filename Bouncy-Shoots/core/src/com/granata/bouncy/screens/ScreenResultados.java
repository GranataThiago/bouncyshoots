package com.granata.bouncy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Render;
import com.granata.bouncy.utiles.Texto;

public class ScreenResultados implements Screen{

	private SpriteBatch sb;
	private Stage stage;
	private Texto txtGanador = new Texto();
	
	private Image logo = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("bs-logo.png"))));
	private Texture txtSalir;
	private Button btnSalir;
	
	private int ganador;
	private Sprite spriteGanador;

	
	public ScreenResultados(int ganador) {
		this.stage = new Stage(new FitViewport(Config.ANCHO / Config.PPM, Config.ALTO / Config.PPM));
		txtSalir = new Texture("exitbtn.png");
		sb = new SpriteBatch();
		this.ganador = ganador;
	}
	
	@Override
	public void show() {
		this.spriteGanador = Render.app.getCliente().getClientes().get(ganador).getSprite();
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        crearInterfaz();
	}

	private void crearInterfaz() {
		btnSalir = new Button(new TextureRegionDrawable(new TextureRegion(txtSalir)));
		btnSalir.setPosition(( (Config.ANCHO / 2) - 140) / Config.PPM, 150 / Config.PPM);
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
        		Render.app.volverAlMenu();
        	}
        });
		
		logo.setPosition(( (Config.ANCHO / 2) - 140) / Config.PPM, (Config.ALTO - 200) / Config.PPM);
		logo.setSize(280 / Config.PPM, 130 / Config.PPM);
		
		stage.addActor(btnSalir);
		stage.addActor(logo);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.25f, 0.29f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Global.partidaIniciada) {
			Render.sb.begin();
				txtGanador.dibujar("El ganador es:  " + Render.app.getCliente().getClientes().get(ganador).getNombre() + "!!!", ( Config.ANCHO / 2 ) - spriteGanador.getWidth() - 100, (Config.ALTO  / 2) + 200);
				Render.sb.draw(spriteGanador, ( Config.ANCHO / 2 ) - spriteGanador.getWidth(), Config.ALTO / 2, 128, 128);
			Render.sb.end();
		}
		
        stage.act();
        stage.draw();
		
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
		txtSalir.dispose();
	}
	
}

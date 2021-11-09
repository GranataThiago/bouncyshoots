package com.granata.bouncy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.granata.bouncy.io.TextInput;
import com.granata.bouncy.red.HiloCliente;
import com.granata.bouncy.utiles.Config;
import com.granata.bouncy.utiles.Global;
import com.granata.bouncy.utiles.Render;

public class ScreenLobby implements Screen{
	
	// Conecto al cliente al lobby
	private HiloCliente hc;
	private Stage stage;
	
	private Texture txtJugar;
	private Button btnJugar;

	private FreeTypeFontGenerator generador;
	private FreeTypeFontParameter parametros;
	private BitmapFont fuente = new BitmapFont();

	private Image logo = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("bs-logo.png"))));
	private Vector2[] secciones = {new Vector2(100, Config.ALTO - 300), new Vector2(600, Config.ALTO - 300), new Vector2(1100, Config.ALTO - 300), new Vector2(1600, Config.ALTO - 300)};

	public ScreenLobby(String nombre) {
		Render.app.getCliente().crearHilo(nombre);
		hc = Render.app.getCliente().getHc();
		
		this.stage = new Stage(new FitViewport(Config.ANCHO / Config.PPM, Config.ALTO / Config.PPM));
		txtJugar = new Texture("playbtn.png");

		
		generador = new FreeTypeFontGenerator(Gdx.files.internal("fuentes/Acme-Regular.ttf"));
		parametros = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parametros.shadowColor = Color.BLACK;
		fuente = generador.generateFont(parametros);
	}
	
	@Override
	public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        
        crearInterfaz();
	}

	private void crearInterfaz() {
		// Creo el Logo, le asigno una posición y tamaño y lo situo en la escena
		logo.setPosition((Config.ANCHO / 2 - 150) / Config.PPM, (Config.ALTO - 200) / Config.PPM);
		logo.setSize(280 / Config.PPM, 130 / Config.PPM);
		stage.addActor(logo);
		
		btnJugar = new Button(new TextureRegionDrawable(new TextureRegion(txtJugar)));
		btnJugar.setPosition((Config.ANCHO / 2 - 150) / Config.PPM, 150 / Config.PPM);
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
        		if(Global.puedeIniciar) {
        			//Render.app.setScreen(new ScreenJuego(Render.app.cambiarMapa()));
        			hc.enviarMensaje("iniciarPartida");
        		}
        	}
        });
		
		stage.addActor(btnJugar);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.25f, 0.29f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Render.sb.begin();
			for(int i = 0; i < Render.app.getCliente().getClientes().size(); i++) {
					Render.sb.draw(Render.app.getCliente().getClientes().get(i).getSprite(), secciones[i].x - 20, secciones[i].y - 250, 128, 128);
					fuente.draw(Render.sb, Render.app.getCliente().getClientes().get(i).getNombre(), secciones[i].x, secciones[i].y);
			}
		Render.sb.end();
		
        stage.act();
        stage.draw();

        if(Global.partidaIniciada) {
			Render.app.setScreen(new ScreenJuego(Global.nroMapaInicial));
        }
        
        // SOLO PARA DEBUGEAR!
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
		fuente.dispose();
		txtJugar.dispose();
		generador.dispose();		
	}

}

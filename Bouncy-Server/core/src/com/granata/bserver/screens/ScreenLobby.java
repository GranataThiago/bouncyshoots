package com.granata.bserver.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.granata.bserver.managers.ControladorNiveles;
import com.granata.bserver.utiles.Config;
import com.granata.bserver.utiles.Render;
import com.granata.bserver.utiles.Utiles;

public class ScreenLobby implements Screen{

	private Stage stage;
	
	private Texture txtJugar;
	
	private FreeTypeFontGenerator generador;
	private FreeTypeFontParameter parametros;
	private BitmapFont fuente = new BitmapFont();

	
	private Image logo = new Image(new TextureRegionDrawable(new TextureRegion(new Texture("bs-logo.png"))));
	private Vector2[] secciones = {new Vector2(100, Config.ALTO - 300), new Vector2(600, Config.ALTO - 300), new Vector2(1100, Config.ALTO - 300), new Vector2(1600, Config.ALTO - 300)};

	public ScreenLobby() {
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
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.25f, 0.29f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Render.sb.begin();
			for(int i = 0; i < Render.app.getSv().getClientes().size(); i++) {
				Render.sb.draw(Render.app.getSv().getClientes().get(i).getSprite(), secciones[i].x - 20, secciones[i].y - 250, 128, 128);
				fuente.draw(Render.sb, Render.app.getSv().getClientes().get(i).getNombre(), secciones[i].x, secciones[i].y);
			}

		Render.sb.end();
		
        if(Utiles.partidaIniciada) {
			Render.app.setScreen(new ScreenJuego(ControladorNiveles.niveles.get(0)));
        }
		
        stage.act();
        stage.draw();

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

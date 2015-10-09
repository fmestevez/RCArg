package com.giantheadgames.rcarg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.giantheadgames.rcarg.RCArg;

public class MenuScreen implements Screen {

	private final RCArg game;
	private Stage stage;

	/**
	 * 
	 * @param game
	 */
	public MenuScreen(final RCArg game) {
		this.game = game;
		stage = new Stage(RCArg.params.MAIN_VIEWPORT_SIZE.x,
				RCArg.params.MAIN_VIEWPORT_SIZE.y, true);

		final Table table = new Table("Menu");
		table.x = RCArg.params.MAIN_VIEWPORT_SIZE.x / 3;
		table.y = RCArg.params.MAIN_VIEWPORT_SIZE.y / 2;
		final TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.font = new BitmapFont(
				Gdx.files.internal("data/font/font.fnt"), false);
		buttonStyle.fontColor = Color.WHITE;
		buttonStyle.checkedFontColor = Color.LIGHT_GRAY;

		final Image logo = new Image(new Texture(Gdx.files.internal("data/logo.png")));
		final TextButton startButton = new TextButton("START", buttonStyle);
		final TextButton scoresButton = new TextButton("HIGH SCORES",
				buttonStyle);
		final TextButton optionsButton = new TextButton("OPTIONS", buttonStyle);
		final TextButton quitButton = new TextButton("QUIT", buttonStyle);

		startButton.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				game.setScreen(game.getInGameScreen());
			}
		});

		scoresButton.setClickListener(new ClickListener() {

			@Override
			public void click(Actor actor, float x, float y) {
				game.setScreen(game.getScoresScreen());
			}
		});

		optionsButton.setClickListener(new ClickListener() {

			@Override
			public void click(Actor actor, float x, float y) {
				game.setScreen(game.getOptionsScreen());
			}
		});

		quitButton.setClickListener(new ClickListener() {

			@Override
			public void click(Actor actor, float x, float y) {
				Gdx.app.exit();
			}
		});

		table.defaults().pad(20).center();
		table.add(logo).minSize(474, 237);
		table.row();
		table.add(startButton).fillX();
		table.row();
		table.add(scoresButton).fillX();
		table.row();
		table.add(optionsButton).fillX();
		table.row();
		table.add(quitButton).fillX();

		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		SpriteBatch sb = new SpriteBatch();
		sb.begin();
		sb.draw(game.menuBackground, 0, 0, RCArg.params.MAIN_VIEWPORT_SIZE.x,
                RCArg.params.MAIN_VIEWPORT_SIZE.y);
		sb.end();

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}

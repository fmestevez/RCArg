package com.giantheadgames.rcarg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.giantheadgames.rcarg.RCArg;
import com.giantheadgames.rcarg.managers.TimerManager;

public class ScoresScreen implements Screen {

	private final RCArg game;
	private Stage stage;

	public ScoresScreen(final RCArg game) {
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
        final LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = new BitmapFont(
                Gdx.files.internal("data/font/font.fnt"), false);
        labelStyle.fontColor = Color.WHITE;

        String [] highScores = TimerManager.getHighScores();
        final Label first = new Label(highScores[0], labelStyle);
        final Label second = new Label(highScores[1], labelStyle);
        final Label third = new Label(highScores[2], labelStyle);
        final TextButton quitButton = new TextButton("BACK", buttonStyle);

        quitButton.setClickListener(new ClickListener() {

            @Override
            public void click(Actor actor, float x, float y) {
                game.setScreen(game.getMenuScreen());
            }
        });

        table.defaults().pad(20).center();
        table.add(first);
        table.row();
        table.add(second);
        table.row();
        table.add(third);
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

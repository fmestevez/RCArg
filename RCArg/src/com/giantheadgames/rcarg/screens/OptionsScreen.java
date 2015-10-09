package com.giantheadgames.rcarg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.giantheadgames.rcarg.RCArg;

public class OptionsScreen implements Screen {

    private final RCArg game;
    private Stage stage;

    public OptionsScreen(final RCArg game) {
        this.game = game;
        stage = new Stage(RCArg.params.MAIN_VIEWPORT_SIZE.x,
                RCArg.params.MAIN_VIEWPORT_SIZE.y, true);

        final Table table = new Table("Menu");
        table.x = RCArg.params.MAIN_VIEWPORT_SIZE.x / 3;
        table.y = RCArg.params.MAIN_VIEWPORT_SIZE.y / 2;
        final CheckBoxStyle checkboxStyle = new CheckBoxStyle();
        checkboxStyle.font = new BitmapFont(
                Gdx.files.internal("data/font/font.fnt"), false);
        checkboxStyle.fontColor = Color.WHITE;
        checkboxStyle.checkedFontColor = Color.LIGHT_GRAY;

        final TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = new BitmapFont(
                Gdx.files.internal("data/font/font.fnt"), false);
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.checkedFontColor = new Color(0.3f, 0.3f, 0.3f, 0f);

        final CheckBox musicCb = new CheckBox("MUSIC", checkboxStyle);
        final CheckBox soundCb = new CheckBox("SOUND", checkboxStyle);
        final CheckBox skidsCb = new CheckBox("SKIDS", checkboxStyle);
        final CheckBox partCb = new CheckBox("PARTICLES", checkboxStyle);
        final TextButton quitButton = new TextButton("BACK", buttonStyle);

        if (RCArg.params.DIRT_ENABLED) {
            partCb.setChecked(false);
        } else {
            partCb.setChecked(true);
        }

        if (RCArg.params.SKID_ENABLED) {
            skidsCb.setChecked(false);
        } else {
            skidsCb.setChecked(true);
        }

        if (RCArg.params.MUSIC_ENABLED && RCArg.params.SOUND_ENABLED) {
            musicCb.setChecked(false);
        } else {
            musicCb.setChecked(true);
        }

        if (RCArg.params.SOUND_ENABLED) {
            soundCb.setChecked(false);
        } else {
            soundCb.setChecked(true);
        }

        musicCb.setClickListener(new ClickListener() {
            @Override
            public void click(Actor actor, float x, float y) {
                if (musicCb.isChecked()) {
                    RCArg.params.MUSIC_ENABLED = false;
                } else {
                    RCArg.params.MUSIC_ENABLED = true;
                }
            }
        });

        soundCb.setClickListener(new ClickListener() {

            @Override
            public void click(Actor actor, float x, float y) {
                if (soundCb.isChecked()) {
                    musicCb.setChecked(true);
                    musicCb.touchable = false;
                    RCArg.params.SOUND_ENABLED = false;
                } else {
                    musicCb.touchable = true;
                    RCArg.params.SOUND_ENABLED = true;
                }
            }
        });

        skidsCb.setClickListener(new ClickListener() {

            @Override
            public void click(Actor actor, float x, float y) {
                if (skidsCb.isChecked()) {
                    RCArg.params.SKID_ENABLED = false;
                } else {
                    RCArg.params.SKID_ENABLED = true;
                }
            }
        });

        partCb.setClickListener(new ClickListener() {

            @Override
            public void click(Actor actor, float x, float y) {
                if (partCb.isChecked()) {
                    RCArg.params.DIRT_ENABLED = false;
                } else {
                    RCArg.params.DIRT_ENABLED = true;
                }
            }
        });

        quitButton.setClickListener(new ClickListener() {

            @Override
            public void click(Actor actor, float x, float y) {
                game.setScreen(game.getMenuScreen());
            }
        });

        table.defaults().pad(20).center();
        table.add(musicCb).fillX();
        table.row();
        table.add(soundCb).fillX();
        table.row();
        table.add(skidsCb).fillX();
        table.row();
        table.add(partCb).fillX();
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

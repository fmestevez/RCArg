package com.giantheadgames.rcarg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.SimpleTileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.giantheadgames.rcarg.RCArg;
import com.giantheadgames.rcarg.entities.InGameState;
import com.giantheadgames.rcarg.entities.TDCar;
import com.giantheadgames.rcarg.managers.ContactManager;
import com.giantheadgames.rcarg.managers.GUIManager;
import com.giantheadgames.rcarg.managers.ParticleManager;
import com.giantheadgames.rcarg.managers.SkidManager;
import com.giantheadgames.rcarg.managers.SoundManager;
import com.giantheadgames.rcarg.managers.TimerManager;
import com.giantheadgames.rcarg.managers.TrackManager;

public class InGameScreen implements Screen {

    private final RCArg game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private TDCar car;
    private TrackManager trackManager;
    private GUIManager guiManager;
    private TileMapRenderer tileRender;
    private static InGameState gameState;

    public InGameScreen(final RCArg game) {

        TimerManager.setMillis(0d);

        this.game = game;
        world = new World(RCArg.params.MAIN_GRAVITY, false);
        world.setContactListener(new ContactManager());

        trackManager = new TrackManager();
        trackManager.init(world);
        guiManager = new GUIManager();
        guiManager.init();
        ContactManager.init();
        SoundManager.init();
        SoundManager.startMusic();
        TimerManager.init();

        car = new TDCar(world, new Vector2(14f, 34f));

        camera = new OrthographicCamera(RCArg.params.MAIN_VIEWPORT_SIZE.x,
                RCArg.params.MAIN_VIEWPORT_SIZE.y);
        camera.position.set(RCArg.params.MAIN_CAMERA_POS);

        batch = new SpriteBatch();
        if (RCArg.params.SKID_ENABLED) {
            SkidManager.init();
        }

        if (RCArg.params.DIRT_ENABLED) {
            ParticleManager.init();
        }

        TiledMap tileMap = TiledLoader.createMap(Gdx.files
                .internal("data/background/background.tmx"));
        SimpleTileAtlas tileAtlas = new SimpleTileAtlas(tileMap,
                Gdx.files.internal("data/background/"));
        tileRender = new TileMapRenderer(tileMap, tileAtlas, 32, 32);
        setGameState(InGameState.COUNTDOWN);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Keys.ESCAPE)
                || Gdx.input.isButtonPressed(Keys.BACK)) {
            this.dispose();
            game.setScreen(game.getMenuScreen());
            return;
        }

        camera.update();

        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        /*
         * Update
         */

        car.update(delta);
        TimerManager.update(delta);
        camera.position.set(car.getWorldPosition().x, car.getWorldPosition().y,
                0f);
        camera.zoom = (float) (1.5f + Math.pow(car.getSpeedKMH() / 100, 2));
        world.step(1 / 60f, RCArg.params.MAIN_VEL_ITERATIONS,
                RCArg.params.MAIN_POS_ITERATIONS);

        guiManager.update((int) Math.floor(car.getSpeedKMH())
                + RCArg.params.CAR_SPEED_UNIT, camera);

        /*
         * Render
         */

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        tileRender.render(camera);

        car.draw(batch);

        guiManager.draw(batch);
        batch.end();
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
        batch.dispose();
        car.dispose();
        world.dispose();
        tileRender.dispose();
        SoundManager.disposeSounds();
        ContactManager.dispose();
        GUIManager.dispose();
        ParticleManager.dispose();
        SkidManager.dispose();
        SoundManager.disposeSounds();
        TimerManager.dispose();
    }

    public static InGameState getGameState() {
        return gameState;
    }

    public static void setGameState(InGameState gameState) {
        InGameScreen.gameState = gameState;
    }
}

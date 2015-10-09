package com.giantheadgames.rcarg.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.giantheadgames.rcarg.RCArg;

public class SoundManager {

    private static Sound screech, crash, engine;
    private static long screechId, engineId;
    private static boolean screechPlaying = false;
    private static Music music;

    public static void init() {
        if (RCArg.params.SOUND_ENABLED) {
            screech = Gdx.audio.newSound(Gdx.files
                    .internal("data/sound/tire_screech.mp3"));
            crash = Gdx.audio.newSound(Gdx.files
                    .internal("data/sound/car_colision.mp3"));
            engine = Gdx.audio.newSound(Gdx.files
                    .internal("data/sound/car_engine.mp3"));
            music = Gdx.audio.newMusic(Gdx.files
                    .internal("data/sound/music.mp3"));
        }
    }

    public static void startMusic() {
        if (RCArg.params.MUSIC_ENABLED && RCArg.params.SOUND_ENABLED) {
            music.play();
            music.setLooping(true);
            music.setVolume(.3f);
        }
    }

    public static void stopMusic() {
        if (RCArg.params.MUSIC_ENABLED && RCArg.params.SOUND_ENABLED) {
            music.stop();
        }
    }

    public static void loopEngineSound() {
        if (RCArg.params.SOUND_ENABLED) {
            engineId = engine.loop();
        }
    }

    public static void setEngineSoundPitch(float pitch) {
        if (RCArg.params.SOUND_ENABLED) {
            engine.setPitch(engineId, pitch);
        }
    }

    public static void stopEngineSound() {
        if (RCArg.params.SOUND_ENABLED) {
            engine.stop(engineId);
        }
    }

    public static void loopScreechSound() {
        if (RCArg.params.SOUND_ENABLED) {
            if (!screechPlaying) {
                screechId = screech.loop(0.1f);
                screechPlaying = true;
            }
        }
    }

    public static void stopScreechSound() {
        if (RCArg.params.SOUND_ENABLED) {
            if (screechPlaying) {
                screech.stop(screechId);
                screechPlaying = false;
            }
        }
    }

    public static void playCrashSound(float volume) {
        if (RCArg.params.SOUND_ENABLED) {
            crash.play(volume);
        }
    }

    public static void disposeSounds() {
        if (RCArg.params.SOUND_ENABLED) {
            if (engine != null) {
                engine.dispose();
            }
            if (crash != null) {
                crash.dispose();
            }
            if (screech != null) {
                screech.dispose();
            }
            if (music != null) {
                music.dispose();
            }
        }
    }
}

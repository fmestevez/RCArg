/***
 * Clase principal de la aplicaci�n
 */

package com.giantheadgames.rcarg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.giantheadgames.rcarg.managers.TimerManager;
import com.giantheadgames.rcarg.screens.InGameScreen;
import com.giantheadgames.rcarg.screens.MenuScreen;
import com.giantheadgames.rcarg.screens.OptionsScreen;
import com.giantheadgames.rcarg.screens.ScoresScreen;
import com.giantheadgames.rcarg.util.ObjectSerializer;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public class RCArg extends Game {

    public Texture menuBackground;
    private String paramsPath = "params.ser";
    public static Params params;
    private ObjectSerializer<Params> os;

    @Override
    public Screen getScreen() {
        // TODO Auto-generated method stub
        return super.getScreen();
    }

    @Override
    public void create() {

        os = new ObjectSerializer<Params>(paramsPath);
        params = os.read();
        if (params == null) {
            params = new Params();
        }

        TimerManager.init();
        menuBackground = new Texture(
                Gdx.files.internal("data/menu_background.png"));
        setScreen(this.getMenuScreen());
    }

    public Screen getMenuScreen() {
        return new MenuScreen(this);
    }

    public Screen getInGameScreen() {
        return new InGameScreen(this);
    }

    public Screen getOptionsScreen() {
        return new OptionsScreen(this);
    }

    public Screen getScoresScreen() {
        return new ScoresScreen(this);
    }

    @Override
    public void dispose() {

        os.write(params);
        os = null;
        if (menuBackground != null) {
            menuBackground.dispose();
        }
        super.dispose();
    }
}

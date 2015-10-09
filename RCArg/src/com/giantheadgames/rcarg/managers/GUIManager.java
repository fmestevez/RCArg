/***
 * Manager de graphical user interfaces
 */

package com.giantheadgames.rcarg.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.giantheadgames.rcarg.RCArg;
import com.giantheadgames.rcarg.entities.InGameState;
import com.giantheadgames.rcarg.entities.TDCheckpoint;
import com.giantheadgames.rcarg.screens.InGameScreen;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public class GUIManager {

    private BitmapFont font;
    private CharSequence str;
    private Texture[] signTextures;
    private static TDCheckpoint currentCp;
    private static int signTime = 0;
    private Camera hudCamera;
    private static boolean backwardsAlert;
    private static int backwardsAlertTime = 0;
    private static final int countdownDuration = 300;
    private static int countdownTimer = 0;

    /**
     * Method init
     */
    public void init() {
        hudCamera = new OrthographicCamera(RCArg.params.MAIN_VIEWPORT_SIZE.x,
                RCArg.params.MAIN_VIEWPORT_SIZE.x);
        font = new BitmapFont(Gdx.files.internal("data/font/font.fnt"), false);
        str = "";

        signTextures = new Texture[4];
        signTextures[0] = new Texture(
                Gdx.files.internal("data/signs/sign180left.png"));
        signTextures[1] = new Texture(
                Gdx.files.internal("data/signs/sign180right.png"));
        signTextures[2] = new Texture(
                Gdx.files.internal("data/signs/sign90left.png"));
        signTextures[3] = new Texture(
                Gdx.files.internal("data/signs/sign90right.png"));
    }

    /**
     * Method draw.
     * 
     * @param sb
     *            SpriteBatch
     */
    public void draw(SpriteBatch sb) {
        sb.setProjectionMatrix(hudCamera.combined);
        if (currentCp != null && signTime < 100) {
            currentCp.draw(sb, hudCamera,
                    signTextures[currentCp.getSignIndex()]);
        }

        if (backwardsAlert && backwardsAlertTime < 100) {
            font.draw(sb, "WRONG DIRECTION", hudCamera.position.x
                    - hudCamera.viewportWidth / 6f, hudCamera.position.y
                    - hudCamera.viewportHeight / 5);
        } else if (backwardsAlert && backwardsAlertTime > 100) {
            backwardsAlert = false;
        }

        font.draw(sb, str, hudCamera.position.x + hudCamera.viewportWidth
                / 3.5f, hudCamera.position.y - hudCamera.viewportHeight / 2.5f);
        drawTimeHighscores(sb);
        if (InGameScreen.getGameState() == InGameState.COUNTDOWN) {
            drawCountdown(sb);
        }
    }

    private void drawCountdown(SpriteBatch sb) {
        String number = "";
        if (countdownTimer < countdownDuration) {
            if (countdownTimer < countdownDuration / 3) {
                number = "READY";
            } else if (countdownTimer < countdownDuration / 1.5) {
                number = "SET";
            } else if (countdownTimer < countdownDuration) {
                number = "GO!";
            }
            font.draw(sb, number, hudCamera.position.x
                    - hudCamera.viewportWidth / 25f, hudCamera.position.y
                    - hudCamera.viewportHeight / 5);
            countdownTimer++;
        } else {
            InGameScreen.setGameState(InGameState.RACING);
        }

    }

    /**
     * Method update.
     * 
     * @param txt
     *            String
     */
    public void update(String txt, Camera camera) {
        str = txt;
        hudCamera.position.set(camera.position);
        hudCamera.update();

        if (currentCp != null) {
            currentCp.update(0f);
            signTime++;
        }

        if (backwardsAlert) {
            backwardsAlertTime++;
        }
    }

    private void drawTimeHighscores(SpriteBatch sb) {
        String[] podium = TimerManager.getHighScores();
        font.drawMultiLine(sb, "Actual " + TimerManager.getFormattedTime()
                + "\n----------------------" + "\n1st - " + podium[0]
                + "\n2nd - " + podium[1] + "\n3rd - " + podium[2],
                hudCamera.position.x - hudCamera.viewportWidth / 2.1f,
                hudCamera.position.y + hudCamera.viewportHeight / 2.1f);

    }

    public static TDCheckpoint getCurrentCp() {
        return currentCp;
    }

    public static void setCurrentCp(TDCheckpoint cp) {
        currentCp = cp;
        cp.fadeIn();
        signTime = 0;
    }

    public static void drawBackwardsAlert() {
        backwardsAlert = true;
        backwardsAlertTime = 0;
    }

    public static void dispose() {
        currentCp = null;
        signTime = 0;
        backwardsAlertTime = 0;
        countdownTimer = 0;
    }
}

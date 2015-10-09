package com.giantheadgames.rcarg.managers;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.giantheadgames.rcarg.entities.TDCar;
import com.giantheadgames.rcarg.entities.TDCheckpoint;
import com.giantheadgames.rcarg.entities.TDTire;
import com.giantheadgames.rcarg.fud.CheckpointFUD;
import com.giantheadgames.rcarg.fud.GroundAreaFUD;
import com.giantheadgames.rcarg.fud.base.FixtureUserData;
import com.giantheadgames.rcarg.fud.base.FixtureUserDataType;

public class ContactManager implements ContactListener {

    private static ArrayList<Integer> completedCps;

    public static void init() {
        completedCps = new ArrayList<Integer>();
    }

    /**
     * Method handleContact.
     * 
     * @param contact
     *            Contact
     * @param began
     *            boolean
     */
    public void handleContact(Contact contact, boolean began) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        FixtureUserData fudA = (FixtureUserData) fixtureA.getUserData();
        FixtureUserData fudB = (FixtureUserData) fixtureB.getUserData();

        if (fudA == null || fudB == null) {
            return;
        }

        if (fudA.getType() == FixtureUserDataType.FUD_CAR_TIRE
                && fudB.getType() == FixtureUserDataType.FUD_GROUND_AREA) {
            tireVsGroundArea(fixtureA, fixtureB, began);
        } else if (fudA.getType() == FixtureUserDataType.FUD_GROUND_AREA
                && fudB.getType() == FixtureUserDataType.FUD_CAR_TIRE) {
            tireVsGroundArea(fixtureB, fixtureA, began);
        } else if (fudA.getType() == FixtureUserDataType.FUD_CAR
                && fudB.getType() == FixtureUserDataType.FUD_CP_NORMAL) {
            carVsNormalCheckpoint(fixtureA, fixtureB, began);
        } else if (fudA.getType() == FixtureUserDataType.FUD_CP_NORMAL
                && fudB.getType() == FixtureUserDataType.FUD_CAR) {
            carVsNormalCheckpoint(fixtureB, fixtureA, began);
        } else if (fudA.getType() == FixtureUserDataType.FUD_CAR
                && fudB.getType() == FixtureUserDataType.FUD_CP_BEGIN) {
            carVsBeginCheckpoint(fixtureA, fixtureB, began);
        } else if (fudA.getType() == FixtureUserDataType.FUD_CP_BEGIN
                && fudB.getType() == FixtureUserDataType.FUD_CAR) {
            carVsBeginCheckpoint(fixtureB, fixtureA, began);
        } else if (fudA.getType() == FixtureUserDataType.FUD_CAR
                && fudB.getType() == FixtureUserDataType.FUD_WALL){
            carVsWall(fixtureA, fixtureB, began);
        } else if (fudB.getType() == FixtureUserDataType.FUD_CAR
                && fudA.getType() == FixtureUserDataType.FUD_WALL){
            carVsWall(fixtureB, fixtureA, began);
        }
    }

    /**
     * Method tire_vs_groundArea.
     * 
     * @param tireFixture
     *            Fixture
     * @param groundAreaFixture
     *            Fixture
     * @param began
     *            boolean
     */
    private void tireVsGroundArea(Fixture tireFixture,
            Fixture groundAreaFixture, boolean began) {

        TDTire tire = (TDTire) tireFixture.getBody().getUserData();
        GroundAreaFUD gaFud = (GroundAreaFUD) groundAreaFixture.getUserData();
        if (began) {
            tire.addGroundArea(gaFud);
        } else {
            tire.removeGroundArea(gaFud);
        }

    }

    private void carVsNormalCheckpoint(Fixture carFixture, Fixture cpFixture,
            boolean began) {
        if (began) {
            TDCheckpoint cp = (TDCheckpoint) cpFixture.getBody().getUserData();
            GUIManager.setCurrentCp(cp);
            CheckpointFUD current = (CheckpointFUD) cp.getBody()
                    .getFixtureList().get(0).getUserData();
            if (!completedCps.contains(current.getCpId())) {
                completedCps.add(current.getCpId());
            } else {
                GUIManager.drawBackwardsAlert();
            }
        }
    }

    private void carVsBeginCheckpoint(Fixture carFixture, Fixture cpFixture,
            boolean began) {
        if (began) {
            if (completedCps.size() == 11) {
                completedCps.clear();
                TimerManager.addScore();
                TimerManager.setMillis(0d);
            }
        }
    }
    
    private void carVsWall(Fixture carFixture,
            Fixture wallFixture, boolean began){
        if(began){
            TDCar car = (TDCar) carFixture.getBody().getUserData();
            SoundManager.playCrashSound(car.getSpeedKMH()/300);
        }
    }

    /**
     * Method beginContact.
     * 
     * @param contact
     *            Contact
     * 
     * @see com.badlogic.gdx.physics.box2d.ContactListener#beginContact(Contact)
     */
    @Override
    public void beginContact(Contact contact) {
        handleContact(contact, true);

    }

    /**
     * Method endContact.
     * 
     * @param contact
     *            Contact
     * 
     * @see com.badlogic.gdx.physics.box2d.ContactListener#endContact(Contact)
     */
    @Override
    public void endContact(Contact contact) {
        handleContact(contact, false);

    }

    /**
     * Method preSolve.
     * 
     * @param contact
     *            Contact
     * @param oldManifold
     *            Manifold
     * 
     * @see com.badlogic.gdx.physics.box2d.ContactListener#preSolve(Contact,
     *      Manifold)
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    /**
     * Method postSolve.
     * 
     * @param contact
     *            Contact
     * @param impulse
     *            ContactImpulse
     * 
     * @see com.badlogic.gdx.physics.box2d.ContactListener#postSolve(Contact,
     *      ContactImpulse)
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
    
    public static void dispose(){
        completedCps = null;
    }
}

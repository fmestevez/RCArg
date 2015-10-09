/***
 * Clase de rueda
 */

package com.giantheadgames.rcarg.entities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.giantheadgames.rcarg.RCArg;
import com.giantheadgames.rcarg.entities.base.BaseBoxObject;
import com.giantheadgames.rcarg.entities.base.BaseTexture;
import com.giantheadgames.rcarg.fud.CarTireFUD;
import com.giantheadgames.rcarg.fud.GroundAreaFUD;
import com.giantheadgames.rcarg.managers.SkidManager;
import com.giantheadgames.rcarg.screens.InGameScreen;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public class TDTire extends BaseBoxObject {

    private float maxForwardSpeed = RCArg.params.TIRE_MAX_FORWARD_SPD;
    private float maxBackwardSpeed = RCArg.params.TIRE_MAX_BACKWARD_SPD;
    private float maxDriveForce = RCArg.params.TIRE_MAX_DRIVE_FORCE;
    private float maxLateralImpulse = RCArg.params.TIRE_MAX_LATERAL_IMPUL;
    private Set<GroundAreaFUD> groundAreas = new HashSet<GroundAreaFUD>(
            RCArg.params.TIRE_FUD_INITIAL_CAPACITY,
            RCArg.params.TIRE_FUD_LOAD_FACTOR);
    private float currentTraction;
    private boolean drift = false;
    private boolean isRear = false;
    private boolean handbrake = false;
    private float currentSpeed = 0f;
    private int tireId = 0;
    private boolean isSkidding = false;
    private int skidPointCounter = 0;

    /**
     * Constructor for TDTire.
     * 
     * @param tireId
     *            int
     * @param world
     *            World
     * @param pos
     *            Vector2
     * @param isRear
     *            boolean
     */
    public TDTire(int tireId, World world, Vector2 pos, boolean isRear) {
        super(pos);

        BodyDef bodyDef = null;
        PolygonShape rect = null;

        this.setTireId(tireId);

        // Defino que el cuerpo sea din�mico.
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(pos.x, pos.y);
        // creo el cuerpo
        body = world.createBody(bodyDef);

        this.isRear = isRear;

        // creo un pol�gono con forma rectangular
        rect = new PolygonShape();
        rect.setAsBox(RCArg.params.TIRE_BODY_SIZE.x,
                RCArg.params.TIRE_BODY_SIZE.y);

        // Defino la textura
        texture = new BaseTexture(new TextureRegion(new Texture(
                Gdx.files.internal(RCArg.params.TIRE_TEXTURE_PATH))), pos);

        currentTraction = RCArg.params.TIRE_DEFAULT_TRACTION;

        body.setUserData(this);
        Fixture fx = body.createFixture(rect, RCArg.params.TIRE_DENSITY);
        fx.setUserData(new CarTireFUD());
        rect.dispose();
    }

    /**
     * Method update.
     * 
     * @param delta
     *            float
     */
    @Override
    public void update(float delta) {
        float rot = body.getAngle() * MathUtils.radiansToDegrees;
        updateWorldPosition();
        texture.setPosition(worldPosition);
        texture.setRotation(rot);
        isSkidding = drift
                || (handbrake && this.getSpeedKMH() != RCArg.params.ZERO);
        if (isSkidding && RCArg.params.SKID_ENABLED) {
            if (skidPointCounter % RCArg.params.SKID_POINT_LIMIT == RCArg.params.ZERO) {
                SkidManager.add(
                        tireId,
                        new Vector2(body.getPosition().x
                                * RCArg.params.MAIN_METER_TO_PIXEL, body
                                .getPosition().y
                                * RCArg.params.MAIN_METER_TO_PIXEL));
            }
            skidPointCounter++;
        } else {
            skidPointCounter = 0;
        }
    }

    /**
     * Method draw.
     * 
     * @param sp
     *            SpriteBatch
     */
    @Override
    public void draw(SpriteBatch sp) {

        sp.end();
        if (RCArg.params.SKID_ENABLED) {
            SkidManager.draw(sp.getProjectionMatrix());
        }
        sp.begin();
        texture.draw(sp);
    }

    /**
     * Method updateFriction
     */
    public void updateFriction() {

        // Anulo la velocidad lateral (para que avance s�lo para atr�s/adelante)
        Vector2 impulse = getLateralVelocity().mul(-body.getMass());
        Vector2 currentForwardNormal = null;
        float currentForwardSpeed = 0f;
        float dragForceMagnitude = 0f;

        // Si el impulso lateral es mayor que el definido, la rueda derrapa
        if (impulse.len() > maxLateralImpulse) {
            impulse.mul(maxLateralImpulse / impulse.len());
            drift = true;
        } else {
            drift = false;
        }

        // Anulo la velocidad angular (para que no gire sobre su eje)
        body.applyAngularImpulse(currentTraction
                * RCArg.params.TIRE_ANGULAR_IMP_SCALAR * body.getInertia()
                * -body.getAngularVelocity());

        if (isRear) {
            if (!handbrake) {
                body.applyLinearImpulse(impulse.mul(currentTraction),
                        body.getWorldCenter());
            }

        } else {
            body.applyLinearImpulse(impulse.mul(currentTraction),
                    body.getWorldCenter());
        }

        // Le aplico al cuerpo una fuerza opuesta cuando se mueve para que
        // eventualmente se detenga.
        currentForwardNormal = getForwardVelocity();
        currentForwardSpeed = currentForwardNormal.nor().len();
        // dragForceMagnitude = RCArg.params.TIRE_DRAG_FORCE_MAG_SCALAR
        // * currentForwardSpeed;

        if (isRear && handbrake) {
            dragForceMagnitude = RCArg.params.TIRE_DRAG_FORCE_MAG_SCALAR_HB
                    * currentForwardSpeed;
        } else {
            dragForceMagnitude = RCArg.params.TIRE_DRAG_FORCE_MAG_SCALAR
                    * currentForwardSpeed;
        }

        body.applyForce(
                currentForwardNormal.mul(dragForceMagnitude * currentTraction),
                body.getWorldCenter());
    }

    /**
     * Method getLateralVelocity.
     * 
     * @return Vector2
     */
    public Vector2 getLateralVelocity() {
        // agarro la fuerza normal al cuerpo de la llanta
        Vector2 currentRightNormal = body.getWorldVector(new Vector2(1f, 0f));
        // hago una proyecci�n de la fuerza lineal actual del cuerpo con la
        // normal horizontal
        // (http://www.iforce2d.net/image/topdown-projectlateral.png)
        return currentRightNormal.mul((currentRightNormal.dot(body
                .getLinearVelocity())));
    }

    /**
     * Method getForwardVelocity.
     * 
     * @return Vector2
     */
    public Vector2 getForwardVelocity() {
        // agarro la fuerza normal al cuerpo de la llanta
        Vector2 curr = body.getWorldVector(new Vector2(0f, 1f));
        // hago una proyecci�n de la fuerza lineal actual del cuerpo con la
        // normal horizontal
        // (http://www.iforce2d.net/image/topdown-projectlateral.png)
        return curr.mul((curr.dot(body.getLinearVelocity())));
    }

    /**
     * Method updateDrive
     */
    public void updateDrive() {
        // Velocidad deseada
        float desiredSpeed = 0f;
        Vector2 currentForwardNormal = null;
        float force = 0f;

        boolean accel = Gdx.input.isKeyPressed(Keys.UP)
                || Gdx.input.isTouched();
        boolean breaq = Gdx.input.isKeyPressed(Keys.DOWN);
        handbrake = Gdx.input.isKeyPressed(Keys.SPACE);

        // Aplico la velocidad m�xima a la deseada
        // en los casos de aceleraci�n y desaceleraci�n
        if (accel) {
            desiredSpeed = maxForwardSpeed;
        } else if (breaq) {
            desiredSpeed = maxBackwardSpeed;
        }

        // Busco la velocidad actual hacia delante
        currentForwardNormal = body.getWorldVector(new Vector2(0f, 1f));
        currentSpeed = getForwardVelocity().dot(
                body.getWorldVector(new Vector2(0f, 1f)));

        // Aplico las fuerzas necesarias
        force = 0;
        if ((desiredSpeed > currentSpeed) && accel) {
            force = maxDriveForce;
        } else if ((desiredSpeed < currentSpeed) && breaq) {
            force = -maxDriveForce;
        } else {
            // si la velocidad deseada es igual a la actual, no aplico fuerzas.
            return;
        }

        if (InGameScreen.getGameState() == InGameState.RACING) {
            body.applyForce(currentForwardNormal.mul(force * currentTraction),
                    body.getWorldCenter());
        }
    }

    /**
     * Method addGroundArea.
     * 
     * @param ga
     *            GroundAreaFUD
     */
    public void addGroundArea(GroundAreaFUD ga) {
        groundAreas.add(ga);
        updateTraction();
    }

    /**
     * Method removeGroundArea.
     * 
     * @param ga
     *            GroundAreaFUD
     */
    public void removeGroundArea(GroundAreaFUD ga) {
        groundAreas.remove(ga);
        updateTraction();
    }

    /**
     * Method updateTraction
     */
    public void updateTraction() {
        if (groundAreas.isEmpty()) {
            currentTraction = 1;
        } else {
            Iterator<GroundAreaFUD> it = null;
            GroundAreaFUD ga = null;
            currentTraction = 0;

            // find area with highest traction
            it = groundAreas.iterator();
            while (it.hasNext()) {
                ga = it.next();
                if (ga.getFrictionModifier() > currentTraction) {
                    currentTraction = ga.getFrictionModifier();
                }
            }
        }
    }

    /*
     * Accessors
     */

    /**
     * Method getMaxForwardSpeed.
     * 
     * @return float
     */
    public float getMaxForwardSpeed() {
        return maxForwardSpeed;
    }

    /**
     * Method setMaxForwardSpeed.
     * 
     * @param maxForwardSpeed
     *            float
     */
    public void setMaxForwardSpeed(float maxForwardSpeed) {
        this.maxForwardSpeed = maxForwardSpeed;
    }

    /**
     * Method getMaxBackwardSpeed.
     * 
     * @return float
     */
    public float getMaxBackwardSpeed() {
        return maxBackwardSpeed;
    }

    /**
     * Method setMaxBackwardSpeed.
     * 
     * @param maxBackwardSpeed
     *            float
     */
    public void setMaxBackwardSpeed(float maxBackwardSpeed) {
        this.maxBackwardSpeed = maxBackwardSpeed;
    }

    /**
     * Method getMaxDriveForce.
     * 
     * @return float
     */
    public float getMaxDriveForce() {
        return maxDriveForce;
    }

    /**
     * Method setMaxDriveForce.
     * 
     * @param maxDriveForce
     *            float
     */
    public void setMaxDriveForce(float maxDriveForce) {
        this.maxDriveForce = maxDriveForce;
    }

    /**
     * Method getMaxLateralImpulse.
     * 
     * @return float
     */
    public float getMaxLateralImpulse() {
        return maxLateralImpulse;
    }

    /**
     * Method setMaxLateralImpulse.
     * 
     * @param maxLateralImpulse
     *            float
     */
    public void setMaxLateralImpulse(float maxLateralImpulse) {
        this.maxLateralImpulse = maxLateralImpulse;
    }

    /**
     * Method getTireId.
     * 
     * @return int
     */
    public int getTireId() {
        return tireId;
    }

    /**
     * Method setTireId.
     * 
     * @param tireId
     *            int
     */
    public void setTireId(int tireId) {
        this.tireId = tireId;
    }

    public boolean isSkidding() {
        return isSkidding;
    }

    public void setSkidding(boolean isSkidding) {
        this.isSkidding = isSkidding;
    }

    /***
     * Method toString
     */
    @Override
    public String toString() {
        return "TDTire [maxForwardSpeed=" + maxForwardSpeed
                + ", maxBackwardSpeed=" + maxBackwardSpeed + ", maxDriveForce="
                + maxDriveForce + ", maxLateralImpulse=" + maxLateralImpulse
                + ", groundAreas=" + groundAreas + ", currentTraction="
                + currentTraction + ", drift=" + drift + ", isRear=" + isRear
                + ", handbrake=" + handbrake + ", currentSpeed=" + currentSpeed
                + ", tireId=" + tireId + ", isSkidding=" + isSkidding
                + ", skidPointCounter=" + skidPointCounter + "]";
    }
}

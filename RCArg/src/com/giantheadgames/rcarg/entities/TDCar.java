/***
 * Clase de Auto
 */

package com.giantheadgames.rcarg.entities;

import java.util.Arrays;

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
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.giantheadgames.rcarg.RCArg;
import com.giantheadgames.rcarg.entities.base.BaseBoxObject;
import com.giantheadgames.rcarg.entities.base.BaseTexture;
import com.giantheadgames.rcarg.fud.CarFUD;
import com.giantheadgames.rcarg.managers.ParticleManager;
import com.giantheadgames.rcarg.managers.SoundManager;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public class TDCar extends BaseBoxObject {

	private TDTire[] tires = new TDTire[RCArg.params.CAR_TIRE_NUMBER];
	private RevoluteJoint flJoint, frJoint;

	private BaseTexture carShadow = null;

	/**
	 * Constructor for TDCar.
	 * 
	 * @param world
	 *            World
	 * @param pos
	 *            Vector2
	 */
	public TDCar(World world, Vector2 pos) {
		super(pos);

		BodyDef bodyDef = new BodyDef();
		PolygonShape polygonShape = new PolygonShape();
		Fixture fix = null;
		RevoluteJointDef jointDef = null;
		TDTire tire = null;
		Texture carTexture = null;
		Texture carShadowTexture = null;

		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos.x, pos.y);
		body = world.createBody(bodyDef);

		polygonShape.set(RCArg.params.CAR_VERTICES);

		fix = body.createFixture(polygonShape, RCArg.params.CAR_DENSITY);
		body.setUserData(this);
		polygonShape.dispose();
		fix.setRestitution(RCArg.params.CAR_RESTITUTION);
		fix.setUserData(new CarFUD());

		jointDef = new RevoluteJointDef();
		jointDef.bodyA = body;
		jointDef.enableLimit = true;
		jointDef.lowerAngle = 0;
		jointDef.upperAngle = 0;
		jointDef.localAnchorB.set(Vector2.Zero);

		// llanta adelante izq
		tire = new TDTire(RCArg.params.CAR_TIRE_ID_1, world, new Vector2(pos.x + 5f,
				pos.y + 5f), false);
		jointDef.bodyB = tire.getBody();
		jointDef.localAnchorA.set(RCArg.params.CAR_FL_JOINT);
		flJoint = (RevoluteJoint) world.createJoint(jointDef);
		tires[0] = tire;

		// llanta adelante der
		tire = new TDTire(RCArg.params.CAR_TIRE_ID_2, world, new Vector2(pos.x + -5f,
				pos.y + 5f), false);
		jointDef.bodyB = tire.getBody();
		jointDef.localAnchorA.set(RCArg.params.CAR_FR_JOINT);
		frJoint = (RevoluteJoint) world.createJoint(jointDef);
		tires[1] = tire;

		// llanta atras izq
		tire = new TDTire(RCArg.params.CAR_TIRE_ID_3, world, new Vector2(pos.x + 5f,
				pos.y - 5f), true);
		jointDef.bodyB = tire.getBody();
		jointDef.localAnchorA.set(RCArg.params.CAR_RL_JOINT);
		world.createJoint(jointDef);
		tires[2] = tire;

		// llanta atras izq
		tire = new TDTire(RCArg.params.CAR_TIRE_ID_4, world, new Vector2(pos.x + -5f,
				pos.y - 5f), true);
		jointDef.bodyB = tire.getBody();
		jointDef.localAnchorA.set(RCArg.params.CAR_RR_JOINT);
		world.createJoint(jointDef);
		tires[3] = tire;

		carTexture = new Texture(Gdx.files.internal(RCArg.params.CAR_TEXTURE_PATH));
		carShadowTexture = new Texture(
				Gdx.files.internal(RCArg.params.CAR_SHADOW_PATH));
		texture = new BaseTexture(new TextureRegion(carTexture), pos);
		carShadow = new BaseTexture(new TextureRegion(carShadowTexture), pos);
		
		SoundManager.loopEngineSound();

	}

	/**
	 * Method update.
	 * 
	 * @param delta
	 *            float
	 */
	@Override
	public void update(float delta) {
		float lockAngle = 0f;
		float turnSpeedPerSec = 0f;
		float turnPerTimeStep = 0f;
		float desiredAngle = 0f;
		float angleNow = 0f;
		float angleToTurn = 0f;
		float newAngle = 0f;

		for (TDTire tire : tires) {
			tire.updateFriction();
		}
		for (TDTire tire : tires) {
			tire.update(delta);
			tire.updateDrive();
		}

		lockAngle = RCArg.params.CAR_LOCK_ANGLE * MathUtils.degreesToRadians;
		turnSpeedPerSec = RCArg.params.CAR_TURN_SPEED_PSEC
				* MathUtils.degreesToRadians;
		turnPerTimeStep = turnSpeedPerSec / RCArg.params.SECONDS;
		desiredAngle = 0;

		if (Gdx.input.isKeyPressed(Keys.LEFT)
				|| (Gdx.input.isButtonPressed(Keys.VOLUME_UP))) {
			desiredAngle = lockAngle;
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)
				|| (Gdx.input.isButtonPressed(Keys.VOLUME_DOWN))) {
			desiredAngle = -lockAngle;
		}

		angleNow = flJoint.getJointAngle();
		angleToTurn = desiredAngle - angleNow;
		angleToTurn = MathUtils.clamp(angleToTurn, -turnPerTimeStep,
				turnPerTimeStep);
		newAngle = angleNow + angleToTurn;
		flJoint.setLimits(newAngle, newAngle);
		frJoint.setLimits(newAngle, newAngle);

		updateWorldPosition();
		texture.setPosition(worldPosition);
		texture.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		Vector2 shadowPos = new Vector2(worldPosition);
		shadowPos.x = shadowPos.x + 15f;
		shadowPos.y = shadowPos.y - 15f;
		carShadow.setPosition(shadowPos);
		carShadow.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		
		SoundManager.setEngineSoundPitch(0.10f + this.getSpeedKMH()/100);

		if (RCArg.params.DIRT_ENABLED) {

			if (tires[0].isSkidding() || tires[1].isSkidding()
					|| tires[2].isSkidding() || tires[3].isSkidding()) {
				ParticleManager.getEmmiter().start();
			} else {
				ParticleManager.getEmmiter().allowCompletion();
			}

			ParticleManager.update(this.getWorldPosition(), delta, this
					.getTexture().getRotation() - RCArg.params.CAR_BASE_ANGLE,
					this.getSpeedKMH());
		}
		
		if (tires[0].isSkidding() || tires[1].isSkidding()
                || tires[2].isSkidding() || tires[3].isSkidding()) {
            SoundManager.loopScreechSound();
        } else {
            SoundManager.stopScreechSound();
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
		for (TDTire tire : tires) {
			if (tire.getTireId() == RCArg.params.CAR_FL_TIRE
					|| tire.getTireId() == RCArg.params.CAR_FR_TIRE) {
				tire.draw(sp);
			}
		}
		if (RCArg.params.DIRT_ENABLED) {
			ParticleManager.draw(sp);
		}
		carShadow.draw(sp);
		texture.draw(sp);
	}

	/**
	 * Method getTires.
	 * 
	 * @return TDTire[]
	 */
	public TDTire[] getTires() {
		return tires;
	}

	/**
	 * Method setTires.
	 * 
	 * @param tires
	 *            TDTire[]
	 */
	public void setTires(TDTire[] tires) {
		this.tires = tires;
	}

	/***
	 * Method toString
	 */
	@Override
	public String toString() {
		return "TDCar [tires=" + Arrays.toString(tires) + ", flJoint="
				+ flJoint + ", frJoint=" + frJoint + "]";
	}

	@Override
	public void dispose() {
		for(TDTire tire : tires){
			tire.dispose();
		}
		
		SoundManager.stopEngineSound();
		
		super.dispose();
	}
}

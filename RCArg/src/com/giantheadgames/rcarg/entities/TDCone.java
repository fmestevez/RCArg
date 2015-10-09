/***
 * Clase de cono (temporal)
 */

package com.giantheadgames.rcarg.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.giantheadgames.rcarg.RCArg;
import com.giantheadgames.rcarg.entities.base.BaseBoxObject;
import com.giantheadgames.rcarg.entities.base.BaseTexture;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public class TDCone extends BaseBoxObject {

	/**
	 * Constructor for TDTransitCone.
	 * 
	 * @param pos
	 *            Vector2
	 * @param world
	 *            World
	 */
	public TDCone(Vector2 pos, World world) {
		super(pos);
		PolygonShape sh = new PolygonShape();
		BodyDef bodyDef = new BodyDef();
		FixtureDef fd = new FixtureDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos.x, pos.y);
		body = world.createBody(bodyDef);
		sh.setAsBox(RCArg.params.CONE_SHAPE.x, RCArg.params.CONE_SHAPE.y);
		fd.friction = RCArg.params.CONE_FRICTION;
		fd.shape = sh;
		fd.restitution = 0.5f;
		fd.density = 1.0f;
		body.createFixture(fd);
		sh.dispose();

		texture = new BaseTexture(new TextureRegion(new Texture(
				Gdx.files.internal(RCArg.params.CONE_TEXTURE_PATH))), pos);
	}

	/**
	 * Method update.
	 * 
	 * @param delta
	 *            float
	 */
	@Override
	public void update(float delta) {
		updateWorldPosition();
		updateFriction();
		texture.setPosition(worldPosition);
		texture.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
	}

	/**
	 * Method updateFriction
	 */
	public void updateFriction() {

		// Anulo la velocidad angular (para que no gire sobre su eje)
		body.applyAngularImpulse(RCArg.params.TIRE_ANGULAR_IMP_SCALAR
				* body.getInertia() * -body.getAngularVelocity());

		Vector2 velInverse = body.getLinearVelocity().mul(-1f);
		velInverse = velInverse.mul(5f);
		body.applyForce(velInverse, body.getWorldCenter());
		// body.applyTorque(20f);
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
	 * Method draw.
	 * 
	 * @param sp
	 *            SpriteBatch
	 */
	@Override
	public void draw(SpriteBatch sp) {
		texture.draw(sp);
	}

	/***
	 * Method toString
	 */
	@Override
	public String toString() {
		return "TDTransitCone []";
	}
}

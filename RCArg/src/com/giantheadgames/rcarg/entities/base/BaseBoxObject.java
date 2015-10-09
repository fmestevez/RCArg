/***
 * Clase base de un cuerpo f�sico.
 */

package com.giantheadgames.rcarg.entities.base;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.giantheadgames.rcarg.RCArg;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public abstract class BaseBoxObject extends BaseObject implements Disposable {

	protected Body body;

	/**
	 * Constructor for BaseBoxObject.
	 * 
	 * @param pos
	 *            Vector2
	 */
	protected BaseBoxObject(Vector2 pos) {
		worldPosition = pos;
	}

	/**
	 * Method updateWorldPosition
	 * 
	 */
	public void updateWorldPosition() {
		worldPosition.set(body.getPosition().x * RCArg.params.MAIN_METER_TO_PIXEL,
				body.getPosition().y * RCArg.params.MAIN_METER_TO_PIXEL);
	}

	/**
	 * Method getSpeedKMH.
	 * 
	 * @return int
	 */
	public float getSpeedKMH() {
		Vector2 velocity = body.getLinearVelocity();
		float len = velocity.len();
		return ((len / 1000) * 3600);
	}

	/**
	 * Method dispose.
	 * 
	 * @see com.badlogic.gdx.utils.Disposable#dispose()
	 */
	@Override
	public void dispose() {
		// Destruyo la referencia al cuerpo
		body.getWorld().destroyBody(body);
	}

	/**
	 * Method getBody.
	 * 
	 * @return Body
	 */
	public Body getBody() {
		return body;
	}

	/**
	 * Method setBody.
	 * 
	 * @param body
	 *            Body
	 */
	public void setBody(Body body) {
		this.body = body;
	}
}

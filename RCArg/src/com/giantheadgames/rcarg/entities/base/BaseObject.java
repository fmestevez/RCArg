/***
 * Clase base de un sprite
 */

package com.giantheadgames.rcarg.entities.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public abstract class BaseObject {

	protected Vector2 worldPosition;
	protected BaseTexture texture;

	/**
	 * Method update.
	 * 
	 * @param delta
	 *            float
	 */
	public abstract void update(float delta);

	/**
	 * Method draw.
	 * 
	 * @param sp
	 *            SpriteBatch
	 */
	public abstract void draw(SpriteBatch sp);

	/*
	 * Accessors
	 */

	/**
	 * Method getWorldPosition.
	 * 
	 * @return Vector2
	 */
	public Vector2 getWorldPosition() {
		return worldPosition;
	}

	/**
	 * Method setWorldPosition.
	 * 
	 * @param worldPosition
	 *            Vector2
	 */
	public void setWorldPosition(Vector2 worldPosition) {
		this.worldPosition = worldPosition;
	}

	/**
	 * Method getTexture.
	 * 
	 * @return TextureWrapper
	 */
	public BaseTexture getTexture() {
		return texture;
	}

	/**
	 * Method setTexture.
	 * 
	 * @param texture
	 *            TextureWrapper
	 */
	public void setTexture(BaseTexture texture) {
		this.texture = texture;
	}
}

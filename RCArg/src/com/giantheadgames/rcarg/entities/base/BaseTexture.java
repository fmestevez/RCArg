/***
 * Clase base de Textura
 */

package com.giantheadgames.rcarg.entities.base;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public class BaseTexture {
	TextureRegion region;
	int width;
	int height;
	Vector2 position;
	float scaleX;
	float scaleY;
	float originX;
	float originY;
	float rotation;

	/**
	 * Constructor for TextureWrapper.
	 * 
	 * @param region
	 *            TextureRegion
	 * @param pos
	 *            Vector2
	 */
	public BaseTexture(TextureRegion region, Vector2 pos) {
		position = pos;
		setTextureRegion(region);
	}

	/**
	 * Method SetTextureRegion.
	 * 
	 * @param region
	 *            TextureRegion
	 */
	public void setTextureRegion(TextureRegion region) {
		this.region = region;
		this.region.getTexture().setFilter(TextureFilter.Linear,
				TextureFilter.Linear);
		width = region.getRegionWidth();
		height = region.getRegionHeight();
		originX = width / 2f;
		originY = height / 2f;
		scaleX = 1f; // 0.016f
		scaleY = 1f;
	}

	/**
	 * Method getWidth.
	 * 
	 * @return int
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Method getHeight.
	 * 
	 * @return int
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Method setPosition.
	 * 
	 * @param vec2
	 *            Vector2
	 */
	public void setPosition(Vector2 vec2) {
		position = vec2.cpy();
	}

	/**
	 * Method setRotation.
	 * 
	 * @param r
	 *            float
	 */
	public void setRotation(float r) {
		rotation = r;
	}

	/**
	 * Method getRotation.
	 * 
	 * @return float
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * Method draw.
	 * 
	 * @param sp
	 *            SpriteBatch
	 */
	public void draw(SpriteBatch sp) {
		sp.draw(region, position.x - width / 2, position.y - height / 2,
				originX, originY, width, height, scaleX, scaleY, rotation);
	}
	
	public void draw(SpriteBatch sp, float alpha) {
//		sp.setColor(region, g, b, a)
//		Sprite
		sp.draw(region, position.x - width / 2, position.y - height / 2,
				originX, originY, width, height, scaleX, scaleY, rotation);
	}

	/**
	 * Method getRegion.
	 * 
	 * @return TextureRegion
	 */
	public TextureRegion getRegion() {
		return region;
	}

	/**
	 * Method setRegion.
	 * 
	 * @param region
	 *            TextureRegion
	 */
	public void setRegion(TextureRegion region) {
		this.region = region;
	}

	/**
	 * Method getTextureRadius.
	 * 
	 * @return int
	 */
	public int getTextureRadius() {
		return Math.max(width, height);
	}
}
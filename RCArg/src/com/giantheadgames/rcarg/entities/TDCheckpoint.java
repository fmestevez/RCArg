package com.giantheadgames.rcarg.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.giantheadgames.rcarg.entities.base.BaseBoxObject;
import com.giantheadgames.rcarg.entities.base.BaseTexture;

public class TDCheckpoint extends BaseBoxObject {

	private int signIndex;
	private float signRotation;
	private float signAlpha = 0f;
	private int fadein = 0;

	public TDCheckpoint(Body body) {
		super(null);
		this.body = body;
		this.body.setUserData(this);
	}

	@Override
	public void update(float delta) {
		if (fadein == 0) {
			signAlpha = signAlpha + 0.01f;
			if (signAlpha > 0.5f) {
				fadein = 1;
			}
		} else if (fadein == 1){
			signAlpha = signAlpha - 0.01f;
			if (signAlpha < 0.02f){
				fadein = -1;
			}
		}
	}

	@Override
	public void draw(SpriteBatch sp) {

	}

	public void draw(SpriteBatch sp, Camera camera, Texture texture) {
		Sprite sprite = new Sprite(texture);
		sprite.setPosition(camera.position.x + camera.viewportWidth / 3.5f,
				camera.position.y + camera.viewportHeight / 3.5f);
		sprite.setRotation(this.getSignRotation());
		sprite.draw(sp, signAlpha);
	}

	public int getSignIndex() {
		return signIndex;
	}

	public void setSignIndex(int signIndex) {
		this.signIndex = signIndex;
	}

	public float getSignRotation() {
		return signRotation;
	}

	public void setSignRotation(float signRotation) {
		this.signRotation = signRotation;
	}

	public float getSignAlpha() {
		return signAlpha;
	}

	public void setSignAlpha(float signAlpha) {
		this.signAlpha = signAlpha;
	}

	public void setTexture(BaseTexture texture) {
		this.texture = texture;
	}
	
	public void fadeIn(){
		fadein = 0;
	}
}

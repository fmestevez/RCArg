/***
 * Manager de part�culas
 */

package com.giantheadgames.rcarg.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.giantheadgames.rcarg.RCArg;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public class ParticleManager {

	private static ParticleEffect particleEffect;
	private static Array<ParticleEmitter> emmiters;

	/**
	 * Method init
	 */
	public static void init() {
		particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal(RCArg.params.DIRT_FX_PATH),
				Gdx.files.internal(RCArg.params.DIRT_FX_FOLDER));
		emmiters = new Array<ParticleEmitter>(particleEffect.getEmitters());
		particleEffect.getEmitters().add(emmiters.get(0));
		emmiters.get(0).setMinParticleCount(0);
		emmiters.get(0).setMaxParticleCount(RCArg.params.DIRT_MAX_PARTICLES);
		emmiters.get(0).getVelocity().setHigh(0);
	}

	/**
	 * Method update.
	 * 
	 * @param pos
	 *            Vector2
	 * @param delta
	 *            float
	 * @param angle
	 *            float
	 * @param speed
	 *            float
	 */
	public static void update(Vector2 pos, float delta, float angle, float speed) {
		particleEffect.setPosition(pos.x, pos.y);
		emmiters.get(0).getAngle().setLow(angle);
		emmiters.get(0).getVelocity().setHigh(speed);
		emmiters.get(0).getTransparency().setHigh(0.03f + (speed / 1000));
		particleEffect.update(delta);
	}

	/**
	 * Method draw.
	 * 
	 * @param sb
	 *            SpriteBatch
	 */
	public static void draw(SpriteBatch sb) {
		particleEffect.draw(sb);
	}

	public static ParticleEmitter getEmmiter() {
		return emmiters.get(0);
	}
	
	public static void dispose(){
	    if(particleEffect != null){
	        particleEffect.dispose();
	    }
	    emmiters = null;
	}
}

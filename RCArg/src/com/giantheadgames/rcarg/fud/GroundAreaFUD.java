/***
 * FUD de área de suelo
 */

package com.giantheadgames.rcarg.fud;

import com.giantheadgames.rcarg.fud.base.FixtureUserData;
import com.giantheadgames.rcarg.fud.base.FixtureUserDataType;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public class GroundAreaFUD extends FixtureUserData {

	float frictionModifier;
	boolean outOfCourse;

	/**
	 * Constructor for GroundAreaFUD.
	 * 
	 * @param frictionModifier
	 *            float
	 * @param outOfCourse
	 *            boolean
	 */
	public GroundAreaFUD(float frictionModifier, boolean outOfCourse) {
		super(FixtureUserDataType.FUD_GROUND_AREA);

		this.frictionModifier = frictionModifier;
		this.outOfCourse = outOfCourse;

	}

	/**
	 * Method getFrictionModifier.
	 * 
	 * @return float
	 */
	public float getFrictionModifier() {
		return frictionModifier;
	}

	/**
	 * Method setFrictionModifier.
	 * 
	 * @param frictionModifier
	 *            float
	 */
	public void setFrictionModifier(float frictionModifier) {
		this.frictionModifier = frictionModifier;
	}

	/**
	 * Method isOutOfCourse.
	 * 
	 * @return boolean
	 */
	public boolean isOutOfCourse() {
		return outOfCourse;
	}

	/**
	 * Method setOutOfCourse.
	 * 
	 * @param outOfCourse
	 *            boolean
	 */
	public void setOutOfCourse(boolean outOfCourse) {
		this.outOfCourse = outOfCourse;
	}

	/***
	 * Method toString
	 */
	@Override
	public String toString() {
		return "GroundAreaFUD [frictionModifier=" + frictionModifier
				+ ", outOfCourse=" + outOfCourse + "]";
	}

}

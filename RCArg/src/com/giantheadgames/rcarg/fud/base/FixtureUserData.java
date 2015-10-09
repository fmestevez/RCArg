/***
 * Clase base de FUD
 */

package com.giantheadgames.rcarg.fud.base;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public class FixtureUserData {
	FixtureUserDataType type;

	/**
	 * Constructor for FixtureUserData.
	 * 
	 * @param type
	 *            FixtureUserDataType
	 */
	protected FixtureUserData(FixtureUserDataType type) {
		this.type = type;
	}

	/**
	 * Method getType.
	 * 
	 * @return FixtureUserDataType
	 */
	public FixtureUserDataType getType() {
		return type;
	}
}
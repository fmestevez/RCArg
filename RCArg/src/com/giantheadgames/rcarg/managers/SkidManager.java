/***
 * Manager de derrapes
 */

package com.giantheadgames.rcarg.managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.giantheadgames.rcarg.RCArg;

/***
 * 
 * @author Facundo Estevez
 * 
 *         Una clase de la que no estoy para nada orgulloso. Un asco.
 * @version $Revision: 1.0 $
 */
public class SkidManager {
	private static ShapeRenderer sr;
	private static List<Vector2> Points1;
	private static List<Vector2> Points2;
	private static List<Vector2> Points3;
	private static List<Vector2> Points4;

	/**
	 * Method init
	 */
	public static void init() {
		sr = new ShapeRenderer();
		Points1 = new ArrayList<Vector2>();
		Points2 = new ArrayList<Vector2>();
		Points3 = new ArrayList<Vector2>();
		Points4 = new ArrayList<Vector2>();
	}

	/**
	 * Method add.
	 * 
	 * @param tireId
	 *            int
	 * @param pos
	 *            Vector2
	 */
	public static void add(int tireId, Vector2 pos) {
		switch (tireId) {
		case 1:
			Points1.add(pos);
			if (Points1.size() > RCArg.params.SKID_MAX_LENGTH) {
				Points1.remove(0);
			}
			break;

		case 2:
			Points2.add(pos);
			if (Points2.size() > RCArg.params.SKID_MAX_LENGTH) {
				Points2.remove(0);
			}
			break;
		case 3:
			Points3.add(pos);
			if (Points3.size() > RCArg.params.SKID_MAX_LENGTH) {
				Points3.remove(0);
			}
			break;
		case 4:
			Points4.add(pos);
			if (Points4.size() > RCArg.params.SKID_MAX_LENGTH) {
				Points4.remove(0);
			}
			break;
		default:
			return;
		}

	}

	/**
	 * Method init
	 */
	public static void draw(Matrix4 projectionMatrix) {
		sr.begin(ShapeType.Line);
		sr.setProjectionMatrix(projectionMatrix);
		sr.setColor(new Color(RCArg.params.SKID_COLOR[0],
		        RCArg.params.SKID_COLOR[1],
		        RCArg.params.SKID_COLOR[2],
		        RCArg.params.SKID_COLOR[3]));
		ohMyGod(Points1);
		ohMyGod(Points2);
		ohMyGod(Points3);
		ohMyGod(Points4);
		sr.end();
	}

	/**
	 * Method ohMyGod.
	 * ParticleEffect.dispose();
        Emmiters = null;
	 * @param points
	 *            ArrayList<Vector2>
	 */
	private static void ohMyGod(List<Vector2> points) {
		float prevx = 0;
		float prevy = 0;
		for (Vector2 pos : points) {
			if (prevx == 0 || prevy == 0) {
				prevx = pos.x;
				prevy = pos.y;
			}

			if (pos.dst(prevx, prevy) < RCArg.params.SKID_MIN_SEP) {
				sr.line(prevx, prevy, pos.x, pos.y);
				for (int i = 0; i < RCArg.params.SKID_TICKNESS + 1; i++) {
					sr.line(prevx + i, prevy + i, pos.x + i, pos.y + i);
					sr.line(prevx - i, prevy - i, pos.x - i, pos.y - i);
					sr.line(prevx + i, prevy - i, pos.x + i, pos.y - i);
					sr.line(prevx - i, prevy + i, pos.x - i, pos.y + i);
				}
			}
			prevx = pos.x;
			prevy = pos.y;
		}
	}
	
	public static void dispose(){
	    if(sr != null){
	        sr.dispose();
	    }
	    Points1 = null;
	    Points2 = null;
	    Points3 = null;
	    Points4 = null;
    }
}

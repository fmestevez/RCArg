/***
 * Clase de par�metros
 */

package com.giantheadgames.rcarg;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * @author Facundo Estevez
 * @version $Revision: 1.0 $
 */
public class Params implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /* Main */
    public Vector2 MAIN_GRAVITY = Vector2.Zero;
    public Vector2 MAIN_VIEWPORT_SIZE = new Vector2(800, 600);
    public Vector3 MAIN_CAMERA_POS = Vector3.Zero;
    public int MAIN_VEL_ITERATIONS = 10; // 10
    public int MAIN_POS_ITERATIONS = 8; // 8
    public float MAIN_METER_TO_PIXEL = 62.5f;

    /* Car */

    public Vector2[] CAR_VERTICES = { new Vector2(0.45f, -1.85f),
            new Vector2(0.84f, -1.47f), new Vector2(0.73f, 1.63f),
            new Vector2(0.48f, 1.86f), new Vector2(-0.48f, 1.86f),
            new Vector2(-0.73f, 1.63f), new Vector2(-0.84f, -1.47f),
            new Vector2(-0.45f, -1.85f) };
    public Vector2 CAR_FL_JOINT = new Vector2(-0.82f, 1.16f); // -0.82f, 1.16
    public Vector2 CAR_FR_JOINT = new Vector2(0.82f, 1.16f);
    public Vector2 CAR_RL_JOINT = new Vector2(-0.82f, -1.2f);
    public Vector2 CAR_RR_JOINT = new Vector2(0.82f, -1.2f);
    public String CAR_TEXTURE_PATH = "data/car.png"; //$NON-NLS-1$
    public String CAR_SHADOW_PATH = "data/car_shadow.png";
    public int CAR_BASE_ANGLE = 90;
    public float CAR_LOCK_ANGLE = 40; // 40
    public float CAR_TURN_SPEED_PSEC = 320; // 320
    public float CAR_DENSITY = 1f;
    public int CAR_FL_TIRE = 1;
    public int CAR_FR_TIRE = 2;
    public int CAR_TIRE_NUMBER = 4;
    public float CAR_RESTITUTION = 0.3f;
    public int CAR_TIRE_ID_1 = 1;
    public int CAR_TIRE_ID_2 = 2;
    public int CAR_TIRE_ID_3 = 3;
    public int CAR_TIRE_ID_4 = 4;

    /* Tire */

    public Vector2 TIRE_BODY_SIZE = new Vector2(0.1f, 0.26f);
    public String TIRE_TEXTURE_PATH = "data/tire.png"; //$NON-NLS-1$
    public float TIRE_MAX_FORWARD_SPD = 12;
    public float TIRE_MAX_BACKWARD_SPD = -4;
    public int TIRE_MAX_SPD_KMH = 220;
    public int TIRE_MAX_B_SPD_KMH = 60;
    public float TIRE_MAX_DRIVE_FORCE = 20;
    public float TIRE_MAX_LATERAL_IMPUL = 0.5f;
    public float TIRE_DEFAULT_TRACTION = 0.8f;
    public float TIRE_DENSITY = 1f;
    public float TIRE_ANGULAR_IMP_SCALAR = 0.1f;
    public float TIRE_DRAG_FORCE_MAG_SCALAR = -2f;
    public float TIRE_DRAG_FORCE_MAG_SCALAR_HB = -32f;
    public int TIRE_FUD_INITIAL_CAPACITY = 32;
    public float TIRE_FUD_LOAD_FACTOR = 0.75f;

    /* Skid */

    public boolean SKID_ENABLED = true;
    public int SKID_MAX_LENGTH = 40;
    public int SKID_TICKNESS = 3;
    public int SKID_MIN_SEP = 80; // 80
    public float [] SKID_COLOR = {0.2f, 0.2f, 0.2f, 1f};
    public int SKID_POINT_LIMIT = 2;

    /* Dirt */

    public boolean DIRT_ENABLED = true;
    public int DIRT_MAX_PARTICLES = 60;
    public String DIRT_FX_PATH = "data/effects/dirt.fx"; //$NON-NLS-1$
    public String DIRT_FX_FOLDER = "data/effects"; //$NON-NLS-1$
    public int DIRT_MIN_SPEED = 6;

    /* Others */
    public String CONE_TEXTURE_PATH = "data/cone.png"; //$NON-NLS-1$
    public Vector2 CONE_SHAPE = new Vector2(0.2f, 0.2f);
    public float CONE_FRICTION = 0.7f;
    public String CAR_SPEED_UNIT = " KM/H"; //$NON-NLS-1$
    public int ZERO = 0;
    public Object NULL = null;
    public float SECONDS = 60f;

    // 0 = sign180left
    // 1 = sign180right
    // 2 = sign90left
    // 3 = sign90right
    public int[] CP_SIGN_INDEXES = { -1, 3, 3, 3, 1, 2, 0, 1, 3, 0, 2, 1 };
    public int[] CP_SIGN_ROTATION = { -1, 360, 270, 180, 90, 270, 360, 180,
            360, 270, 90, 180 };

    public boolean MUSIC_ENABLED = true;
    public boolean SOUND_ENABLED = true;
}

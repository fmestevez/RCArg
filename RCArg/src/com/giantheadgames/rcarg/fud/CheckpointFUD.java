package com.giantheadgames.rcarg.fud;

import com.giantheadgames.rcarg.fud.base.FixtureUserData;
import com.giantheadgames.rcarg.fud.base.FixtureUserDataType;

public class CheckpointFUD extends FixtureUserData{
    
    private int cpId;

	public CheckpointFUD(int cpId) {
	    super(FixtureUserDataType.FUD_CP_NORMAL);
        this.cpId = cpId;
    }

    public int getCpId() {
        return cpId;
    }


    public void setCpId(int cpId) {
        this.cpId = cpId;
    }
}

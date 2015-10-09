package com.giantheadgames.rcarg.managers;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.giantheadgames.rcarg.RCArg;
import com.giantheadgames.rcarg.entities.TDCheckpoint;
import com.giantheadgames.rcarg.fud.CheckpointBeginFUD;
import com.giantheadgames.rcarg.fud.CheckpointFUD;
import com.giantheadgames.rcarg.fud.WallFUD;

public class TrackManager {
	
	private Body track = null;
	private List<TDCheckpoint> checkpoints = null;
	
	public void init(World world){
		loadTrack(world);
		loadCheckpoints(world);
	}
	
	private void loadTrack(World world){
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/track/track.json"));
		
	    BodyDef bd = new BodyDef();
	    bd.position.set(0, 0);
	    bd.type = BodyType.StaticBody;
	 
	    FixtureDef fd = new FixtureDef();
	    fd.density = 0;
	    fd.friction = 0.5f;
	 
	    track = world.createBody(bd);
	 
	    loader.attachFixture(track, "track", fd, 102.4f);
	    for(Fixture fx : track.getFixtureList()){
	        fx.setUserData(new WallFUD());
	    }
	}
	
	private void loadCheckpoints(World world){
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/track/cp.json"));
		checkpoints = new ArrayList<TDCheckpoint>();
		
		for(int i = 0; i < 12; i++){
			BodyDef bd = new BodyDef();
		    bd.type = BodyType.StaticBody;
		 
		    FixtureDef fd = new FixtureDef();
		    fd.isSensor = true;
		    
		    Body cpBody = world.createBody(bd);
		    
		    TDCheckpoint cp = new TDCheckpoint(cpBody);
		    cp.setSignIndex(RCArg.params.CP_SIGN_INDEXES[i]);
		    cp.setSignRotation(RCArg.params.CP_SIGN_ROTATION[i]);
		    
		    loader.attachFixture(cpBody, "cp" + i, fd, 102.4f);
		    
		    if(i == 0) {
		    	cpBody.getFixtureList().get(0).setUserData(new CheckpointBeginFUD());
		    } else {
		    	cpBody.getFixtureList().get(0).setUserData(new CheckpointFUD(i));
		    }
		    
			checkpoints.add(cp);
		}
	}
}

package com.example.v4;

import org.andengine.entity.sprite.TiledSprite;

public class Obstacles {

	final int WIDTH_OBS = 25;
	final int HEIGHT_OBS = 25;
	
	public TiledSprite Obs;
	
	BaseActivity base_activity;
	
	
	  public Obstacles(){
		  
			  
		  base_activity = BaseActivity.getSharedInstance();
	
		  double random = Math.random();
	      float pX = (float) (random * 480);
	      float pY = (float) (random * 400);
		  Obs = new TiledSprite(pX,pY,WIDTH_OBS,HEIGHT_OBS, base_activity.mObstaclesTextureRegion, base_activity.getVertexBufferObjectManager());
		   
	  }
	 
}

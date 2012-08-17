package com.example.v4;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.util.Constants;
import org.andengine.util.math.MathUtils;

import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;

import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.touch.TouchEvent;


public class Juego extends Scene{
    
	final int BOUND_CAMERA_WIDTH = 480;
	final int BOUND_CAMERA_HEIGHT = 320;
	
	private BoundCamera mBoundChaseCamera;
	
	public static Juego instance;
	
	BaseActivity base_activity;

  
	public Juego()
	{
	  

		instance = this;
		base_activity = BaseActivity.getSharedInstance();
		
		
		//Añadimos escena1
		Escena1 e1 = new Escena1();
		Personaje p1 = new Personaje();
		
		attachChild(e1.getLayer());
		 
		base_activity.mBoundChaseCamera.setBounds(0,0,e1.getLayer().getHeight(),e1.getLayer().getWidth());
		base_activity.mBoundChaseCamera.setBoundsEnabled(true);
		
		//Añadimos Personaje a la escena:
		attachChild(p1.getSprite());
		
		base_activity.mBoundChaseCamera.setChaseEntity(p1.getSprite());
		
		// ************************************************
		 
		 //**************************************************************
 
		 final Path path = new Path(5).to(0, 160).to(0, 500).to(600, 500).to(600, 160).to(0, 160);
		 p1.getSprite().registerEntityModifier(new LoopEntityModifier(new PathModifier(30, path, null, new IPathModifierListener() {
				@Override
				public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {

				}

				@Override
				public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
					System.out.println("EN JUEGO PERSONAJE *****");
				}

				@Override
				public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {

				}

				@Override
				public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {

				}
			})));
	 

		 registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() { }

				@Override
				public void onUpdate(final float pSecondsElapsed) {
					
				}
			}); 
		 
		 
	}
	
public static Juego getSharedInstance() {
		
	    return instance;
	    
	}
}


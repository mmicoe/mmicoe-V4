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
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.util.Constants;

import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;

import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.extension.tmx.TMXTile;

public class Juego extends Scene {
    
	final int BOUND_CAMERA_WIDTH = 480;
	final int BOUND_CAMERA_HEIGHT = 320;
	
	public BoundCamera mBoundChaseCamera;
	
	public static Juego instance;
	
	public Personaje perso1;
	public Escena1 level1;
	
	
	public Juego() {
		
		
		 
		 instance = this;
		 
		final BoundCamera mBoundChaseCamera = new BoundCamera(0, 0, 480, 320);
		 
		//Añadimos escena1
		Escena1 e1 = new Escena1();
		attachChild(e1.tmxLayer);
		
		
		 mBoundChaseCamera.setBounds(0,0,e1.tmxLayer.getHeight(),e1.tmxLayer.getWidth());
	     mBoundChaseCamera.setBoundsEnabled(true);
		
		 
		 Personaje p1 = new Personaje();
		 mBoundChaseCamera.setChaseEntity(p1.sprite);
		 
		 final Path path = new Path(5).to(0, 160).to(0, 500).to(600, 500).to(600, 160).to(0, 160);
		
 
		 p1.sprite.registerEntityModifier(new LoopEntityModifier(new PathModifier(30, path, null, new IPathModifierListener() {
				@Override
				public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {

				}

				@Override
				public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
					switch(pWaypointIndex) {
						
					}
				}

				@Override
				public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {

				}

				@Override
				public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {

				}
			}))); 

		
		 //**********************************
		 
		 registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() { }

				@Override
				public void onUpdate(final float pSecondsElapsed) {
					
				}
			});
		 
		 //Añadimos Personaje a la escena:
		 attachChild(p1.sprite);
		 registerTouchArea(p1.sprite);
		 
		
	}
	
public static Juego getSharedInstance() {
		
	    return instance;
	    
	}
}


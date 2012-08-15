package com.example.v4;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;

public class BaseActivity extends SimpleBaseGameActivity {
	
	static final int CAMERA_WIDTH = 480;
	static final int CAMERA_HEIGHT = 320;

	public Font mFont;
	public Camera mCamera;
	
	//A reference to the current scene
	public Scene mCurrentScene;
	public static BaseActivity instance;
	
	public Engine mEngine;
	 
	public static BitmapTextureAtlas mBitmapTextureAtlas;
	public TiledTextureRegion mPlayerTextureRegion;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
	
		    instance = this;
		   
		    mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		    return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),  mCamera);
	   
	}
	
	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
	
		    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("img/");
		
		    mFont = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		    mFont.load();
		  
		    
		    //Recursos Personaje
		    mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 72, 128);
			mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas,this.getBaseContext(), "enemy.png", 0, 0, 3, 4);
			//mGrassBackground = new RepeatingSpriteBackground(BaseActivity.getSharedInstance().CAMERA_WIDTH,BaseActivity.getSharedInstance().CAMERA_HEIGHT,BaseActivity.getSharedInstance().getTextureManager(), AssetBitmapTextureAtlasSource.create(BaseActivity.getSharedInstance().getAssets(), "gfx/background_grass.png"),BaseActivity.getSharedInstance().getVertexBufferObjectManager());
			mBitmapTextureAtlas.load();
			
			
		    
	}
	
	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
	
		    mCurrentScene = new Presentacion();
		    //Comentado porque ya modifico el Background en la clase Presentacion
		    //mCurrentScene.setBackground(new Background(0.09804f, 0.7274f, 0.8f));
		    return mCurrentScene;
		
	}
	
public static BaseActivity getSharedInstance() {
		
	    return instance;
	    
	}

	// Lo usamos para cambiar a la nueva escena.
	public void setCurrentScene(Scene scene) {
	    mCurrentScene = scene;
	    getEngine().setScene(mCurrentScene);
	}

}
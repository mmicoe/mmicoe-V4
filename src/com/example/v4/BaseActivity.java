package com.example.v4;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
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
	
	public BoundCamera mBoundChaseCamera;
	
	//A reference to the current scene
	public Scene mCurrentScene;
	public static BaseActivity instance;
	
	//public Engine mEngine;
	 
	//Personaje
	private BitmapTextureAtlas mBitmapTextureAtlas;
	public TiledTextureRegion mPlayerTextureRegion;
	
	//Números flotantes
	public BitmapTextureAtlas mNumbersTexture;
	public TiledTextureRegion mNumbersTextureRegion;
	
	//Obstacles
		public BitmapTextureAtlas mObstaclesTexture;
		public TiledTextureRegion mObstaclesTextureRegion;
	
	//Sonido_letras que salen
	Sound s1;
	//Collision
	Sound s2;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
	
		    instance = this;
		    
		    //mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		    //return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),  mCamera);
		    mBoundChaseCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		  
		    //Para el uso del Sonido necesito crear un objeto de EngineOptions y activarlo.
		    //return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),  mBoundChaseCamera);
		    
		    EngineOptions e1 = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),  mBoundChaseCamera);
	        e1.getAudioOptions().setNeedsSound(true);
		    
		    return e1;
	}
	
	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
	
		    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("img/");
		
		    mFont = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		    mFont.load();
		 
		    //Recursos Personaje
		    mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 72, 32, TextureOptions.DEFAULT);
		    //imagen columnas= 3 filas=1
			mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas,this, "monstruo_capas.png", 0, 0, 3, 1);
			//mGrassBackground = new RepeatingSpriteBackground(BaseActivity.getSharedInstance().CAMERA_WIDTH,BaseActivity.getSharedInstance().CAMERA_HEIGHT,BaseActivity.getSharedInstance().getTextureManager(), AssetBitmapTextureAtlasSource.create(BaseActivity.getSharedInstance().getAssets(), "gfx/background_grass.png"),BaseActivity.getSharedInstance().getVertexBufferObjectManager());
			mBitmapTextureAtlas.load();
			
			//Los números flotantes
			//imagen de 308X176
			//Tamaño de la letra: 44x44
			//Matriz de 
			//Se trata de una matriz de 7 columnas y 4 filas
			this.mNumbersTexture = new BitmapTextureAtlas(this.getTextureManager(), 308, 176, TextureOptions.BILINEAR);
			this.mNumbersTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mNumbersTexture, this, "abecedario.png", 0, 0, 7, 4);
			this.mNumbersTexture.load();
			
			//Obstacles
			this.mObstaclesTexture = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
			this.mObstaclesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mObstaclesTexture, this, "wall_1.png", 0, 0, 1, 1);
			this.mObstaclesTexture.load();
		    
		
			//Sonido
			SoundFactory.setAssetBasePath("mfx/");
			try {
				//Letras que salen
                s1 = SoundFactory.createSoundFromAsset(this.getSoundManager(), this, "chip017.wav");
        } catch (final IOException e) {
                Debug.e("Error", e);
        }
			try {
				//Collision
                s2 = SoundFactory.createSoundFromAsset(this.getSoundManager(), this, "crash.wav");
        } catch (final IOException e) {
                Debug.e("Error", e);
        }
			
	}
	
	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
	
		    mCurrentScene = new Juego();
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

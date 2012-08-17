package com.example.v4;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.math.MathUtils;




public class Personaje {
	
	public static Personaje instance;

	public AnimatedSprite sprite;
	
	Juego juego_activity;
	BaseActivity base_activity;
	
	public static Personaje getSharedInstance() {
       
        return instance;
    }
	    
public Personaje() {
	    

		juego_activity = Juego.getSharedInstance();
		base_activity = BaseActivity.getSharedInstance();
		
		
		/* Calculate the coordinates for the face, so its centered on the camera. */
		/* Calculate the coordinates for the face, so its centered on the camera. */
		final float centerX = (juego_activity.BOUND_CAMERA_WIDTH - base_activity.mPlayerTextureRegion.getWidth()) / 2;
		final float centerY = (juego_activity.BOUND_CAMERA_HEIGHT - base_activity.mPlayerTextureRegion.getHeight()) / 2;

		System.out.println("CENTERX:"+centerX);
		System.out.println("CENTERY"+ centerY);
		
		sprite = new AnimatedSprite(centerX, centerY, base_activity.mPlayerTextureRegion, base_activity.getVertexBufferObjectManager());
	
		
    }

public AnimatedSprite getSprite(){
	
	     return sprite;
}
}

package com.example.v4;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
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

	
	final AnimatedSprite sprite;
	
	private float b_touchX;
    private float b_touchY;
  
	Juego juego_activity;
	BaseActivity base_activity;
	
	public static Personaje getSharedInstance() {
       
        return instance;
    }
	    
	public Personaje() {
	   
		
		juego_activity = Juego.getSharedInstance();
		base_activity = BaseActivity.getSharedInstance();
		
		
		/* Calculate the coordinates for the face, so its centered on the camera. */
		final float centerX = (juego_activity.BOUND_CAMERA_WIDTH - base_activity.mPlayerTextureRegion.getWidth()) / 2;
		final float centerY = (juego_activity.BOUND_CAMERA_HEIGHT - base_activity.mPlayerTextureRegion.getHeight()) / 2;

		
		sprite = new AnimatedSprite(centerX, centerY, base_activity.mPlayerTextureRegion, base_activity.getVertexBufferObjectManager())
		{
			//Movimiento Touch
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				
				b_touchX = sprite.getX() + (sprite.getWidth() / 2);
			    b_touchY = sprite.getY() + (sprite.getHeight() / 2);
				
                
				
			    float touchX = pSceneTouchEvent.getX();
                float touchY = pSceneTouchEvent.getY();

                float x_length = touchX - b_touchX;
                float y_length = touchY - b_touchY;
                
                sprite.setRotation(MathUtils.radToDeg((float)Math.atan2(y_length, x_length)));
				sprite.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2); 
                sprite.animate(new long[]{200, 200, 200}, 3, 5, true);
                    
                //Escena1.getSharedInstance().mBoundChaseCamera.setChaseEntity(sprite);
                /* Rota sobre sí mismo
                if(pSceneTouchEvent.isActionMove()){
                sprite.setRotation(sprite.getRotation()+90);} */
               
				return true;
		
		}
			
		}; 
		
	    
    }
			
}

package com.example.v4;



import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.debug.Debug;

import android.widget.Toast;



public class Escena1 {
	
	protected int mCactusCount;
	public static Escena1 instance;
	
	public TMXTiledMap mTMXTiledMap;
	
	public TMXLayer tmxLayer;
	
	public static Escena1 getSharedInstance() {
        if (instance == null)
            instance = new Escena1();
        return instance;
    }
	
public Escena1() {
		 	
		//Recursos Escena1.
		try {
			final TMXLoader tmxLoader = new TMXLoader(BaseActivity.getSharedInstance().getAssets(), BaseActivity.getSharedInstance().getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, BaseActivity.getSharedInstance().getVertexBufferObjectManager(), new ITMXTilePropertiesListener() {
				@Override
				public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
					/* We are going to count the tiles that have the property "cactus=true" set. */
					
				}
			});
			mTMXTiledMap = tmxLoader.loadFromAsset("txm/desert.tmx");

			
		} catch (final TMXLoadException e) {
			Debug.e(e);
		}
	
		tmxLayer = mTMXTiledMap.getTMXLayers().get(0);
		
}

public TMXLayer getLayer(){
	
	     return tmxLayer;
}

}

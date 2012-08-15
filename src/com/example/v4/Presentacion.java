package com.example.v4;

import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierMatcher;
import org.andengine.util.modifier.IModifier;



//Creamos una clase para la escena presentación.
public class Presentacion extends Scene {
	
	BaseActivity activity;

	  public Presentacion(){
		 
		  
		  setBackground(new Background(0.09804f, 0.6274f, 0));
		  activity = BaseActivity.getSharedInstance();
		  Text title1 = new Text(0, 0, activity.mFont, activity.getString(R.string.title_1), activity.getVertexBufferObjectManager());
		  Text title2 = new Text(0, 0, activity.mFont, activity.getString(R.string.title_2), activity.getVertexBufferObjectManager());

		  title1.setPosition(-title1.getWidth(), activity.mCamera.getHeight() / 2);
		  title2.setPosition(activity.mCamera.getWidth(), activity.mCamera.getHeight() / 2);

		  attachChild(title1);
		  attachChild(title2);

		  title1.registerEntityModifier(new MoveXModifier(1, title1.getX(), activity.mCamera.getWidth() / 2 - title1.getWidth()));
		  title2.registerEntityModifier(new MoveXModifier(1, title2.getX(), activity.mCamera.getWidth() / 2));

		  loadResources();

	  }

	//Funciona Mauro bien
	  
	void loadResources(){
		
		//El Menu Inicial se dispara después de 5 segundos.
		DelayModifier dMod = new DelayModifier(2, new IEntityModifierListener() {
			@Override
			public void onModifierStarted(
					org.andengine.util.modifier.IModifier<org.andengine.entity.IEntity> pModifier,
					org.andengine.entity.IEntity pItem) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onModifierFinished(
					org.andengine.util.modifier.IModifier<org.andengine.entity.IEntity> pModifier,
					org.andengine.entity.IEntity pItem) {
				// TODO Auto-generated method stub
				   activity.setCurrentScene(new MenuInicialScene());

				
			}
		});
		registerEntityModifier(dMod);
 
	} 
	  
}


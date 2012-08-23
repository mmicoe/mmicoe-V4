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
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.Constants;
import org.andengine.util.math.MathUtils;

import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;

import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import android.hardware.SensorManager;
import android.util.Log;


public class Juego extends Scene implements IAccelerationListener {
    
	
	final int BOUND_CAMERA_WIDTH = 480;
	final int BOUND_CAMERA_HEIGHT = 320;

	public static Juego instance;
	
	BaseActivity base_activity;

	Escena1 e1;
	Personaje p1,p2,p3;
    Numbers n1;
	
    //Variables del mundo físico
    //Subiendo la eslasticidad obtenemos el efecto de partículas gravitando.
    public static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 1f, 0.5f);
    private PhysicsWorld MundoFisico;
  
   
	public Juego()
	{
		base_activity = BaseActivity.getSharedInstance();
		
		//Añadimos escena1
		e1 = new Escena1();
				
		//************ Parámetros del Mundo físico
		MundoFisico = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_PLUTO), false);
		//Establecemos el tamaño del mundo físico que corresponderá al tamaño del tiledMap
		
		final VertexBufferObjectManager vertexBufferObjectManager = base_activity.getVertexBufferObjectManager();
	
		final Rectangle ground = new Rectangle(0,BOUND_CAMERA_HEIGHT - 2, BOUND_CAMERA_WIDTH, 2, vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(0, 0, BOUND_CAMERA_WIDTH, 2, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(0, 0, 2,BOUND_CAMERA_HEIGHT, vertexBufferObjectManager);
		final Rectangle right = new Rectangle(BOUND_CAMERA_WIDTH - 2, 0, 2, BOUND_CAMERA_HEIGHT, vertexBufferObjectManager);

		//CORRECTO TENEMOS EN CUENTA TODO EL MAPA
		/* final Rectangle ground = new Rectangle(0,e1.getLayer().getHeight() - 2, e1.getLayer().getWidth(), 2, vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(0, 0, e1.getLayer().getWidth(), 2, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(0, 0, 2,e1.getLayer().getHeight(), vertexBufferObjectManager);
		final Rectangle right = new Rectangle(e1.getLayer().getWidth() - 2, 0, 2, e1.getLayer().getHeight(), vertexBufferObjectManager); */
		
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		
		PhysicsFactory.createBoxBody(MundoFisico, ground, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(MundoFisico, roof, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(MundoFisico, left, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(MundoFisico, right, BodyType.StaticBody, wallFixtureDef);

		attachChild(ground);
		attachChild(roof);
		attachChild(left);
		attachChild(right);
		
		
		
		registerUpdateHandler(MundoFisico); 
		
		
		
		//** Fin parámetros del mundo físico
		
		instance = this;
		base_activity = BaseActivity.getSharedInstance();
		
		p1 = new Personaje();
		
		attachChild(e1.getLayer());
		 
		base_activity.mBoundChaseCamera.setBounds(0,0,e1.getLayer().getHeight(),e1.getLayer().getWidth());
		base_activity.mBoundChaseCamera.setBoundsEnabled(true);
		
		//Añadimos Personaje a la escena:
		attachChild(p1.getSprite());
		
		
		registerTouchArea(p1.getSprite());
	
		base_activity.mBoundChaseCamera.setChaseEntity(p1.getSprite());
		
		//Los números flotantes
		n1 = new Numbers();
			
		//Mundo físico
		final Body body;
		body = PhysicsFactory.createBoxBody(MundoFisico, n1.mNum, BodyType.DynamicBody, FIXTURE_DEF);
		//body.applyForce(new Vector2(0,-SensorManager.GRAVITY_PLUTO), new Vector2(body.getWorldCenter()));
		
		// ************************************************
		attachChild(n1.mNum);
		MundoFisico.registerPhysicsConnector(new PhysicsConnector(n1.mNum, body, true, true));
		
 /*
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
	 
*/
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

@Override
public void onAccelerationChanged(final AccelerationData pAccelerationData) {
	
	final Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX(), pAccelerationData.getY());
	MundoFisico.setGravity(gravity);
	//Vector2Pool.recycle(gravity);
}



@Override
public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
	// TODO Auto-generated method stub
	
}

}


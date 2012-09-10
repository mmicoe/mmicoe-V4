package com.example.v4;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.TiledSprite;
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
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Manifold;

import android.hardware.SensorManager;
import android.util.Log;

import org.andengine.extension.physics.box2d.PhysicsConnector;
public class Juego extends Scene implements IAccelerationListener {
    
	
	final int BOUND_CAMERA_WIDTH = 480;
	final int BOUND_CAMERA_HEIGHT = 320;

	public static Juego instance;
	
	BaseActivity base_activity;

	Escena1 e1;
	Personaje p1;
    Numbers n1;
	Obstacles o1;
	
	AnimatedSprite Player;
	
    //Variables del mundo físico
    //Subiendo la eslasticidad obtenemos el efecto de partículas gravitando.
   public static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 1f, 0.5f);
	
   
   public PhysicsWorld MundoFisico;
  
    //Obstaculos
    final int MAX_OBS = 7;
    //Letras
    final int MAX_LETRAS = 12;
  
    float pos_y;
    boolean flag = true; //el flag se activa después de los 4 primeros segundos para evitar null.
    
    Body perso1;
    Body body_letras;
    

    //Creamos la lista de elementos
  	LinkedList<Numbers> Nlist = new LinkedList<Numbers>();	
    
	public Juego()
	{
		base_activity = BaseActivity.getSharedInstance();
		
		//Añadimos escena1
		e1 = new Escena1();
				
		//************ Parámetros del Mundo físico
		MundoFisico = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_PLUTO), false);
		
		//Establecemos el tamaño del mundo físico que corresponderá al tamaño del tiledMap
		
		final VertexBufferObjectManager vertexBufferObjectManager = base_activity.getVertexBufferObjectManager();
	
		/*final Rectangle ground = new Rectangle(0,BOUND_CAMERA_HEIGHT - 2, BOUND_CAMERA_WIDTH, 2, vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(0, 0, BOUND_CAMERA_WIDTH, 2, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(0, 0, 2,BOUND_CAMERA_HEIGHT, vertexBufferObjectManager);
		final Rectangle right = new Rectangle(BOUND_CAMERA_WIDTH - 2, 0, 2, BOUND_CAMERA_HEIGHT, vertexBufferObjectManager);*/

		//CORRECTO TENEMOS EN CUENTA TODO EL MAPA
		final Rectangle ground = new Rectangle(0,e1.getLayer().getHeight() - 2, e1.getLayer().getWidth(), 2, vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(0, 0, e1.getLayer().getWidth(), 2, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(0, 0, 2,e1.getLayer().getHeight(), vertexBufferObjectManager);
		final Rectangle right = new Rectangle(e1.getLayer().getWidth() - 32, 0, 32, e1.getLayer().getHeight(), vertexBufferObjectManager); 
		
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		
		PhysicsFactory.createBoxBody(MundoFisico, ground, BodyType.StaticBody,wallFixtureDef);
		PhysicsFactory.createBoxBody(MundoFisico, roof, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(MundoFisico, left, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(MundoFisico, right, BodyType.StaticBody,wallFixtureDef);

		attachChild(ground);
		attachChild(roof);
		attachChild(left);
		attachChild(right);
		
		
		registerUpdateHandler(MundoFisico); 
		
		
		//** Fin parámetros del mundo físico
		
		instance = this;
		base_activity = BaseActivity.getSharedInstance();
		
		//Creamos personaje
		p1 = new Personaje();
		
		Player = p1.getSprite();
		
		attachChild(e1.getLayer());
		 
		base_activity.mBoundChaseCamera.setBounds(0,0,e1.getLayer().getWidth(),e1.getLayer().getHeight());
		base_activity.mBoundChaseCamera.setBoundsEnabled(true);
		
		//Añadimos Personaje a la escena:
		attachChild(Player);
		
		registerTouchArea(Player);
	
		base_activity.mBoundChaseCamera.setChaseEntity(Player);
	
		//Obstacles*********************
				//final FixtureDef ObstaclesFixtureDef = PhysicsFactory.createFixtureDef(10, 0.2f, 0.5f);
				//Definimos lista de obstaculos
				List<Obstacles> mObstacles = new LinkedList<Obstacles>();
				for (int i = 0; i <= MAX_OBS; i++) {
					o1 = new Obstacles();
					mObstacles.add(o1);
					final Body body_Obs = PhysicsFactory.createBoxBody(MundoFisico, o1.Obs,BodyType.StaticBody, FIXTURE_DEF);
					attachChild(o1.Obs);
					MundoFisico.registerPhysicsConnector(new PhysicsConnector(o1.Obs, body_Obs, true, true));
				}
				
					
				//Control de colisiones	
	registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() { }

				@Override
				public void onUpdate(final float pSecondsElapsed) {
					
				if (flag==false) {

					Iterator<Numbers> i = Nlist.listIterator();
					while(i.hasNext()) {
						Numbers b = (Numbers) i.next(); //Obtengo el elemento contenido 
				       
				          if (Player.collidesWith(b.getSprite())){
								 System.out.println("COLISIONNNNNNNN "+ b.tipo);
								 
							}
				          
				}
					}
					
				}
			}); 
	
	//createCollisionListener();
	//MundoFisico.setContactListener(collisionListener);
				
	registerUpdateHandler(new TimerHandler(4f, true, new ITimerCallback() {
             @Override
             public void onTimePassed(final TimerHandler pTimerHandler) {
            	
            	 n1 = new Numbers();
            	 Nlist.add(n1);
                 Body body_letras = PhysicsFactory.createBoxBody(MundoFisico, n1.getSprite(), BodyType.DynamicBody, FIXTURE_DEF);

                 attachChild(n1.getSprite());
                 MundoFisico.registerPhysicsConnector(new PhysicsConnector(n1.getSprite(), body_letras, true, true));
                
                 flag = false;
                 
             }
            
     }));
				
	}

	
public static Juego getSharedInstance() {
		
	    return instance;
	    
	}

@Override
public void onAccelerationChanged(final AccelerationData pAccelerationData) {
	
	final Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX(), pAccelerationData.getY());
	MundoFisico.setGravity(gravity);
	
	
}



@Override
public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
	// TODO Auto-generated method stub
	
}

}


package com.example.v4;

import java.util.LinkedList;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.TiledSprite;

public class Numbers {

	public TiledSprite mNum;
	final int WIDTH_NUMBER = 25;
	final int HEIGHT_NUMBER = 25;
	
	BaseActivity base_activity;
	
	public int tipo;
	
	public Numbers(){
		
		base_activity = BaseActivity.getSharedInstance();
		//Podemos establecer Anchura= 40, altura=50 del sprite
		
		float px;
	
        int Pos = 1 + (int)(Math.random()*4); 
        
        px = 0F;
        
        switch(Pos){
        case 1:
        	 px = 64.0F;
        	 break;
        case 2:
        	 px = 160.0F;
        	 break;
        case 3:
        	px = 288.0F;
        	break;
        case 4:
        	px = 384.0F;
        	break;
        }
        
        //Tamaño de las letras en pantalla 30x30
		mNum = new TiledSprite(px, 0, WIDTH_NUMBER,HEIGHT_NUMBER, base_activity.mNumbersTextureRegion, base_activity.getVertexBufferObjectManager());
		
		
		//index del mapa de tiles.
		double mr = Math.random();
		//Genera números de Min + Max=12 de momento
		int til = 0 + (int)(Math.random()*21); 
		tipo = til;
		mNum.setCurrentTileIndex(til);
		
	}

//Números a adivinar
public Numbers(int x,int y){
	
	   base_activity = BaseActivity.getSharedInstance();
        //Tamaño de las letras en pantalla 30x30
		mNum = new TiledSprite(x, y, WIDTH_NUMBER, HEIGHT_NUMBER, base_activity.mNumbersTextureRegion, base_activity.getVertexBufferObjectManager());
		
		//index del mapa de tiles.
		double mr_a = Math.random();
		//Genera números de Min + Max=12 de momento
		int til = 0 + (int)(mr_a*21); 
		tipo = til;
		mNum.setCurrentTileIndex(til);
		//guardamos los id de las letras a adivinar

	}	

public TiledSprite getSprite(){
	
    return mNum;
}

	
}

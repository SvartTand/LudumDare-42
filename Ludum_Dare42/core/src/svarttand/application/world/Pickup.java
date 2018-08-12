package svarttand.application.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Pickup extends Sprite{
	
	private int ammo;
	private int hp;
	
	private PointLight light;
	
	public Pickup(TextureAtlas atlas, float f, float g, String path, int ammo, int hp, RayHandler rayHandler) {
		super(atlas.findRegion(path));
		setPosition(f-getRegionWidth()*0.5f, g-getRegionHeight()*0.5f);
		this.ammo = ammo;
		this.hp = hp;
		
		light = new PointLight(rayHandler, 100, Color.BLUE, 50, 100, 100);
		light.setPosition(f, g);
	}
	
	public void update(float delta){
		setRotation(getRotation() + 100 * delta);
	}

	public int getAmmo() {
		return ammo;
	}
	
	public void remove(){
		light.remove();
	}

	public int getHp() {
		return hp;
	}
	
	

}

package svarttand.application.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Pickup extends Sprite{
	
	private int ammo;
	private int hp;
	
	public Pickup(TextureAtlas atlas, float f, float g, String path, int ammo, int hp) {
		super(atlas.findRegion(path));
		setPosition(f-getRegionWidth()*0.5f, g-getRegionHeight()*0.5f);
		this.ammo = ammo;
		this.hp = hp;
	}
	
	public void update(float delta){
		setRotation(getRotation() + 100 * delta);
	}

	public int getAmmo() {
		return ammo;
	}

	public int getHp() {
		return hp;
	}
	
	

}

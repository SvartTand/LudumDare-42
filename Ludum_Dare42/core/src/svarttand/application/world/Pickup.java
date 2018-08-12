package svarttand.application.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Pickup extends Sprite{
	
	public Pickup(TextureAtlas atlas, float f, float g, String path) {
		super(atlas.findRegion(path));
		setPosition(f-getRegionWidth()*0.5f, g-getRegionHeight()*0.5f);
		
	}
	
	public void update(float delta){
		setRotation(getRotation() + 100 * delta);
	}
	
	

}

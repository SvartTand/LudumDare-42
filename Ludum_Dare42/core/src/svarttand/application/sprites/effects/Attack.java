package svarttand.application.sprites.effects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;

public class Attack{
	
	private String path;
	private Animation<TextureRegion> animation;
	private float stateTime;
	private Vector2 pos;
	private float rot;
	
	public Attack(TextureAtlas atlas, Vector2 pos, String path, float rotation) {

		animation = new Animation<TextureRegion>(0.05f, atlas.findRegions("Attack"));
		stateTime = 0f;
		this.pos = pos;
	}
	
	public void update(float delta){
		stateTime += delta;
	}
	
	public void render(SpriteBatch batch, TextureAtlas atlas){
		TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
		batch.draw(currentFrame,pos.x, pos.y, pos.x, pos.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight(), 1, 1, rot);
		
	}
	
	
	
	

}

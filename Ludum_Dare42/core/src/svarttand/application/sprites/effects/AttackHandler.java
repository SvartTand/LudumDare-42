package svarttand.application.sprites.effects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class AttackHandler {
	
	ArrayList<Attack> attacks;
	
	public AttackHandler() {
		attacks = new ArrayList<Attack>();
	}
	
	public void update(float delta){
		for (int i = 0; i < attacks.size(); i++) {
			attacks.get(i).update(delta);
		}
	}
	
	public void render(SpriteBatch batch, TextureAtlas atlas){
		for (int i = 0; i < attacks.size(); i++) {
			attacks.get(i).render(batch, atlas);
		}
	}
	
	public void addAttack(Vector2 pos, TextureAtlas atlas, float rotation){
		attacks.add(new Attack(atlas, pos, "Attack", rotation));
	}

}

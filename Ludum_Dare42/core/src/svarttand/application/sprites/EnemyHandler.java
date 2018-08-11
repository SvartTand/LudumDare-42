package svarttand.application.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class EnemyHandler {
	
	private ArrayList<Zombie> enemies;
	private float timer;
	
	public EnemyHandler() {
		enemies = new ArrayList<Zombie>();
		timer = 0;
	}
	
	public void update(float delta, TextureAtlas atlas, Vector2 playerPos){
		timer += delta;
		if (timer >= 2) {
			enemies.add(new Zombie(atlas, 100, 100));
			timer = 0;
		}
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update(delta, playerPos);
		}
	}
	
	public void render(SpriteBatch batch){
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(batch);
		}
	}

}

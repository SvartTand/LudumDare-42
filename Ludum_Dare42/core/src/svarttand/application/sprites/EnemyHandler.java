package svarttand.application.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import svarttand.application.sprites.effects.BulletHandler;

public class EnemyHandler {
	
	private ArrayList<Zombie> enemies;
	private float timer;
	
	public EnemyHandler() {
		enemies = new ArrayList<Zombie>();
		timer = 0;
	}
	
	public void update(float delta, TextureAtlas atlas, Vector2 playerPos, BulletHandler handler, float worldSize){
		timer += delta;
		if (timer >= 1) {
			double randx =Math.random() * worldSize +1;
			float x = (float) randx;
			double randy =Math.random() * worldSize +1;
			float y = (float) randy;
			
			enemies.add(new Zombie(atlas, x, y));
			timer = 0;
		}
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update(delta, playerPos, handler, atlas);
		}
	}
	
	public void render(SpriteBatch batch){
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(batch);
		}
	}

	public ArrayList<Zombie> getEnemies() {
		return enemies;
	}
	
	public void dmg(int i, float dmg){
		System.out.println("dmg");
		if (enemies.get(i).dmg(dmg)) {
			enemies.remove(i);
			//Effect!!
		}
	}

}

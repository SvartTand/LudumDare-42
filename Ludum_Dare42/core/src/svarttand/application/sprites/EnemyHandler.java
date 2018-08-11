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
	
	public void update(float delta, TextureAtlas atlas, Vector2 playerPos, BulletHandler handler){
		timer += delta;
		if (timer >= 2) {
			enemies.add(new Zombie(atlas, 100, 100));
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

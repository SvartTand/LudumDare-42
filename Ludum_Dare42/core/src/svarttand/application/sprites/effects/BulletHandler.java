package svarttand.application.sprites.effects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import svarttand.application.sprites.EnemyHandler;

public class BulletHandler {
	
	private ArrayList<Bullet> bullets;
	
	public BulletHandler() {
		bullets = new ArrayList<Bullet>();
	}

	public void update(float delta, EnemyHandler enemyHandler) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(delta, enemyHandler, this);
		}
		
	}
	
	public void render(SpriteBatch batch){
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(batch);
			if (bullets.get(i).getTimer() >= 5) {
				bullets.remove(i);
			}
		}
	}

	public void add(Bullet bullet) {
		bullets.add(bullet);
		
	}

	public void remove(Bullet bullet) {
		bullets.remove(bullet);
		
	}

}

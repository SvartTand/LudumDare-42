package svarttand.application.sprites.effects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import svarttand.application.misc.ParticleHandler;
import svarttand.application.sprites.EnemyHandler;
import svarttand.application.sprites.Player;
import svarttand.application.states.PlayState;

public class BulletHandler {
	
	private ArrayList<Bullet> bullets;
	private PlayState state;
	
	public BulletHandler(PlayState state) {
		bullets = new ArrayList<Bullet>();
		this.state = state;
	}

	public void update(float delta, EnemyHandler enemyHandler, Player player, ParticleHandler particles) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(delta, enemyHandler, this, player, particles);
		}
		
	}
	
	public void render(SpriteBatch batch){
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(batch);
			if (bullets.get(i).getTimer() >= 5) {
				bullets.get(i).removeLight();
				bullets.remove(i);
			}
		}
	}

	public void add(Bullet bullet) {
		bullets.add(bullet);
		
	}

	public void remove(Bullet bullet) {
		state.addLight(bullet.getPosition());
		bullets.remove(bullet);
		
		state.shake();
	}

}

package svarttand.application.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import svarttand.application.misc.AudioHandler;
import svarttand.application.misc.ParticleHandler;
import svarttand.application.sprites.effects.BulletHandler;
import svarttand.application.states.PlayState;

public class EnemyHandler {
	
	private ArrayList<Zombie> enemies;
	private float timer;
	
	private int killCount;
	private PlayState state;
	public EnemyHandler(PlayState state) {
		enemies = new ArrayList<Zombie>();
		timer = 0;
		killCount = 0;
		this.state = state;
	}
	
	public void update(float delta, TextureAtlas atlas, Vector2 playerPos, BulletHandler handler, float worldSize, ParticleHandler pHandler){
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
			enemies.get(i).update(delta, playerPos, handler, atlas, pHandler, state.getAudioHandler());
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
			killCount++;
			state.getAudioHandler().playSound(AudioHandler.EXPLOSION);
		}
	}
	public int getKills(){
		return killCount;
	}

}

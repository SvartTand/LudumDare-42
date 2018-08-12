package svarttand.application.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import box2dLight.RayHandler;
import svarttand.application.Application;
import svarttand.application.misc.AudioHandler;
import svarttand.application.misc.ParticleHandler;
import svarttand.application.sprites.effects.BulletHandler;
import svarttand.application.states.PlayState;

public class EnemyHandler {
	
	public static final int GOAL = 10;
	private ArrayList<Zombie> enemies;
	private float timer;
	
	private int killCount;
	private PlayState state;
	
	private Boss boss;
	public EnemyHandler(PlayState state) {
		enemies = new ArrayList<Zombie>();
		timer = 0;
		killCount = 0;
		this.state = state;
	}
	
	public void update(float delta, TextureAtlas atlas, Vector2 playerPos, BulletHandler handler, float worldSize, ParticleHandler pHandler, RayHandler rayHandler){
		timer += delta;
		if (killCount >= GOAL && boss == null) {
			boss = new Boss(atlas, playerPos.x, playerPos.y + 200, rayHandler);
			state.boss();
			System.out.println("BOSS");
		}
		if (timer >= 1 && boss == null) {
			double randx =Math.random() * worldSize +1;
			float x = (float) randx;
			double randy =Math.random() * worldSize +1;
			float y = (float) randy;
			
			enemies.add(new Zombie(atlas, x, y,rayHandler));
			timer = 0;
		}
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update(delta, playerPos, handler, atlas, pHandler, state.getAudioHandler(), state.getRayhandler());
		}
		if (boss != null) {
			boss.update(delta, playerPos, handler, atlas, pHandler, state.getAudioHandler(), state.getRayhandler());
		}
	}
	
	public void render(SpriteBatch batch){
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(batch);
		}
		if (boss != null) {
			boss.draw(batch);
		}
	}
	
	public Boss getBoss(){
		return boss;
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

	public void dmgBoss(float dmg) {
		state.getAudioHandler().playSound(AudioHandler.Explosion);
		if (boss.dmg(dmg)) {
			System.out.println("VICTORY!");
			boss = null;
			state.victory();
		}
		
	}

}

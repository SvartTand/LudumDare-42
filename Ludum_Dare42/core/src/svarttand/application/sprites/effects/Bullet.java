package svarttand.application.sprites.effects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import svarttand.application.misc.ParticleHandler;
import svarttand.application.misc.ParticleType;
import svarttand.application.sprites.EnemyHandler;
import svarttand.application.sprites.Player;

public class Bullet extends Sprite{
	
	private static final int SPEED = 300;
	private static final int SIZE = 3;
	private static final float DMG = 10;
	
	private Rectangle bounds;
	private Vector2 direction;
	private float timer;
	private boolean enemy;
	
	public Bullet(TextureAtlas atlas, float f, float g, float rotation, boolean enemy, String path) {
		super(atlas.findRegion(path));
		timer = 0;
		bounds = new Rectangle(f, g, SIZE,SIZE);
		setPosition(f- getRegionHeight()*0.5f, g-getRegionHeight()*0.5f);
		System.out.println(f + ", " + g);
		setRotation(rotation);
		rotation += 90;
		this.enemy = enemy;
		direction = new Vector2(MathUtils.cosDeg(rotation), MathUtils.sinDeg(rotation));
		// TODO Auto-generated constructor stub
	}
	
	public void update(float delta, EnemyHandler handler, BulletHandler bhandler, Player player, ParticleHandler particles){
		
		checkBounds(handler, bhandler, player, particles);
		timer += delta;
		//System.out.println(direaaaction.x + ", " + direction.y);
		float speedx = direction.x * delta * SPEED;
		float speedy = direction.y * delta * SPEED;
		//System.out.println(speedx + ", " + speedy);
		setPosition(getX() + speedx, getY() + speedy);
		
		bounds.set(getBoundingRectangle());
	}
	private void checkBounds(EnemyHandler handler, BulletHandler bHandler, Player player, ParticleHandler particles ) {
		if (enemy) {
			if (bounds.overlaps(player.getBounds())) {
				player.takeDmg(DMG);
				//player.addHP(-1);
				particles.addParticleEffect(ParticleType.HIT, getX(), getY(),45);
				bHandler.remove(this);
			}
		}else{
			for (int i = 0; i < handler.getEnemies().size(); i++) {
				if (bounds.overlaps(handler.getEnemies().get(i).getBounds())) {
					handler.dmg(i, DMG);
					particles.addParticleEffect(ParticleType.HIT, getX(), getY(),45);
					bHandler.remove(this);
				}
			}
			if(handler.getBoss() != null){
				
				if (handler.getBoss().getBoundingRectangle().overlaps(bounds)) {
					handler.dmgBoss(DMG);
					particles.addParticleEffect(ParticleType.HIT, getX(), getY(),45);
					bHandler.remove(this);
				}
				
			}
		}
		
		
		
	}

	public float getTimer(){
		return timer;
	}
	

}

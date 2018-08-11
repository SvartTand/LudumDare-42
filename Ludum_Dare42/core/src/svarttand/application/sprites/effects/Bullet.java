package svarttand.application.sprites.effects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import svarttand.application.sprites.EnemyHandler;

public class Bullet extends Sprite{
	
	private static final int SPEED = 300;
	private static final int SIZE = 3;
	private static final float DMG = 5;
	
	private Rectangle bounds;
	private Vector2 direction;
	private float timer;
	
	public Bullet(TextureAtlas atlas, float f, float g, float rotation) {
		super(atlas.findRegion("Bullet"));
		timer = 0;
		bounds = new Rectangle(f, g, SIZE,SIZE);
		setPosition(f, g);
		System.out.println(f + ", " + g);
		setRotation(rotation);
		rotation += 90;
		direction = new Vector2(MathUtils.cosDeg(rotation), MathUtils.sinDeg(rotation));
		// TODO Auto-generated constructor stub
	}
	
	public void update(float delta, EnemyHandler handler, BulletHandler bhandler){
		
		checkBounds(handler, bhandler);
		timer += delta;
		//System.out.println(direaaaction.x + ", " + direction.y);
		float speedx = direction.x * delta * SPEED;
		float speedy = direction.y * delta * SPEED;
		//System.out.println(speedx + ", " + speedy);
		setPosition(getX() + speedx, getY() + speedy);
		
		bounds.setPosition(getX(), getY());
	}
	private void checkBounds(EnemyHandler handler, BulletHandler bHandler) {
		for (int i = 0; i < handler.getEnemies().size(); i++) {
			if (bounds.overlaps(handler.getEnemies().get(i).getBounds())) {
				handler.dmg(i, DMG);
				bHandler.remove(this);
			}
		}
		
		
	}

	public float getTimer(){
		return timer;
	}
	

}

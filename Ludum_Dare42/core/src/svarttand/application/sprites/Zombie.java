package svarttand.application.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import svarttand.application.Application;
import svarttand.application.misc.AudioHandler;
import svarttand.application.misc.ParticleHandler;
import svarttand.application.misc.ParticleType;
import svarttand.application.sprites.Player.State;
import svarttand.application.sprites.effects.Bullet;
import svarttand.application.sprites.effects.BulletHandler;

public class Zombie extends Sprite{

	private static final float MAX_SPEED = 50f;
	private static final float ACCELERATION = 10f;
	private static final float DETECTION = 500;
	private static final int HP = 10;
	private static final float COOLDOWN = 2.5f;
	private static final float COOLDOWN_Effect = 0.6f;

	private Vector2 rotationV;
	private float speed;
	private boolean isPressed;
	private Rectangle bounds;
	private float hp;
	private float timer;
	
	
	public enum State {STANDING, WALKING,SHOOTING};
	public State currentState;
	public State previousState;
	
	private float stateTimer;
	
	private TextureRegion stand;
	
	private Animation walking;
	private Animation shooting;
	
	private float cooldownToShoot;
	private boolean shootingB;
	
	public Zombie(TextureAtlas atlas, float x, float y) {
		super(atlas.findRegion("Player"));
		rotationV = new Vector2();
		bounds = new Rectangle(x, y, 20, 20);
		hp = HP;
		speed = 0;
		timer = 0;
		setPosition(x, y);
		
		stateTimer = 0;
		
		walking = new Animation<TextureRegion>(0.1f, atlas.findRegions("ZWalking"), PlayMode.LOOP);
		shooting = new Animation<TextureRegion>(0.1f, atlas.findRegions("ZShooting"));
		stand = atlas.findRegion("ZWalking");
		setRegion(stand);
		currentState = State.STANDING;
		cooldownToShoot = COOLDOWN_Effect;
		shootingB = false;
	}
	
	public void update(float delta, Vector2 playerPos, BulletHandler handler, TextureAtlas atlas, ParticleHandler pHandler, AudioHandler audioHandler){
		timer += delta;
		
		updateRotation(playerPos);
		if (playerIsNear(playerPos)) {
			if (speed != MAX_SPEED) {
				speed += ACCELERATION;
			}
			if (timer >= COOLDOWN) {
				shootingB = true;
				if (cooldownToShoot <= 0) {
					handler.add(new Bullet(atlas, getPosition().x, getPosition().y, getRotation(), true, "ZBullet",300));
					pHandler.addParticleEffect(ParticleType.ZFIRE, getPosition().x, getPosition().y, getRotation()+90);
					timer = 0;
					shootingB = false;
					cooldownToShoot = COOLDOWN_Effect;
					audioHandler.playSound(AudioHandler.Shoot_2);
				}
				speed = 0;
				cooldownToShoot -= delta;
			
				
				
			}
		}else{
			if (speed <= 0){
				speed = 0;
			}else{
				speed = 0;
			}
			
		}
		float x = (float) MathUtils.cosDeg(getRotation()+90) *speed;
		float y = (float) MathUtils.sinDeg(getRotation()+90) *speed;
		setPosition(getX() + x* delta, getY() + y *delta);

		bounds.setPosition(getX(), getY());
		setRegion(getFrame(delta));
	}
	
	private TextureRegion getFrame(float delta) {
		currentState = getState();
		TextureRegion region;
		switch (currentState) {
		case SHOOTING:
			region =  (TextureRegion) shooting.getKeyFrame(stateTimer);
			break;

		case WALKING:
			region = (TextureRegion) walking.getKeyFrame(stateTimer, true);
			break;
			
		case STANDING:
			
		default:
			region = stand;
			break;
		}
		
		stateTimer = currentState == previousState ? stateTimer + delta: 0;
		previousState = currentState;
		return region;
	}
	
	private State getState() {
		if (shootingB) {
			return State.SHOOTING;
		}
		if (speed != 0) {
			return State.WALKING;
		}
		return State.STANDING;
	}
	
	private boolean playerIsNear(Vector2 playerPos) {
		float dist = (float) Math.sqrt(Math.pow(playerPos.x - getX(),2)+ Math.pow(playerPos.y - getY(), 2));
		//System.out.println(dist + "<" + DETECTION);
		if (dist <= DETECTION) {
			return true;
		}
		return false;
	}

	public void setPressed(boolean b){
		isPressed = b;
	}
	

	public void updateRotation(Vector2 playerPos) {
		float rotation = 0;
		float mouseX = playerPos.x;
	    float mouseY = playerPos.y;
	    rotation = MathUtils.radiansToDegrees * MathUtils.atan2(mouseY - (getY() + getHeight()/2), mouseX - (getX() + getWidth()/2));
	    rotationV.set(mouseX - (getX() + getWidth()/2), mouseY - (getY() + getHeight()/2f));
		setRotation(rotation- 90);
		
	}

	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return bounds;
	}

	public boolean dmg(float dmg) {
		hp -= dmg;
		if (hp <= 0) {
			return true;
		}
		return false;
	}
	
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return new Vector2(getX()+getRegionWidth()*0.5f, getY()+getRegionHeight()*0.5f);
	}

}

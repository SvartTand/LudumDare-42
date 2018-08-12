package svarttand.application.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import svarttand.application.misc.AudioHandler;
import svarttand.application.misc.ParticleHandler;
import svarttand.application.misc.ParticleType;
import svarttand.application.sprites.effects.Bullet;
import svarttand.application.sprites.effects.BulletHandler;

public class Boss extends Sprite{
	
	private static final float MAX_SPEED = 50f;
	private static final float ACCELERATION = 10f;
	private static final float DETECTION = 4000;
	public static final int HP = 200;
	private static final float COOLDOWN = 2.5f;
	private static final float COOLDOWN_Effect = 0.6f;

	private Vector2 rotationV;
	private float speed;
	private boolean isPressed;
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
	
	private boolean machineGun;
	private float machineTimer;
	
	private boolean slingerGun;
	private float slingerTimer;
	private float slingerCounter;
	private float slingerRotation;
	
	private PointLight light;
	
	public Boss(TextureAtlas atlas, float x, float y, RayHandler rayHandler) {
		super(atlas.findRegion("Boss"));
		rotationV = new Vector2();
		
		hp = HP;
		speed = 0;
		timer = 0;
		setPosition(x, y);
		
		stateTimer = 0;
		machineGun = false;
		walking = new Animation<TextureRegion>(0.1f, atlas.findRegions("Boss"), PlayMode.LOOP);
		shooting = new Animation<TextureRegion>(0.1f, atlas.findRegions("ZShooting"));
		stand = atlas.findRegion("Boss");
		setRegion(stand);
		currentState = State.STANDING;
		cooldownToShoot = COOLDOWN_Effect;
		shootingB = false;
		slingerGun = false;
		slingerCounter = 0;
		
		light = new PointLight(rayHandler, 100, Color.MAGENTA, 200, 80, 80);
	}
	
	public void update(float delta, Vector2 playerPos, BulletHandler handler, TextureAtlas atlas, ParticleHandler pHandler, AudioHandler audioHandler, RayHandler rayHandler){
		timer += delta;
		
		updateRotation(playerPos);
		if (playerIsNear(playerPos)) {
			if (speed != MAX_SPEED) {
				speed += ACCELERATION;
			}
			if (timer >= COOLDOWN) {
				
				shootingB = true;
				if (cooldownToShoot <= 0 && !machineGun && !slingerGun) {
					double rand =Math.random() * 3 +1;
					rand = (int) rand;
					//System.out.println(rand);
					if (rand == 1) {
						handler.add(new Bullet(atlas, getPosition().x, getPosition().y, getRotation(), true, "BigBullet", 250, rayHandler));
						pHandler.addParticleEffect(ParticleType.ZFIRE, getPosition().x, getPosition().y, getRotation()+90);
						timer = 0;
						shootingB = false;
						cooldownToShoot = COOLDOWN_Effect;
						audioHandler.playSound(AudioHandler.BIG_SHOOT);
					}else if (rand == 2) {
						slingerGun = true;
						slingerTimer = 0.1f;
						slingerCounter = 0;
						slingerRotation = getRotation();
						System.out.println("AUDIO!!!");
						audioHandler.playSound(AudioHandler.MULTI_SHOOT);
						cooldownToShoot = COOLDOWN_Effect;
					}else {
						
						machineGun = true;
						machineTimer = 1f;
						audioHandler.playSound(AudioHandler.MULTI_SHOOT);
						cooldownToShoot = COOLDOWN_Effect;
					}
					
				}
				if (machineGun) {
					double rand =Math.random() * 360 +1;
					float r = (float) rand;
					handler.add(new Bullet(atlas, getPosition().x, getPosition().y, r, true, "ZBullet", 200, rayHandler));
					pHandler.addParticleEffect(ParticleType.ZFIRE, getPosition().x, getPosition().y, r+90);
					machineTimer-= delta;
					
					if (machineTimer <= 0) {
						machineGun = false;
						timer = 0;
					}
				}
				if (slingerGun) {
					slingerTimer-=delta;
					if (slingerTimer <= 0) {
						if (slingerCounter <= 5) {
							slingerRotation+= 15;
						}else{
							slingerRotation-=15;
						}
						handler.add(new Bullet(atlas, getPosition().x, getPosition().y, slingerRotation, true, "ZBullet", 200, rayHandler));
						pHandler.addParticleEffect(ParticleType.ZFIRE, getPosition().x, getPosition().y, slingerRotation+90);
						slingerCounter++;
						if (slingerCounter >= 15) {
							slingerGun = false;
							timer = 0;
							slingerCounter = 0;
							slingerRotation = 0;
							//aslingerTimer = 0;
						}
					}
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

		TextureRegion region;
		region = (TextureRegion) walking.getKeyFrame(stateTimer, true);
		stateTimer += delta;
		setRegion(region);
		light.setPosition(getPosition());
	}
	
//	private TextureRegion getFrame(float delta) {
//		currentState = getState();
//		TextureRegion region;
//		switch (currentState) {
//		case SHOOTING:
//			region =  (TextureRegion) shooting.getKeyFrame(stateTimer);
//			break;
//
//		case WALKING:
//			region = (TextureRegion) walking.getKeyFrame(stateTimer, true);
//			break;
//			
//		case STANDING:
//			
//		default:
//			region = stand;
//			break;
//		}
//		
//		stateTimer = currentState == previousState ? stateTimer + delta: 0;
//		previousState = currentState;
//		return region;
//	}
	
//	private State getState() {
//		if (shootingB) {
//			return State.SHOOTING;
//		}
//		if (speed != 0) {
//			return State.WALKING;
//		}
//		return State.STANDING;
//	}
	
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


	public boolean dmg(float dmg) {
		hp -= dmg;
		if (hp <= 0) {
			light.remove();
			return true;
		}
		return false;
	}
	
	public Vector2 getPosition() {
		return new Vector2(getX()+getRegionWidth()*0.5f, getY()+getRegionHeight()*0.5f);
	}

	public float getHp() {
		return hp;
	}


}

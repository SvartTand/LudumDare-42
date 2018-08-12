package svarttand.application.sprites;



import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import box2dLight.RayHandler;
import svarttand.application.Application;
import svarttand.application.misc.AudioHandler;
import svarttand.application.misc.ParticleHandler;
import svarttand.application.misc.ParticleType;
import svarttand.application.misc.ScreenShake;
import svarttand.application.sprites.effects.Bullet;
import svarttand.application.sprites.effects.BulletHandler;
import svarttand.application.states.PlayState;


public class Player extends Sprite{
	public static final float MAX_SPEED = 100f;
	//private static final float ACCELERATION = 10f;
	private static final float COOLDOWN = .6f;
	
	public static final int MAXHP = 5;
	
	public enum State {STANDING, WALKING,SHOOTING};
	public State currentState;
	public State previousState;
	
	private float stateTimer;
	
	private TextureRegion stand;
	
	private Animation walking;
	private Animation shooting;
	
	private float cooldownToShoot;
	private boolean shootingB;
	
	private Vector2 rotationV;
	private float speedSide;
	private float speedUp;

	private Rectangle bounds;
	private PlayState state;
	
	private int ammo;
	private int hp;
	
	private boolean secondaryFire;
	private float secTimer;
	private float secCounter;
	
	
	
	public Player(float f, float g, TextureAtlas atlas, PlayState state) {
		super(atlas.findRegion("Walking"));
		rotationV = new Vector2();
		bounds = new Rectangle(f, g, 16, 16);
		this.state = state;
		speedUp = 0;
		speedSide = 0;
		setPosition(f, g);
		stateTimer = 0;
		secCounter = 0;
		
		walking = new Animation<TextureRegion>(0.1f, atlas.findRegions("Walking"), PlayMode.LOOP);
		shooting = new Animation<TextureRegion>(0.1f, atlas.findRegions("Shooting"));
		stand = atlas.findRegion("Walking");
		setRegion(stand);
		currentState = State.STANDING;
		cooldownToShoot = 0;
		shootingB = false;
		
		ammo = 10;
		hp = MAXHP;
	}
	
	public void update(float delta, Vector2 mouse, TextureAtlas atlas, BulletHandler handler, ScreenShake screenShake, ParticleHandler pHandler, AudioHandler audio, RayHandler rayHandler){
		updateRotation(mouse);
//		if (isPressed) {
//			if (speed != MAX_SPEED) {
//				speed += ACCELERATION;
//			}
//		}else{
//			if (speed <= 0){
//				speed = 0;
//			}else{
//				speed = 0;
//			}
//			
//		}
		if (shootingB) {
			if (cooldownToShoot <= 0) {
				if (secondaryFire) {
					if (secTimer <= 0) {
						handler.add(new Bullet(atlas, getPosition().x, getPosition().y, getRotation(), false, "Bullet", 300, rayHandler));
						screenShake.shake(250, 250, 250);
						ammo--;
						pHandler.addParticleEffect(ParticleType.FIRE, getPosition().x, getPosition().y, getRotation()+90);
						audio.playSound(AudioHandler.SHOOT_1);
						secTimer = 0.2f;
						secCounter++;
						if (secCounter>= 5) {
							shootingB = false;
							secCounter = 0;
							secondaryFire = false;
						}
					}
					
					secTimer -= delta;
				}else{
					handler.add(new Bullet(atlas, getPosition().x, getPosition().y, getRotation(), false, "Bullet", 400,rayHandler));
					screenShake.shake(250, 250, 250);
					ammo--;
					pHandler.addParticleEffect(ParticleType.FIRE, getPosition().x, getPosition().y, getRotation()+90);
					shootingB = false;
					audio.playSound(AudioHandler.SHOOT_1);
				}
				
			}
			speedUp = 0;
			speedSide = 0;
			cooldownToShoot -= delta;
		}
		
		
//		float x = (float) MathUtils.cosDeg(getRotation()+90) *speed;
//		float y = (float) MathUtils.sinDeg(getRotation()+90) *speed;
		//System.out.println((float) MathUtils.cos(getRotation()+90) + ", " + (float) MathUtils.sin(getRotation()+90));
		setPosition(getX() + speedSide* delta, getY() + speedUp *delta);
		
		
		bounds.setPosition(getPosition().x-8, getPosition().y-8);
		setRegion(getFrame(delta));
		speedSide = 0;
		speedUp = 0;
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
		if (speedSide != 0 || speedUp != 0) {
			return State.WALKING;
		}
		return State.STANDING;
	}
	
	public void setSpeedUp(float s){
		speedUp = s;
	}
	public void setSpeedSide(float s){
		speedSide = s;
	}
	
	
	public int getAmmo(){
		return ammo;
	}
	
	public void addAmmo(int add){
		ammo += add;
	}
	public void addHP(int add){
		if (hp != 9) {
			hp += add;
		}
		
	}
	
	public int getHP(){
		return hp;
	}

	public void updateRotation(Vector2 mouse) {
		float rotation = 0;
		float mouseX = mouse.x;
	    float mouseY = Application.V_HEIGHT - mouse.y;
	    rotation = MathUtils.radiansToDegrees * MathUtils.atan2(mouseY - (getY() + getHeight()/2), mouseX - (getX() + getWidth()/2));
	    rotationV.set(mouseX - (getX() + getWidth()/2), mouseY - (getY() + getHeight()/2f));
		setRotation(rotation - 90);
		
	}

	public Vector2 getPosition() {
		return new Vector2(getX()+getRegionWidth()*0.5f, getY()+getRegionHeight()*0.5f);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void takeDmg(float dmg) {
		state.dmg();
		hp--;
		
	}

	public void shoot(AudioHandler handler) {
		if (ammo > 0) {
			if (!shootingB) {
				currentState = State.SHOOTING;
				cooldownToShoot = COOLDOWN;
				shootingB = true;
			}
		}else{
			handler.playSound(AudioHandler.ERROR);
		}
	}
	
	public void shootR(AudioHandler handler){
		if (ammo >= 5) {
			if (!shootingB) {
				currentState = State.SHOOTING;
				cooldownToShoot = COOLDOWN +0.1f;
				shootingB = true;
				secondaryFire = true;
			}
		}else {
			handler.playSound(AudioHandler.ERROR);
		}
	}
	

}

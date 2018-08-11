package svarttand.application.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import svarttand.application.Application;
import svarttand.application.sprites.effects.Bullet;
import svarttand.application.sprites.effects.BulletHandler;
import svarttand.application.states.PlayState;


public class Player extends Sprite{
	public static final float MAX_SPEED = 100f;
	private static final float ACCELERATION = 10f;
	private static final float COOLDOWN = .5f;
	
	
	
	public enum State {STANDING, WALKING,SHOOTING};
	public State currentState;
	public State previousState;
	
	private float stateTimer;
	
	private TextureRegion stand;
	
	private Animation walking;
	private Animation shooting;
	
	private float cooldownToShoot;

	private Vector2 rotationV;
	private float speedSide;
	private float speedUp;
	private boolean isPressed;
	private Rectangle bounds;
	private PlayState state;
	
	private boolean shootingB;
	
	public Player(int posX, int posY, TextureAtlas atlas, PlayState state) {
		super(atlas.findRegion("Walking"));
		rotationV = new Vector2();
		bounds = new Rectangle(posX, posY, 20, 20);
		this.state = state;
		speedUp = 0;
		speedSide = 0;
		setPosition(posX, posY);
		stateTimer = 0;
		
		walking = new Animation<TextureRegion>(0.1f, atlas.findRegions("Walking"), PlayMode.LOOP);
		shooting = new Animation<TextureRegion>(0.1f, atlas.findRegions("Shooting"));
		stand = atlas.findRegion("Walking");
		setRegion(stand);
		currentState = State.STANDING;
		cooldownToShoot = 0;
		shootingB = false;
	}
	
	public void update(float delta, Vector2 mouse, TextureAtlas atlas, BulletHandler handler){
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
				handler.add(new Bullet(atlas, getPosition().x, getPosition().y, getRotation(), false, "Bullet"));
				
				shootingB = false;
			}
			speedUp = 0;
			speedSide = 0;
			cooldownToShoot -= delta;
		}
		
		
//		float x = (float) MathUtils.cosDeg(getRotation()+90) *speed;
//		float y = (float) MathUtils.sinDeg(getRotation()+90) *speed;
		//System.out.println((float) MathUtils.cos(getRotation()+90) + ", " + (float) MathUtils.sin(getRotation()+90));
		setPosition(getX() + speedSide* delta, getY() + speedUp *delta);

		
		
		bounds.setPosition(getX(), getY());
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

	public void setPressed(boolean b){
		isPressed = b;
	}
	
	public void setSpeedUp(float s){
		speedUp = s;
	}
	public void setSpeedSide(float s){
		speedSide = s;
	}
	
	
	public void dispose() {
		dispose();
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
		// TODO Auto-generated method stub
		return new Vector2(getX()+getRegionWidth()*0.5f, getY()+getRegionHeight()*0.5f);
	}

	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return bounds;
	}

	public void takeDmg(float dmg) {
		state.dmg();
		
		
	}

	public void shoot() {
		if (!shootingB) {
			currentState = State.SHOOTING;
			cooldownToShoot = COOLDOWN;
			shootingB = true;
		}
		
		
	}
	

}

package svarttand.application.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import svarttand.application.Application;

public class Zombie extends Sprite{

	private static final float MAX_SPEED = 20f;
	private static final float ACCELERATION = 10f;
	private static final float DETECTION = 0.5f;
	

	private Vector2 rotationV;
	private float speed;
	private boolean isPressed;
	private Rectangle bounds;
	
	public Zombie(TextureAtlas atlas, float x, float y) {
		super(atlas.findRegion("Player"));
		rotationV = new Vector2();
		bounds = new Rectangle(x, y, 20, 20);
		
		speed = 0;
		setPosition(x, y);
	}
	
	public void update(float delta, Vector2 playerPos){
		updateRotation(playerPos);
		if (playerIsNear(playerPos)) {
			if (speed != MAX_SPEED) {
				speed += ACCELERATION;
			}
		}else{
			if (speed <= 0){
				speed = 0;
			}else{
				speed = 0;
			}
			
		}
		float x = rotationV.x *speed *delta;
		float y = rotationV.y *speed *delta;
		setPosition(getX() + x* delta, getY() + y *delta);
		bounds.setPosition(getX(), getY());
		
	}
	
	private boolean playerIsNear(Vector2 playerPos) {
		float dist = (float) Math.sqrt(Math.pow(playerPos.x - getX(),2)+ Math.pow(playerPos.y - getY(), 2));
		if (dist <= DETECTION) {
			return true;
		}
		return false;
	}

	public void setPressed(boolean b){
		isPressed = b;
	}
	
	
	public void dispose() {
		dispose();
	}

	public void updateRotation(Vector2 playerPos) {
		float rotation = 0;
		float mouseX = playerPos.x;
	    float mouseY = Application.V_HEIGHT - playerPos.y;
	    rotation = MathUtils.radiansToDegrees * MathUtils.atan2(mouseY - (getY() + getHeight()/2), mouseX - (getX() + getWidth()/2));
	    rotationV.set(mouseX - (getX() + getWidth()/2), mouseY - (getY() + getHeight()/2f));
		setRotation(rotation - 90);
		
	}

}

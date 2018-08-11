package svarttand.application.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import svarttand.application.Application;


public class Player extends Sprite{
	private static final float MAX_SPEED = 200f;
	private static final float ACCELERATION = 10f;
	private static final float DE_ACCELERATION = 0.5f;
	

	private Vector2 rotationV;
	private float speed;
	private boolean isPressed;
	private Rectangle bounds;
	
	public Player(int posX, int posY, TextureAtlas atlas) {
		super(atlas.findRegion("Player"));
		rotationV = new Vector2();
		bounds = new Rectangle(posX, posY, 20, 20);
		
		speed = 0;
		setPosition(20, 20);
	}
	
	public void update(float delta, Vector2 mouse){
		updateRotation(mouse);
		if (isPressed) {
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
		float x = (float) MathUtils.cosDeg(getRotation()+90) *speed;
		float y = (float) MathUtils.sinDeg(getRotation()+90) *speed;
		//System.out.println((float) MathUtils.cos(getRotation()+90) + ", " + (float) MathUtils.sin(getRotation()+90));
		setPosition(getX() + x* delta, getY() + y *delta);

		bounds.setPosition(getX(), getY());
		
	}
	
	public void setPressed(boolean b){
		isPressed = b;
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
	

}

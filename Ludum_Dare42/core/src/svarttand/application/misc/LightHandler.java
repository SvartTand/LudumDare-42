package svarttand.application.misc;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class LightHandler {
	
	private static final float TIME = 0.05f;
	private ArrayList<Light> lights;
	private ArrayList<Float> timers;
	
	public LightHandler() {

		lights = new ArrayList<Light>();
		timers = new ArrayList<Float>();
	}
	
	public void update(float delta){
		
		for (int i = 0; i < timers.size(); i++) {
			if (timers.get(i) <= 0) {
				lights.get(i).remove();
				lights.remove(i);
				timers.remove(i);
			}else{
				float x = timers.get(i);
				x -= delta;
				timers.set(i, x);
			}
			
		}
	}
	
	public void addLight(Vector2 pos, RayHandler rayHandler){
		PointLight light = new PointLight(rayHandler, 100, Color.WHITE, 300, 50, 50);
		light.setPosition(pos);
		lights.add(light);
		
		timers.add(TIME);
	}
	
	

}

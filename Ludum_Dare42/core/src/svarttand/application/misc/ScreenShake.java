package svarttand.application.misc;

import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class ScreenShake {
	
private Random random;
	
	private float elapsed;
	private float duration;
	private float intensity;
	
	public ScreenShake() {
		random = new Random();
		this.elapsed = 100;
		this.duration = 0;
		intensity = 5;
	}
	
	public void shake(float radius , float duration, float intensity) {
		 System.out.println("SHAKE!!!");
	    this.elapsed = 0;
	    this.duration = duration / 1000f;

	}
	
	public void update(float delta, OrthographicCamera camera) {
		 
	    // Only shake when required.
	    if(elapsed < duration) {
	        // Calculate the amount of shake based on how long it has been shaking already
	        float currentPower = intensity * camera.zoom * ((duration - elapsed) / duration);
	        float x = (random.nextFloat() - 0.5f) * currentPower;
	        float y = (random.nextFloat() - 0.5f) * currentPower;
	        camera.translate(-x, -y);
	 
	        // Increase the elapsed time by the delta provided.
	        elapsed += delta;
	    }
	}

}

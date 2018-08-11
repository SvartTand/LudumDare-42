package svarttand.application.misc;

import com.badlogic.gdx.utils.Array;

public class Animation {
	private Array<String> frames;
	private float maxFrameTime;
	private float currentFrameTime;
	private int frameCount;
	private int frame;

	
	public Animation(String name, int frameCount, float cycletime){
		frames = new Array<String>();
		for (int i = 1; i <= frameCount; i++) {
			frames.add(name + i);
		}
		this.frameCount = frameCount;
		maxFrameTime = cycletime / frameCount;
		frame = 0;

	}
	
	
	public void update(float delta){
		currentFrameTime += delta;
		if (currentFrameTime > maxFrameTime){
			frame++;
			currentFrameTime = 0;
		}
		if (frame >= frameCount){
			frame = 0;
		}
	}
	
	public String getFrame(){
		return frames.get(frame);
	}
	
	public String getFrame(int index){
		return frames.get(index);
	}

}

package svarttand.application.misc;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import svarttand.application.states.LoadingState;

public class AudioHandler {
	
	public static final int Explosion = 0;
	public static final int PICKUO = 1;
	public static final int EXPLOSION = 2;
	public static final int SHOOT_1 = 3;
	public static final int Shoot_2 = 4;
	public static final int ERROR = 5;
	
	private ArrayList<Sound> audioList;
	
	public AudioHandler(AssetManager assetManager){
		audioList = new ArrayList<Sound>();
		for (int i = 0; i < LoadingState.AUDIO_AMOUNT; i++) {
			audioList.add(assetManager.get("Audio/"+ i + ".wav",Sound.class));
		}
	}
	
	public Sound getsound(int index){
		return audioList.get(index);
	}
	
	public void playSound(int index){
		audioList.get(index).play();
	}
	
	public void dispose(){
		for (int i = 0; i < audioList.size(); i++) {
			audioList.get(i).dispose();
		}
	}

}

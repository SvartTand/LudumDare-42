package svarttand.application.misc;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import svarttand.application.states.LoadingState;

public class AudioHandler {
	

	
	private ArrayList<Sound> audioList;
	
	public AudioHandler(AssetManager assetManager){
		audioList = new ArrayList<Sound>();
		for (int i = 0; i < LoadingState.AUDIO_AMOUNT; i++) {
			audioList.add(assetManager.get("audio/"+ i + ".wav",Sound.class));
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

package svarttand.application.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import svarttand.application.Application;


public class LoadingState extends State{
	public static final int AUDIO_AMOUNT = 6;
	public static final String ATLAS_PATH = "ThePack.pack";
 	private Viewport viewport;
    private boolean loaded;
    private String[] audioPaths;

    public LoadingState(GameStateManager gsm) {
        super(gsm);
        viewport = new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, cam);
        loaded = false;
        makeAudioPaths();
    }
    
    private void makeAudioPaths(){
    	
    	audioPaths = new String[AUDIO_AMOUNT];
        for (int i = 0; i < AUDIO_AMOUNT; i++) {
        	if (true) {
				audioPaths[i] = "Audio/"+ i + ".wav";
				System.out.println("Audio/"+ i + ".wav");
			}
            
        }
      
    }
    

    private void load(){
        for (int i = 0; i < audioPaths.length; i++) {
        	gsm.assetManager.load(audioPaths[i], Sound.class);
        }
        gsm.assetManager.load(ATLAS_PATH, TextureAtlas.class);
        gsm.assetManager.load("Audio/Music.mp3", Music.class);
        loaded = true;
    }

    @Override
    protected void handleInput(float delta) {

    }

    @Override
    public void update(float delta) {
        if (!loaded){
            load();
        }
        gsm.assetManager.update();
        if (gsm.assetManager.getProgress() >= 1){
        	
            gsm.set(new MenuState(gsm));
            dispose();
            
        }
    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.end();

    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

    }

}

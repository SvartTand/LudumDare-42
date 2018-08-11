package svarttand.application;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import svarttand.application.states.GameStateManager;
import svarttand.application.states.LoadingState;


public class Application extends ApplicationAdapter {
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 800;
	private SpriteBatch batch;
	private GameStateManager gsm;
	private AssetManager assetManager;
	
	@Override
	public void create () {
		Gdx.graphics.setResizable(false);
		assetManager = new AssetManager();
		batch = new SpriteBatch();
		gsm = new GameStateManager(assetManager);
		LoadingState loadingState = new LoadingState(gsm);
		gsm.push(loadingState);
	}

	@Override
	public void render () {
		gsm.update(Gdx.graphics.getDeltaTime());
		
		gsm.render(batch,Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose () {
		gsm.dispose();
	}

	@Override
	public void resize(int width, int height){
		gsm.resize(width, height);

	}
}

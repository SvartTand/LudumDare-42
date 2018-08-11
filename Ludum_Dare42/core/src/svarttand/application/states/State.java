package svarttand.application.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import svarttand.application.Application;


public abstract class State {
	
	 protected OrthographicCamera cam;
	    protected Vector2 mouse;
	    protected GameStateManager gsm;

	    public State(GameStateManager gsm){
	        this.gsm = gsm;
	        cam = new OrthographicCamera();
	        cam.position.set(Application.V_WIDTH*0.5f, Application.V_HEIGHT*0.5f,0);
	        mouse = new Vector2();
	    }

	    protected abstract void handleInput(float delta);
	    public abstract void update(float delta);
	    public abstract void render(SpriteBatch batch, float delta);
	    public abstract void dispose();
	    public abstract void resize(int width, int height);

}

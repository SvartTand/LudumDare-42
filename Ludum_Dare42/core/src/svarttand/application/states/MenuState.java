package svarttand.application.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import svarttand.application.Application;
import svarttand.application.input.MenuUI;

public class MenuState extends State {

	private Viewport viewport;
	private TextureAtlas textureAtlas;
	private MenuUI ui;
	private boolean b;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		textureAtlas = gsm.assetManager.get("ThePack.pack", TextureAtlas.class);
		viewport = new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, cam);
        cam.position.set(Application.V_WIDTH*0.5f, Application.V_HEIGHT*0.5f,0);
        cam.update();
        viewport.apply();
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ui = new MenuUI(this, textureAtlas);
       
	}

	@Override
	protected void handleInput(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta) {
		if (b) {
			ui.init();
			b = false;
		}
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(textureAtlas.findRegion("Background"), 0, 0, Application.V_WIDTH, Application.V_HEIGHT);
		//batch.draw(textureAtlas.findRegion("Title"), Application.V_WIDTH*0.2f, Application.V_HEIGHT*0.7f);
		batch.end();
		ui.getStage().draw();

	}

	@Override
	public void dispose() {
		textureAtlas.dispose();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		ui.getStage().getViewport().update(width, height);

	}

	public void addPlayState() {
		gsm.push(new PlayState(gsm, textureAtlas));
		b = true;
		
	}

}

package svarttand.application.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import svarttand.application.Application;
import svarttand.application.input.InputController;
import svarttand.application.sprites.Player;


public class PlayState extends State{
	
	private TextureAtlas textureAtlas;
	private Viewport viewport;
	private Player player;
	private InputController controller;
	private BitmapFont font;
	private Label label;
	//private LabelStyle style;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		textureAtlas = gsm.assetManager.get("ThePack.pack", TextureAtlas.class);
		viewport = new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		player = new Player(0, 0, textureAtlas);
		controller = new InputController(this);
		
		Gdx.input.setInputProcessor(controller);
		font = new BitmapFont();
		label = new Label("HELLO!", new LabelStyle(font, Color.WHITE));
	}

	@Override
	protected void handleInput(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		player.update(delta, controller.getMouse());
		
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		batch.setProjectionMatrix(cam.combined);
		cam.update();
		Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		label.draw(batch, 1);
		player.draw(batch);
		//batch.draw(textureAtlas.findRegion("Player"), 20, 20);
		batch.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	
	public Player getPlayer() {
		return player;
	}

	public OrthographicCamera getCam() {
		// TODO Auto-generated method stub
		return cam;
	}

}

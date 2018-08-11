package svarttand.application.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import svarttand.application.Application;
import svarttand.application.input.InputController;
import svarttand.application.sprites.Bullet;
import svarttand.application.sprites.Player;
import svarttand.application.world.Map;


public class PlayState extends State{
	
	private TextureAtlas textureAtlas;
	private Viewport viewport;
	private Player player;
	private InputController controller;
	private Map map;
	
	private ArrayList<Bullet> bullets;
//	private BitmapFont font;
//	private Label label;
	//private LabelStyle style;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		textureAtlas = gsm.assetManager.get("ThePack.pack", TextureAtlas.class);
		viewport = new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		player = new Player(0, 0, textureAtlas);
		controller = new InputController(this);
		map = new Map();
		Gdx.input.setInputProcessor(controller);
		bullets = new ArrayList<Bullet>();
//		font = new BitmapFont();
//		label = new Label("HELLO!", new LabelStyle(font, Color.WHITE));
	}

	@Override
	protected void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Keys.W)) {
			player.setPressed(true);
		}else{
			player.setPressed(false);
		}
		
	}

	@Override
	public void update(float delta) {
		handleInput(delta);
		player.update(delta, controller.getMouse());
		cam.position.x = player.getX();
		cam.position.y = player.getY();
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(delta);
		}
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		batch.setProjectionMatrix(cam.combined);
		cam.update();
		Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		map.render(batch, textureAtlas);
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(batch);
		}
		player.draw(batch);
		//batch.draw(textureAtlas.findRegion("Player"), 20, 20);
		batch.end();
	}

	@Override
	public void dispose() {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).dispose();
		}
		
	}

	@Override
	public void resize(int width, int height) {
		
		cam.viewportHeight = width;
		cam.viewportWidth = height;
		cam.update();
		//viewport.update(width, height);
		
		//viewport.setScreenSize(width, height);
		
	}
	
	public Player getPlayer() {
		return player;
	}

	public OrthographicCamera getCam() {
		// TODO Auto-generated method stub
		return cam;
	}

	public void addBullet() {
		bullets.add(new Bullet(textureAtlas, player.getX(), player.getY(), player.getRotation()));
		
	}

}

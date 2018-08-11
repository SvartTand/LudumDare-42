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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import svarttand.application.Application;
import svarttand.application.input.InputController;
import svarttand.application.misc.ParticleHandler;
import svarttand.application.misc.ParticleType;
import svarttand.application.misc.ScreenShake;
import svarttand.application.sprites.EnemyHandler;
import svarttand.application.sprites.Player;
import svarttand.application.sprites.effects.AttackHandler;
import svarttand.application.sprites.effects.Bullet;
import svarttand.application.sprites.effects.BulletHandler;
import svarttand.application.world.Map;


public class PlayState extends State{
	
	private TextureAtlas textureAtlas;
	private Viewport viewport;
	private Player player;
	private InputController controller;
	private Map map;
	private EnemyHandler enemyHandler;
	private BulletHandler bullets;
	private AttackHandler attackHandler;
	
	private ScreenShake screenShake;
	private ParticleHandler particleHandler;
	private World world;
	private RayHandler rayHandler;
	private PointLight light;
//	private BitmapFont font;
//	private Label label;
	//private LabelStyle style;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		textureAtlas = gsm.assetManager.get("ThePack.pack", TextureAtlas.class);
		viewport = new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		controller = new InputController(this);
		map = new Map();
		player = new Player(map.getWorldSize()*0.5f, map.getWorldSize()*0.5f, textureAtlas, this);
		Gdx.input.setInputProcessor(controller);
		bullets = new BulletHandler(this);
		enemyHandler = new EnemyHandler();
		attackHandler = new AttackHandler();
		screenShake = new ScreenShake();
		particleHandler = new ParticleHandler(textureAtlas);
		particleHandler.addParticleEffect(ParticleType.HIT, -100, -100);
		
//		world = new World(new Vector2(Application.V_WIDTH,Application.V_HEIGHT),false);
//		rayHandler = new RayHandler(world);
//		rayHandler.setCombinedMatrix(cam.combined);
//		light = new PointLight(rayHandler,100, Color.YELLOW,300,100,100);
//		
//		rayHandler.setAmbientLight(.7f);
//		font = new BitmapFont();
//		label = new Label("HELLO!", new LabelStyle(font, Color.WHITE));
	}

	@Override
	protected void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Keys.W)) {
			player.setSpeedUp(Player.MAX_SPEED);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			player.setSpeedUp(-Player.MAX_SPEED);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			player.setSpeedSide(-Player.MAX_SPEED);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			player.setSpeedSide(Player.MAX_SPEED);
		}
		
	}

	@Override
	public void update(float delta) {
		
		
		enemyHandler.update(delta, textureAtlas, player.getPosition(), bullets, map.getWorldSize());
		handleInput(delta);
		player.update(delta, controller.getMouse(), textureAtlas, bullets, screenShake);
		
		cam.position.x = player.getX();
		cam.position.y = player.getY();
		bullets.update(delta, enemyHandler, player, particleHandler);
		
		System.out.println(cam.position.x + ", " + cam.position.y);
		if (cam.position.y < 400) {
			cam.position.y = 400;
		}
		if (cam.position.y > 2800) {
			cam.position.y = 2800;
		}
		if (cam.position.x < 400) {
			cam.position.x = 400;
		}
		if (cam.position.x > 2800) {
			cam.position.x = 2800;
		}
		//attackHandler.update(delta);
		//light.setPosition(player.getPosition().x, player.getPosition().y);
	}

	@Override
	public void render(SpriteBatch batch, float delta) {
		screenShake.update(delta, cam);
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
		cam.update();
		batch.begin();
		map.render(batch, textureAtlas);
		bullets.render(batch);
		enemyHandler.render(batch);

		player.draw(batch);
		attackHandler.render(batch, textureAtlas);
		
		map.renderLeaves(batch, textureAtlas);
		particleHandler.render(batch, delta);
		//batch.draw(textureAtlas.findRegion("Player"), 20, 20);
		
		batch.end();
		//rayHandler.updateAndRender();
		
	}

	@Override
	public void dispose() {
		particleHandler.dispose();
		
	}

	@Override
	public void resize(int width, int height) {
		
		cam.viewportHeight = width;
		cam.viewportWidth = height;
		cam.update();
		//viewport.update(width, height);
		
		//viewport.setScreenSize(width, height);
		
	}
	
	public ParticleHandler getParticleHandler(){
		return particleHandler;
	}
	
	public Player getPlayer() {
		return player;
	}

	public OrthographicCamera getCam() {
		// TODO Auto-generated method stub
		return cam;
	}

	public void addBullet() {
		player.shoot();
		
		
	}

	public void addAttack() {
		attackHandler.addAttack(player.getPosition(), textureAtlas, player.getRotation());
		
	}
	
	public void shake(){
		screenShake.shake(500, 1000, 500);
	}

	public void dmg() {
		Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth() -100, Gdx.graphics.getHeight() -100);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//screenShake.shake(100, 1000, 1000);
		
	}

}

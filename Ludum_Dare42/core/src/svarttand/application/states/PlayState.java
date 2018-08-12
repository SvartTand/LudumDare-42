package svarttand.application.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import svarttand.application.Application;
import svarttand.application.input.InputController;
import svarttand.application.input.PlayUI;
import svarttand.application.misc.AudioHandler;
import svarttand.application.misc.LightHandler;
import svarttand.application.misc.ParticleHandler;
import svarttand.application.misc.ParticleType;
import svarttand.application.misc.ScreenShake;
import svarttand.application.sprites.EnemyHandler;
import svarttand.application.sprites.Player;
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
	
	private ScreenShake screenShake;
	private ParticleHandler particleHandler;
	private World world;
	private RayHandler rayHandler;
	private PointLight light;
	private ConeLight coneLight;
	
	private PlayUI ui;
	private AudioHandler audioHandler;
	private ShapeRenderer renderer;
	
	private LightHandler lightHandler;
	
	private Music music;
//	private BitmapFont font;
//	private Label label;
	//private LabelStyle style;

	public PlayState(GameStateManager gsm, TextureAtlas textureAtlas) {
		super(gsm);
		this.textureAtlas= textureAtlas;
		viewport = new FitViewport(Application.V_WIDTH, Application.V_HEIGHT, cam);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		renderer = new ShapeRenderer();
		audioHandler = new AudioHandler(gsm.assetManager);
		music = gsm.assetManager.get("Audio/Music.mp3", Music.class);
		music.setLooping(true);
		music.play();
		
		lightHandler = new LightHandler();
		world = new World(new Vector2(Application.V_WIDTH,Application.V_HEIGHT),false);
		rayHandler = new RayHandler(world);
		RayHandler.useDiffuseLight(true);
		
		controller = new InputController(this);
		map = new Map(textureAtlas, rayHandler);
		player = new Player(map.getWorldSize()*0.5f, map.getWorldSize()*0.5f, textureAtlas, this);
		Gdx.input.setInputProcessor(controller);
		bullets = new BulletHandler(this);
		enemyHandler = new EnemyHandler(this);

		screenShake = new ScreenShake();
		particleHandler = new ParticleHandler(textureAtlas);
		
		ui = new PlayUI(textureAtlas, this);
		
		particleHandler.addParticleEffect(ParticleType.HIT, 0, 0, 1);
		particleHandler.addParticleEffect(ParticleType.HIT2, 0, 0, 1);
		particleHandler.addParticleEffect(ParticleType.FIRE, 0, 0, 1);
		particleHandler.addParticleEffect(ParticleType.ZFIRE, 0, 0, 1);
		particleHandler.addParticleEffect(ParticleType.PICKUP, 0, 0, 1);
		particleHandler.addParticleEffect(ParticleType.HP_PICKUP, 0, 0, 1);
		
		
		light = new PointLight(rayHandler,100, Color.YELLOW,100,100,100);
		coneLight = new ConeLight(rayHandler, 100, Color.YELLOW, 600, 100, 100, player.getRotation()+ 90, 30);
		rayHandler.setAmbientLight(new Color(.4f, .4f, .4f, .4f));
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
		
		if (Gdx.input.isKeyPressed(Keys.V)) {
			particleHandler.addParticleEffect(ParticleType.HIT, 0, 0, 0);
			particleHandler.addParticleEffect(ParticleType.HIT2, 0, 0, 0);
			particleHandler.addParticleEffect(ParticleType.FIRE, 0, 0, 0);
			particleHandler.addParticleEffect(ParticleType.ZFIRE, 0, 0, 0);
			particleHandler.addParticleEffect(ParticleType.PICKUP, 0, 0, 0);
			particleHandler.addParticleEffect(ParticleType.HP_PICKUP, 0, 0, 0);
			particleHandler.addParticleEffect(ParticleType.HP_PICKUP, 0, 0, 0);
		}
		
	}

	@Override
	public void update(float delta) {
		
		
		enemyHandler.update(delta, textureAtlas, player.getPosition(), bullets, map.getWorldSize(), particleHandler, rayHandler);
		handleInput(delta);
		player.update(delta, controller.getMouse(), textureAtlas, bullets, screenShake, particleHandler, audioHandler, rayHandler);
		
		cam.position.x = player.getX();
		cam.position.y = player.getY();
		bullets.update(delta, enemyHandler, player, particleHandler);
		map.update(delta, player, audioHandler, this);
		//System.out.println(cam.position.x + ", " + cam.position.y);
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
		
		ui.update(delta, enemyHandler.getKills());
		//attackHandler.update(delta);
		light.setPosition(player.getPosition().x, player.getPosition().y);
		coneLight.setPosition(player.getPosition());
		coneLight.setDirection(player.getRotation()+90);
		lightHandler.update(delta);
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
		
		enemyHandler.render(batch);

		player.draw(batch);
		//attackHandler.render(batch, textureAtlas);
		map.renderPickups(batch);
		map.renderLeaves(batch, textureAtlas);
		bullets.render(batch);
		particleHandler.render(batch, delta);
		//batch.draw(textureAtlas.findRegion("Player"), 20, 20);
		
		batch.end();
		rayHandler.setCombinedMatrix(cam);
		rayHandler.updateAndRender();
		ui.render(renderer, enemyHandler.getBoss(), cam);
		ui.getStage().draw();
		
		
	}

	@Override
	public void dispose() {
		particleHandler.dispose();
		audioHandler.dispose();
		ui.getStage().dispose();
		music.dispose();
		rayHandler.dispose();
	}

	@Override
	public void resize(int width, int height) {
		
		cam.viewportHeight = width;
		cam.viewportWidth = height;
		cam.update();
		//viewport.update(width, height);
		
		//viewport.setScreenSize(width, height);
		
	}
	
	public void addLight(Vector2 pos){
		lightHandler.addLight(pos, rayHandler);
	}
	
	public ParticleHandler getParticleHandler(){
		return particleHandler;
	}
	
	public Player getPlayer() {
		return player;
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public void addBullet() {
		player.shoot(audioHandler);
		
		
	}

	public void addAttack() {
		player.shootR(audioHandler);
		
	}
	
	public void shake(){
		screenShake.shake(500, 1000, 500);
	}

	public void dmg() {
		Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth() -100, Gdx.graphics.getHeight() -100);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ui.resize(Gdx.graphics.getWidth());
		if (player.getHP() == 0) {
			defeat();
		}
		//screenShake.shake(100, 1000, 1000);
		
	}
	
	public void heal(){
		Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth()+ 100, Gdx.graphics.getHeight()+ 100);
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		ui.resize(Gdx.graphics.getWidth());
		
	}
	
	public AudioHandler getAudioHandler(){
		return audioHandler;
	}
	public void defeat(){
		System.out.println("DEFEAT");
		gsm.setText("You Have Been Defeated...");
		music.stop();
		gsm.pop();
		Gdx.graphics.setWindowedMode(Application.V_WIDTH, Application.V_HEIGHT);
		//Gdx.app.exit();
	}

	public void victory() {
		System.out.println("VICTORY");
		gsm.setText("You Are Victorious!");
		gsm.pop();
		music.stop();
		Gdx.graphics.setWindowedMode(Application.V_WIDTH, Application.V_HEIGHT);
		
		//Gdx.app.exit();
		
	}

	public void boss() {
		ui.addBossLabel();
		
	}

	public RayHandler getRayhandler() {
		return rayHandler;
	}

}

package svarttand.application.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import svarttand.application.Application;
import svarttand.application.sprites.Boss;
import svarttand.application.sprites.EnemyHandler;
import svarttand.application.sprites.Player;
import svarttand.application.states.PlayState;

public class PlayUI {
	
	
	
	private static final float RECT_WIDTH = 400;
	private static final float RECT_HEIGHT = 40;
	Stage stage;
	PlayState state;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private BitmapFont font;
	private LabelStyle labelStyle;
	
	private Label kilLabel;
	private Label ammoLabel;
	private Label hpLabel;
	private Label bossLabel;
	
	
	public PlayUI(TextureAtlas atlas, final PlayState state){
		camera = new OrthographicCamera();
		viewport = new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, camera);
		stage = new Stage(viewport);
		this.state = state;
		
		font = new BitmapFont();
		labelStyle = new LabelStyle(font, Color.WHITE);
		kilLabel = new Label("Kill Count: 0/" + EnemyHandler.GOAL, labelStyle);
		kilLabel.setPosition(Application.V_WIDTH*0.5f, Application.V_HEIGHT *0.9f);
		stage.addActor(kilLabel);
		
		ammoLabel = new Label("Ammo: 10", labelStyle);
		ammoLabel.setPosition(Application.V_WIDTH*0.05f, Application.V_HEIGHT *0.05f);
		stage.addActor(ammoLabel);
		
		hpLabel = new Label("Hp: "+Player.MAXHP+"/"+Player.MAXHP, labelStyle);
		hpLabel.setPosition(Application.V_WIDTH*0.05f, Application.V_HEIGHT *0.03f);
		stage.addActor(hpLabel);
		
		bossLabel = new Label("Boss Hp:", labelStyle);
		bossLabel.setPosition(Application.V_WIDTH*0.5f, Application.V_HEIGHT *0.04f);
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public void render(ShapeRenderer renderer, Boss boss, OrthographicCamera cam){
		if (boss != null) {

			
			bossLabel.setText("Boss Hp: " + boss.getHp() +"/" + Boss.HP);
			renderer.setProjectionMatrix(camera.combined);
			renderer.begin(ShapeType.Filled);
			renderer.setColor(Color.BLACK);
			renderer.rect(Application.V_WIDTH*0.5f - Boss.HP*0.5f, Application.V_HEIGHT*0.04f, RECT_WIDTH, RECT_HEIGHT);
			renderer.setColor(Color.FIREBRICK);
			renderer.rect(Application.V_WIDTH*0.5f - Boss.HP*0.5f, Application.V_HEIGHT*0.04f, (float)boss.getHp()/Boss.HP * RECT_WIDTH, RECT_HEIGHT);
			renderer.end();
		}
		
	}
	
	public void addBossLabel(){
		stage.addActor(bossLabel);
	}
		

	public void update(float delta, int kills) {
		kilLabel.setText("Kill Count: " + kills +"/"+ EnemyHandler.GOAL);
		ammoLabel.setText("Ammo: " + state.getPlayer().getAmmo());
		hpLabel.setText("Hp: " + state.getPlayer().getHP() + "/" + Player.MAXHP);
	}
	
	public void resize(float scale){
		float s = Application.V_HEIGHT*2f -scale;
		kilLabel.setFontScale(s/Application.V_HEIGHT);
		ammoLabel.setFontScale(s/Application.V_HEIGHT);
		hpLabel.setFontScale(s/Application.V_HEIGHT);
	}

}

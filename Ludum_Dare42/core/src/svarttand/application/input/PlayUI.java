package svarttand.application.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import svarttand.application.Application;
import svarttand.application.states.PlayState;

public class PlayUI {
	
	Stage stage;
	PlayState state;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private BitmapFont font;
	private LabelStyle labelStyle;
	
	private Label kilLabel;
	
	public PlayUI(TextureAtlas atlas, final PlayState state){
		camera = new OrthographicCamera();
		viewport = new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, camera);
		stage = new Stage(viewport);
		this.state = state;
		
		font = new BitmapFont();
		labelStyle = new LabelStyle(font, Color.WHITE);
		kilLabel = new Label("Kill Count: 0", labelStyle);
		kilLabel.setPosition(Application.V_WIDTH*0.5f, Application.V_HEIGHT *0.9f);
		stage.addActor(kilLabel);
	}
	
	public Stage getStage(){
		return stage;
	}

	public void update(float delta, int kills) {
		kilLabel.setText("Kill Count: " + kills);
		
	}

}

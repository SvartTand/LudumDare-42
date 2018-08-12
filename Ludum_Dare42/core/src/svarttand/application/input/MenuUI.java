package svarttand.application.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import svarttand.application.Application;
import svarttand.application.states.MenuState;

public class MenuUI {
	
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	private Stage stage;
	
	private TextButton.TextButtonStyle style;
	private Skin skin;
	private BitmapFont font;
	
	private Button playButton;
	
	private Label infoText;
	private Label gameOverText;
	private Label controlls;
	private LabelStyle labelStyle;
	private Label objective;
	
	public MenuUI(final MenuState state, TextureAtlas atlas) {
		camera = new OrthographicCamera();
		viewport = new StretchViewport(Application.V_WIDTH, Application.V_HEIGHT, camera);
		stage = new Stage(viewport);
		
		
		font = new BitmapFont();
	    skin = new Skin(atlas);
	    style = new TextButton.TextButtonStyle();
	    style.font = font;
	    style.up = skin.getDrawable("Button");
	    style.down = skin.getDrawable("ButtonPressed");
	    
	    labelStyle = new LabelStyle(font, Color.WHITE);
	    infoText = new Label("Made by Albert Lindberg\nfor Ludum Dare \nin 48 hours" , labelStyle);
	    infoText.setPosition(Application.V_WIDTH *0.12f - infoText.getWidth()*0.5f, Application.V_HEIGHT*0.03f);
	    stage.addActor(infoText);
	    
	    controlls = new Label("Controlls: Movement: W/A/S/D, Primary Fire: LeftMouse, Secondary Fire: RightMouse" , labelStyle);
	    controlls.setPosition(Application.V_WIDTH *0.5f - controlls.getWidth()*0.5f, Application.V_HEIGHT*0.15f);
	    stage.addActor(controlls);
	    
	    objective = new Label("Deafeat the Boss that spawns after you killed some of his minions" , labelStyle);
	    objective.setPosition(Application.V_WIDTH *0.5f - objective.getWidth()*0.5f, Application.V_HEIGHT*0.2f);
	    stage.addActor(objective);
	    
	    gameOverText = new Label("GAME OVER!" , labelStyle);
	    gameOverText.setPosition(Application.V_WIDTH *0.5f - gameOverText.getWidth()*0.5f, Application.V_HEIGHT*0.3f);
	    stage.addActor(gameOverText);
	    gameOverText.setVisible(false);
	    
	    playButton = new TextButton("Play", style);
	    playButton.setPosition(Application.V_WIDTH*0.5f-playButton.getWidth()*0.5f, Application.V_HEIGHT*0.25f -playButton.getHeight()*0.5f);
	    playButton.addListener( new ClickListener() {
	         @Override
	         public void clicked(InputEvent event, float x, float y) {
	        	 System.out.println("Play pressed");
	        	 state.addPlayState();
	            }
	        });
	    stage.addActor(playButton);
	    
	    Gdx.input.setInputProcessor(stage);
	}
	
	public void dispos(){
		stage.dispose();
		font.dispose();
	}
	
	public void updateGameOverText(int score, int wave){
		gameOverText.setVisible(true);
		gameOverText.setText("GAME OVER!\nScore: " + score + "\nWave: " + wave);
		gameOverText.setPosition(Application.V_WIDTH *0.5f - gameOverText.getWidth()*0.5f, Application.V_HEIGHT*0.3f);
	}
	
	public Stage getStage(){
		return stage;
	}

	public void init(String string) {
		System.out.println(string);
		gameOverText.setText(string);
		Gdx.input.setInputProcessor(stage);
		gameOverText.setVisible(true);
		gameOverText.setPosition(Application.V_WIDTH *0.5f - gameOverText.getWidth()*0.7f, Application.V_HEIGHT*0.3f);
		
	}

}

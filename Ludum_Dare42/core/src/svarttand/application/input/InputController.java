package svarttand.application.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import svarttand.application.Application;
import svarttand.application.sprites.Player;
import svarttand.application.states.PlayState;

public class InputController implements InputProcessor{
	
	private PlayState state;
	
	public InputController(PlayState playstate) {
		state = playstate;
	}
		

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.A) {
//			Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth() -100, Gdx.graphics.getHeight() -100);
//			state.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			state.shake();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//System.out.println("shoot " + button);
		if (button == 0) {
			state.addBullet();
		}else if (button == 1) {
			state.addAttack();
		}
		
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		state.getPlayer().updateRotation(convertToGameCordinates(screenX, screenY));
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Vector2 convertToGameCordinates(int x, int y){
		Vector2 v = new Vector2(0,0);
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		 w = w / Application.V_WIDTH;
		 h = h / Application.V_HEIGHT;
		 
		 w = x / w;
		 h = y / h;
		 
		 w = (float) (state.getCam().position.x + w - Application.V_WIDTH * 0.5);
		 h = (float) ( -state.getCam().position.y + h + Application.V_HEIGHT*0.5);
		 
		 
		 v.x = w;
		 v.y = h;
		
		return v;
	}


	public Vector2 getMouse() {
		// TODO Auto-generated method stub
		return convertToGameCordinates(Gdx.input.getX(), Gdx.input.getY());
	}

}

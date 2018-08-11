package svarttand.application.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {
	
	private String path;
	private Rectangle bounds;
	private boolean passable;
	
	private Vector2 position;
	
	public Tile(String path, int x, int y) {
		this.path = path;
		position = new Vector2(x,y);
	}
	
	public String getPath(){
		return path;
	}

	public float getPosX() {
		// TODO Auto-generated method stub
		return position.x;
	}

	public float getPosY() {
		// TODO Auto-generated method stub
		return position.y;
	}

}

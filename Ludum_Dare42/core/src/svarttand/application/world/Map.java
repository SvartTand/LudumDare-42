package svarttand.application.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Map {
	
	private static final int SIZE = 50;
	private static final int TILE_SIZE = 32;
	private Tile[][] map;
	
	public Map() {
		map = new Tile[SIZE][SIZE];
		generateMap();
	}

	private void generateMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile("GrassTile",i * TILE_SIZE, j *TILE_SIZE);

				
			}
		}
		
	}
	
	public void render(SpriteBatch batch, TextureAtlas atlas){
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {

				batch.draw(atlas.findRegion(map[i][j].getPath()), map[i][j].getPosX(), map[i][j].getPosY());
			}
		}
	}
	

}

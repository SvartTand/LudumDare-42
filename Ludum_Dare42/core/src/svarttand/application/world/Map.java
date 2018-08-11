package svarttand.application.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Map {
	
	private static final int SIZE = 50;
	private static final int TILE_SIZE = 32;
	private Tile[][] map;
	private Tile[][] leavesMap;
	
	private ArrayList<Tile> obstacles;
	
	public Map() {
		map = new Tile[SIZE][SIZE];
		leavesMap = new Tile[SIZE][SIZE];
		generateMap();
	}

	private void generateMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				double rand =Math.random() * 100 +1;
				rand = (int) rand;
				System.out.println(rand);
				if (rand == 5) {
					map[i][j] = new Tile("TreeTile",i * TILE_SIZE, j *TILE_SIZE);
					for (int k = i-3; k < i+4; k++) {
						for (int l = j-3; l < j+4; l++) {
							try {
								leavesMap[k][l] = new Tile("LeavesTile",k * TILE_SIZE, l *TILE_SIZE);
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}
					System.out.println("yo");
				}else{
					map[i][j] = new Tile("GrassTile",i * TILE_SIZE, j *TILE_SIZE);
				}
				

				
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
	
	public void renderLeaves(SpriteBatch batch, TextureAtlas atlas){
		for (int i = 0; i < leavesMap.length; i++) {
			for (int j = 0; j < leavesMap[i].length; j++) {
				if (leavesMap[i][j] != null) {
					batch.draw(atlas.findRegion(leavesMap[i][j].getPath()), leavesMap[i][j].getPosX(), leavesMap[i][j].getPosY());
				}
				
			}
		}
	}
	

}

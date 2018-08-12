package svarttand.application.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import svarttand.application.misc.AudioHandler;
import svarttand.application.sprites.Player;
import svarttand.application.states.PlayState;

public class Map {
	
	private static final int SIZE = 100;
	private static final int TILE_SIZE = 32;
	
	private static final int NUMBER_OF_PICKUPS = 10;
	private Tile[][] map;
	private Tile[][] leavesMap;
	
	private ArrayList<Tile> obstacles;
	private ArrayList<Pickup> pickups;
	
	public Map(TextureAtlas atlas) {
		map = new Tile[SIZE][SIZE];
		leavesMap = new Tile[SIZE][SIZE];
		pickups = new ArrayList<Pickup>();
		generateMap(atlas);
	}

	private void generateMap(TextureAtlas atlas) {
		int pickup = NUMBER_OF_PICKUPS;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				double randP =Math.random() * 1000 +1;
				randP = (int) randP;
				if (randP == 50) {
					pickups.add(new Pickup(atlas, i*TILE_SIZE + TILE_SIZE*0.5f, j*TILE_SIZE + TILE_SIZE*0.5f, "Pickup", 0, 1));
				}
				if (randP <= 2d) {
					pickups.add(new Pickup(atlas, i*TILE_SIZE + TILE_SIZE*0.5f, j*TILE_SIZE + TILE_SIZE*0.5f, "Ammo", 10, 0));
				}
				
				double rand =Math.random() * 100 +1;
				rand = (int) rand;
				if (rand == 5) {
					map[i][j] = new Tile("TreeTile",i * TILE_SIZE, j *TILE_SIZE);
					for (int k = i-3; k < i+4; k++) {
						for (int l = j-3; l < j+4; l++) {
							if (k == i-3 && l == j-3 ||
								k == i-3 && l == j+3 ||
								k == i+3 && l == j-3 ||
								k == i+3 && l == j+3) {
								
							}else{
								try {
									leavesMap[k][l] = new Tile("LeavesTile",k * TILE_SIZE, l *TILE_SIZE);
								} catch (Exception e) {
									// TODO: handle exception
								}
							
							}
						}
					}
				}else if (rand > 75) {
					map[i][j] = new Tile("DirtTile",i * TILE_SIZE, j *TILE_SIZE);
				}else if (rand > 40) {
					map[i][j] = new Tile("DirtGrassTile",i * TILE_SIZE, j *TILE_SIZE);
				}
				else{
					map[i][j] = new Tile("DirtTile",i * TILE_SIZE, j *TILE_SIZE);
				}
				

				
			}
		}
		
	}
	public void update(float delta, Player player, AudioHandler audioHandler, PlayState state){
		for (int i = 0; i < pickups.size(); i++) {
			pickups.get(i).update(delta);
			if (pickups.get(i).getBoundingRectangle().overlaps(player.getBounds())) {
				if (pickups.get(i).getHp() > 0) {
					if (player.getHP() >= player.MAXHP) {
						//audioHandler.playSound(AudioHandler.EXPLOSION);
						break;
					}
					player.addHP(pickups.get(i).getHp());
					state.heal();
				}
				player.addAmmo(pickups.get(i).getAmmo());
				
				
				System.out.println(player.getAmmo() + ", " + player.getHP());
				pickups.remove(i);
				
				audioHandler.playSound(AudioHandler.PICKUO);
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
	
	public void renderPickups(SpriteBatch batch){
		for (int i = 0; i < pickups.size(); i++) {
			pickups.get(i).draw(batch);
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
	public float getWorldSize(){
		return TILE_SIZE * SIZE;
	}
	

}

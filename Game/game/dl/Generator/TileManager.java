package game.dl.Generator;

import game.dl.MoveableObjects.Player;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class TileManager {

	public static ArrayList<Block> blocks = new ArrayList<Block>();

	public TileManager() {

	}

	public void tick(double deltaTime) {
		for (Block block : blocks) {
			block.tick(deltaTime);
			
			if(Player.render.intersects(block)){
				block.setAlive(true);
			}else{
				block.setAlive(false);
			}
			
		}
	}

	public void render(Graphics2D g) {
		for (Block block : blocks) {
			block.render(g);
		}
	}

}

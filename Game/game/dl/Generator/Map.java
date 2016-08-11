package game.dl.Generator;

import game.dl.Generator.Block.BlockType;
import game.dl.MoveableObjects.Player;
import game.dl.main.Main;
import game.gos.main.VectorToF;
import game.gos.main.loadImageFrom;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Map {

	
	public static BufferedImage map = null;
	
	public World world;
	
	public Map() {

	}

	public void init() {
		System.out.println("INIT Map");
		
		try {
			map = loadImageFrom.LoadImageFrom(Main.class, "map.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		world = new World("world", map, 100, 100);
		world.generateWorld();
	}

	public void tick(double deltaTime) {
		world.tick(deltaTime);
	}

	public void render(Graphics2D g) {
		world.render(g);
	}

}

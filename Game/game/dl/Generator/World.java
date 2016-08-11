package game.dl.Generator;

import game.dl.Generator.Block.BlockType;
import game.dl.MoveableObjects.Player;
import game.gos.main.VectorToF;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class World {

	private String worldName;
	private BufferedImage world_image;
	private int world_width;
	private int world_height;
	private TileManager tiles = new TileManager();
	private Player player = new Player();
	public static CopyOnWriteArrayList<BlockEntity> blockEnts = new CopyOnWriteArrayList<BlockEntity>();
		
	public World(String worldName, BufferedImage world_image, int world_width, int world_height){
		this.worldName = worldName;
		this.world_image = world_image;
		this.world_width = world_width;
		this.world_height = world_height;
		
	}
	
	public void generateWorld(){
		

		for (int x = 0; x < world_width; x++) {
			for (int y = 0; y < world_height; y++) {

				int col = world_image.getRGB(x, y);

				switch (col & 0xFFFFFF) {

				case 0x808080:
					tiles.blocks.add(new Block(new VectorToF(x * 64, y * 64),
							BlockType.STONE_1));
					break;
									
				case 0x404040:
					tiles.blocks.add(new Block(new VectorToF(x * 64, y * 64),BlockType.WALL_1).isSolid(true));
					break;
					
				case 0x303030:
					tiles.blocks.add(new Block(new VectorToF(x * 64, y * 64),BlockType.WALL_TOP).isSolid(true).isSolid(true));

				}

			}
		}
		player.init(this);
	}
	
	public void tick(double deltaTime) {
		tiles.tick(deltaTime);
//		 tick block entity
		if (!blockEnts.isEmpty()) {
			for (BlockEntity ent : blockEnts) {
				if (player.render.intersects(ent)) {
					ent.tick(deltaTime);
					ent.setAlive(true);
				} else {
					ent.setAlive(false);
				}
			}
		}
		player.tick(deltaTime);
	}

	public void render(Graphics2D g) {
		tiles.render(g);
		
		if (!blockEnts.isEmpty()){
			for(BlockEntity ent: blockEnts){
				if(player.render.intersects(ent)){
					ent.render(g);
				}
			}
		}
		player.render(g);
		
		g.drawString(blockEnts.size() + "", 50, 200);// debug number of entities shown on screen
	}
	
	public static void dropBlockEntity(VectorToF pos, BufferedImage block_image){
		
		BlockEntity ent = new BlockEntity(pos, block_image);
		if(!blockEnts.contains(ent)){
			blockEnts.add(ent);
		}
		
	}
	
	public String getWorldName() {
		return worldName;
	}
	
	public BufferedImage getWorld_image() {
		return world_image;
	}

	public static void removeDroppedEntity(BlockEntity ent) {
		if(blockEnts.contains(ent)){
			blockEnts.remove(ent);
		}		
	}

}

package game.dl.Generator;

import game.dl.main.Assets;
import game.gos.main.VectorToF;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Block extends Rectangle {

	VectorToF pos = new VectorToF();
	private int blockSize = 64;//42 is optimal, 64 makes less blocks, change height and width in player when changing this
	private BlockType blockType;
	private BufferedImage block;
	private boolean isSolid;
	private boolean dropped = false;
	private boolean isAlive;
	
	public Block(VectorToF pos, BlockType blockType) {
		this.pos = pos;
		isAlive = true;
		this.blockType = blockType;
		init();
	}
	
	public Block isSolid(boolean isSolid){
		this.isSolid = isSolid;
		return this;
	}

	public void init() {
		switch (blockType) {
		case STONE_1:
			block = Assets.getStone_1();
			break;
					
		case WALL_1:
			block = Assets.getWall_1();
			break;
		
		case WALL_TOP:
			block = Assets.getwall_top();
			
		}
	}

	public void tick(double deltaTime) {
		if(isAlive){
			setBounds((int)pos.xpos, (int)pos.ypos, blockSize, blockSize);
		}
	}

	public void render(Graphics2D g) {
		
		if(isAlive){
			g.drawImage(block, (int) pos.getWorldLocation().xpos,
					(int) pos.getWorldLocation().ypos, blockSize, blockSize, null);
			if(isSolid){
				
				g.drawRect((int) pos.getWorldLocation().xpos,
						(int) pos.getWorldLocation().ypos, blockSize, blockSize);
			}
		}else{
			if(!dropped){
				float xpos = pos.xpos + 24 -12;
				float ypos = pos.ypos + 24 -12;
				
				VectorToF newPos = new VectorToF(xpos, ypos);
				
				World.dropBlockEntity(newPos, block);				
				dropped = true;
			}
		}
				
	}

	public enum BlockType {
		STONE_1,
		WALL_1,
		WALL_TOP
	}

	public boolean isSolid() {
		return isSolid;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean alive){
		isAlive = alive;
	}
	

}

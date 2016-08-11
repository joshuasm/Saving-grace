package game.dl.Generator;

import game.gos.main.VectorToF;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BlockEntity extends Rectangle {
	
	private VectorToF pos;
	private BufferedImage blockImage;
	private double rotation;
	private double rotationSpeed = 0.8;
	private double blockSize = 24;
	private boolean isAlive;
	private int lifeTime;
	private boolean isDying;
	private float lifeFade = 1;
	

	public BlockEntity(VectorToF pos, BufferedImage blockImage) {
		this.pos = pos;
		this.blockImage = blockImage;
		rotation = new Random().nextInt(720);
		lifeTime = new Random().nextInt(500);
		if(lifeTime < 300){
			lifeTime = new Random().nextInt(500);
		}
		setBounds((int)pos.xpos, (int)pos.ypos, (int)blockSize, (int)blockSize);
		
	}
	
	public void tick(double deltaTime){
		if(isAlive){
			rotation -= rotationSpeed;
			setBounds((int)pos.xpos, (int)pos.ypos, (int)blockSize, (int)blockSize);
			if (!isDying){
				
				if(lifeTime != 0){
					lifeTime --;
				}
				if (lifeTime == 0){
					isDying = true;
				}
			}
			if(isDying){
				if(lifeFade != 0.000010000){
					lifeFade -= 0.01;
				}
				if (lifeFade<0.5){
					blockSize -= 0.2;
//					TODO: fade to middle or not?
					pos.xpos += 0.1;
					pos.ypos += 0.1;
				}
				if(lifeFade <= 0.000010000){
					World.removeDroppedEntity(this);
					isAlive = false;
					
				}
			}
		}
	}
	
	public void render(Graphics2D g) {
		if(isAlive){
			
			if(isDying){
				
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lifeFade));
			}
			
			g.rotate(Math.toRadians(rotation), pos.getWorldLocation().xpos + blockSize / 2,
					pos.getWorldLocation().ypos + blockSize / 2);
			
			g.drawImage(blockImage, (int) pos.getWorldLocation().xpos,
					(int) pos.getWorldLocation().ypos, (int) blockSize,(int) blockSize, null);		
			g.drawRect((int) pos.getWorldLocation().xpos, (int) pos.getWorldLocation().ypos,
					(int) blockSize,(int) blockSize);		
			
			
			g.rotate(-Math.toRadians(rotation), pos.getWorldLocation().xpos + blockSize / 2,
					pos.getWorldLocation().ypos + blockSize / 2);
			
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
}

package game.dl.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import game.dl.Managers.MouseManager;
import game.dl.gamestate.GameState;
import game.dl.gamestate.GameStateManager;
import game.dl.main.Assets;
import game.gos.main.VectorToF;

public class gameStateButton extends Rectangle{

	private VectorToF pos = new VectorToF();
	private GameState gameState;
	private GameStateManager gsm;
//	private boolean isClicked;
	private boolean isHeldOver;
	private int width = 48*3;
	private int height = 32;
	private BufferedImage defaultImage;
	private String buttonMsg;
	
	
	public gameStateButton(float xpos, float ypos, GameState gameState, GameStateManager gsm, String buttonMsg) {
		this.gameState = gameState;
		this.gsm = gsm;
		this.pos.xpos = xpos;
		this.pos.ypos = ypos;
		this.buttonMsg = buttonMsg;
		setBounds((int)pos.xpos, (int)pos.ypos, width, height);
		defaultImage = Assets.getButton_notHoveredOver();
	}
	
	public gameStateButton(float xpos, float ypos, String buttonMsg) {
		this.pos.xpos = pos.xpos;
		this.pos.ypos = pos.ypos;
		this.buttonMsg = buttonMsg;
		setBounds((int)pos.xpos, (int)pos.ypos, width, height);
		defaultImage = Assets.getButton_notHoveredOver();
	}
	
	public void tick(){
		setBounds((int)pos.xpos, (int)pos.ypos, width, height);
		
		if(getBounds().contains(MouseManager.mouse)){
			isHeldOver = true;
		}else{
			isHeldOver = false;
		}
		
		if(isHeldOver){
			if(defaultImage != Assets.getButton_hoveredOver()){
				defaultImage = Assets.getButton_hoveredOver();
			}
			
		}else{
			if(defaultImage != Assets.getButton_notHoveredOver()){
				defaultImage = Assets.getButton_notHoveredOver();
			}
		}
		
		if(gameState != null){
			if(isHeldOver){
				if(isPressed()){
					gsm.states.push(gameState);
					gsm.states.peek().init();
					isHeldOver = false;
					MouseManager.pressed = false;
				}
			}
		}
	}
	
	
	Font font = new Font("Viner Hand ITC", 10, 20);
	
	public void render(Graphics2D g){
		g.drawImage(defaultImage, (int)pos.xpos, (int)pos.ypos, width, height, null);
		
		g.setFont(font);
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int tw = (int) font.getStringBounds(buttonMsg , frc).getWidth();
		
		g.drawString(buttonMsg, pos.xpos+ width/2 - tw/2, pos.ypos + height/2 + 6);
	}
	
//	public boolean isClicked(){
//		return isClicked;
//	}
	public boolean isHeldOver(){
		return isHeldOver;
	}
	
	public boolean isPressed (){
		return MouseManager.pressed;
	}

}

package game.dl.main;

import java.awt.image.BufferedImage;

import game.gos.main.SpriteSheet;
import game.gos.main.loadImageFrom;

public class Assets {

	SpriteSheet blocks = new SpriteSheet();
	public static SpriteSheet player = new SpriteSheet();

	public static BufferedImage stone_1;
	public static BufferedImage wall_1;
	public static BufferedImage wall_top;
	
	public static BufferedImage mouse_pressed;
	public static BufferedImage mouse_unpressed;
	
	public static BufferedImage button_hoveredOver;
	public static BufferedImage button_notHoveredOver;
	

	public void init() {
		blocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "SpriteSheet.png"));
		player.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "PlayerSheet.png"));
		

		stone_1 = blocks.getTile(0, 0, 16, 16);
		wall_1 = blocks.getTile(16, 0, 16, 16);
		wall_top = blocks.getTile(32, 0, 16, 16);

		mouse_unpressed = player.getTile(96, 8, 8, 8);
		mouse_pressed = player.getTile(104, 8, 8, 8);
		
		button_notHoveredOver = player.getTile(112, 32, 48, 16);
		button_hoveredOver = player.getTile(160, 32, 48, 16);

	}

	public static BufferedImage getStone_1() {
		return stone_1;
	}

	public static BufferedImage getWall_1() {
		return wall_1;
	}

	public static BufferedImage getwall_top() {
		return wall_top;
	}

	public static BufferedImage getMouse_pressed() {
		return mouse_pressed;
	}
	public static BufferedImage getMouse_unpressed() {
		return mouse_unpressed;
	}
	
	public static BufferedImage getButton_hoveredOver() {
		return button_hoveredOver;
	}
	public static BufferedImage getButton_notHoveredOver() {
		return button_notHoveredOver;
	}

}

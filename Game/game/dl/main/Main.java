package game.dl.main;

import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;

import game.dl.Managers.MouseManager;
import game.dl.MoveableObjects.Player;
import game.dl.gameloop.GameLoop;
import game.gos.main.GameWindow;
import game.gos.main.SpriteSheet;

public class Main {

	static SpriteSheet blocks = new SpriteSheet();

	public static GraphicsDevice gd = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static int width = gd.getDisplayMode().getWidth();
	public static int height = gd.getDisplayMode().getHeight();

	public static void main(String[] args) {
		GameWindow frame = new GameWindow("Dungeon Looter", width, height);
		frame.setFullscreen(1);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor cursor = toolkit.createCustomCursor(toolkit.getImage(""), new Point(0,0), "cursor");
		frame.setCursor(cursor);
		
		frame.addMouseListener(new MouseManager());
		frame.addMouseMotionListener(new MouseManager());
		frame.addMouseWheelListener(new MouseManager());
		
		frame.addKeyListener(new Player());
		frame.add(new GameLoop(width, height));
		frame.setVisible(true);
	}

}

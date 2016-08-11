package game.dl.gamestates;

import java.awt.Graphics2D;

import game.dl.Managers.MouseManager;
import game.dl.gamestate.GameState;
import game.dl.gamestate.GameStateManager;
import game.dl.gamestate.gameStateButton;
import game.dl.gamestates.DungeonLvlLoader;
import game.dl.main.Main;

public class MenuState extends GameState {

	MouseManager mm;
	gameStateButton startGame;

	public MenuState(GameStateManager gsm) {
		super(gsm);

	}

	@Override
	public void init() {

		startGame = new gameStateButton((float) (Main.width/2.25), (float)500, new DungeonLvlLoader(gsm), gsm, "start Game");
		mm = new MouseManager();

	}

	@Override
	public void tick(double deltaTime) {
		mm.tick();
		startGame.tick();
	}

	@Override
	public void render(Graphics2D g) {
		startGame.render(g);
		mm.render(g);

// 		g.drawString("Hello World!", 150, 200);
//		g.clipRect(0, 0, Main.width, Main.height);

	}

}

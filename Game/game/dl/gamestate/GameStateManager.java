package game.dl.gamestate;

import game.dl.gamestate.GameState;
import game.dl.gamestates.DungeonLvlLoader;
import game.dl.gamestates.MenuState;

import java.awt.Graphics2D;
import java.util.Stack;

public class GameStateManager {

	public static Stack<GameState> states;

	public GameStateManager() {

		states = new Stack<GameState>();
		states.push(new MenuState(this));

	}

	public void tick(double deltaTime) {
		states.peek().tick(deltaTime);
	}

	public void render(Graphics2D g) {
		states.peek().render(g);
	}

	public void init() {
		states.peek().init();

	}

}

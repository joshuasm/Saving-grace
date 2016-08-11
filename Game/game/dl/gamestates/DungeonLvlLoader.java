package game.dl.gamestates;

import java.awt.Graphics2D;

import game.dl.Generator.Map;
import game.dl.gamestate.GameState;
import game.dl.gamestate.GameStateManager;
import game.dl.main.Main;
import game.gos.main.SpriteSheet;
import game.gos.main.loadImageFrom;

public class DungeonLvlLoader extends GameState {

	SpriteSheet test = new SpriteSheet();
	Map map;

	public DungeonLvlLoader(GameStateManager gsm) {
		super(gsm);

	}

	@Override
	public void init() {
		map = new Map();
		map.init();
	}

	@Override
	public void tick(double deltaTime) {

		map.tick(deltaTime);

	}

	@Override
	public void render(Graphics2D g) {
		// g.drawString("Hello World!", 500, 500);
		map.render(g);

	}

}

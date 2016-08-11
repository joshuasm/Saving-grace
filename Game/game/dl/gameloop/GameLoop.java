package game.dl.gameloop;

import game.dl.gamestate.GameStateManager;
import game.dl.main.Assets;
import game.gos.main.CGGameLoop;
import game.gos.main.VectorToF;

import java.awt.Component;

public class GameLoop extends CGGameLoop {

	GameStateManager gsm;
	public static Assets assets = new Assets();
	public static VectorToF map = new VectorToF();

	public GameLoop(int width, int height) {
		super(width, height);
	}

	@Override
	public void init() {

		assets.init();
		VectorToF.setWorldVariables(map.xpos, map.ypos);
		gsm = new GameStateManager();
		gsm.init();

		super.init();
	}

	@Override
	public void tick(double deltaTime) {
		VectorToF.setWorldVariables(map.xpos, map.ypos);
		gsm.tick(deltaTime);
	}

	@Override
	public void render() {
		super.render();
		gsm.render(graphics2D);
		clear();
	}

	@Override
	public void clear() {
		super.clear();
	}

}

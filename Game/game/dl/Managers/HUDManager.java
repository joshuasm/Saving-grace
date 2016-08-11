package game.dl.Managers;

import game.dl.MoveableObjects.Player;
import game.dl.main.Main;
import game.gos.main.Light;
import game.gos.main.VectorToF;
import game.gos.main.loadImageFrom;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HUDManager {

	private Player player;
	// private BufferedImage light;
	private BufferedImage lightmap = new BufferedImage(100 * 64, 100  * 64,
			BufferedImage.TYPE_INT_ARGB);
	private ArrayList<Light> lights = new ArrayList<Light>();
	private VectorToF lightm = new VectorToF();

	public HUDManager(Player player) {
		this.player = player;
		// light = loadImageFrom.LoadImageFrom(Main.class, "light.png");
		addLights();
	}

	private void addLights() {
		lights.add(new Light(100, 100, 500, 255));
	}

	public void updateLights() {
		Graphics2D g = null;
		if (g == null) {
			g = (Graphics2D) lightmap.getGraphics();
		}

		g.setColor(new Color(0, 0, 0, 255));
		g.fillRect(0, 0, lightmap.getWidth(), lightmap.getHeight());
		g.setComposite(AlphaComposite.DstOut);

		for (Light light : lights) {
			light.render(g);
		}
		g.dispose();

	}

	public void render(Graphics2D g) {
//		updateLights();

		g.drawImage(lightmap, (int) lightm.getWorldLocation().xpos,
				(int) lightm.getWorldLocation().ypos, null);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.width, Main.height / 10);
		g.fillRect(0, 925, Main.width, Main.height / 4);
		g.setColor(Color.WHITE);

//		g.drawString(Player.getPos().xpos + "	" + player.getPos().ypos , 200, 200);
//		 g.drawImage(light, 0, 0, Main.width, Main.height, null);
	}

}

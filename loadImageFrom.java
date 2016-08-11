package game.gos.main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public final class loadImageFrom {

	public static BufferedImage LoadImageFrom(Class<?> Classfile, String path) {
		URL url = Classfile.getResource(path);
		BufferedImage img = null;

		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

}

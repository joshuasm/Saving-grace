package game.gos.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

public class Light {

	public VectorToF lightpos = new VectorToF();
	private int radius;
	private int lightPower;
	private BufferedImage image;

	public Light(float xpos, float ypos, int radius, int lightPower) {

		this.lightpos.xpos = xpos;
		this.lightpos.ypos = ypos;
		this.radius = radius;
		this.lightPower = lightPower;

		Point center = new Point(radius, radius);
		Point focus = new Point(radius, radius);

		float[] dist = { 0F, 1F };
		Color[] color = { new Color(0, 0, 0, lightPower), new Color(0, 0, 0, 0) };

		image = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 =(Graphics2D)image.getGraphics();
		
		RadialGradientPaint rgp = new RadialGradientPaint(center, radius, dist, color);
		g2.setPaint(rgp);
		
		g2.fillOval(0, 0, radius * 2, radius * 2);
	}
	
	public void render (Graphics2D g){
		
		g.drawImage(image, (int) lightpos.xpos, (int) lightpos.ypos, null);
		
	}
	
	
	

}

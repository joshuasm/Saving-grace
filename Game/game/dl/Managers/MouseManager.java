package game.dl.Managers;

import game.dl.main.Assets;

import java.awt.Event;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static int mouseMovedx, mouseMovedy;
	public static Point mouse;
	public static boolean pressed;
	
	public void tick(){
		mouse = new Point(mouseMovedx, mouseMovedy);
	}
	
	public void render(Graphics2D g){
		g.fillRect(mouseMovedx, mouseMovedy, 4, 4);
		
		if (pressed){
			g.drawImage(Assets.getMouse_pressed(), mouseMovedx, mouseMovedy, 32, 32, null);
		}else{
			g.drawImage(Assets.getMouse_unpressed(), mouseMovedx, mouseMovedy, 32, 32, null);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			pressed = true;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			pressed = false;
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMovedx = e.getX();
		mouseMovedy = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseMovedx = e.getX();
		mouseMovedy = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	

}

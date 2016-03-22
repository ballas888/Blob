package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseMotionListener {
	
	private int x = 0;
	private int y = 0;
	
	public Point retrievePos(){
		return new Point(x,y);
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		this.x = e.getX();
		this.y = e.getY();
	}

}

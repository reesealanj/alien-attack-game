import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class LargeAlien extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	private int gridx;
	private int gridy;
	private int speed;
    private int largeWidth;
    private Setup config;
	
	public LargeAlien(Setup config) {
		super();
        this.config = config;
        gridx = 0;
		gridy = 0;
		speed = config.getLargeSpeed();
		largeWidth = 90;
		setLocation(0,0);
		setSize(largeWidth,largeWidth);
	}
	
	public int getGridx() {
		return gridx;
	}
	
	public void setGridx(int x) {
		if(gridx < 0 || x > AlienAttackBoard.boardSize - largeWidth) {
			System.err.println("Bad Grid X in LargeAlien setGridx() x = " + gridx);
		}
		else {
			this.gridx += x;
			setLocation(gridx, gridy);
		}
	}
	
	public int getGridy() {
		return gridy;
	}
	
	public void setGridy(int y) {
		if(gridy < 0 || gridy > AlienAttackBoard.boardSize - largeWidth) {
			System.err.println("Bad Grid Y in LargeAlien setGridy() y = " + gridy);
		}
		else {
			this.gridy += y;
			setLocation(gridx, gridy);
		}
	}
	
	public void updatePosition() {
		this.gridy += speed;
		setLocation(gridx, gridy);
	}
	
	public boolean atBottom() {
		if(gridy >= (AlienAttackBoard.boardSize - largeWidth)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Rectangle bounds() {
		return (new Rectangle(gridx,gridy, largeWidth, largeWidth));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Polygon large = new Polygon(new int[] {0,0,25,65,90,90,45}, new int[] {45,25,0,0,25,45,90}, 7);
		g2d.setColor(Color.RED);
		g2d.fill(large);
	}
}

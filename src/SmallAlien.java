
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class SmallAlien extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	private int gridx;
	private int gridy;
	private int speed;
    private int smallWidth;
    private Setup config;
	
	public SmallAlien(Setup config) {
        super();
        this.config = config;
		gridx = 0;
		gridy = 0;
		speed = config.getSmallSpeed();
		smallWidth = 30;
		setLocation(0,0);
		setSize(smallWidth,smallWidth);
	}
	
	public int getGridx() {
		return gridx;
	}
	
	public void setGridx(int x) {
		if(gridx < 0 || x > AlienAttackBoard.boardSize - smallWidth) {
			System.err.println("Bad Grid X in SmallAlien setGridx() x = " + gridx);
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
		if(gridy < 0 || gridy > AlienAttackBoard.boardSize - smallWidth) {
			System.err.println("Bad Grid Y in SmallAlien setGridy() y = " + gridy);
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
		if(gridy >= (AlienAttackBoard.boardSize - smallWidth)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Rectangle bounds() {
		return (new Rectangle(gridx,gridy, smallWidth, smallWidth));
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Polygon small = new Polygon(new int[] {0,5,10,15,20,25,30,15}, new int[] {10,10,0,10,0,10,10,30}, 8);

		g2d.setColor(Color.GREEN);
		g2d.fill(small);
	}
}

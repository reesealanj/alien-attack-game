import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;


public class MediumAlien extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	private int gridx;
	private int gridy;
	private int speed;
    private int mediumWidth;
    private Setup config;
	
	public MediumAlien(Setup config) {
        super();
        this.config = config;
		gridx = 0;
		gridy = 0;
		speed = config.getMediumSpeed();
		mediumWidth = 60;
		setLocation(0,0);
		setSize(mediumWidth, mediumWidth);
	}
	
	public int getGridx() {
		return gridx;
	}
	
	public void setGridx(int x) {
		if(gridx < 0 || x > AlienAttackBoard.boardSize - mediumWidth) {
			System.err.println("Bad Grid X in MediumAlien setGridx() x = " + gridx);
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
		if(gridy < 0 || gridy > AlienAttackBoard.boardSize - mediumWidth) {
			System.err.println("Bad Grid Y in MediumAlien setGridy() y = " + gridy);
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
		if(gridy >= (AlienAttackBoard.boardSize - mediumWidth)) {
			return true;
		}
		else {
			return false;
		}
	}
	public Rectangle bounds() {
		return (new Rectangle(gridx,gridy, mediumWidth, mediumWidth));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Polygon med = new Polygon(new int[] {0,15,30,45,60,60,54,48,42,36,30,24,18,12,6,0}, new int[] {0,0,15,0,0,40,60,40,60,40,60,40,60,40,60,40}, 16);
		g2d.setColor(Color.YELLOW);
		g2d.fill(med);
	}
}

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PlayerToken extends JComponent
{
    private static final long serialVersionUID = 1L;
    //Setup to take from
    private Setup config;
    //Variables for Position
    private int gridx;
    private int gridy;
    //Variables for Player movement
    private int speed;
    private int playerWidth;
    //Variable for color
    private Color activeColor;

    public PlayerToken(Setup config)
    {
        super();
        gridx = 0;
        gridy = 0;
        speed = config.getPlayerSpeed();
        playerWidth = 90;
        activeColor = Color.GREEN;
        startPosition();
        setSize(playerWidth, playerWidth);
    }

    public int getGridX()
    {
        return gridx;
    }

    public int getGridY()
    {
        return gridy;
    }
    //Generic method to move left and right
    public void setGridX(int x)
    {
        //-5 allows the player to get close to the edge, and the other makes sure it doesn't go off screen
        if(gridx < -5 || (gridx + x) > (AlienAttackBoard.boardSize - playerWidth))
        {
            System.err.println("Bad GridX in PlayerToken setGridX() x = " + gridx);
        }
        else
        {
            this.gridx += x;
            setLocation(gridx, gridy);
        }
    }

    public void setGridY(int y)
    {
        if(gridy < 0 || (gridy + y) > (AlienAttackBoard.boardSize - playerWidth))
        {
            System.err.println("Bad GridY in PlayerToken setGridY() y = " + gridy);
        }
        else
        {
            this.gridy += y;
            setLocation(gridx, gridy);
        }
    }

    public void moveLeft()
    {
        setGridX((this.speed) * -1);
    }
    
    public void moveRight()
    {
        setGridX(this.speed);
    }

    public void startPosition()
    {
        gridx = ((AlienAttackBoard.boardSize / 2) - ((playerWidth / 2) + (playerWidth / 4)));
        gridy = ((AlienAttackBoard.boardSize) - playerWidth);
        setLocation(gridx, gridy);
    }

    public int getPlayerWidth()
    {
        return playerWidth;
    }

    public void setPlayerWidth(int playerWidth){
		this.playerWidth = playerWidth;
	}	
	
	public void setActiveColor(Color activeColor)
	{
		this.activeColor = activeColor;
    }

	public void setSpeed(int speed) {
		this.speed = speed;
    }

    public Rectangle bounds() {
		return (new Rectangle(gridx,gridy, playerWidth, playerWidth));
    }
    
    @Override 
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(activeColor);
		g2d.fillOval(0, 0, playerWidth, playerWidth);
    }
    
}

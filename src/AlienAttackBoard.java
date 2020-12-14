import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AlienAttackBoard extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected static int boardSize = 750;
	protected static int gridSize = 30;
	protected static int numGrids = 25;

	public AlienAttackBoard() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setSize(boardSize, boardSize);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(boardSize, boardSize);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(boardSize, boardSize);
	}
}

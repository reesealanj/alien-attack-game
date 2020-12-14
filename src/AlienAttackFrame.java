/**
 * CSCI 2113 - Project 2 - Alien Attack
 * 
 * @author Reese Jones
 *
 */
import javax.swing.JFrame;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;

public class AlienAttackFrame extends JFrame
{
  //Parts of Game Logic/Operation 
  private AlienAttackBoard aaBoard;
  private GameEngine game;
  private PlayerToken player;
  private Setup config;
  //Parts of the Control Panel (At Bottom)
  //Panel Containers
  private JPanel buttonPanel;
  private JPanel labelPanel;
  private JPanel bottomPanel;
  //Buttons
  private JButton startButton;
  private JButton endButton;
  private JButton pauseButton;
  private JButton restartButton;
  //Labels
  private JLabel points;
  private JLabel time;
  //Variables For Class
  //Control:
  private int allowMovement;
  private int cycleTime;
  //Points
  protected int lrgPoints;
  protected int medPoints;
  protected int smallPoints;
  //For End of Game
  private ArrayList<Integer> top10scores;
  private GameEndPanel endPanel;
  //ActionListeners / Timer
  private StartStopListener startStop;
  private KeyInputListener keysIn;
  private Timer tm;
  //Variables related to powerups

  public AlienAttackFrame()
  {
     config = new Setup();
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     //Initialize Panels
     bottomPanel = new JPanel(new GridLayout(2,1));
     labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
     buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
     //Set Panel Sizing
     bottomPanel.setPreferredSize(new Dimension(AlienAttackBoard.boardSize, 60));
     labelPanel.setPreferredSize(new Dimension(AlienAttackBoard.boardSize, 20));
     buttonPanel.setPreferredSize(new Dimension(AlienAttackBoard.boardSize, 40));
     //Initialize Buttons
     startButton = new JButton("Start");
     endButton = new JButton("End");
     pauseButton = new JButton("Pause");
     //Initialize Points Label / Format It
     points = new JLabel("Points: 0");
     points.setBorder(BorderFactory.createLineBorder(Color.black));
     points.setHorizontalTextPosition(JLabel.CENTER);
     points.setVerticalTextPosition(JLabel.CENTER);
     //Initialize Time Label / Format It
     time = new JLabel("Time Elapsed 00.00 seconds");
     time.setBorder(BorderFactory.createLineBorder(Color.black));
     time.setHorizontalTextPosition(JLabel.CENTER);
     time.setVerticalTextPosition(JLabel.CENTER);  
     //CONTROL PANEL SETUP
     //Add Buttons to Panel
     buttonPanel.add(startButton);
     buttonPanel.add(pauseButton);
     buttonPanel.add(endButton);
     //Not Focusable (Doesn't affect key movement)
     startButton.setFocusable(false);
     endButton.setFocusable(false);
     pauseButton.setFocusable(false);
     //Add StartStopListener to Buttons
     startStop = new StartStopListener();
     startButton.addActionListener(startStop);
     endButton.addActionListener(startStop);
     pauseButton.addActionListener(startStop);
     //Add Labels to LabelPanel
     labelPanel.add(points);
     labelPanel.add(time);
     //Add ButtonPanel and LabelPanel to BottomPanel
     bottomPanel.add(buttonPanel, 0,0);
     bottomPanel.add(labelPanel, 1,0);
     //Add BottomPanel to Frame
     getContentPane().add(bottomPanel, BorderLayout.SOUTH);
     keysIn = new KeyInputListener();
     addKeyListener(keysIn);
     setFocusable(true);
     top10scores = new ArrayList<Integer>(10);
     //Initialize GameBoard and Logic Associated
     gameStart();
     pack();
     setTitle("Alien Attack!");
     setVisible(true);
  }
  //Method to instantiate the game
  public void gameStart()
  {
     
     aaBoard = new AlienAttackBoard();
     player = new PlayerToken(config);
     game = new GameEngine(aaBoard, player, config);
     allowMovement = 0;
     cycleTime = config.getCycleTime();
     smallPoints = config.getSmallPoints();
     medPoints = config.getMediumPoints();
     lrgPoints = config.getLargePoints();
     tm = new Timer(cycleTime, new RandomButtonListener());
     aaBoard.add(player);
     getContentPane().add(aaBoard, BorderLayout.CENTER);
  }

  public void addPowerUps()
  {

   
  }

  public void gameEnd()
  {
     aaBoard.removeAll();
     repaint();
     endPanel = new GameEndPanel(config, game.getPoints(), time.getText(), top10scores);
     getContentPane().remove(aaBoard);
     getContentPane().add(endPanel, BorderLayout.CENTER);
     restartButton = new JButton("Restart");
     restartButton.addActionListener(startStop);
     buttonPanel.add(restartButton);
     validate();
     repaint();
  }


  class KeyInputListener implements KeyListener
  {
     public void keyPressed(KeyEvent event)
     {
        int x = player.getGridX();
        int keyIn = event.getKeyCode();

        if(keyIn == KeyEvent.VK_LEFT)
        {
           //If moving the player left would push the token off screen or movement isn't allowed
           if(((x - player.getPlayerWidth()) < 0) || (allowMovement == 0))
           {
              //Do nothing and Return
              return;
           }
           else
           {
              player.moveLeft();
           }
        }
        else if(keyIn == KeyEvent.VK_RIGHT)
        {
           //If moving the player right would push the token off screen or movement isn't allowed
           if(((x + player.getPlayerWidth()) > (AlienAttackBoard.boardSize - player.getPlayerWidth())) || (allowMovement == 0))
           {
              //Do nothing and return
              return;
           }
           else
           {
              player.moveRight();
           }
        }
     }
     public void keyTyped(KeyEvent event){}
     public void keyReleased(KeyEvent event){}
  }

  class RandomButtonListener implements ActionListener
  {
     @Override
     public void actionPerformed(ActionEvent event)
     {
        //So long as the game hasn't ended (player died)
        if(game.isEnded() == false)
        {
           game.addTime(tm.getDelay());
           game.updateGameState();
           points.setText(game.updatePointDisplay());
           time.setText(game.updateTimeDisplay());

           if((game.getCycles() > 20) && (game.getCycles() % 50) == 0 && (tm.getDelay() > 40))
           {
              tm.setDelay((tm.getDelay() - 10));
              System.out.println("Cycle Time Decreased to: " + tm.getDelay() + "ms.");
           }
        }
        else if(game.isEnded() == true)
        {
           tm.stop();
           gameEnd();
           startButton.setEnabled(false);
           pauseButton.setEnabled(false);
           endButton.setEnabled(false);
        }
      }
  }
  
  class StartStopListener implements ActionListener
  {
     @Override
     public void actionPerformed(ActionEvent event)
     {
        Object button = event.getSource();

        if(button == startButton)
        {
           tm.start();
           game.genLevel();
           allowMovement = 1;
           endButton.setEnabled(true);
           pauseButton.setEnabled(true);
           startButton.setEnabled(false);
           startButton.setText("Start");
           setFocusable(true);
        }
        else if(button == endButton)
        {
				tm.stop();
				allowMovement = 0;
				endButton.setEnabled(false);
				pauseButton.setEnabled(false);
				startButton.setEnabled(true);
            gameEnd(); 
        }
        else if(button == pauseButton)
        {
				tm.stop();
				allowMovement = 0;
				endButton.setEnabled(false);
				pauseButton.setEnabled(false);
				startButton.setEnabled(true);
				startButton.setText("Resume");
        }
        else if(button == restartButton)
        {
            allowMovement = 0;
            endButton.setEnabled(false);
				pauseButton.setEnabled(false);
				startButton.setEnabled(true);
            getContentPane().remove(endPanel);
            buttonPanel.remove(restartButton);
            gameStart();
            repaint();
            validate();
            repaint();
        }
        else
        {
           System.err.println("Not sure what happened: " + button);
        }
     }
  }
}

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class GameEngine
{
    //Variables for Logic Computation
    private int time;
    private int cycles;
    private int numHits;
    private int points;
    //Variables For Spawning Aliens
    private int spawnInterval;
    private int baseMinAlien;
    private int baseMaxAlien; 
    private int alienLevelStep;
    private int levelIncInterval;
    //Variables for Points assignement
    private int smallPoints;
    private int mediumPoints;
    private int largePoints;
    //Variables for interfacing with Frame
    private AlienAttackBoard aaBoard;
    private PlayerToken player;
    private Setup config;
    private boolean stopRunning;
    //Variable for collision
    private Rectangle aRect;

    //Constructor for Game Engine
    public GameEngine(AlienAttackBoard aaBoard, PlayerToken player, Setup config)
    {
        //Assigning all logic objects
        this.aaBoard = aaBoard;
        this.player = player;
        this.config = config;
        //Resetting Score and Time
        cycles = 0;
        points = 0;
        //Grabbing proper values from configuration file
        smallPoints = config.getSmallPoints();
        mediumPoints = config.getMediumPoints();
        largePoints = config.getLargePoints();
        spawnInterval = config.getSpawnInterval();
        baseMinAlien = config.getBaseMin();
        baseMaxAlien = config.getBaseMax();
        alienLevelStep = config.getLevelStep();
        levelIncInterval = config.getIncInterval();
        //Telling the frame the game has NOT stopped running
        stopRunning = false;
    }
    //Returns whether or not the game is still running
    public boolean isEnded()
    {
        return stopRunning;
    }
    //increments both the number of cycles AND the total time in ms
    public void addTime(int time)
    {
        this.time += time;
        cycles++;
    }
    //returns total number of cycles
    public int getCycles()
    {
        return cycles;
    }
    //adds specified points to total
    public void addPoints(int addPoint)
    {
        points += addPoint;
    }
    //returns total points
    public int getPoints()
    {
        return points;
    }
    //Creates a string to update the time label on the main frame
    public String updateTimeDisplay()
    {
        //format to default show 2 digits and 2 sig figs
        DecimalFormat timeForm = new DecimalFormat("#00.00");
        //dividing by thousand takes ms->seconds
        double newTime = time / 1000;
        String time = "Time Elapsed: " + timeForm.format(newTime) + " seconds";
        return time;
    }
    //Creates a string to update the points label on the main frame
    public String updatePointDisplay()
    {
        String points = "Points: " + this.points;
        return points;
    }
    //Decides whether a player is colliding with an alien object
    public boolean isColliding(PlayerToken player, Component alien)
    {
        //gets the player 'hitbox'
        Rectangle pRect = player.bounds();
        //if statement chooses how to cast the Component into its proper alien type
        //and then grabs proper 'hitbox' of said alien
		if(alien instanceof SmallAlien) {
			 SmallAlien use = (SmallAlien) alien;
			 aRect = use.bounds();
		}
		else if(alien instanceof MediumAlien) {
			 MediumAlien use = (MediumAlien) alien;
			 aRect = use.bounds();
		}
		else if(alien instanceof LargeAlien) {
			 LargeAlien use = (LargeAlien) alien;
			 aRect = use.bounds();	 
		}
		//uses native intersects method to decide if the player's hitbox intersects alien's
		if(aRect.intersects(pRect)) {			
			return true;
		}
		else {
			return false;
		}
    }
    //decides what to do every time a cycle happens within the main frame
    public void updateGameState()
    {
        //starts by ALWAYS updating alien positions
        //becasue within that method:
            //points are given 
            //collisions are checked 
            //and aliens at the bottom are removed
        moveAliens();
        //iif the number of cycles is a multiple of the spawnInterval
        if((cycles % spawnInterval) == 0)
        {
            //generate a new level
            genLevel();
        }
        //if the number of cycles is a multiple of the level size increase interval
        if((cycles % levelIncInterval) == 0)
        {
            //adjust the max and min numbers
            baseMaxAlien += alienLevelStep;
            baseMinAlien += alienLevelStep;
            //print to console what the new range is
            System.out.println("Alien Range: [" + baseMinAlien + " , " + baseMaxAlien + "]");
        }
    }
    //generates a new level of aliens at the top of the screen
    public void genLevel()
    {
        Random rand = new Random();
        //Decides how many to spawn as a random number between baseMin and baseMax (inclusive)
        int numSpawn = rand.nextInt((baseMaxAlien - baseMinAlien) + 1) + baseMinAlien;
        //creates an arrayList of positions which have already been chosen
        ArrayList<Integer> usedPositions = new ArrayList<Integer>(numSpawn);
        //creates a new alien numSpawn times
        for(int i = 0; i < numSpawn; i++) {
            //decides which type of alien to randomly generate
            int type = rand.nextInt(3) + 1;
			switch(type) 
			{
            //if type == 1, generate a small alien
            case 1: 
                //this is the x positition to start it from
                int randSmall = rand.nextInt((AlienAttackBoard.boardSize - 30) + 1);
                //if the exact position is already in the usedPositions
                while(usedPositions.contains((randSmall + 30)) || usedPositions.contains((randSmall - 30))) {
                    //choose a new one
                    randSmall = rand.nextInt((AlienAttackBoard.boardSize - 30) + 1);
                }
                //generate new alien at (randSmall, 0)
				SmallAlien small = new SmallAlien(config);
				small.setGridy(0);
				small.setGridx(randSmall);
				usedPositions.add(randSmall);
                //add it to the board
                aaBoard.add(small);
                break;
            //if type == 2, generate a medium alien
            case 2:
                //this is the x position to start it from
				int randMed = rand.nextInt((AlienAttackBoard.boardSize - 60) + 1);
                //if the exact position is already in the usedPositions
                while(usedPositions.contains((randMed + 60)) || usedPositions.contains((randMed - 60))) {
                    //choose a new one
                    randMed = rand.nextInt((AlienAttackBoard.boardSize - 60) + 1);
                }
                //generate a new alien at (randMed, 0)
				MediumAlien med = new MediumAlien(config);
				med.setGridy(0);
				med.setGridx(randMed);
				usedPositions.add(randMed);
                //add it to the board
                aaBoard.add(med);
                break;
            //if type == 3, generate a large alien
            case 3:
                //this is the x position to start it from
				int randLrg = rand.nextInt((AlienAttackBoard.boardSize - 90) + 1);
                //if the exact position is already in the usedPositions
                while(usedPositions.contains((randLrg + 90)) || usedPositions.contains((randLrg - 90))) {
                    //choose a new one
                    randLrg = rand.nextInt((AlienAttackBoard.boardSize - 90) + 1);
                }
                //generate a new alien at (randLrg, 0)
				LargeAlien lrg = new LargeAlien(config);
				lrg.setGridy(0);
				lrg.setGridx(randLrg);
				usedPositions.add(randLrg);
                //add it to the board
                aaBoard.add(lrg);
				break;
			}
        }
        //empty out the arraylist to be safe for the next time
		usedPositions.clear();
    }
    //updates the position of all aliens, checks to see if it is colliding w the player, and adds points if it is at the bottom
    public void moveAliens() 
	{
        //begin by creating an array of all components on the game board
        //will include aliens and players
		Component[] components = aaBoard.getComponents();
		//for each loop to go through all components
		for(Component comp: components) 
		{
            //if statement to decide which type of alien to cast the component as
			if(comp instanceof SmallAlien)
			{
				SmallAlien small = (SmallAlien) comp;
				small.updatePosition();
				if(isColliding(player, small) == true)
				{
					System.err.println("Collision Detected");
					aaBoard.remove(small);
					collision();
				}
				if(small.atBottom() == true)
				{
					addPoints(smallPoints);
					updatePointDisplay();
					aaBoard.remove(small);
				}
			}
			else if(comp instanceof MediumAlien)
			{
				MediumAlien medium = (MediumAlien) comp;
				medium.updatePosition();
				if(isColliding(player, medium) == true)
				{
					System.err.println("Collision Detected");
					aaBoard.remove(medium);
					collision();
				}
				if(medium.atBottom() == true)
				{
					addPoints(mediumPoints);
					updatePointDisplay();
					aaBoard.remove(medium);
				}
			}
			else if(comp instanceof LargeAlien)
			{
				LargeAlien large = (LargeAlien) comp;
				large.updatePosition();
				if(isColliding(player, large) == true)
				{
					System.err.println("Collision Detected");
					aaBoard.remove(large);
					collision();
				}
				if(large.atBottom() == true) 
				{
					addPoints(largePoints);
					updatePointDisplay();
					aaBoard.remove(large);
				}
			}
		}
    }

	public void collision()
	{
		numHits++;
		switch(numHits) 
		{
			case 1:
				player.setActiveColor(Color.YELLOW);
				player.setPlayerWidth(60);
				player.setSize(60,60);
				player.setGridY(25);
				player.setSpeed(30);
				break;
			case 2:
				player.setActiveColor(Color.RED);
				player.setPlayerWidth(30);
				player.setSize(30,30);
				player.setSpeed(20);
				break;
			case 3:
				player.setPlayerWidth(0);
				player.setSize(0,0);
				stopRunning = true;
				break;
		}
	}
}
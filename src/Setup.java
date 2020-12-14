import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Setup{

    private int cycleTime;
    private int spawnInterval;
    private int baseMin;
    private int baseMax;
    private int alienStep;
    private int levelIncInterval;
    private int playerSpeed;

    private int smallSpeed;
    private int smallPoints;
    private int mediumSpeed;
    private int mediumPoints;
    private int largeSpeed;
    private int largePoints;

    public Setup()
    {
       /*try
       {
        File file = new File("/resources/AlienAttack.properties.txt");
        Scanner input = new Scanner(file);
        while(input.hasNextLine())
        {
            String line = input.nextLine();
            System.out.println(line);
        }
        input.close();
       }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
       */
      try{
       Properties prop = new Properties();
       InputStream input = getClass().getClassLoader().getResourceAsStream("./resources/AlienAttack.properties");
       prop.load(input);
       this.cycleTime =Integer.parseInt(prop.getProperty("cycleTime"));
       this.spawnInterval = Integer.parseInt(prop.getProperty("spawnInterval"));
       this.baseMin = Integer.parseInt(prop.getProperty("baseMin"));
       this.baseMax = Integer.parseInt(prop.getProperty("baseMax"));
       this.alienStep = Integer.parseInt(prop.getProperty("alienStep"));
       this.levelIncInterval = Integer.parseInt(prop.getProperty("levelIncInterval"));
       this.playerSpeed = Integer.parseInt(prop.getProperty("playerSpeed"));
       this.smallSpeed = Integer.parseInt(prop.getProperty("smallSpeed"));
       this.mediumSpeed = Integer.parseInt(prop.getProperty("mediumSpeed"));
       this.largeSpeed = Integer.parseInt(prop.getProperty("largeSpeed"));
       this.smallPoints = Integer.parseInt(prop.getProperty("smallPoints"));
       this.mediumPoints = Integer.parseInt(prop.getProperty("mediumPoints"));
       this.largePoints = Integer.parseInt(prop.getProperty("largePoints"));
    }
      catch(IOException ex)
      {
          ex.printStackTrace();
      }
    }  

    public int getCycleTime()
    {
        return cycleTime;
    }

    public int getSmallSpeed()
    {
        return smallSpeed;
    }

    public int getPlayerSpeed()
    {
        return playerSpeed;
    }

    public int getMediumSpeed()
    {
        return mediumSpeed;
    }

    public int getLargeSpeed()
    {
        return largeSpeed;
    }

    public int getSmallPoints()
    {
        return smallPoints;
    }

    public int getMediumPoints()
    {
        return mediumPoints;
    }

    public int getLargePoints()
    {
        return largePoints;
    }

    public int getSpawnInterval()
    {
        return spawnInterval;
    }

    public int getBaseMin()
    {
        return baseMin;
    }

    public int getBaseMax()
    {
        return baseMax;
    }

    public int getLevelStep()
    {
        return alienStep;
    }

    public int getIncInterval()
    {
        return levelIncInterval;
    }



}
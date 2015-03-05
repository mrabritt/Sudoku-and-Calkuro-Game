import java.io.*;
import java.util.*;
public class PlayerStatistics 
{
  private PrintWriter statsWrite;
  private Scanner statsRead;
  private String fileName;
  private int easyGamesPlayed = 0, easyGamesWon = 0, easyGamesSolved = 0, easyHintsAsked = 0, medGamesPlayed = 0, medGamesWon = 0, medGamesSolved = 0, medHintsAsked = 0, hardGamesPlayed = 0, hardGamesWon = 0, hardGamesSolved = 0, hardHintsAsked = 0;
  private int points = 0;
  
  public PlayerStatistics(String playerName) 
  {
    fileName = "./Statistics/" + playerName + "Statistics.dat";
    readStats();
    writeStats();
  }
  
  private void readStats()
  {
    try
    {
      statsRead = new Scanner(new File(fileName));
      while (statsRead.hasNextLine())
      {
        points = statsRead.nextInt();
        easyGamesPlayed = statsRead.nextInt();
        easyGamesWon = statsRead.nextInt();
        easyGamesSolved = statsRead.nextInt();
        easyHintsAsked = statsRead.nextInt();
        medGamesPlayed = statsRead.nextInt();
        medGamesWon = statsRead.nextInt();
        medGamesSolved = statsRead.nextInt();
        medHintsAsked = statsRead.nextInt();
        hardGamesPlayed = statsRead.nextInt();
        hardGamesWon = statsRead.nextInt();
        hardGamesSolved = statsRead.nextInt();
        hardHintsAsked = statsRead.nextInt();
      }
      statsRead.close();
    }
    catch (Exception e)
    {
    }
  }
    
  public void addPlay(String difficulty) 
  {
    readStats();
    if (difficulty.equals("Easy"))
    {
      easyGamesPlayed++;
    }
    if (difficulty.equals("Medium"))
    {
      medGamesPlayed++;
    }
    if (difficulty.equals("Hard"))
    {
      hardGamesPlayed++;
    }
    writeStats();
  }
  public void addWon(String difficulty) 
  {
    readStats();
    if (difficulty.equals("Easy"))
    {
      easyGamesWon++;
      points += 20;
    }
    if (difficulty.equals("Medium"))
    {
      medGamesWon++;
      points += 40;
    }
    if (difficulty.equals("Hard"))
    {
      hardGamesWon++;
      points += 100;
    }
    writeStats();
  }
  public void addSolve(String difficulty) 
  {
    readStats();
    if (difficulty.equals("Easy"))
    {
      easyGamesSolved++;
      points -= 20;
    }
    if (difficulty.equals("Medium"))
    {
      medGamesSolved++;
      points -= 10;
    }
    if (difficulty.equals("Hard"))
    {
      hardGamesSolved++;
      points -= 5;
    }
    writeStats();
  }
  public void addHint(String difficulty) 
  {
    readStats();
    if (difficulty.equals("Easy"))
    {
      easyHintsAsked++;
      points -= 3;
    }
    if (difficulty.equals("Medium"))
    {
      medHintsAsked++;
      points -= 2;
    }
    if (difficulty.equals("Hard"))
    {
      hardHintsAsked++;
      points -= 1;
    }
    writeStats();
  }
  private void writeStats() 
  {
    try
    {
      statsWrite = new PrintWriter(new FileWriter(fileName));
      statsWrite.println(points);
      statsWrite.println(easyGamesPlayed);
      statsWrite.println(easyGamesWon);
      statsWrite.println(easyGamesSolved);
      statsWrite.println(easyHintsAsked);
      statsWrite.println(medGamesPlayed);
      statsWrite.println(medGamesWon);
      statsWrite.println(medGamesSolved);
      statsWrite.println(medHintsAsked);
      statsWrite.println(hardGamesPlayed);
      statsWrite.println(hardGamesWon);
      statsWrite.println(hardGamesSolved);
      statsWrite.print(hardHintsAsked);
      statsWrite.close();
    }
    catch (IOException e)
    {}
  }
}
  
    
        
  
  
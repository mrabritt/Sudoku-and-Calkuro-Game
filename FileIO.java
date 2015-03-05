import java.io.*;
import java.util.*;
import java.awt.*;

public class FileIO
{
  private final String SAVE_DIRECTORY = "./Saves/";
  private final String PUZZLE_DIRECTORY = "./Puzzles/";
  
  private final Color[] colorsThatWork = {new Color(0.36F, 0.54F, 0.66F), new Color(0.79F, 1.0F, 0.90F), new Color(0.64F, 0.15F, 0.22F), new Color(0.77F, 0.38F, 0.06F),
                                                 new Color(0.94F, 0.87F, 0.80F), new Color(0.23F, 0.48F, 0.34F), new Color(1.0F, 0.75F, 0.0F),
                                                 new Color(1.0F, 0.49F, 0.0F), new Color(1.0F, 0.01F, 0.24F), new Color(0.60F, 0.40F, 0.80F), new Color(0.0F, 1.0F, 1.0F),
                                                 new Color(0.50F, 1.0F, 0.83F), new Color(0.95F, 0.95F, 0.96F)};
  private Scanner puzzleFile;
  private PrintWriter fileWrite;
  private Color[] cageColors;
  private CalkuroCage[] cages;
  private PlayerStatistics playersGameStats;
  private int[][] puzzleGrid;
  private int gameNumber, gridSize;
  private String fileName, gameType, gameDifficulty, playerName;
  

  public void setPlayerName(String playerName)
  {
    playersGameStats = new PlayerStatistics(playerName);
    this.playerName = playerName;    
  }
  public String getPlayerName()
  {
    return playerName;
  }
  
  public void endGame()
  {
    puzzleFile = null;
    fileWrite = null;
    cageColors = null;
    cages = null;
    playersGameStats = null;
    puzzleGrid = null;
    fileName = null;
    gameType = null;
    gameDifficulty = null;
    playerName = null;
    new FileIO();
  }
    
  private void setGameType(String gameType)
  {
    this.gameType = gameType;
  }
  
  private void setGameDifficulty(String gameDifficulty)
  {
    this.gameDifficulty = gameDifficulty;
  }
  
  private void setGameNumber(int gameNumber)
  {
    this.gameNumber = gameNumber;
  }
  
  public void setGameParameters(String gameType, String gameDifficulty, int gameNumber)
  {
    setGameType(gameType);
    setGameDifficulty(gameDifficulty);
    setGameNumber(gameNumber);
  }
  
  public void setNewFileName() // Requires that gameType, gameDifficulty, and gameNumber have been set
  {
    fileName = PUZZLE_DIRECTORY + "" + gameType + "" + gameDifficulty + "" + gameNumber + ".txt";
  }
  
  public void setOldFileName() // Requires that playerName, gameType, gameDifficulty, and gameNumber have been set
  {
    fileName = SAVE_DIRECTORY + "" + playerName + "" + gameType + "" + gameDifficulty + "" + gameNumber + ".txt";
  }
  
  public void setSudokuGame() throws IOException // Requires that fileName has been set
  {
    puzzleFile = new Scanner(new File(fileName));
    gridSize = puzzleFile.nextInt();
    puzzleFile.nextLine();
    puzzleGrid = new int[gridSize][gridSize];
    for (int i = 0; i < puzzleGrid.length; ++i)
    {
      for (int j = 0; j < puzzleGrid.length; ++j)
      {
        puzzleGrid[i][j] = puzzleFile.nextInt();
      }
      puzzleFile.nextLine();
    }
    puzzleFile.close();
  }
  
  public int[][] getPuzzleGrid()
  {
    return puzzleGrid;
  }
  
  public void setCalkuroGame(boolean newGame) throws IOException // Requires that fileName has been set
  {
    puzzleFile = new Scanner(new File(fileName));
    gridSize = puzzleFile.nextInt();
    cages = new CalkuroCage[puzzleFile.nextInt()];
    puzzleFile.nextLine();
    setCageColors();
    int oper, sum, size, row, col;
    for (int i = 0; i < cages.length; ++i)
    {
      oper = puzzleFile.nextInt();
      sum = puzzleFile.nextInt();
      size = puzzleFile.nextInt();
      cages[i] = new CalkuroCage(gridSize, oper, sum, size, cageColors[i]);
      for (int j = 0; j < size; ++j)
      {
        row = puzzleFile.nextInt();
        col = puzzleFile.nextInt();
        cages[i].addToCage(row, col);
      }
      puzzleFile.nextLine();
    } 
    puzzleFile.close();
    if (newGame)
    {
      puzzleGrid = new int[gridSize][gridSize];
      for (int i = 0; i < puzzleGrid.length; ++i)
      {
        for (int j = 0; j < puzzleGrid.length; ++j)
        {
          puzzleGrid[i][j] = 0;
        }
      }
    }
    else
    {
      setOldFileName();
      setSudokuGame();
    }
  }
  
  public CalkuroCage[] getCalkuroCages()
  {
    return cages;
  }
  
  public String saveGame(int[][] puzzleGrid) throws IOException
  {
    setOldFileName();
    fileWrite = new PrintWriter(new FileWriter(fileName));
    fileWrite.println(puzzleGrid.length);
    for (int row = 0; row < puzzleGrid.length; ++row)
    {
      for (int col = 0; col < puzzleGrid.length; ++col)
      {
        fileWrite.print(puzzleGrid[row][col] + " ");
      }
      fileWrite.println();
    }
    fileWrite.close();
    return fileName;
  }
  
  public void addToStats(int fieldToAddTo)
  {
    switch (fieldToAddTo)
    {
      case 1:   playersGameStats.addPlay(gameDifficulty);
                     break;
      case 2:    playersGameStats.addWon(gameDifficulty);
                     break;
      case 3:  playersGameStats.addSolve(gameDifficulty);
                     break;
      case 4:   playersGameStats.addHint(gameDifficulty);
                     break;
    }
  }
  
  public PlayerStatistics getPlayerStats()
  {
    return playersGameStats;
  }
  
  private void setCageColors()
  {
    cageColors = new Color[cages.length];
    for (int i = 0, j = 0; i < cageColors.length; ++i, ++j)
    {
      if (j == colorsThatWork.length)
        j = 0;
      cageColors[i] = colorsThatWork[j];
    }
  }
  
}
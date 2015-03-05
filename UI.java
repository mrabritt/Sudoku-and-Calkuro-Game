import java.awt.*;
import javax.swing.*;
import java.io.*;


public class UI extends JFrame
{
  private GraphicsConfiguration gc;
  private Rectangle bounds;
  private Dimension size;
  private SudokuPuzzlePanel sudokuPuzzlePanel;
  private CalkuroPuzzlePanel calkuroPuzzlePanel;
  private CalkuroCage[] cages;
  private Controller listener;
  private OptionsPanel options;
  private String playerName, gameType, gameDifficulty;
  private int gameNumber;
  private boolean newGame;
  
  public UI()
  {
  }
  
  public UI(Controller listener) 
  {
    super("Sudoku & Calkuro");
    this.listener = listener;
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gc = getGraphicsConfiguration();  
    bounds = gc.getBounds();  
  }
  
  public void makeGameWindow(int[][] puzzleGrid, String gameType)
  {
    setVisible(false);
    this.gameType = gameType;
    options = new OptionsPanel(listener);
    if (this.gameType.equals("Sudoku"))
    {
      sudokuPuzzlePanel = new SudokuPuzzlePanel(puzzleGrid, listener);
      sudokuPuzzlePanel.createButtons();
      sudokuPuzzlePanel.createValues();
      setLayout(new BorderLayout());
      add(sudokuPuzzlePanel, BorderLayout.CENTER);
      add(options, BorderLayout.EAST);
      pack();
      validate();
      size = getPreferredSize();
      setLocation((int) ((bounds.width / 2) - (size.getWidth() / 2)), (int) ((bounds.height / 2) - (size.getHeight() / 2))); 
      setVisible(true);
    }
    else
    {
      calkuroPuzzlePanel = new CalkuroPuzzlePanel(puzzleGrid, listener, cages);
      calkuroPuzzlePanel.createButtons();
      calkuroPuzzlePanel.createValues();
      setLayout(new BorderLayout());
      add(calkuroPuzzlePanel, BorderLayout.CENTER);
      add(options, BorderLayout.EAST);
      pack();
      validate();
      size = getPreferredSize();
      setLocation((int) ((bounds.width / 2) - (size.getWidth() / 2)), (int) ((bounds.height / 2) - (size.getHeight() / 2))); 
      setVisible(true);
    }
  }
    
  public String getUserName()
  {
    String[] temp;
    File directory = new File("./Statistics");
    FilenameFilter onlyStats = new OnlyDisplayPlayerStatFiles();
    temp = directory.list(onlyStats);
    Object[] names = new Object[temp.length + 2];
    names[0] = "Select a user";
    names[names.length - 1] = "Add a new user";
    for (int i = 1; i < names.length - 1; ++i)
    {
      names[i] = temp[i-1].substring(0, temp[i-1].indexOf("Statistics"));
    }
    String userName = (String)JOptionPane.showInputDialog(new JFrame(), "Select your name", "User Selection", JOptionPane.PLAIN_MESSAGE, null, names, 0);
    if (userName.equals("Select a user"))
      return getUserName();
    else if (userName.equals("Add a new user"))
      userName = enterName();
    playerName = userName;
    return userName;    
  }
  
  
  private String enterName()
  {
    String playerName = "";
    try
    {
      playerName = JOptionPane.showInputDialog("Enter your name");
      if (playerName.equals(""))
      {
        JOptionPane.showMessageDialog(null, "You must enter your name to continue.", "Error!", JOptionPane.ERROR_MESSAGE);
        enterName();
      }
      else
      {
        String first = playerName.substring(0, 1);
        first = first.toUpperCase();
        playerName = playerName.toLowerCase();
        playerName = first + "" + playerName.substring(1);
      }
    }
    catch (NullPointerException error)
    {}
    return playerName;
  }
  
  public void setCalkuroCages(CalkuroCage[] cages)
  {
    this.cages = cages;
  }
    
  public boolean getNewGame()
  {
    return newGame;
  }
  
  public String getGameType()
  {
    Object[] gameTypes = {"Sudoku", "Calkuro"};
    int selection = JOptionPane.showOptionDialog(null, "What would you like to play?", "Select which game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, gameTypes, gameTypes[0]);
    gameType = (String)gameTypes[selection];
    return gameType;
  }
  
  public String getGameDifficulty()
  {
    Object[] gameDifficulties = {"Easy", "Medium", "Hard"};
    int selection = JOptionPane.showOptionDialog(null, "Please select a difficulty to play.", "Select the difficulty", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, gameDifficulties, gameDifficulties[0]);
    gameDifficulty = (String)gameDifficulties[selection];
    return gameDifficulty;
  }
  
  public int getGameNumber()
  {
    String[] temp;
    File file = new File("./Puzzles");
    FilenameFilter only = new OnlyDisplayAvailableGames(gameType, gameDifficulty);
    temp = file.list(only);
    String[] availableGames = new String[temp.length + 1];
    availableGames[0] = "Select a game";
    for (int i = 1; i < availableGames.length; ++i)
    {
      availableGames[i] = temp[i-1].substring(0, temp[i-1].length() - 4);
    }
    String selected = (String)JOptionPane.showInputDialog(null, "Select a game to play", "Select a game to begin", JOptionPane.PLAIN_MESSAGE, null, availableGames, 0);
    if (selected.equals(availableGames[0]))
      gameNumber = getGameNumber();
    else
      gameNumber = Integer.parseInt(selected.substring(selected.length()-1));
    return gameNumber;
  }
  
  public void setMove(int row, int col, int value, boolean solve)
  {
    if (gameType.equals("Sudoku"))
      sudokuPuzzlePanel.setValue(row, col, value, solve);
    else
      calkuroPuzzlePanel.setValue(row, col, value);
  }
  
  public void unset(int row, int col, int value)
  {
    if (gameType.equals("Sudoku"))
      sudokuPuzzlePanel.unset(row, col, value);
    else
      calkuroPuzzlePanel.unset(row, col, value);
  }
  
  public void togglePuzzleValues(boolean enable)
  {
    if (gameType.equals("Sudoku"))
      sudokuPuzzlePanel.toggleValues(enable);
    else
      calkuroPuzzlePanel.toggleValues(enable);
  }
  public void toggleHintButton(boolean enable)
  {
    options.toggleHint(enable);
  }
  
  public void showHint(int row, int column, int value)
  {
    if (gameType.equals("Sudoku"))
      sudokuPuzzlePanel.showHint(row, column, value);
    //else
      //calkuroPuzzlePanel.showHint(row, column);
  }
  
  public void createGrid()
  {
    if (gameType.equals("Sudoku"))
      sudokuPuzzlePanel.createButtons();
    else
      calkuroPuzzlePanel.createButtons();
  }
  
  public void showCageInfo(CalkuroCage cage)
  {
    options.showCageInfo(cage);
  }
  
  public void showStats()
  {
    ShowStatistics stats = new ShowStatistics(this, playerName);
  }
  
  public void endGame()
  {
    this.dispose();
    new UI();
  }
}
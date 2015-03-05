import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Controller implements ActionListener, MouseListener
{
  private UI userInterface;
  private JButton chosen, lastChosen;
  private SudokuGame sudokuPuzzleGame;
  private CalkuroGrid calkuroPuzzleGame;
  private FileIO file;
  private int[][] puzzleGrid;
  private int gameNumber, row, col, value, finishedCages = 0;
  private String gameType, gameDifficulty, playerName;
  private CalkuroCage[] cages;
  private boolean finished = false, solved = false, newGame = false;
  
  public Controller()
  {
    try
    {
      userInterface = new UI(this);
      file = new FileIO();
    }
    catch (Exception e)
    {}
  }
  
  public Controller(String name)
  {
    playerName = name;
    try
    {
      userInterface = new UI(this);
      file = new FileIO();
    }
    catch (Exception e)
    {}
    file.setPlayerName(playerName);
    start();
  }
  
  public void start()
  {
    finished = false;
    playerName = userInterface.getUserName();
    file.setPlayerName(playerName);
    checkName();
    if (newGame)
    {
      gameType = userInterface.getGameType();
      gameDifficulty = userInterface.getGameDifficulty();
      gameNumber = userInterface.getGameNumber();
    }
    file.setGameParameters(gameType, gameDifficulty, gameNumber);
    file.setNewFileName();
    if (gameType.equals("Sudoku"))
    {
      sudokuPuzzleGame = new SudokuGame();
      try
      {
        file.setSudokuGame();
      }
      catch (IOException e)
      {
        JOptionPane.showMessageDialog(null, "Error with file. Exiting.", "Error!", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }
      puzzleGrid = file.getPuzzleGrid();
      puzzleGrid = sudokuPuzzleGame.makePuzzle(puzzleGrid, this);
      userInterface.makeGameWindow(puzzleGrid, gameType);
      file.addToStats(1);
      runGameLoop();
      endGame();
    }
    else
    {
      try
      {
        file.setCalkuroGame(newGame);
      }
      catch (IOException e)
      {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }
      calkuroPuzzleGame = new CalkuroGrid();
      puzzleGrid = file.getPuzzleGrid();
      cages = file.getCalkuroCages();
      puzzleGrid = calkuroPuzzleGame.makePuzzle(puzzleGrid, cages, this, gameDifficulty, gameNumber);
      userInterface.setCalkuroCages(cages);
      userInterface.makeGameWindow(puzzleGrid, gameType);
      file.addToStats(1);
      runGameLoop();
      endGame();
    }
  }
  
  public void checkName()
  {
    File saveDir = new File("./Saves/");
    FilenameFilter only = new OnlyDisplayPlayersGames(playerName);
    String[] saveGames = saveDir.list(only);
    if (saveGames.length == 0)
    {
      newGame = true;
    }
    else
    {
      int response = JOptionPane.showConfirmDialog(null, "Would you like to load a previous game?", "Load saved game?", JOptionPane.YES_NO_OPTION);    
      if (response == JOptionPane.YES_OPTION)
      {
        showSavedGames(saveGames);
      }
      else
      {
        newGame = true;
      }
    }
  }
  
  public void showSavedGames(String[] saves)
  {
    Object[] options = new Object[saves.length];
    for (int i = 0; i < saves.length; ++i)
    {
      options[i] = saves[i].substring(0, saves[i].length()-4);
    }
    String selected = (String)JOptionPane.showInputDialog(new JFrame(), "Select a game to Load", "Load Game", JOptionPane.PLAIN_MESSAGE, null, options, 0);
    if (selected.charAt(playerName.length()) == 'S')
      gameType = "Sudoku";
    else 
      gameType = "Calkuro";
    gameDifficulty = selected.substring(selected.indexOf(gameType) + gameType.length(), selected.length() -1);
    gameNumber = Integer.parseInt(selected.substring(selected.length()-1));
    newGame = false;
  }
  
  public void runGameLoop()
  {
    while(!finished || !solved)
    {
    }
  }
  
  public void mousePressed(MouseEvent event)
  {}
  
  public void mouseReleased(MouseEvent event)
  {}
  
  public void mouseClicked(MouseEvent event)
  {}
  
  public void mouseEntered(MouseEvent event)
  {
    int row, col;
    JButton button = (JButton)event.getSource();
    String command = button.getActionCommand();
    row = Integer.parseInt(command.substring(2, 3));
    col = Integer.parseInt(command.substring(command.length() - 1));
    for (int i = 0; i < cages.length; ++i)
    {
      if (cages[i].isInThisCage(row, col))
      {
        userInterface.showCageInfo(cages[i]);
      }
    }
  }
  
  public void mouseExited(MouseEvent event)
  {
    
  }
  
  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() instanceof JButton)
    {
      JButton optionSelected = (JButton)e.getSource();
      String option = optionSelected.getText();
      if (option.equals("Get Hint"))
      {
        if (gameType.equals("Sudoku"))
          sudokuPuzzleGame.littleHelp(row, col);
        else
        {
          value = calkuroPuzzleGame.littleHelp(row, col);
          userInterface.setMove(row, col, value, false);
        }
        userInterface.toggleHintButton(false);
        file.addToStats(4);
      }
      else if (option.equals("Undo"))
      {
        if (gameType.equals("Sudoku"))
          sudokuPuzzleGame.undoLast();
        else
          calkuroPuzzleGame.undoLast();
      }
      else if (option.equals("Re-do"))
      {
        if (gameType.equals("Sudoku"))
          sudokuPuzzleGame.redoLast();
        else
          calkuroPuzzleGame.redoLast();
      }
      else if (option.equals("Solve Puzzle"))
      {
        if (gameType.equals("Sudoku"))
          sudokuPuzzleGame.solvePuzzle();
        else
          calkuroPuzzleGame.solvePuzzle();
        solved = true;
        file.addToStats(3);
      }
      else if (option.equals("View Statistics"))
      {
        userInterface.showStats();
      }
      else if (option.equals("Save Game"))
      {
        String savedAs;
        try
        {
          if (gameType.equals("Sudoku"))
            savedAs = file.saveGame(sudokuPuzzleGame.getGrid());
          else
            savedAs = file.saveGame(calkuroPuzzleGame.getPuzzle());
          savedAs = savedAs.substring(8, savedAs.length() - 4);
          JOptionPane.showMessageDialog(null, "Game saved as " + savedAs + "", "Successfully Saved", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException error)
        {
          JOptionPane.showMessageDialog(null, "An error occurred", "Save Failed!", JOptionPane.ERROR_MESSAGE);
        }
      }
      else if (option.equals("Quit"))
      {
        System.exit(0);
      }
      else
      {
        chosen = (JButton)e.getSource();
        String action = chosen.getActionCommand();
        if (action.charAt(0) == 'r')
        {
          if (lastChosen instanceof JButton && gameType.equals("Calkuro"))
          {
            for (int i = 0; i < cages.length; ++i)
            {
              if (cages[i].isInThisCage(row, col))
              {
                lastChosen.setBackground(cages[i].getCageColor());
              }
            }
          }
          row = Integer.parseInt(action.substring(2, 3));
          col = Integer.parseInt(action.substring(action.length()-1));
          userInterface.togglePuzzleValues(true);
          userInterface.toggleHintButton(true);
          chosen.setBackground(Color.blue);
          lastChosen = (JButton)e.getSource();
        }
        else
        {
          value = Integer.parseInt(action.substring(2));
          makeMove(row, col, value, false);
          userInterface.togglePuzzleValues(false);
          userInterface.toggleHintButton(false);
        }
      }
    }
  }
  
  public void setSolved()
  {
    finished = true;
  }
  
  public void makeMove(int row, int col, int value, boolean solve)
  {
    if (gameType.equals("Sudoku"))
    {
      puzzleGrid = sudokuPuzzleGame.makeMove(row, col, value, false, false);
      userInterface.setMove(row, col, value, solve);
      if (sudokuPuzzleGame.donePuzzle())
      {
        finished = true;
      }
    }
    else
    {
      finishedCages = 0;
      puzzleGrid = calkuroPuzzleGame.makeMove(row, col, value, false, false);
      userInterface.setMove(row, col, value, solve);
      for (int i = 0; i < cages.length; ++i)
      {
        if (cages[i].checkCage(puzzleGrid))
        {
          finishedCages++;
        }
      }
      if (finishedCages == cages.length && !calkuroPuzzleGame.duplicateCheck())
      {
        finished = true;
      }
    }
  }
  
  public void undoMove(int row, int col, int value)
  {
    userInterface.unset(row, col, value);
  }
  
  public void redoMove(int row, int col, int value)
  {
    userInterface.setMove(row, col, value, false);
  }
  
  public void showHint(int row, int column, int value)
  {
    userInterface.showHint(row, column, value);
  }
  
  public void endGame()
  {
    if (gameType.equals("Sudoku"))
    {
      if (sudokuPuzzleGame.donePuzzle() && !solved)
      {
        JOptionPane.showMessageDialog(null, "You Won! Congratulations!!!", "WINNER!", JOptionPane.INFORMATION_MESSAGE);
        file.addToStats(2);
      }
      else
      {
        JOptionPane.showMessageDialog(null, "Better luck next time", "Oh well", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    else
    {
      if (!solved)
      {
        JOptionPane.showMessageDialog(null, "You Won! Congratulations!!!", "WINNER!", JOptionPane.INFORMATION_MESSAGE);
        file.addToStats(2);
      }
      else
      {
        JOptionPane.showMessageDialog(null, "Better luck next time", "Oh well", JOptionPane.INFORMATION_MESSAGE);
      }
    }
    int response = JOptionPane.showConfirmDialog(null, "Would you like to play another?", "Play again?", JOptionPane.YES_NO_OPTION);    
    if (response == JOptionPane.YES_OPTION)
    {
      userInterface.endGame();
      file.endGame();
      new Controller(playerName);
    }
    else
      System.exit(0);
  }
}
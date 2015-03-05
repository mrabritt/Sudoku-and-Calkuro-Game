import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class CalkuroGrid implements ActionListener
{
  private int[][] grid, hintGrid;
  private MovesRecorder recorder;
  private CalkuroCage[] cages;
  private Controller control;
  private javax.swing.Timer solvingTimer;
  private int solverRow = 0, solverCol = 0;
  
  
  public int[][] makePuzzle(int[][] grid, CalkuroCage[] cages, Controller control, String gameDifficulty, int gameNumber)
  {
    this.grid = grid;
    this.cages = cages;
    recorder = new MovesRecorder();
    this.control = control;
    hintGrid = new int[grid.length][grid.length];
    try
    {
      Scanner solution = new Scanner(new File("./Solutions/Calkuro" + gameDifficulty + "" + gameNumber + "Sol"));
      for (int i = 0; i < hintGrid.length; ++i)
      {
        for (int j = 0; j < hintGrid.length; ++j)
        {
          hintGrid[i][j] = solution.nextInt();
        }
      }
    }
    catch (FileNotFoundException e)
    {
      System.out.print("Solution unavailable");
    }
    return this.grid;
  }
  
  public int[][] getPuzzle()
  {
    return grid;
  }
  
  public boolean checkCage(int[][] grid, int row, int col)
  {
    for (int i = 0; i < cages.length; ++i)
    {
      if (cages[i].isInThisCage(row, col))
      {
        switch (cages[i].cageOper)
        {
          case 1:  return cages[i].cageAddsUp(grid);
          case 2:  return cages[i].cageSubsUp(grid);
          case 3:  return cages[i].cageDivsUp(grid);
          case 4:  return cages[i].cageMultsUp(grid);
        }
      }
    }
    System.out.println("Error on checkCage()");
    return false; // Something went wrong if it gets to here
  }
    
  public int[][] makeMove(int row, int column, int value, boolean redo, boolean hint)
  {
    if (!hint)
    {
      int preValue = grid[row][column];
      grid[row][column] = value;
      if (!redo)
      {
        recorder.record(row, column, value, preValue);
      }
      return grid;
    }
    else
    {
      grid[row][column] = value;
      return grid;
    }
  }
  
  public boolean duplicateCheck()
  {
    int target = 0, actual = 0;
    for (int i = 0; i < grid.length; ++i)
    {
      for (int j = 0; j < grid.length; ++j)
      {
        target += j + 1;
        actual += grid[i][j];
      }
    }
    return target != actual;
  }
  
  public void undoLast()
  {
    int lastRow, lastColumn, lastValue;
    if (recorder.getPosition() > -1)
    {
      lastRow = recorder.undoRow();
      lastColumn = recorder.undoColumn();
      lastValue = recorder.undoValue();
      grid[lastRow][lastColumn] = lastValue;
      control.undoMove(lastRow, lastColumn, lastValue);
    }
    else
      JOptionPane.showMessageDialog(null, "Nothing to Undo!", "Oops", JOptionPane.ERROR_MESSAGE);
  }
  
  public void redoLast()
  {
    int nextRow, nextColumn, nextValue;
    if ((recorder.getListSize() - 1)  == recorder.getPosition())
    {
      JOptionPane.showMessageDialog(null, "Nothing to Re-do!", "Oops", JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      nextRow = recorder.redoRow();
      nextColumn = recorder.redoColumn();
      nextValue = recorder.redoValue();
      grid = makeMove(nextRow, nextColumn, nextValue, true, false);
      control.redoMove(nextRow, nextColumn, nextValue);
    }
  }
  
  public void solvePuzzle()
  {
    solvingTimer = new javax.swing.Timer(25, this);
    solvingTimer.setRepeats(true);
    solvingTimer.start();
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if (solverCol < hintGrid.length)
    {
      if (solverRow < hintGrid.length)
      {
        control.makeMove(solverRow, solverCol, hintGrid[solverRow][solverCol], false);
        solverRow++;
      }
      else
      {
        solverRow = 0;
        solverCol++;
      }
    }
    else
    {
      solvingTimer.stop();
      control.setSolved();
    }
  }
  
  public int littleHelp(int row, int column)
  {    
    control.showHint(row, column, hintGrid[row][column]);
    makeMove(row, column, hintGrid[row][column], false, true);
    return hintGrid[row][column];
  }
}
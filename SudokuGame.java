import javax.swing.*;
import java.awt.event.*;


public class SudokuGame implements ActionListener
{
  private SudokuGrid puzzle;
  private MovesRecorder recorder;
  private MySudokuSolver solver;
  private Controller control;
  private Timer solvingTimer;
  private int[][] puzzleGrid, hintGrid;
  private int solverRow = 0, solverCol = 0;
  private boolean madeMove = false;

  public int[][] getGrid()
  {
    return puzzleGrid;
  }
  
  public int[][] makePuzzle(int[][] grid, Controller control)
  {
    this.control = control;
    try
    {
      puzzle = new SudokuGrid(grid, grid.length, 3);
    }
    catch (InvalidGrid e)
    {
      System.out.println(e.getMessage());
      System.exit(0);
    }
    catch (DuplicateValue e)
    {
      System.out.println(e.getMessage());
      System.exit(0);
    }
    puzzleGrid = puzzle.get();
    recorder = new MovesRecorder();
    solvePuzzle(true);
    return puzzleGrid;
  }
  
  public int[][] makeMove(int row, int column, int value, boolean redo, boolean hint)
  {
    if (!hint)
    {
      int preValue = -1;
      try
      {
        preValue = puzzleGrid[row][column];
        puzzle.set(row, column, value);
      }
      catch (InvalidIndex e)
      {}
      catch (AlreadySet e)
      {
        try
        {
          puzzle.unset(row, column);
          puzzle.set(row, column, value);
        }
        catch (Exception error)
        {}
      }
      catch (ConstantSet e)
      {}
      puzzleGrid = puzzle.get();
      try
      {
        puzzle.isValid(puzzleGrid, row, column);
      }
      catch (DuplicateValue e)
      {}
      if (!redo)
      {
        recorder.record(row, column, value, preValue);
      }
      madeMove = true;
    }
    else
    {
      try
      {
        puzzle.set(row, column, value);
      }
      catch (Exception e)
      {}
      puzzleGrid = puzzle.get();
    }
    return puzzleGrid;
  }
  
  public boolean donePuzzle()
  {
    boolean finished = true, noDups = true;
    puzzleGrid = puzzle.get();
    try
    {
      for (int i = 0; i < puzzleGrid.length && finished; ++i)
      {
        for (int j = 0; j < puzzleGrid.length && finished; ++j)
        {
          if (puzzleGrid[i][j] != 0)
            puzzle.isValid(puzzleGrid, i, j);
          else
            finished = false;
        }
      }
    }
    catch (DuplicateValue e)
    {
      noDups = false;
    }
    return finished && noDups;
  }
        
  
  private void solvePuzzle(boolean forHint)
  {
    hintGrid = puzzle.get();
    solver = new MySudokuSolver(hintGrid);
    solver.solve(0, 0); // 0, 0 is to start the recursive method at the first row and first column
    hintGrid = solver.getSolved();
  }
  
  public void solvePuzzle()
  {
    solvingTimer = new Timer(25, this);
    solvingTimer.setRepeats(true);
    if (!madeMove)
    {
      solvingTimer.start();
    }
    else
    {
      solver = new MySudokuSolver(puzzleGrid);
      if (solver.solve(0, 0))
      {
        hintGrid = solver.getSolved();
        solvingTimer.start();
      }
      else
      {
        solvingTimer.start();
      }
    }
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if (solverCol < hintGrid.length)
    {
      if (solverRow < hintGrid.length)
      {
        control.makeMove(solverRow, solverCol, hintGrid[solverRow][solverCol], true);
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
  
  public void undoLast()
  {
    int lastRow, lastColumn, lastValue;
    if (recorder.getPosition() > -1)
    {
      lastRow = recorder.undoRow();
      lastColumn = recorder.undoColumn();
      lastValue = recorder.undoValue();
      try
      {
        puzzle.set(lastRow, lastColumn, lastValue);
      }
      catch (Exception e)
      {}
      control.undoMove(lastRow, lastColumn, lastValue);
    }
    else
      JOptionPane.showMessageDialog(null, "Nothing to Undo!", "Error", JOptionPane.ERROR_MESSAGE);
  }
  
  public void redoLast()
  {
    int nextRow, nextColumn, nextValue;
    if ((recorder.getListSize() - 1)  == recorder.getPosition())
    {
      JOptionPane.showMessageDialog(null, "Nothing to Re-do!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    else
    {
      nextRow = recorder.redoRow();
      nextColumn = recorder.redoColumn();
      nextValue = recorder.redoValue();
      puzzleGrid = makeMove(nextRow, nextColumn, nextValue, true, false);
      control.redoMove(nextRow, nextColumn, nextValue);
    }
  }
  
  public void littleHelp(int row, int column)
  {    
    control.showHint(row, column, hintGrid[row][column]);
    makeMove(row, column, hintGrid[row][column], false, true);
  }
}
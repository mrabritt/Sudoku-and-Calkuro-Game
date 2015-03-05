import java.awt.*;

public class CalkuroCage
{
  private int gridSize, cageSum;
  public int cageOper;
  private int[][] grid;
  private Color cageColor;
  
  
  public CalkuroCage(int gridSize, int cageOper, int cageSum, int cageSize, Color cageColor)
  {
    this.cageSum = cageSum;
    this.cageOper = cageOper;
    this.gridSize = gridSize;
    grid = new int[this.gridSize][this.gridSize];
    this.cageColor = cageColor;
  }

  public void addToCage(int memberRow, int memberCol)
  {
    grid[memberRow][memberCol] = 1;
  }

  public boolean checkCage(int[][] puzzleGrid)
  {
    if (cageIsFull(puzzleGrid))
    {
      switch (cageOper)
      {
        case 1:   return cageAddsUp(puzzleGrid);
        case 2:   return cageSubsUp(puzzleGrid);
        case 3:   return cageDivsUp(puzzleGrid);
        case 4:   return cageMultsUp(puzzleGrid);
        default:  System.out.println("Invalid operator");
                  return false;
      }
    }
    else
      return false;
  }
  
  public boolean isInThisCage(int row, int col)
  {
    return grid[row][col] == 1;
  }
  
  public boolean cageIsFull(int[][] checkGrid)
  {
    boolean result = false, found = false;
    for (int i = 0; i < grid.length && !found; ++i)
    {
      for (int j = 0; j < grid.length; ++j)
      {
        if (grid[i][j] == 1 && checkGrid[i][j] != 0)
          result = true;
        else if (grid[i][j] == 1 && checkGrid[i][j] == 0)
          return false;
      }
    }
    return result;
  }
  
  public boolean cageAddsUp(int[][] checkGrid)
  {
    int sum = 0;
    for (int i = 0; i < grid.length; ++i)
    {
      for (int j = 0; j < grid.length; ++j)
      {
        if (grid[i][j] == 1)
        {
          sum = sum + checkGrid[i][j];
        }
      }
    }
    return sum == cageSum;
  }
  
  public boolean cageMultsUp(int[][] checkGrid)
  {
    boolean first = false;
    int sum = 0;
    for (int i = 0; i < grid.length; ++i)
    {
      for (int j = 0; j < grid.length; ++j)
      {
        if (grid[i][j] == 1)
        {
          if (!first)
          {
            sum = checkGrid[i][j];
            first = true;
          }
          else
            sum = sum * checkGrid[i][j];
        }
      }
    }
    return sum == cageSum;
  }
  
  public boolean cageDivsUp(int[][] checkGrid)
  {
    boolean first = false;
    int sum = 0;
    for (int i = 0; i < grid.length; ++i)
    {
      for (int j = 0; j < grid.length; ++j)
      {
        if (grid[i][j] == 1)
        {
          if (!first)
          {
            sum = checkGrid[i][j];
            first = true;
          }
          else
            sum = sum / checkGrid[i][j];
        }
      }
    }
    first = false;
    if (sum != cageSum)
    {
      for (int i = grid.length - 1; i >= 0; --i)
      {
        for (int j = grid.length - 1; j >= 0; --j)
        {
          if (grid[i][j] == 1)
          {
            if (!first)
            {
              sum = checkGrid[i][j];
              first = true;
            }
            else
              sum = sum / checkGrid[i][j];
          }
        }
      }
    }
    return sum == cageSum;
  }
  
  public boolean cageSubsUp(int[][] checkGrid)
  {
    boolean first = false;
    int sum = 0;
    for (int i = 0; i < grid.length; ++i)
    {
      for (int j = 0; j < grid.length; ++j)
      {
        if (grid[i][j] == 1)
        {
          if (!first)
          {
            sum = checkGrid[i][j];
            first = true;
          }
          else
            sum = sum - checkGrid[i][j];
        }
      }
    }
    return Math.abs(sum) == cageSum;
  }
  
  public Color getCageColor()
  {
    return cageColor;
  }
  
  public int getCageOper()
  {
    return cageOper;
  }
  
  public int getCageSum()
  {
    return cageSum;
  }
}
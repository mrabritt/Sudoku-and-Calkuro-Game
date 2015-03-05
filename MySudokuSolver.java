public class MySudokuSolver 
{ 
  /*
   * Decided a different approach to solving the puzzles would be beneficial
   * to the initial solve that will happen when a puzzle is first loaded into the
   * game. At first we looked into using threads to solve the puzzle in parallel 
   * so that the user could still interact with the interface while the solve was happening, but
   * after looking into it we decided we might not have the time or skill to accomplish this properly.
   * After several hours trial and error, this algorithm was born.
   */
  
  private int[][] puzzleGrid;
  
  public MySudokuSolver(int[][] puzzleGrid)
  {
    this.puzzleGrid = puzzleGrid;
  }
  
  public boolean solve(int row, int col) 
  {
    if (row == 9) 
    {
      row = 0; // Go to next row
      if (++col == 9) // end of puzzle
      {
        return true; 
      }
    }
    if (puzzleGrid[row][col] != 0)
    {
      return solve(row + 1, col);
    }
    for (int value = 1; value <= 9; ++value) 
    {
      if (valid(row, col, value)) 
      {
        puzzleGrid[row][col] = value;
        if (solve(row + 1, col))
        {
          return true;
        }
      }
    }
    puzzleGrid[row][col] = 0; // reset on backtrack
    return false;
  }

  public boolean valid(int row, int col, int val) 
  {
    for (int i = 0; i < 9; ++i)
    {
      if (val == puzzleGrid[i][col])
      {
        return false;
      }
    }
    for (int i = 0; i < 9; ++i)
    {
      if (val == puzzleGrid[row][i])
      {
        return false;
      }
    }
    int boxRowOffset = (row / 3)*3;
    int boxColOffset = (col / 3)*3;
    for (int i = 0; i < 3; ++i) // check box
    {
      for (int j = 0; j < 3; ++j)
      {
        if (val == puzzleGrid[boxRowOffset+i][boxColOffset+j])
        {
          return false;
        }
      }
    }
    return true; // no violations, so it's valid
  }

  public int[][] getSolved() 
  {
    return puzzleGrid;
  }
}
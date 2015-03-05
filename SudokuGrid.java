public class SudokuGrid 
{int gridSize;
 int boxSize;
 int[][] grid, startGrid;
 
 // Constructor
 public SudokuGrid(int[][] newGrid, int newGridSize, int newBoxSize) throws InvalidGrid, DuplicateValue
   {gridSize = newGridSize;
    boxSize = newBoxSize;
    checkGrid(newGrid);
    for (int i = 0; i < newGrid.length; i++)
      for (int j = 0; j < newGrid[i].length; j++)
        if (newGrid[i][j] != 0)
          isValid(newGrid, i, j);      
    grid = new int [gridSize][gridSize];
    startGrid = new int [gridSize][gridSize];
    for (int i = 0; i < gridSize; i++)
      for (int j = 0; j < gridSize; j++)
        startGrid[i][j] = grid[i][j] = newGrid[i][j];
   }
 
 // Obtain a list of free cells from the puzzle 
 int[][] getFreeCellList(int [][]grid) 
   {// Determine the number of free cells 
    int numberOfFreeCells = 0;   
    for (int i = 0; i < gridSize; i++)
      for (int j = 0; j < gridSize; j++) 
        if (grid[i][j] == 0) 
          numberOfFreeCells++;
    
    // Store free cell positions into freeCellList 
    int[][] freeCellList = new int[numberOfFreeCells][2];
    int count = 0;
    for (int i = 0; i < gridSize; i++)
      for (int j = 0; j < gridSize; j++) 
        if (grid[i][j] == 0) 
          {freeCellList[count][0] = i;
           freeCellList[count++][1] = j;
          }   
    return freeCellList;
   }

 // Check whether grid[i][j] is valid in the grid 
 public void isValid(int[][] grid, int i, int j) throws DuplicateValue
   {// Check whether grid[i][j] is valid at the i's row
    for (int column = 0; column < gridSize; column++)
      if (column != j && grid[i][column] == grid[i][j])
        throw new DuplicateValue("Duplicate row value : Row " + (i + 1) + " Cols " + (j + 1) + " and " +
                                 (column + 1) + ". Value is " + grid[i][j]);
    // Check whether grid[i][j] is valid at the j's column
    for (int row = 0; row < gridSize; row++)
      if (row != i && grid[row][j] == grid[i][j])
        throw new DuplicateValue("Duplicate col value : Col " + (j + 1) + " Rows " + (i + 1) + " and " +
                                 (row + 1) + ". Value is " + grid[i][j]);

    // Check whether grid[i][j] is valid in the boxSize by boxSize box
    for (int row = (i / boxSize) * boxSize; row < (i / boxSize) * boxSize + boxSize; row++)
      for (int col = (j / boxSize) * boxSize; col < (j / boxSize) * boxSize + boxSize; col++)
        if (row != i && col != j && grid[row][col] == grid[i][j])
          throw new DuplicateValue("Duplicate box value : Row " + (i + 1) + ", Col " + (j + 1) + 
                                   " : Row " + (row + 1) + ", Col " + (col + 1) + ". Value is " + grid[i][j]);
   }

 // Check a grid for size and duplicates 
 public void checkGrid(int[][] grid) throws InvalidGrid, DuplicateValue
   {if (grid.length != gridSize)
      throw new InvalidGrid("Invalid number of rows : " + grid.length);
    for (int i = 0; i < grid.length; i++)
      if (grid[i].length != gridSize)
        throw new InvalidGrid("Invalid col length for row " + (i + 1) +", length is " + grid[i].length);
    for (int i = 0; i < grid.length; i++)
      for (int j = 0; j < grid[i].length; j++)
        if (grid[i][j] != 0)
          isValid(grid, i, j);      
   }
 
 // Generate a solution from a starting point 
 public boolean solution(int[][] grid) throws InvalidGrid, DuplicateValue
   {checkGrid(grid);
    int[][] freeCellList = getFreeCellList(grid); // Free cells
    if (freeCellList.length == 0) 
      return true; // "No free cells");
    
    int k = 0; // Start from the first free cell      
    while (true) 
      {int i = freeCellList[k][0];
       int j = freeCellList[k][1];
       if (grid[i][j] == 0) 
         grid[i][j] = 1; // Fill the free cell with number 1
       boolean duplicate = false;
       try
         {isValid(grid, i, j);
         }
       catch (DuplicateValue e)
         {duplicate = true;
         }
       if (!duplicate)
         if (k + 1 == freeCellList.length)  // No more free cells 
           return true; // A solution is found
         else  // Move to the next free cell
           k++;
       else if (grid[i][j] < gridSize) 
         // Fill the free cell with the next possible value
         grid[i][j] = grid[i][j] + 1; 
       else  // free cell grid[i][j] is gridSize, backtrack
         {while (grid[i][j] == gridSize) 
            {if (k == 0) 
               return false; // No possible value
             grid[i][j] = 0; // Reset to free cell
             k--; // Backtrack to the preceding free cell
             i = freeCellList[k][0];
             j = freeCellList[k][1];
            }
          // Fill the free cell with the next possible value, 
          // search continues from this free cell at k
          grid[i][j] = grid[i][j] + 1;
         } 
      }
   }
  
 // Return the current puzzle
 public int[][] get()
   {int[][] result = new int [gridSize][gridSize];
    for (int i = 0; i < gridSize; i++)
      for (int j = 0; j < gridSize; j++)
        result[i][j] = grid[i][j];
    return result;
   }
 
 void checkRanges(int i, int j) throws InvalidIndex
   {if (i < 0 || i >= gridSize)
      throw new InvalidIndex("Invalid row index : " + (i + 1));
    if (j < 0 || j >= gridSize)
      throw new InvalidIndex("Invalid col index : " + (j + 1));
   }

  // Return an element of the puzzle
 public int get(int i, int j) throws InvalidIndex
  {checkRanges(i, j);
   return grid[i][j];  
  }
 
 // Set an element of the puzzle
 public void set(int i, int j, int value) throws InvalidIndex, AlreadySet, ConstantSet
   {checkRanges(i, j);
    if (startGrid[i][j] != 0)
      throw new ConstantSet(i, j, grid[i][j]);
    if (grid[i][j] != 0)
      throw new AlreadySet(i, j, grid[i][j]);
    grid[i][j] = value;
   }

 // Delete an element of the puzzle
 public void unset(int i, int j) throws InvalidIndex, NotSet, ConstantSet
   {checkRanges(i, j);
    if (startGrid[i][j] != 0)
      throw new ConstantSet(i, j, grid[i][j]);
    if (grid[i][j] == 0)
      throw new NotSet(i, j);
    grid[i][j] = 0;
   } 
}
import java.io.*;

public class GameSave
{
 private String saveName = "./Saves/";
 private String params;

 public void save(String playerName, int[][] grid)
 {
   saveName = saveName + "" + playerName + "" + params + ".txt";
   try
   {
     PrintWriter write = new PrintWriter(new FileWriter(saveName));
     write.println(grid.length);
     for (int row = 0; row < grid.length; ++row)
     {
       for (int col = 0; col < grid.length; ++col)
         write.print(grid[row][col] + " ");
       write.println();
     }
     write.close();
   }
   catch (IOException e)
   {
     System.out.print(e.getMessage() + "Error!");
   }
   System.out.println("Game Saved in " + saveName + "\nGoodbye " + playerName + "!");  
   System.exit(0);
 }
}
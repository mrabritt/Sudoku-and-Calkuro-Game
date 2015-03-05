import java.io.*;
public class OnlyDisplayPlayersGames implements FilenameFilter
{
  String playerName;
  public OnlyDisplayPlayersGames(String playerName)
  {
    this.playerName = playerName;
  }
  public boolean accept(File dir, String name)
  {
    if (playerName.equals(name.substring(0, playerName.length())) && (name.indexOf("Sudoku") == playerName.length() || name.indexOf("Calkuro") == playerName.length()))
      return true;
    else
      return false;
  }
}
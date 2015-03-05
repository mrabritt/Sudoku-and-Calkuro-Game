import java.io.*;
public class OnlyDisplayAvailableGames implements FilenameFilter
{
  String gameType, gameDifficulty, filter;
  public OnlyDisplayAvailableGames(String gameType, String gameDifficulty)
  {
    this.gameType = gameType;
    this.gameDifficulty = gameDifficulty;
    filter = gameType + "" + gameDifficulty;
  }
  public boolean accept(File dir, String name)
  {
    if (filter.equals(name.substring(0, filter.length())))
      return true;
    else
      return false;
  }
}
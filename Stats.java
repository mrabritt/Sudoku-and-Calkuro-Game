public class Stats implements Comparable<Stats>
{
  private String playerName;
  private int points;
  
  public Stats(String playerName, int points)
  {
    this.playerName = playerName;
    this.points = points;
    if (points < 0)
      this.points = 0;
  }
  
  public String toString()
  {
    return playerName + ": " + points;
  }
  
  public String getName()
  {
    return playerName;
  }
  
  public int compareTo(Stats other)
  {
    final int BEFORE = -1;
    final int EQUAL = 0;
    final int AFTER = 1;
    
    if (this.points < other.points)
      return AFTER;
    if (this.points > other.points)
      return BEFORE;
    else
      return EQUAL;
  }
}
import java.io.*;
public class OnlyDisplayPlayerStatFiles implements FilenameFilter
{
  public OnlyDisplayPlayerStatFiles()
  {
  }
  public boolean accept(File dir, String name)
  {
    if (name.indexOf("Statistics.dat") != -1)
      return true;
    else
      return false;
  }
}
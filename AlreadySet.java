 public class AlreadySet extends Exception
{
   AlreadySet(int i, int j, int value)
   {
     super("Element at " + i + ", " + j + " already set to " + value);
   }
}
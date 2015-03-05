public class NotSet extends Exception
{NotSet(int i, int j)
   {super("Element at " + i + ", " + j + " has not been set");
   }
}
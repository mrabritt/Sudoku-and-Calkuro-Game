public class ConstantSet extends Exception
{
  ConstantSet(int i, int j, int value)
   {
     super("Element at " + i + ", " + j + " cannot be changed " + value);
   }
}
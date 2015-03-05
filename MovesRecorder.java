import java.util.*;
public class MovesRecorder
{
  private List<Integer> rowMoves; 
  private List<Integer> columnMoves; 
  private List<Integer> valueMoves; 
  private List<Integer> preValueMoves; 
  
  private int position;
  
  public MovesRecorder()
  {
    rowMoves = new ArrayList<Integer>();
    columnMoves = new ArrayList<Integer>();
    valueMoves = new ArrayList<Integer>();
    preValueMoves = new ArrayList<Integer>();
    position = -1;
  }
  
  public int getPosition()
  {
    return position;
  }
  
  public int getListSize()
  {
    return rowMoves.size();
  }
  
  public void record(int row, int column, int value, int preValue)
  {
    rowMoves.add(row);
    columnMoves.add(column);
    valueMoves.add(value);
    preValueMoves.add(preValue);
    position++;
    if (position < rowMoves.size() - 1)
      clearRedo();
  }
  
  public int undoRow()
  {
    return rowMoves.get(position);
  }
  public int undoColumn()
  {
    return columnMoves.get(position);
  }
  public int undoValue()
  {
    return preValueMoves.get(position--);
  }
  
  public int redoRow()
  {
    return rowMoves.get(++position);
  }
  public int redoColumn()
  {
    return columnMoves.get(position);
  }
  public int redoValue()
  {
    return valueMoves.get(position);
  }
  
  private void clearRedo()
  {
    while (position < rowMoves.size() - 1)
    {
      rowMoves.remove(position + 1);
      columnMoves.remove(position + 1);
      valueMoves.remove(position + 1);
      preValueMoves.remove(position + 1);
    }
  }
      
  
}
import java.awt.*;
import javax.swing.*;

public class CalkuroPuzzlePanel extends JPanel
{
  private JButton[][] puzzleButtons;
  private JButton[] numbers;
  private JPanel valuesPanel, gamePanel;
  private CalkuroCage[] cages;
  private Controller listener;
  private int[][] puzzleGrid;
  
  public CalkuroPuzzlePanel(int[][] puzzleGrid, Controller listener, CalkuroCage[] cages)
  {
    this.cages = cages;
    this.puzzleGrid = puzzleGrid;
    this.listener = listener;
    int gridSize = puzzleGrid.length;
    setLayout(new BorderLayout());
    valuesPanel = new JPanel();
    gamePanel = new JPanel();
    gamePanel.setLayout(new GridLayout(gridSize, gridSize));
    gamePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    gamePanel.setPreferredSize(new Dimension(500, 480));
    gamePanel.setMaximumSize(new Dimension(500, 480));
    gamePanel.setMinimumSize(new Dimension(500, 480));
    add(gamePanel, BorderLayout.CENTER);
    add(valuesPanel, BorderLayout.WEST);
  }
  
  public void createButtons()
  {
    puzzleButtons = new JButton[puzzleGrid.length][puzzleGrid.length];
    for (int i = 0; i < puzzleGrid.length; ++i)
    {
      for (int j = 0; j < puzzleGrid.length; ++j)
      {
        if (puzzleGrid[i][j] != 0)
          puzzleButtons[i][j] = new JButton("" + puzzleGrid[i][j] + "");
        else
          puzzleButtons[i][j] = new JButton("");
        for (int cage = 0; cage < cages.length; ++cage)
          if (cages[cage].isInThisCage(i, j))
            puzzleButtons[i][j].setBackground(cages[cage].getCageColor());
        puzzleButtons[i][j].addActionListener(listener);
        puzzleButtons[i][j].addMouseListener(listener);
        puzzleButtons[i][j].setActionCommand("r " + i + " c " + j);
        gamePanel.add(puzzleButtons[i][j]);
      }
    }
  }
  
  public void createValues()
  {
    int gridSize = puzzleGrid.length;
    valuesPanel.setPreferredSize(new Dimension(50, 500));
    valuesPanel.setMaximumSize(new Dimension(50, 500));
    valuesPanel.setMinimumSize(new Dimension(50, 500));
    valuesPanel.setLayout(new GridLayout(gridSize, 1, 10, 10));
    valuesPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    numbers = new JButton[gridSize];
    for (int i = 0; i < numbers.length; ++i)
    {
      numbers[i] = new JButton("" + (i + 1) + "");
      numbers[i].setActionCommand("v " + (i + 1)); 
      numbers[i].addActionListener(listener);
      numbers[i].setPreferredSize(new Dimension(50, 50));
      numbers[i].setEnabled(false);
      valuesPanel.add(numbers[i]);
    }
  }
  
  public void toggleValues(boolean enable)
  {
    for (int i = 0; i < numbers.length; ++i)
    {
      numbers[i].setEnabled(enable);
    }
  }
  
  public void setValue(int value)
  {
    for (int i = 0; i < puzzleButtons.length; ++i)
    {
      for (int j = 0; j < puzzleButtons.length; ++j)
      {
        if (puzzleButtons[i][j].getBackground() == Color.blue)
        {
          puzzleButtons[i][j].setText("" + value + "");
        }
      }
    }
  }
  
  public void setValue(int row, int col, int value)
  {
    puzzleButtons[row][col].setText("" + value + "");
    for (int i = 0; i < cages.length; ++i)
    {
      if (cages[i].isInThisCage(row, col))
      {
        puzzleButtons[row][col].setBackground(cages[i].getCageColor());
      }
    }
  }
  
  public void unset(int lastRow, int lastCol, int lastValue)
  {
    if (lastValue == 0)
      puzzleButtons[lastRow][lastCol].setText("");
    else
      puzzleButtons[lastRow][lastCol].setText("" + lastValue);
    for (int i = 0; i < cages.length; ++i)
    {
      if (cages[i].isInThisCage(lastRow, lastCol))
      {
        puzzleButtons[lastRow][lastCol].setBackground(cages[i].getCageColor());
      }
    }
  }
  
  
}
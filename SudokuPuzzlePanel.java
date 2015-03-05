import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SudokuPuzzlePanel extends JPanel implements MouseListener
{
  private JButton[][] puzzleButtons;
  private JButton[] numbers;
  private JPanel[] boxes;
  private JPanel gamePanel, valuesPanel;
  private Controller listener;
  private int[][] puzzleGrid;
  private int colSelected, rowSelected;
  private boolean spotSelected = false;
  
  public SudokuPuzzlePanel(int[][] puzzleGrid, Controller listener)
  {
    this.listener = listener;
    this.puzzleGrid = puzzleGrid;
    setLayout(new BorderLayout());
    valuesPanel = new JPanel();
    gamePanel = new JPanel();
    gamePanel.setLayout(new GridLayout(3, 3));
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
    boxes = new JPanel[9];
    int box = 0;
    for (int i = 0; i < boxes.length; i += 3)
    {
      for (int j = 0; j < boxes.length; j += 3, ++box)
      {
        boxes[box] = new JPanel(new GridLayout(3, 3));
        boxes[box].setBorder(BorderFactory.createLineBorder(Color.blue, 2));
        for (int k = 0; k < 3; ++k)
        {
          for (int l = 0; l < 3; ++l)
          {
            if (puzzleGrid[i + k][j + l] != 0)
            {
              puzzleButtons[i + k][j + l] = new JButton("" + puzzleGrid[i + k][j + l] + "");
              puzzleButtons[i + k][j + l].setPreferredSize(new Dimension(50, 50));
              puzzleButtons[i + k][j + l].setBackground(Color.green);
              puzzleButtons[i + k][j + l].setEnabled(false);
            }
            else
            {
              puzzleButtons[i + k][j + l] = new JButton("");
              puzzleButtons[i + k][j + l].setBackground(Color.white);
              puzzleButtons[i + k][j + l].addMouseListener(this);
              puzzleButtons[i + k][j + l].addActionListener(listener);
            }
            puzzleButtons[i + k][j + l].setActionCommand("r " + (i + k) + " c " + (j + l));
            boxes[box].add(puzzleButtons[i + k][j + l]);
          }
        }
        gamePanel.add(boxes[box]);
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
  
  public void mouseEntered(MouseEvent e)
  { 
    JButton hover = (JButton)e.getSource();
    if (hover.getBackground() != Color.blue)
      hover.setBackground(Color.yellow);
  }
  
  public void mouseExited(MouseEvent e)
  {
    JButton hover = (JButton)e.getSource();
    if (hover.isEnabled() && hover.getBackground() != Color.blue)
      hover.setBackground(Color.white);
  }
  
  public void mousePressed(MouseEvent e)
  {
  }
  
  public void mouseReleased(MouseEvent e)
  {
  }
  
  public void mouseClicked(MouseEvent e)
  {
    JButton selectedButton = (JButton)e.getSource();
    selectedButton.setBackground(Color.blue);
    for (int i = 0; i < puzzleButtons.length; ++i)
    {
      for (int j = 0; j < puzzleButtons.length; ++j)
      {
        if (puzzleButtons[i][j].getBackground() == Color.blue && puzzleButtons[i][j] != selectedButton)
        {
          puzzleButtons[i][j].setBackground(Color.white);
        }
        else if(selectedButton == puzzleButtons[i][j])
        {
          rowSelected = i;
          colSelected = j;
        }
      }
    }
    spotSelected = true;
  }

  
  public void setValue(int row, int col, int value, boolean solve)
  {
    puzzleButtons[row][col].setText("" + value + "");
    if (!solve)
      puzzleButtons[row][col].setBackground(Color.white);
    else
      puzzleButtons[row][col].setBackground(Color.green);
    spotSelected = false;
  }
  
  public void unset(int lastRow, int lastCol, int lastValue)
  {
    if (lastValue == 0)
      puzzleButtons[lastRow][lastCol].setText("");
    else
      puzzleButtons[lastRow][lastCol].setText("" + lastValue);
  }
  
  public int getSelectedRow()
  {
    return rowSelected;
  }
  
  public int getSelectedCol()
  {
    return colSelected;
  }
  
  public boolean spotSelected()
  {
    return spotSelected;
  }
  
  public void showHint(int row, int column, int value)
  {
    puzzleButtons[row][column].setText("" + value + "");
    puzzleButtons[row][column].setBackground(Color.green);
    puzzleButtons[row][column].removeMouseListener(this);
    puzzleButtons[row][column].setEnabled(false);
    spotSelected = false;
  }
}
          
import java.awt.*;
import javax.swing.*;

public class OptionsPanel extends JPanel
{
  private JButton undo, redo, hint, solve, save, stats, quit;
  private Controller listener;
  private JPanel cagesInfo;
  private JLabel displayOper, displaySum;
  
  public OptionsPanel(Controller listener)
  {
    super();
    this.listener = listener;
    setPreferredSize(new Dimension(150, 500));
    setMaximumSize(new Dimension(150, 500));
    setMinimumSize(new Dimension(150, 500));
    

    undo = new JButton("Undo");
    redo = new JButton("Re-do");
    hint = new JButton("Get Hint");
    solve = new JButton("Solve Puzzle");
    save = new JButton("Save Game");
    stats = new JButton("View Statistics");
    quit = new JButton("Quit");
    cagesInfo = new JPanel();
    displayOper = new JLabel();
    displaySum = new JLabel();
    
    undo.addActionListener(this.listener);
    redo.addActionListener(this.listener);
    hint.addActionListener(this.listener);
    solve.addActionListener(this.listener);
    save.addActionListener(this.listener);
    stats.addActionListener(this.listener);
    quit.addActionListener(this.listener);
    undo.setPreferredSize(new Dimension(120, 30));
    redo.setPreferredSize(new Dimension(120, 30));
    hint.setPreferredSize(new Dimension(120, 30));
    solve.setPreferredSize(new Dimension(120, 30));
    save.setPreferredSize(new Dimension(120, 30));
    stats.setPreferredSize(new Dimension(120, 30));
    quit.setPreferredSize(new Dimension(120, 30));
    
    cagesInfo.setLayout(new BoxLayout(cagesInfo, BoxLayout.Y_AXIS));
    cagesInfo.add(displayOper);
    cagesInfo.add(Box.createVerticalStrut(40));
    cagesInfo.add(displaySum);
    displayOper.setAlignmentX(Component.CENTER_ALIGNMENT);
    displaySum.setAlignmentX(Component.CENTER_ALIGNMENT);
    displaySum.setFont(new Font("Arial", Font.BOLD, 48));
    cagesInfo.setPreferredSize(new Dimension(120, 120));
    
    hint.setEnabled(false);
    
    add(hint);
    add(undo);
    add(redo);
    add(solve);
    add(save);
    add(stats);
    add(quit);
    add(cagesInfo);
  }
  
  public void showCageInfo(CalkuroCage cage)
  {
    String labelContent = "";
    switch (cage.getCageOper())
    {
      case 1: labelContent = "Adds up to:";
              break;
      case 2: labelContent = "Subtracts down to:";
              break;
      case 3: labelContent = "Divides down to:";
              break;
      case 4: labelContent = "Multiplies up to:";
              break;
      default: System.out.print("Cage Error");
    }
    int sum = cage.getCageSum();
    displayOper.setText(labelContent);
    displaySum.setText("" + sum);
    cagesInfo.setBackground(cage.getCageColor());
    repaint();
  }
  
  public void toggleHint(boolean enable)
  {
    hint.setEnabled(enable);
  }
}
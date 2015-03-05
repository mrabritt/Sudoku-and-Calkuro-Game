import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class ShowStatistics extends JDialog 
{
  private String[] playerList;
  private String playerName;
  private int[] pointsList;
  private Stats[] statsList;
  private JLabel[] labels;
  private JLabel title;
  
  public ShowStatistics(JFrame window, String playerName) 
  {
    super(window, "Leaderboard", true);
    this.playerName = playerName;
    try
    {
      makeLists();
    }
    catch (IOException e)
    {
      System.out.println(e.getMessage());
    }
    setStatsList();
    getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    title = new JLabel("Leaderboard");
    title.setFont(new Font("Arial", Font.BOLD, 30));
    title.setForeground(Color.red);
    title.setVerticalAlignment(SwingConstants.CENTER);
    getContentPane().add(title);
    getContentPane().add(Box.createVerticalStrut(20));
    labels = new JLabel[statsList.length];
    makeLeaderBoard();
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLocationRelativeTo(window);
    setPreferredSize(new Dimension(350, 250));
    getContentPane().setBackground(Color.black);
    pack(); 
    setVisible(true);
  }
  
  private void makeLeaderBoard()
  {
    for (int i = 0; i < statsList.length; ++i)
    {
      labels[i] = new JLabel( (i+1) + ") " + statsList[i]);
      labels[i].setFont(new Font("Arial", Font.ITALIC, 16));
      labels[i].setForeground(Color.white);
      if (statsList[i].getName().equals(playerName))
      {
        labels[i].setFont(new Font("Arial", Font.BOLD, 20));
        labels[i].setForeground(Color.green);
      }
      add(labels[i]);
      add(Box.createVerticalStrut(20));
    }
  }
  
  private void makeLists() throws IOException
  {
    String[] temp;
    File dir = new File("./Statistics");
    FilenameFilter only = new OnlyDisplayPlayerStatFiles();
    temp = dir.list(only);
    playerList = new String[temp.length];
    pointsList = new int[temp.length];
    for (int i = 0; i < temp.length; ++i)
    {
      playerList[i] = temp[i].substring(0, temp[i].indexOf("Statistics"));
    }
    Scanner file;
    for (int i = 0; i < temp.length; ++i)
    {
      file = new Scanner(new File("./Statistics/" + temp[i]));
      pointsList[i] = file.nextInt();
      file.close();
    }
  }
  
  private void setStatsList()
  {
    statsList = new Stats[playerList.length];
    for (int i = 0; i < statsList.length; ++i)
    {
      statsList[i] = new Stats(playerList[i], pointsList[i]);
    }
    Arrays.sort(statsList);
  }
    
}
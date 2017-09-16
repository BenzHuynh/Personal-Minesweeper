//Benz Huynh

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.color.*;
import javax.swing.*;
public class Minesweeper extends JApplet implements ActionListener, MouseListener
{
	private static JFrame mainFrame = new JFrame("Minesweeper");
	private static JButton [][] map = new JButton[30][16];
	private static JLabel [][] mines = new JLabel[30][16];
	private static JPanel [][] bottom = new JPanel[30][16];
	
	private static ArrayList<JPanel> mineHold = new ArrayList<>();
	private static ArrayList<JLabel> emptyHold = new ArrayList<>();
	private static ArrayList<JLabel> numHold = new ArrayList<>();
	
	private static int clicks = 0;
	private static int num = 0;
	
	public Minesweeper()
	{
		mainFrame.setSize(290, 590);
		mainFrame.setLayout(new GridLayout(30,16));
	
		createBoard(this, this);
		
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//end constructor
	
	/** METHODS HERE**/
	
	private static void createBoard(ActionListener a, MouseListener b)
	{
		/** placing buttons on the board **/
		for(int x = 0; x < 30; x++)
		{
			for(int y = 0; y < 16; y++)
			{
				map[x][y] = new JButton();
				bottom[x][y] = new JPanel();
				
				map[x][y].setMaximumSize(new Dimension(20, 20));
				bottom[x][y].setLayout(new OverlayLayout(bottom[x][y]));
				bottom[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				map[x][y].setBackground(new Color(70, 130, 180));
				
				map[x][y].addActionListener(a);
				map[x][y].addMouseListener(b);
				mainFrame.add(bottom[x][y]);
				bottom[x][y].add(map[x][y]);
			}
		}
		
		/** placing mines on the board **/
		for(int mineAmount = 0; mineAmount < 99; mineAmount++)
		{
			int x = (int)(Math.random()*30);
			int y = (int)(Math.random()*16);
			
			if(mineHold.contains(bottom[x][y]) == true)
			{
				mineAmount--;
			}
			else
			{
				mines[x][y] = new JLabel("M");
				bottom[x][y].setBackground(Color.pink);
				
				bottom[x][y].add(mines[x][y]);
				mineHold.add(bottom[x][y]);
			}
		}
		
		/** placing numbers on the board **/
		for(int x = 0; x < 30; x++)
		{
			for(int y = 0; y < 16; y++)
			{
				num = 0;
				if(mineHold.contains(bottom[x][y]) == false)
				{
					if(x == 0)
					{
						if(y == 0)
							mineOrEmptySpaceInfo("M", "234", x, y);
						else if(y == 15)
							mineOrEmptySpaceInfo("M", "456", x, y);
						else
							mineOrEmptySpaceInfo("M", "23456", x, y);
					}
					else if(x == 29)
					{
						if(y == 0)
							mineOrEmptySpaceInfo("M", "012", x, y);
						else if(y == 15)
							mineOrEmptySpaceInfo("M", "670", x, y);
						else
							mineOrEmptySpaceInfo("M", "67012", x, y);
					}
					else
					{
						if(y == 0)
							mineOrEmptySpaceInfo("M", "01234", x, y);
						else if(y == 15)
							mineOrEmptySpaceInfo("M", "45670", x, y);
						else
							mineOrEmptySpaceInfo("M", "01234567", x, y);
					}
					
					if(num > 0)
					{
						mines[x][y] = new JLabel("" + num);
						numHold.add(mines[x][y]);
					}
					else
						mines[x][y] = new JLabel();
					bottom[x][y].add(mines[x][y]);
				}
			}
		}
	}//end createBoard
	
 	private static void mineOrEmptySpaceInfo(String a, String b,int x, int y)
	{
		ArrayList<Integer> choice = new ArrayList<>();
		for(int z = 0; z < b.length(); z++)
		{
			choice.add(Integer.parseInt(b.substring(z,z + 1)));
		}
		
		if(a.equals("M"))
		{
			int count = 0;
			while(count < choice.size())
			{
				switch(choice.get(count))
				{
					case 0 :
						if(mineHold.contains(bottom[x - 1][y]))//top
							num++;
						break;
						
					case 1 :
						if(mineHold.contains(bottom[x - 1][y + 1]))//top right
							num++;
						break;
						
					case 2 :
						if(mineHold.contains(bottom[x][y + 1]))//right
							num++;
						break;
						
					case 3 :
						if(mineHold.contains(bottom[x + 1][y + 1]))//bottom right
							num++;
						break;
						
					case 4 :
						if(mineHold.contains(bottom[x + 1][y]))//bottom
							num++;
						break;
						
					case 5 :
						if(mineHold.contains(bottom[x + 1][y - 1]))//bottom left
							num++;
						break;
						
					case 6 :
						if(mineHold.contains(bottom[x][y - 1]))//left
							num++;
						break;
						
					case 7 :
						if(mineHold.contains(bottom[x - 1][y - 1]))//top left
							num++;
						break;
				}
				count++;
			}
		}//end mines part
		else if(a.equals(""))
		{
			int count = 0;
			while(count < choice.size())
			{
				switch(choice.get(count))
				{
					case 0 :
							map[x - 1][y].setVisible(false);//top
							if(!(emptyHold.contains(mines[x - 1][y])) && mines[x - 1][y].getText().equals(""))
								emptyHold.add(mines[x - 1][y]);
							
						break;
						
					case 1 :
							map[x - 1][y + 1].setVisible(false);//top right
							if(!(emptyHold.contains(mines[x - 1][y + 1])) && mines[x - 1][y + 1].getText().equals(""))
								emptyHold.add(mines[x - 1][y + 1]);
							
						break;
						
					case 2 :
							map[x][y + 1].setVisible(false);//right
							if(!(emptyHold.contains(mines[x][y + 1])) && mines[x][y + 1].getText().equals(""))
								emptyHold.add(mines[x][y + 1]);
							
						break;
						
					case 3 :
							map[x + 1][y + 1].setVisible(false);//bottom right
							if(!(emptyHold.contains(mines[x + 1][y + 1])) && mines[x + 1][y + 1].getText().equals(""))
								emptyHold.add(mines[x + 1][y + 1]);
							
						break;
						
					case 4 :
							map[x + 1][y].setVisible(false);//bottom
							if(!(emptyHold.contains(mines[x + 1][y])) && mines[x + 1][y].getText().equals(""))
								emptyHold.add(mines[x + 1][y]);
							
						break;
						
					case 5 :
							map[x + 1][y - 1].setVisible(false);//bottom left
							if(!(emptyHold.contains(mines[x + 1][y - 1])) && mines[x + 1][y - 1].getText().equals(""))
								emptyHold.add(mines[x + 1][y - 1]);
							
						break;
						
					case 6 :
							map[x][y - 1].setVisible(false);//left
							if(!(emptyHold.contains(mines[x][y - 1])) && mines[x][y - 1].getText().equals(""))
								emptyHold.add(mines[x][y - 1]);
							
						break;
						
					case 7 :
							map[x - 1][y - 1].setVisible(false);//top left
							if(!(emptyHold.contains(mines[x - 1][y - 1])) && mines[x - 1][y - 1].getText().equals(""))
								emptyHold.add(mines[x - 1][y - 1]);
							
						break;
				}
				count++;
			}
		}
		
	}//end mineOrEmptySpaceInfo
 	
 	public static void revealAll(Object[][] a, String b)
 	{
 		for(int x = 0; x < 30;x++)
 		{
 			for(int y = 0; y < 16;y++)
 			{
 				if(mines[x][y].getText().equals(b))
 				{
 					((Component) a[x][y]).setVisible(false);
 				}
 			}
 		}
 	}
 	
 	public static void clearEmptySpace(JLabel a)
 	{
 		if(a.getText().equals(""))
 		{
 			int x = 0;
 	 		int y = 0;
 	 		search:
 	 		for(x = 0; x < 30; x++)
 	 		{
 	 			for(y = 0; y < 16; y++)
 	 			{
 	 				if(a == mines[x][y])
 	 					break search;
 	 			}
 	 		}
 	 		
 	 		if(x == 0)
 			{
 				if(y == 0)
 					mineOrEmptySpaceInfo("", "234", x, y);
 				else if(y == 15)
 					mineOrEmptySpaceInfo("", "456", x, y);
 				else
 					mineOrEmptySpaceInfo("", "23456", x, y);
 			}
 			else if(x == 29)
 			{
 				if(y == 0)
 					mineOrEmptySpaceInfo("", "012", x, y);
 				else if(y == 15)
 					mineOrEmptySpaceInfo("", "670", x, y);
 				else
 					mineOrEmptySpaceInfo("", "67012", x, y);
 			}
 			else
 			{
 				if(y == 0)
 					mineOrEmptySpaceInfo("", "01234", x, y);
 				else if(y == 15)
 					mineOrEmptySpaceInfo("", "45670", x, y);
 				else
 					mineOrEmptySpaceInfo("", "01234567", x, y);
 			}
 		}
 	}//end clearEmptySpace
 	
 	/** Implemented Methods**/
 	
 	public void actionPerformed(ActionEvent e)
	{
		for(int x = 0; x < 30;x++)
		{
			for(int y = 0; y < 16;y++)
			{
				if(e.getSource() == map[x][y])
				{
						map[x][y].setVisible(false);
						if(mines[x][y].getText().equals("M"))
						{
							revealAll(map,"M");
						}
						if(mines[x][y].getText().equals(""))
						{
							emptyHold.add(mines[x][y]);
							for(int a = 0; a < emptyHold.size(); a++)
								clearEmptySpace(emptyHold.get(a));
						}
					clicks++;	
				}
				
			}
		}
	}//end actionPerformed
 	
 	public void mouseClicked(MouseEvent e)
 	{
 		for(int x = 0; x < 30;x++)
		{
			for(int y = 0; y < 16;y++)
			{
				if(e.getSource() == map[x][y])
				{
					if(SwingUtilities.isRightMouseButton(e))
					{
						Color flagged = new Color(214, 138, 89);
						Color unFlagged = new Color(70, 130, 180);
						if(map[x][y].getBackground().equals(unFlagged))
						{
							map[x][y].setBackground(flagged);
							map[x][y].setEnabled(false);
						}
						else if(map[x][y].getBackground().equals(flagged))
						{
							map[x][y].setBackground(unFlagged);
							map[x][y].setEnabled(true);
						}
					}
				}
			}
		}
 	}//end mouseClicked
 	
 	/** Useless Methods **/
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e) {}
}//end Class

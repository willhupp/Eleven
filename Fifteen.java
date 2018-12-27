
/*
William Hupp
Marc Singer
Fifteen.java
COP3252 Final Project 
*/
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
 
//The class is a panel 
public class Fifteen extends JPanel 
{
	//the game board of a 4x4 of buttons
	public JButton[][] buttonArray  = new JButton[4][4];
	//menu buttons
	public static JButton startover;
	public static JButton quit; 
	
	//constructor
    public Fifteen()
	{
    	//grid layout
        setLayout(new GridLayout(4,4));
        //creates a new game, not a restart 
        newGame(true);
		
		//loops through board 
        for (int a = 0; a < 4; a++)
            for (int b = 0; b < 4; b++)
			{
				//adds action listener 
                buttonArray [a][b].addActionListener
				(
                    new ActionListener() 
					{
                        public void actionPerformed(ActionEvent e) 
						{
							//command is row "" column 
                            String command = e.getActionCommand();
							//splits into 2 values seperated by space 
                            String[] indices = command.split(" ");
							//parses values 
                            int rowVal = Integer.parseInt(indices[0]);
                            int colVal = Integer.parseInt(indices[1]);
							//possible move values 
                            int[] row = {rowVal - 1, rowVal, rowVal + 1, rowVal};
                            int[] column = {colVal, colVal + 1, colVal, colVal - 1};
							//loops through move values 
                            for (int i = 0; i < 4; i++) 
							{
								//if valid range on graph for moving 
                                if (row[i] >= 0 && row[i] < 4 && column[i] >= 0 && column[i] < 4) 
								{
									//creates a new button of the swapped value 
                                    JButton button = buttonArray [row[i]][column[i]];
                                   
									//switches text of the swapped values 
                                    if (button.getText().equals(""))
									{
                                        button.setText(buttonArray [rowVal][colVal].getText());
                                        buttonArray [rowVal][colVal].setText("");
                                    }
                                    isWin(buttonArray);
                                }
                                
                            } 
                        } 	
                    }       
			);
		}
		//action listener for buttons 
		startover.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					//restarts game  on new 
					newGame(false);
				}
			}
		);
		quit.addActionListener
		(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					//exits game on quit 
					System.exit(0);
				}
			}
		);
    }
    public void newGame(boolean firstTime)
	{
    	//arraylist of Integer objects for the button values 
        ArrayList list = new ArrayList();
        for (int val = 1; val < 16; val++)
			//adds the values to the arraylist
            list.add(new Integer(val));
		//shuffles the list 
        Collections.shuffle(list);
		//adds 0 for blank use 
        list.add(new Integer(0));
		//loops through 4x4 board 
        int i=0;
        for (int a=0; a < 4; a++)
            for (int b=0; b < 4; b++)
			{
				//gets values for board 
                Integer spot = (Integer)list.get(i);
				i++;
				//if its a brand new game creatres buttons 
                if (firstTime)
				{
                    buttonArray [a][b] = new JGradientButton(spot.intValue() + "");
                    buttonArray[a][b].setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));
					//format for accessing buttons 
                    buttonArray[a][b].setBackground(new Color(182, 155, 76));
                    buttonArray[a][b].setFont(new Font("Impact", Font.PLAIN, 40));

                    buttonArray [a][b].setActionCommand(a + " " + b);
					//adds button array to the panel
                    add(buttonArray [a][b]);
                }
				//restarts the game by resetting the text values 
                else
                    buttonArray [a][b].setText(spot.intValue() + "");
            //	isWin(buttonArray);
            }
        	
			//makes last button empty 
            buttonArray [3][3].setText("");
      
    }
    
    public void isWin(JButton[][] in)
    {	
    	int test=1;
	int flag=0;
    	for(int i=0; i<4; i++){
		for(int x=0; x<3; x++){
			if(buttonArray[i][x].getText().equals(Integer.tostring(test)){
				test++;
			}
			else{flag=1;}
		}
	}	
	if(flag!=1){
                String winmessage= "YOU WIN";
	    	JOptionPane.showMessageDialog(null, winmessage, "YOU WIN ", JOptionPane.INFORMATION_MESSAGE);		
	    	newGame(false);
	}
    }
 
    public static void main(String[] args) 
	{
    	//look and feel
    	try 
        { 
    	    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
    	} 
    	catch(Exception e)
    	{ 
    	}
		//creates a frame 
	    JFrame frame= new JFrame();
	    frame.setTitle("Fifteen");
	    
		//default close 
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//menu bar for buttons added to frame
        JMenuBar bar = new JMenuBar();
        frame.setJMenuBar(bar);
		//adds buttons to bar 
        quit = new JButton("Quit");
        bar.add(quit);
 
        startover = new JButton("New");
        bar.add(startover);
 
		//calls fifteen constructor 
        Fifteen fifteen = new Fifteen();
		//makes the pane the game (Extends panel )
        frame.setContentPane(fifteen);

        //makes visible
        frame.pack();
        frame.setVisible(true);
    }
    //button gradient
    private static final class JGradientButton extends JButton
    {
    	//gets the text on constructor and sets it to the button
        private JGradientButton(String text)
        {
            super(text);
            setContentAreaFilled(false);
        }

        @Override
        //paints gradient 
        protected void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setPaint(new GradientPaint(
                    new Point(0, 0), 
                    getBackground(), 
                    new Point(0, getHeight()/3), 
                    Color.WHITE));
            g2.fillRect(0, 0, getWidth(), getHeight()/3);
            g2.setPaint(new GradientPaint(
                    new Point(0, getHeight()/3), 
                    Color.WHITE, 
                    new Point(0, getHeight()), 
                    getBackground()));
            g2.fillRect(0, getHeight()/3, getWidth(), getHeight());
            g2.dispose();

            super.paintComponent(g);
        }
    }
}

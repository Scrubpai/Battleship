import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class BattleshipGame implements ActionListener{
	//Properties
	JFrame theFrame = new JFrame("Battleship");
	BattleshipPlayPanel playPanel = new BattleshipPlayPanel();
	
	//Help Panel
	BattleshipHelpPanel helpPanel = new BattleshipHelpPanel();
	
	Timer theTimer = new Timer(1000/60, this);
	
	JMenuBar theBar = new JMenuBar();
	JMenu theMenu = new JMenu("Menu");
	JMenuItem theHome = new JMenuItem("Home");
	JMenuItem theHelp = new JMenuItem("Help");
	JMenuItem theQuit = new JMenuItem("Quit");
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			playPanel.repaint();
		}
		
		if(evt.getSource() == theHome){
			System.out.println("Home");
		}else if(evt.getSource() == theHelp){
			System.out.println("Help");
			theFrame.setContentPane(helpPanel);
			theFrame.pack();
			helpPanel.repaint();
			
		}else if(evt.getSource() == theQuit){
			System.out.println("Quit");
			System.exit(1);
		}
	}
	
	//Constructor
	public BattleshipGame(){
		// Panel
		playPanel.setPreferredSize(new Dimension(1280, 720));		
		
		//Add JItems
		theFrame.setJMenuBar(theBar);
		
		theBar.add(theMenu);
		theMenu.add(theHome);
		theMenu.add(theHelp);
		theMenu.add(theQuit);
		theHome.addActionListener(this);
		theHelp.addActionListener(this);
		theQuit.addActionListener(this);
		
		// Frame
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setContentPane(playPanel);
		theFrame.pack();
		theFrame.setVisible(true);	
		theFrame.setResizable(false);
		
		//Bring in the help panel if you click help on the menu
		helpPanel.setLayout(null);
		helpPanel.setPreferredSize(new Dimension(1280, 720));
		
		theTimer.start();
	}

	//Main Method
	public static void main(String[] args){
		new BattleshipGame();
	}



}

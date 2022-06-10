import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class BattleshipGame implements ActionListener{
	//Properties
	JFrame theFrame = new JFrame("Battleship");
	
	//Home Panel
	BattleshipHomePanel homePanel = new BattleshipHomePanel();
	
	//Play Panel
	BattleshipPlayPanel playPanel = new BattleshipPlayPanel();
	
	//Help Panel
	BattleshipHelpPanel helpPanel = new BattleshipHelpPanel();
	
	Timer theTimer = new Timer(1000/60, this);
	
	JMenuBar theBar = new JMenuBar();
	JMenu theMenu = new JMenu("Menu");
	JMenuItem theHelp = new JMenuItem("Help");
	JMenuItem theQuit = new JMenuItem("Quit");
	JMenuItem theHome = new JMenuItem("Home");
	JMenuItem thePlay = new JMenuItem("Play");
	JButton playButton = new JButton("Play");
	JButton helpButton = new JButton("Help");
	JButton quitButton = new JButton("Quit");
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			playPanel.repaint();
		}
		
		if(evt.getSource() == theHelp){
			System.out.println("Help");
			theFrame.setContentPane(helpPanel);
			theFrame.pack();
			helpPanel.repaint();
		}else if(evt.getSource() == theQuit){
			System.out.println("Quit");
			System.exit(1);
		}else if(evt.getSource() == theHome){
			System.out.println("Home");
			theFrame.setContentPane(homePanel);
			theFrame.pack();
			homePanel.repaint();
		}else if(evt.getSource() == thePlay){
			System.out.println("Play");
			theFrame.setContentPane(playPanel);
			theFrame.pack();
			playPanel.repaint();
		}
	}
	
	//Constructor
	public BattleshipGame(){
		// Panel
		homePanel.setPreferredSize(new Dimension(1280, 720));		
		
		//Add JItems
		theFrame.setJMenuBar(theBar);
		
		homePanel.add(playButton);
		theBar.add(theMenu);
		theMenu.add(theHome);
		theMenu.add(thePlay);
		theMenu.add(theHelp);
		theMenu.add(theQuit);
		thePlay.addActionListener(this);
		theHelp.addActionListener(this);
		theQuit.addActionListener(this);
		theHome.addActionListener(this);
		
		// Frame
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setContentPane(homePanel);
		theFrame.pack();
		theFrame.setVisible(true);	
		theFrame.setResizable(false);
		
		//Bring in the help panel if you click help on the menu
		helpPanel.setLayout(null);
		helpPanel.setPreferredSize(new Dimension(1280, 720));
		
		//Bring in the home panel if you click home on the menu
		homePanel.setLayout(null);
		homePanel.setPreferredSize(new Dimension(1280, 720));
		
		//Bring in the play panel if you click play on the menu
		playPanel.setLayout(null);
		playPanel.setPreferredSize(new Dimension(1280, 720));
		
		theTimer.start();
	}

	//Main Method
	public static void main(String[] args){
		new BattleshipGame();
	}



}

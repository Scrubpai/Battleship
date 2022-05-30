import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class BattleshipGame implements ActionListener{
	//Properties
	JFrame theFrame = new JFrame("Battleship");
	BattleshipPanel thePanel = new BattleshipPanel();
	Timer theTimer = new Timer(1000/60, this);
	
	JMenuBar theBar = new JMenuBar();
	JMenu theMenu = new JMenu("Menu");
	JMenuItem theHome = new JMenuItem("Home");
	JMenuItem theHelp = new JMenuItem("Help");
	JMenuItem theQuit = new JMenuItem("Quit");
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			thePanel.repaint();
		}
		
		if(evt.getSource() == theHome){
			System.out.println("Home");
		}else if(evt.getSource() == theHelp){
			System.out.println("Help");
		}else if(evt.getSource() == theQuit){
			System.out.println("Quit");
			System.exit(1);
		}
	}
	
	//Constructor
	public BattleshipGame(){
		// Panel
		thePanel.setPreferredSize(new Dimension(1280, 720));		
		
		//Add JItems
		theFrame.setJMenuBar(theBar);
		
		theBar.add(theMenu);
		theMenu.add(theHome);
		theMenu.add(theHelp);
		theMenu.add(theQuit);
		theHome.addActionListener(this);
		theBar.add(theHelp);
		theHelp.addActionListener(this);
		theBar.add(theQuit);
		theQuit.addActionListener(this);
		
		// Frame
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setContentPane(thePanel);
		theFrame.pack();
		theFrame.setVisible(true);	
		theFrame.setResizable(false);
		theTimer.start();
	}

	//Main Method
	public static void main(String[] args){
		new BattleshipGame();
	}



}

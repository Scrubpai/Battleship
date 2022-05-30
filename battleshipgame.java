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
	JMenu theHome = new JMenu("Home");
	JMenu theHelp = new JMenu("Help");
	JMenu theQuit = new JMenu("Quit");
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			thePanel.repaint();
		}
	}
	
	//Constructor
	public BattleshipGame(){
		thePanel.setPreferredSize(new Dimension(1280, 720));
		theFrame.setContentPane(thePanel);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.pack();
		theFrame.setJMenuBar(theBar);
		theFrame.setVisible(true);	
		theFrame.setResizable(false);
		theTimer.start();
		
		//Add JItems
		theBar.add(theHome);
		theBar.add(theHelp);
		theBar.add(theQuit);
	}

	//Main Method
	public static void main(String[] args){
		new BattleshipGame();
	}



}

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class BattleshipGame implements ActionListener, MouseListener, MouseMotionListener {
	//Properties
	Font font1 = new Font("SansSerif", Font.BOLD, 20);
	JFrame theFrame = new JFrame("Battleship");
	Timer theTimer = new Timer(1000/60, this);
	JMenuBar theBar = new JMenuBar();
	JMenu theMenu = new JMenu("Menu");
	JMenuItem theHelp = new JMenuItem("Help");
	JMenuItem theQuit = new JMenuItem("Quit");
	JMenuItem theHome = new JMenuItem("Home");
	SuperSocketMaster ssm;
	
	//Home Panel
	BattleshipHomePanel homePanel = new BattleshipHomePanel();
	JTextField joinIP = new JTextField();
	JButton hostButton = new JButton("Host Game");
	JButton joinButton = new JButton("Join Game");
	JButton helpButton = new JButton("Help");
	JButton quitButton = new JButton("Quit");
	
	//Play Panel
	BattleshipPlayPanel playPanel = new BattleshipPlayPanel();
	JButton rotateButton = new JButton("Rotate");
	JButton placeButton = new JButton("Place");
	JTextField msgField = new JTextField();
	
	//Help Panel
	BattleshipHelpPanel helpPanel = new BattleshipHelpPanel();	
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer) {
			playPanel.repaint();
		} else if (evt.getSource() == hostButton) {
			ssm = new SuperSocketMaster(9001, this);
			ssm.connect();
			
			theFrame.setContentPane(playPanel);
			theFrame.pack();
		} else if (evt.getSource() == joinButton) {
			String strText = joinIP.getText();
			ssm = new SuperSocketMaster(strText, 9001, this);
			ssm.connect();
			
			theFrame.setContentPane(playPanel);
			theFrame.pack();
		}
		
		if (evt.getSource() == theHelp) {
			System.out.println("Help");
			theFrame.setContentPane(helpPanel);
			theFrame.pack();
			helpPanel.repaint();
		} /*else if(evt.getSource() == theQuit){
			System.out.println("Quit");
			System.exit(1);
		}else if(evt.getSource() == theHome){
			System.out.println("Home");
			theFrame.setContentPane(homePanel);
			theFrame.pack();
			homePanel.repaint();
		} else if(evt.getSource() == thePlay){
			System.out.println("Play");
			theFrame.setContentPane(playPanel);
			theFrame.pack();
			playPanel.repaint();
		}*/
	}
	
	public void mouseDragged(MouseEvent evt) {
	}
	
	public void mouseMoved(MouseEvent evt) {
	}
	
	public void mouseClicked(MouseEvent evt) {
	}
	
	public void mouseEntered(MouseEvent evt) {
	}
	
	public void mouseExited(MouseEvent evt) {
	}
	
	public void mousePressed(MouseEvent evt) {
	}
	
	public void mouseReleased(MouseEvent evt) {
	}
	
	//Constructor
	public BattleshipGame() {		
		// Battleship Play Panel
		playPanel.setLayout(null);
		playPanel.setPreferredSize(new Dimension(1280, 720));
		rotateButton.setFont(font1);
		rotateButton.setBounds(1000, 360, 240, 80);
		placeButton.setFont(font1);
		placeButton.setBounds(1000, 460, 240, 80);
		msgField.setFont(font1);
		msgField.setEditable(false);
		msgField.setBounds(1000, 600, 240, 80);
		playPanel.add(rotateButton);
		playPanel.add(placeButton);
		playPanel.add(msgField);
		
		//Add JItems
		theFrame.setJMenuBar(theBar);
		theBar.add(theMenu);
		theMenu.add(theHome);
		theMenu.add(theHelp);
		theMenu.add(theQuit);
		theHelp.addActionListener(this);
		/*
		theQuit.addActionListener(this);
		theHome.addActionListener(this);*/
		
		// Help Panel
		helpPanel.setLayout(null);
		helpPanel.setPreferredSize(new Dimension(1280, 720));
		
		// Home Panel
		homePanel.setLayout(null);
		homePanel.setPreferredSize(new Dimension(1280, 720));
		joinIP.setBounds(700, 300, 200, 50);
		joinIP.setFont(font1);
		joinButton.setBounds(700, 370, 200, 80);
		joinButton.setFont(font1);
		hostButton.setBounds(380, 330, 200, 80);
		hostButton.setFont(font1);
		helpButton.setBounds(540, 500, 200, 80);
		helpButton.setFont(font1);
		quitButton.setBounds(540, 600, 200, 80);
		quitButton.setFont(font1);
		homePanel.add(joinIP);
		homePanel.add(joinButton);
		homePanel.add(hostButton);
		homePanel.add(helpButton);
		homePanel.add(quitButton);
		hostButton.addActionListener(this);
		joinButton.addActionListener(this);
		quitButton.addActionListener(this);
		
		// Frame
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setContentPane(homePanel);
		theFrame.pack();
		theFrame.setVisible(true);	
		theFrame.setResizable(false);
		
		theTimer.start();
	}

	//Main Method
	public static void main(String[] args) {
		new BattleshipGame();
	}
}

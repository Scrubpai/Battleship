import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class BattleshipGame implements ActionListener{
	//Properties
	Font font1 = new Font("SansSerif", Font.BOLD, 20);
	JFrame theFrame = new JFrame("Battleship");
	Timer theTimer = new Timer(1000/60, this);
	JMenuBar theBar = new JMenuBar();
	JMenu theMenu = new JMenu("Menu");
	JMenuItem theHelp = new JMenuItem("Help");
	JMenuItem theQuit = new JMenuItem("Quit");
	JMenuItem theHome = new JMenuItem("Home");
	JMenuItem thePlay = new JMenuItem("Play");
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
	
	//Help Panel
	BattleshipHelpPanel helpPanel = new BattleshipHelpPanel();	
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer) {
			playPanel.repaint();
		} else if (evt.getSource() == hostButton) {
			playPanel.blnPlayer1 = true;
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
	
	//Constructor
	public BattleshipGame() {
		//Add JItems
		theFrame.setJMenuBar(theBar);
		theBar.add(theMenu);
		theMenu.add(theHome);
		theMenu.add(thePlay);
		theMenu.add(theHelp);
		theMenu.add(theQuit);
		theHelp.addActionListener(this);
		/*thePlay.addActionListener(this);
		theQuit.addActionListener(this);
		theHome.addActionListener(this);*/
		
		//Bring in the help panel if you click help on the menu
		helpPanel.setLayout(null);
		helpPanel.setPreferredSize(new Dimension(1280, 720));
		
		//Bring in the home panel if you click home on the menu
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
		
		//Bring in the play panel if you click play on the menu
		playPanel.setLayout(null);
		playPanel.setPreferredSize(new Dimension(1280, 720));
		
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

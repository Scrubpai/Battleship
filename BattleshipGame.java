import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class BattleshipGame implements ActionListener, MouseListener, MouseMotionListener {
	// Properties
	Font font1 = new Font("SansSerif", Font.BOLD, 20);
	JFrame theFrame = new JFrame("Battleship");
	Timer theTimer = new Timer(1000/60, this);
	Timer animTimer = new Timer(1000/10, this);
	JMenuBar theBar = new JMenuBar();
	JMenu theMenu = new JMenu("Menu");
	JMenuItem theHelp = new JMenuItem("Help");
	JMenuItem theQuit = new JMenuItem("Quit");
	JMenuItem theHome = new JMenuItem("Home");
	SuperSocketMaster ssm;
	
	// Home Panel
	BattleshipHomePanel homePanel = new BattleshipHomePanel();
	JTextField joinIP = new JTextField();
	JButton hostButton = new JButton("Host Game");
	JButton joinButton = new JButton("Join Game");
	JButton helpButton = new JButton("Help");
	JButton quitButton = new JButton("Quit");
	JButton themesButton = new JButton("Themes");

	
	// Play Panel
	BattleshipPlayPanel playPanel = new BattleshipPlayPanel();
	JButton rotateButton = new JButton("Rotate");
	JButton readyButton = new JButton("Ready");
	JTextField yourMsgField = new JTextField();
	JTextField opponentMsgField = new JTextField();
	
	// Help Panel
	BattleshipHelpPanel helpPanel = new BattleshipHelpPanel();
	BattleshipHelpPanel2 helpPanel2 = new BattleshipHelpPanel2();
	JButton nextButton1 = new JButton("Next");
	JTextField step1 = new JTextField("Demo Step 1: Drag and Drop your ships onto your map");
	
	//Themes Panel
	BattleshipThemesPanel themesPanel = new BattleshipThemesPanel();
	JTextField themesField = new JTextField();
	JButton  homeButton = new JButton("Back Home");
	
	
	// Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer && playPanel.blnPlayingAnimation == false) {
			if (theFrame.getContentPane() == playPanel) {
				playPanel.repaint();
			} else if (theFrame.getContentPane() == helpPanel2) {
				helpPanel2.repaint();
			}
		} else if (evt.getSource() == animTimer) {
			for (int intAnimation=1; intAnimation<=3; intAnimation++) {
				if (playPanel.blnPlayAnimation[intAnimation] == true) {
					playPanel.repaint();
					playPanel.intAnimCount++;
						
					if(playPanel.intAnimCount > playPanel.intMaxAnimationSprites[intAnimation]){
						playPanel.blnPlayAnimation[intAnimation] = false;
						playPanel.intAnimCount = 0;
					}
					
					break; // So it doesn't play 2 animations at the same time
				}
			}
			
			if (playPanel.blnPlayAnimation[1] == false && playPanel.blnPlayAnimation[2] == false && playPanel.blnPlayAnimation[3] == false) {
				playPanel.blnPlayingAnimation = false;
				
				if (playPanel.intSunkValue != 0) {
					playPanel.intOpponentGrid[playPanel.intSunkRow][playPanel.intSunkCol] = playPanel.intSunkValue;
					playPanel.intSunkRow = playPanel.intSunkCol = playPanel.intSunkValue = 0;
				}
			}
		}
		else if (evt.getSource() == hostButton) {
			ssm = new SuperSocketMaster(9001, this);
			ssm.connect();
			playPanel.blnYourTurn = true; // Server goes first
			
			theFrame.setContentPane(playPanel);
			theFrame.pack();
		} else if (evt.getSource() == joinButton) {
			String strText = joinIP.getText();
			ssm = new SuperSocketMaster(strText, 9001, this);
			ssm.connect();
			
			theFrame.setContentPane(playPanel);
			theFrame.pack();
		} else if(evt.getSource() == quitButton){
			System.out.println("Quit");
			System.exit(1);			
		} else if (evt.getSource() == rotateButton) {
			playPanel.blnHorizontal = !playPanel.blnHorizontal;
			
			for (int intShip=1; intShip<=5; intShip++) {
				if (playPanel.intPlaced[intShip] == 0) {
					if (playPanel.blnHorizontal == false) {
						playPanel.intPositions[intShip][0] = playPanel.intDefaultPositionsV[intShip][0];
						playPanel.intPositions[intShip][1] = playPanel.intDefaultPositionsV[intShip][1];					
					} else {
						playPanel.intPositions[intShip][0] = playPanel.intDefaultPositionsH[intShip][0];
						playPanel.intPositions[intShip][1] = playPanel.intDefaultPositionsH[intShip][1];	
					}
				}
			}
		} else if (evt.getSource() == readyButton) {
			if (playPanel.intPlaced[1] == 0 || playPanel.intPlaced[2] == 0 || playPanel.intPlaced[3] == 0 || playPanel.intPlaced[4] == 0 || playPanel.intPlaced[5] == 0) {
				return;
			}
			
			ssm.sendText("READY");
			yourMsgField.setText("READY");
			
			if (opponentMsgField.getText().equals("READY")) {
				playPanel.blnStartGame = true;
				System.out.println(playPanel.blnStartGame);
				System.out.println(playPanel.blnYourTurn);
			}
		} else if (evt.getSource() == ssm) {
			String strText = ssm.readText();
			System.out.println("THEMSG: " + strText);
			if (strText.equals("READY")) {
				opponentMsgField.setText("READY");
				if (yourMsgField.getText().equals("READY")) {
					playPanel.blnStartGame = true;
				}
			} else if (strText.equals("HIT")) {
				playPanel.blnHit = true;
				playPanel.blnYourTurn = true;
				playPanel.blnPlayAnimation[2] = true; // Explosion
				opponentMsgField.setText("HIT");
				String strLocation = yourMsgField.getText();
				int intCol = strLocation.charAt(0) - 'A' + 1;
				int intRow = Integer.parseInt(strLocation.substring(1, 2));
				if (strLocation.length() == 3) {
					intRow *= 10;
				}
				
				playPanel.intOpponentGrid[intRow][intCol] = 1; // 1 = Hit
			} else if (strText.equals("MISS")) {
				playPanel.blnHit = false;
				playPanel.blnYourTurn = false;
				playPanel.blnPlayAnimation[3] = true; // Splash
				opponentMsgField.setText("MISS");
				
				String strLocation = yourMsgField.getText();
				int intCol = strLocation.charAt(0) - 'A' + 1;
				int intRow = Integer.parseInt(strLocation.substring(1, 2));
				if (strLocation.length() == 3) {
					intRow *= 10;
				}
				
				playPanel.intOpponentGrid[intRow][intCol] = 2; // 2 = Miss
			} else if (strText.length() >= 4 && strText.substring(0, 4).equals("Sunk")) {
				playPanel.blnHit = true;
				playPanel.blnYourTurn = true;
				playPanel.blnPlayAnimation[2] = true; // Explosion
				opponentMsgField.setText("HIT");
				String strLocation = yourMsgField.getText();
				int intCol = strLocation.charAt(0) - 'A' + 1;
				int intRow = Integer.parseInt(strLocation.substring(1, 2));
				if (strLocation.length() == 3) {
					intRow *= 10;
				}
				playPanel.intOpponentGrid[intRow][intCol] = 1; // 1 = Hit
				
				String strSub = strText.substring(5);
				String strArray[] = strSub.split(" ");
				int intShip = Integer.parseInt(strArray[0]);
				int intSunkRow = Integer.parseInt(strArray[1]);
				int intSunkCol = Integer.parseInt(strArray[2]);
				int intOrientation = Integer.parseInt(strArray[3]); // 1 - Vertical, 2 - Horizontal
				System.out.println(strSub + " " + intShip + " " + intSunkRow + " " + intSunkCol + " " + intOrientation);
				
				playPanel.intSunkRow = intSunkRow;
				playPanel.intSunkCol = intSunkCol;
				playPanel.intSunkValue = intShip * 10 + intOrientation;
			} else if (strText.equals("You Win")) {
				playPanel.intWinLose = 1;
				yourMsgField.setText("You win");
				opponentMsgField.setText("You lose");
			} else {
				opponentMsgField.setText(strText);
				int intCol = strText.charAt(0) - 'A' + 1;
				int intRow = Integer.parseInt(strText.substring(1, 2));
				if (strText.length() == 3) {
					intRow *= 10;
				}
				
				if (playPanel.intYourGrid[intRow][intCol] >= 1 && playPanel.intYourGrid[intRow][intCol] <= 5) {
					int intShip = playPanel.intYourGrid[intRow][intCol];
					playPanel.intYourGrid[intRow][intCol] *= 10;
					playPanel.intShipHits[intShip]++; 
					if(playPanel.intSizes[intShip] == playPanel.intShipHits[intShip]) {
						playPanel.intShipsSunk++;
						int intSunkRow = (int)Math.floor(1.0 * (playPanel.intPositions[intShip][1] - 80) / 64) + 1;
						int intSunkCol = (int)Math.floor(1.0 * (playPanel.intPositions[intShip][0] - 80) / 64) + 1;
						System.out.println("Sunk "+ intShip + " " + intSunkRow + " " + intSunkCol + " " + playPanel.intPlaced[intShip]);
						ssm.sendText("Sunk "+ intShip + " " + intSunkRow + " " + intSunkCol + " " + playPanel.intPlaced[intShip]);
						yourMsgField.setText("HIT");
						
						if(playPanel.intShipsSunk >= 5)	{
							ssm.sendText("You Win");
							yourMsgField.setText("You Lose");
							opponentMsgField.setText("You Win");
							playPanel.intWinLose = 2;
						}
					} else {
						ssm.sendText("HIT");
						yourMsgField.setText("HIT");
					}
				} else {
					playPanel.intYourGrid[intRow][intCol] = -1; // 1 Means Miss
					ssm.sendText("MISS");
					yourMsgField.setText("MISS");
					playPanel.blnYourTurn = true;
				}
			}
		} else if(evt.getSource() == helpButton) {
			theFrame.setContentPane(helpPanel);
			theFrame.pack();
			helpPanel.repaint();
		} else if (evt.getSource() == nextButton1) {
			theFrame.setContentPane(helpPanel2);
			theFrame.pack();
			helpPanel2.repaint();
		}
		
		
		
		if (evt.getSource() == theHelp) {
			theFrame.setContentPane(helpPanel);
			theFrame.pack();
			helpPanel.repaint();
		} else if(evt.getSource() == theQuit){
			System.exit(1);
		}else if(evt.getSource() == theHome){
			theFrame.setContentPane(homePanel);
			theFrame.pack();
			homePanel.repaint();
		}else if(evt.getSource() == themesButton){
			theFrame.setContentPane(themesPanel);
			theFrame.pack();
			themesPanel.repaint();
		}else if(evt.getSource() == homeButton){
			theFrame.setContentPane(homePanel);
			theFrame.pack();
			homePanel.repaint();
		}
	}
	
	public void mouseDragged(MouseEvent evt) {
		if (theFrame.getContentPane() == playPanel) {
			if (playPanel.intShipSelected == 0) {
				return;
			}
			
			playPanel.intPositions[playPanel.intShipSelected][0] = evt.getX() - 32;
			playPanel.intPositions[playPanel.intShipSelected][1] = evt.getY() - 32;
		} else if (theFrame.getContentPane() == helpPanel2) {
			if (helpPanel2.blnSelected == true) {
				helpPanel2.intPositionX = evt.getX() - 32;
				helpPanel2.intPositionY = evt.getY() - 32;
			}
		}
	}
	
	public void mouseMoved(MouseEvent evt) {
	}
	
	public void mouseClicked(MouseEvent evt) {
		int intRow = calcRow(evt.getY());
		int intCol = calcRow(evt.getX());
		System.out.println(intRow + " " + intCol);
		
		if (intRow >= 1 && intRow <= 10 && intCol >= 1 && intCol <= 10 && playPanel.blnYourTurn == true && playPanel.blnStartGame == true && playPanel.blnPlayingAnimation == false) {
			if (playPanel.intOpponentGrid[intRow][intCol] != 0) {
				return;
			}
			String strLetter = playPanel.strLetters[intCol];
			String strNumber = Integer.toString(intRow);
			
			playPanel.blnPlayingAnimation = true;
			playPanel.intAnimationRow = intRow;
			playPanel.intAnimationCol = intCol;
			playPanel.blnPlayAnimation[1] = true;
			
			yourMsgField.setText(strLetter + strNumber);
			ssm.sendText(strLetter + strNumber);
		}
		
		if (playPanel.blnStartGame == true && playPanel.blnPlayingAnimation == false && playPanel.blnGameOver == true) {
			if (evt.getX() > 830 && evt.getX() < 980 && evt.getY() > 177 && evt.getY() < 317) {
				System.exit(1);
			}
		}
	}
	
	public void mouseEntered(MouseEvent evt) {
	}
	
	public void mouseExited(MouseEvent evt) {
	}
	
	public void startGame() {
		
	}
	
	public void mousePressed(MouseEvent evt) {
		if (theFrame.getContentPane() == playPanel) {
			for (int intShip=1; intShip<=5; intShip++) {
				if (playPanel.intPlaced[intShip] == 0) {
					int intX = evt.getX();
					int intY = evt.getY();
					if (playPanel.blnHorizontal == false) {
						int intC1 = playPanel.intDefaultPositionsV[intShip][0];
						int intR1 = playPanel.intDefaultPositionsV[intShip][1];
						int intC2 = playPanel.intDefaultPositionsV[intShip][2];
						int intR2 = playPanel.intDefaultPositionsV[intShip][3];
						if (intX>=intC1 && intX<=intC2 && intY>=intR1 && intY<=intR2) {
							playPanel.intShipSelected = intShip;
							playPanel.intPlaced[playPanel.intShipSelected] = 1;	
							break;
						}
					} else {
						int intC1 = playPanel.intDefaultPositionsH[intShip][0];
						int intR1 = playPanel.intDefaultPositionsH[intShip][1];
						int intC2 = playPanel.intDefaultPositionsH[intShip][2];
						int intR2 = playPanel.intDefaultPositionsH[intShip][3];
						if (intX>=intC1 && intX<=intC2 && intY>=intR1 && intY<=intR2) {
							playPanel.intShipSelected = intShip;
							playPanel.intPlaced[playPanel.intShipSelected] = 2;
							break;
						}
					}
				}
			}
		} else if (theFrame.getContentPane() == helpPanel2) {
			System.out.println("HERE");
			int intX = evt.getX();
			int intY = evt.getY();
			if (helpPanel2.intPositionX == 800 && helpPanel2.intPositionY == 300 && intX >= 800 && intX <= 864 && intY >= 300 && intY <= 492) {
				System.out.println("ADTY ");
				helpPanel2.blnSelected = true;
			}
		}
	}
	
	public void mouseReleased(MouseEvent evt) {
		if (theFrame.getContentPane() == playPanel) {
			int intRow = calcRow(evt.getY());
			int intCol = calcCol(evt.getX());
			int intSize = playPanel.intSizes[playPanel.intShipSelected];
			
			System.out.println(intRow + " " + intCol + " " + intSize);
			
			if (intRow < 1 || intRow > 10 || intCol < 1 || intCol > 10 || (playPanel.blnHorizontal == false && intRow + intSize - 1 > 10) || (playPanel.blnHorizontal == true && intCol + intSize - 1 > 10) || !isPossible(intRow, intCol, intSize, playPanel.blnHorizontal)) {
				// Out of bounds
				playPanel.intPlaced[playPanel.intShipSelected] = 0;
				if (playPanel.blnHorizontal == false) {
					playPanel.intPositions[playPanel.intShipSelected][0] = playPanel.intDefaultPositionsV[playPanel.intShipSelected][0];
					playPanel.intPositions[playPanel.intShipSelected][1] = playPanel.intDefaultPositionsV[playPanel.intShipSelected][1];					
				} else {
					playPanel.intPositions[playPanel.intShipSelected][0] = playPanel.intDefaultPositionsH[playPanel.intShipSelected][0];
					playPanel.intPositions[playPanel.intShipSelected][1] = playPanel.intDefaultPositionsH[playPanel.intShipSelected][1];	
				}
				return;
			}
			
			placeShip(intRow, intCol, intSize, playPanel.blnHorizontal, playPanel.intShipSelected);
			playPanel.intPositions[playPanel.intShipSelected][0] = (intCol - 1) * 64 + 80;
			playPanel.intPositions[playPanel.intShipSelected][1] = (intRow - 1) * 64 + 80;
			
			printYourGrid();
			System.out.println(playPanel.intPlaced[playPanel.intShipSelected]);
			System.out.println();

			playPanel.intShipSelected = 0;
		} else if (theFrame.getContentPane() == helpPanel2) {
			int intRow = calcRow(evt.getY());
			int intCol = calcCol(evt.getX());
			
			if (helpPanel2.blnSelected == true && (intRow < 1 || intRow > 10 || intCol < 1 || intCol > 10 || intRow + 3 - 1 > 10)) {
				helpPanel2.intPositionX = 800;
				helpPanel2.intPositionY = 300;
			} else if (helpPanel2.blnSelected == true) {
				helpPanel2.intPositionX = (intCol - 1) * 64 + 80;
				helpPanel2.intPositionY = (intRow - 1) * 64 + 80;
			}
			
			helpPanel2.blnSelected = false;
		}
		
	}
	
	public boolean isPossible(int intRow, int intCol, int intSize, boolean blnHorizontal) {
		if (blnHorizontal == false) {
			for (int intCount=0; intCount<intSize; intCount++) {
				if (playPanel.intYourGrid[intRow+intCount][intCol] != 0) {
					System.out.println(playPanel.intYourGrid[intRow+intCount][intCol]);
					return false;
				}
			}
		} else {
			for (int intCount=0; intCount<intSize; intCount++) {
				if (playPanel.intYourGrid[intRow][intCol+intCount] != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void placeShip(int intRow, int intCol, int intSize, boolean blnHorizontal, int intShip) {
		if (blnHorizontal == false) {
			for (int intCount=0; intCount<intSize; intCount++) {
				playPanel.intYourGrid[intRow+intCount][intCol] = intShip;
			}
		} else {
			for (int intCount=0; intCount<intSize; intCount++) {
				playPanel.intYourGrid[intRow][intCol+intCount] = intShip;
			}
		}
	}
	
	public int calcRow(int intY) {
		return (int)Math.floor(1.0 * (intY - 80) / 64) + 1;
	}
	
	public int calcCol(int intX) {
		return (int)Math.floor(1.0 * (intX - 80) / 64) + 1;
	}
	
	public void printYourGrid() {
		for (int intRow=1; intRow<=10; intRow++) {
			for (int intCol=1; intCol<=10; intCol++) {
				System.out.print(playPanel.intYourGrid[intRow][intCol] + " ");
			}
			System.out.println();
		}
	}
	
	public void printOpponentGrid() {
		for (int intRow=1; intRow<=10; intRow++) {
			for (int intCol=1; intCol<=10; intCol++) {
				System.out.print(playPanel.intOpponentGrid[intRow][intCol] + " ");
			}
			System.out.println();
		}
	}
	
	//Constructor
	public BattleshipGame() {		
		// Battleship Play Panel
		playPanel.setLayout(null);
		playPanel.setPreferredSize(new Dimension(1280, 720));
		playPanel.addMouseMotionListener(this);
		playPanel.addMouseListener(this);
		rotateButton.setFont(font1);
		rotateButton.setBounds(1000, 360, 240, 80);
		rotateButton.addActionListener(this);
		readyButton.setFont(font1);
		readyButton.setBounds(1000, 460, 240, 80);
		readyButton.addActionListener(this);
		yourMsgField.setFont(font1);
		yourMsgField.setEditable(false);
		yourMsgField.setBounds(1000, 560, 240, 60);
		opponentMsgField.setFont(font1);
		opponentMsgField.setEditable(false);
		opponentMsgField.setBounds(1000, 640, 240, 60);
		playPanel.add(rotateButton);
		playPanel.add(readyButton);
		playPanel.add(yourMsgField);
		playPanel.add(opponentMsgField);
		
		//Add JItems
		theFrame.setJMenuBar(theBar);
		theBar.add(theMenu);
		theMenu.add(theHome);
		theMenu.add(theHelp);
		theMenu.add(theQuit);
		theHelp.addActionListener(this);
		theQuit.addActionListener(this);
		theHome.addActionListener(this);
		
		// Help Panel
		helpPanel.setLayout(null);
		helpPanel.setPreferredSize(new Dimension(1280, 720));
		nextButton1.setBounds(540, 650, 200, 50);
		nextButton1.setFont(font1);
		nextButton1.addActionListener(this);
		helpPanel.add(nextButton1);
		
		helpPanel2.setLayout(null);
		helpPanel2.setPreferredSize(new Dimension(1280, 720));
		helpPanel2.addMouseListener(this);
		helpPanel2.addMouseMotionListener(this);
		step1.setBounds(300, 600, 600, 50);
		step1.setFont(font1);
		step1.setHorizontalAlignment(JTextField.CENTER);
		step1.setEditable(false);
		helpPanel2.add(step1);
		
		/*help3Button.setBounds(1000, 600, 200, 80);
		help3Button.setFont(font1);
		help2Panel.add(help3Button);
		help3Button.addActionListener(this);
		
		help3Panel.setLayout(null);
		help3Panel.setPreferredSize(new Dimension(1280, 720));
		help4Button.setBounds(1000, 600, 200, 80);
		help4Button.setFont(font1);
		help3Panel.add(help4Button);
		help4Button.addActionListener(this);
		
		help4Panel.setLayout(null);
		help4Panel.setPreferredSize(new Dimension(1280, 720));
		help5Button.setBounds(1000, 600, 200, 80);
		help5Button.setFont(font1);
		help4Panel.add(help5Button);
		help5Button.addActionListener(this);
		
		help5Panel.setLayout(null);
		help5Panel.setPreferredSize(new Dimension(1280, 720));
		help6Button.setBounds(1000, 600, 200, 80);
		help6Button.setFont(font1);
		help5Panel.add(help6Button);
		help6Button.addActionListener(this);*/
		
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
		themesButton.setBounds(1000, 600, 200, 80);
		themesButton.setFont(font1);
		homePanel.add(joinIP);
		homePanel.add(joinButton);
		homePanel.add(hostButton);
		homePanel.add(helpButton);
		homePanel.add(quitButton);
		homePanel.add(themesButton);
		hostButton.addActionListener(this);
		joinButton.addActionListener(this);
		helpButton.addActionListener(this);
		quitButton.addActionListener(this);
		themesButton.addActionListener(this);
		
		
		//Themes Panel
		themesPanel.setLayout(null);
		themesPanel.setPreferredSize(new Dimension(1280, 720));
		themesPanel.add(homeButton);
		homeButton.setBounds(1000, 600, 200, 80);
		homeButton.setFont(font1);
		homeButton.addActionListener(this);
		
		// Frame
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setContentPane(homePanel);
		theFrame.pack();
		theFrame.setVisible(true);	
		theFrame.setResizable(false);
		
		theTimer.start();
		animTimer.start();
	}

	//Main Method
	public static void main(String[] args) {
		new BattleshipGame();
	}
}

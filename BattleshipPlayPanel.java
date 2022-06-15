import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Font.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BattleshipPlayPanel extends JPanel{
	// Properties
	boolean blnHorizontal = false;
	String strLetters[] = new String[11];
	int intDefaultPositionsV[][] = new int[6][4];
	int intDefaultPositionsH[][] = new int[6][4];
	int intPositions[][] = new int[6][2]; // [0] - width, [1] - height
	int intSizes[] = new int[6];
	int intPlaced[] = new int[6]; // 0 - Not Placed, 1 - Vertical, 2 - Horizontal
	int intYourGrid[][] = new int[11][11];
	int intOpponentGrid[][] = new int[11][11];
	int intShipSelected = 0;
	int intHealth = 17;
	boolean blnStartGame = false;
	boolean blnYourTurn = false; // Server goes first
	boolean blnHit = false;
	
	int intAnimationRow = 0;
	int intAnimationCol = 0;
	int intAnimCount = 0;
	boolean blnPlayingAnimation = false;
	boolean blnPlayAnimation[] = new boolean[4]; // 1 - Bomb, 2 - Explosion, 3 - Splash
	int intMaxAnimationSprites[] = new int[4]; // 1 - Bomb, 2 - Explosion, 3 - Splash
	
	int intShipHits[] = new int[6];
	int intShipsSunk = 0;
	int intWinLose = 0; // 1 - win 2 - lose.
	
	// Buffering Images
	BufferedImage imgLetters = null;
	BufferedImage imgNumbers = null;
	BufferedImage imgWater = null;
	BufferedImage imgBox = null;
	BufferedImage imgMinimap = null;
	BufferedImage imgPause = null;
	BufferedImage imgWin = null;
	BufferedImage imgLose = null;
	BufferedImage imgShipsV[] = new BufferedImage[6];
	BufferedImage imgShipsH[] = new BufferedImage[6];
	BufferedImage imgShipsMiniV[] = new BufferedImage[6];
	BufferedImage imgShipsMiniH[] = new BufferedImage[6];
	BufferedImage imgShipsSunkV[] = new BufferedImage[6];
	BufferedImage imgShipsSunkH[] = new BufferedImage[6];
	BufferedImage imgSprite = null; // For animation
	BufferedImage imgMinimapHit = null;
	BufferedImage imgMinimapMiss = null;
	BufferedImage imgBattleshipHit = null;
	BufferedImage imgBattleshipMiss = null;
	
	// Methods
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		
		// Draw Images
		g.drawImage(imgPause, 0, 0, null);
		g.drawImage(imgLetters, 80, 0, null);
		g.drawImage(imgNumbers, 0, 80, null);
		g.drawImage(imgWater, 80, 80, null);
		g.drawImage(imgBox, 720, 0, null);
		g.drawImage(imgMinimap, 960, 0, null);
		
		// Draw Ships
		if (blnStartGame == false) {
			for (int intShip=1; intShip<=5; intShip++) {
				if (intPlaced[intShip] == 0) {
					if (blnHorizontal == false) {
						g.drawImage(imgShipsV[intShip], intDefaultPositionsV[intShip][0], intDefaultPositionsV[intShip][1], null);
					} else {
						g.drawImage(imgShipsH[intShip], intDefaultPositionsH[intShip][0], intDefaultPositionsH[intShip][1], null);
					}
				} else if (intPlaced[intShip] == 1) {
					g.drawImage(imgShipsV[intShip], intPositions[intShip][0], intPositions[intShip][1], null);
				} else {
					g.drawImage(imgShipsH[intShip], intPositions[intShip][0], intPositions[intShip][1], null);
				}
			}
		} else {
			for (int intShip=1; intShip<=5; intShip++) {
				int intRow = (int)Math.floor(1.0 * (intPositions[intShip][1] - 80) / 64) + 1;
				int intCol = (int)Math.floor(1.0 * (intPositions[intShip][0] - 80) / 64) + 1;
				
				if (intPlaced[intShip] == 1) {
					g.drawImage(imgShipsMiniV[intShip], 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
				} else {
					g.drawImage(imgShipsMiniH[intShip], 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
				}
			}
			
			for (int intRow=1; intRow<=10; intRow++) {
				for (int intCol=1; intCol<=10; intCol++) {
					// Radar (Your Ships)
					if (intYourGrid[intRow][intCol] >= 10) {
						g.drawImage(imgMinimapHit, 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
					} else if (intYourGrid[intRow][intCol] == -1) {
						g.drawImage(imgMinimapMiss, 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
					}
					
					// Map (Guessing Your Opponent's Ships)
					if (intOpponentGrid[intRow][intCol] == 1) {
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						
					} else if (intOpponentGrid[intRow][intCol] == 2) {
						g.drawImage(imgBattleshipMiss, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					}
				}
			}
		}
		
		if (blnPlayAnimation[1] == true) {
			try {
				imgSprite = ImageIO.read(new File("Assets/Sprites/BattleshipBomb"+Integer.toString(intAnimCount)+".png"));
			} catch (IOException e) {
				System.out.println("Error: bomb animation");
			}
			g.drawImage(imgSprite, (intAnimationCol - 1) * 64 + 80, (intAnimationRow - 1) * 64 + 80, null);
		} else if (blnPlayAnimation[2] == true) {
			try {
				imgSprite = ImageIO.read(new File("Assets/Sprites/BattleshipHitExplosion"+Integer.toString(intAnimCount)+".png"));
			} catch (IOException e) {
				System.out.println("Error: bomb animation");
			}
				// Draw Win/Lose
			if(intWinLose == 1) {
				g.drawImage(imgWin, 0,0, null);
			} else if(intWinLose == 2) {
				g.drawImage(imgLose, 0,0, null);
			}
			g.drawImage(imgSprite, (intAnimationCol - 1) * 64 + 80, (intAnimationRow - 1) * 64 + 80, null);
		} else if (blnPlayAnimation[3] == true) {
			try {
				imgSprite = ImageIO.read(new File("Assets/Sprites/BattleshipSplash"+Integer.toString(intAnimCount)+".png"));
			} catch (IOException e) {
				System.out.println("Error: bomb animation");
			}
			g.drawImage(imgSprite, (intAnimationCol - 1) * 64 + 80, (intAnimationRow - 1) * 64 + 80, null);
		}
		
		// Create Lines to split up stuff
		g.setColor(Color.WHITE);
		g.fillRect(80, 0, 1, 720);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 80, 720, 1);
		
		g.setColor(Color.WHITE);
		g.fillRect(720, 0, 1, 720);
		
		g.setColor(Color.WHITE);
		g.fillRect(960, 0, 1, 720);
		
		g.setColor(Color.WHITE);
		g.fillRect(960, 320, 320, 1);
		
		// Draw Win/Lose
		if(intWinLose == 1)
		{
			g.drawImage(imgWin, 0,0, null);
		}
		else if(intWinLose == 2)
		{
			g.drawImage(imgLose, 0,0, null);
		}
	}
	
	
	// Constructor
	public BattleshipPlayPanel(){
		super();
		intSizes[1] = 2;
		intSizes[2] = 3;
		intSizes[3] = 3;
		intSizes[4] = 4;
		intSizes[5] = 5;
		
		intDefaultPositionsV[1][0] = intPositions[1][0] = 750;
		intDefaultPositionsV[1][1] = intPositions[1][1] = 20;
		intDefaultPositionsV[1][2] = intDefaultPositionsV[1][0]+64;
		intDefaultPositionsV[1][3] = intDefaultPositionsV[1][1]+128;
		
		intDefaultPositionsV[2][0] = intPositions[2][0] = 850;
		intDefaultPositionsV[2][1] = intPositions[2][1] = 20;
		intDefaultPositionsV[2][2] = intDefaultPositionsV[2][0]+64;
		intDefaultPositionsV[2][3] = intDefaultPositionsV[2][1]+192;
		
		intDefaultPositionsV[3][0] = intPositions[3][0] = 750;
		intDefaultPositionsV[3][1] = intPositions[3][1] = 170;
		intDefaultPositionsV[3][2] = intDefaultPositionsV[3][0]+64;
		intDefaultPositionsV[3][3] = intDefaultPositionsV[3][1]+192;
		
		intDefaultPositionsV[4][0] = intPositions[4][0] = 850;
		intDefaultPositionsV[4][1] = intPositions[4][1] = 270;
		intDefaultPositionsV[4][2] = intDefaultPositionsV[4][0]+64;
		intDefaultPositionsV[4][3] = intDefaultPositionsV[4][1]+256;
		
		intDefaultPositionsV[5][0] = intPositions[5][0] = 750;
		intDefaultPositionsV[5][1] = intPositions[5][1] = 380;
		intDefaultPositionsV[5][2] = intDefaultPositionsV[5][0]+64;
		intDefaultPositionsV[5][3] = intDefaultPositionsV[5][1]+320;
		
		intDefaultPositionsH[1][0] = 720;
		intDefaultPositionsH[1][1] = 100;
		intDefaultPositionsH[1][2] = intDefaultPositionsH[1][0]+128;
		intDefaultPositionsH[1][3] = intDefaultPositionsH[1][1]+64;
		
		intDefaultPositionsH[2][0] = 720;
		intDefaultPositionsH[2][1] = 200;
		intDefaultPositionsH[2][2] = intDefaultPositionsH[2][0]+192;
		intDefaultPositionsH[2][3] = intDefaultPositionsH[2][1]+64;
		
		intDefaultPositionsH[3][0] = 720;
		intDefaultPositionsH[3][1] = 300;
		intDefaultPositionsH[3][2] = intDefaultPositionsH[3][0]+192;
		intDefaultPositionsH[3][3] = intDefaultPositionsH[3][1]+64;
		
		intDefaultPositionsH[4][0] = 720;
		intDefaultPositionsH[4][1] = 400;
		intDefaultPositionsH[4][2] = intDefaultPositionsH[4][0]+256;
		intDefaultPositionsH[4][3] = intDefaultPositionsH[4][1]+64;
		
		intDefaultPositionsH[5][0] = 720;
		intDefaultPositionsH[5][1] = 500;
		intDefaultPositionsH[5][2] = intDefaultPositionsH[5][0]+320;
		intDefaultPositionsH[5][3] = intDefaultPositionsH[5][1]+64;
		
		strLetters[1] = "A";
		strLetters[2] = "B";
		strLetters[3] = "C";
		strLetters[4] = "D";
		strLetters[5] = "E";
		strLetters[6] = "F";
		strLetters[7] = "G";
		strLetters[8] = "H";
		strLetters[9] = "I";
		strLetters[10] = "J";
		
		intMaxAnimationSprites[1] = 9;
		intMaxAnimationSprites[2] = 5;
		intMaxAnimationSprites[3] = 7;
		
		// Try Catch Images
		try{
			imgPause = ImageIO.read(new File("Assets/Sprites/BattleshipPause.png"));
		}catch(IOException e){
			System.out.println("Error: imgPause");
		}
		
		try{
			imgWin = ImageIO.read(new File("Assets/Sprites/Win.png"));
		}catch(IOException e){
			System.out.println("Error: imgWin");
		}
		
		try{
			imgLose = ImageIO.read(new File("Assets/Sprites/Lose.png"));
		}catch(IOException e){
			System.out.println("Error: imgLose");
		}
		
		try{
			imgLetters = ImageIO.read(new File("Assets/Sprites/Battleship Theme/BattleshipLetters.png"));
		}catch(IOException e){
			System.out.println("Error: imgLetters");
		}
		
		try{
			imgNumbers = ImageIO.read(new File("Assets/Sprites/Battleship Theme/BattleshipNumbers.png"));
		}catch(IOException e){
			System.out.println("Error: imgNumbers");
		}		
		
		try{
			imgWater = ImageIO.read(new File("Assets/Sprites/BattleshipWater.png"));
		}catch(IOException e){
			System.out.println("Error: imgWater");
		}	
		
		try{
			imgBox = ImageIO.read(new File("Assets/Sprites/BattleshipBox.png"));
		}catch(IOException e){
			System.out.println("Error: imgBox");
		}		
		
		try{
			imgMinimap = ImageIO.read(new File("Assets/Sprites/BattleshipMinimap.png"));
		}catch(IOException e){
			System.out.println("Error: imgMinimap");
		}
		
		try {
			imgShipsV[1] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShip.png"));
			imgShipsV[2] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShip.png"));
			imgShipsV[3] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSub.png"));
			imgShipsV[4] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShip.png"));
			imgShipsV[5] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShip.png"));
			imgShipsH[1] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShipH.png"));
			imgShipsH[2] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShipH.png"));
			imgShipsH[3] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubH.png"));
			imgShipsH[4] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShipH.png"));
			imgShipsH[5] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShipH.png"));
			
			imgShipsSunkV[1] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShipSunk.png"));
			imgShipsSunkV[2] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShipSunk.png"));
			imgShipsSunkV[3] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubSunk.png"));
			imgShipsSunkV[4] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShipSunk.png"));
			imgShipsSunkV[5] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShipSunk.png"));
			imgShipsSunkH[1] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShipSunkH.png"));
			imgShipsSunkH[2] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShipSunkH.png"));
			imgShipsSunkH[3] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubSunkH.png"));
			imgShipsSunkH[4] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShipSunkH.png"));
			imgShipsSunkH[5] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShipSunkH.png"));
			
			imgShipsMiniV[1] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShipMinimap.png"));
			imgShipsMiniV[2] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShipMinimap.png"));
			imgShipsMiniV[3] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubMinimap.png"));
			imgShipsMiniV[4] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShipMinimap.png"));
			imgShipsMiniV[5] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShipMinimap.png"));
			imgShipsMiniH[1] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShipMinimapH.png"));
			imgShipsMiniH[2] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShipMinimapH.png"));
			imgShipsMiniH[3] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubMinimapH.png"));
			imgShipsMiniH[4] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShipMinimapH.png"));
			imgShipsMiniH[5] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShipMinimapH.png"));
			
			imgMinimapHit = ImageIO.read(new File("Assets/Sprites/BattleshipMinimapHit.png"));
			imgMinimapMiss = ImageIO.read(new File("Assets/Sprites/BattleshipMinimapMiss.png"));
			imgBattleshipHit = ImageIO.read(new File("Assets/Sprites/BattleshipHit.png"));
			imgBattleshipMiss = ImageIO.read(new File("Assets/Sprites/BattleshipMiss.png"));
		}catch(IOException e){
			System.out.println("Error: imgShips");
		}
		
	}
}

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
	int intOpponentGrid[][] = new int[11][11]; // 1 - Hit, 2 - Miss
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
	boolean blnGameOver = false;
	
	int intSunkRow = 0;
	int intSunkCol = 0;
	int intSunkValue = 0;
	
	int intTheme = 2;
	int intThemetotal = 3;
	
	// Buffering Images
	BufferedImage imgLetters[] = new BufferedImage[intThemetotal];
	BufferedImage imgNumbers[] = new BufferedImage[intThemetotal];
	BufferedImage imgWater = null;
	BufferedImage imgBox = null;
	BufferedImage imgMinimap = null;
	BufferedImage imgPause = null;
	BufferedImage imgWin = null;
	BufferedImage imgLose = null;
	BufferedImage imgShipsV[][] = new BufferedImage[6][intThemetotal];
	BufferedImage imgShipsH[][] = new BufferedImage[6][intThemetotal];
	BufferedImage imgShipsMiniV[][] = new BufferedImage[6][intThemetotal];
	BufferedImage imgShipsMiniH[][] = new BufferedImage[6][intThemetotal];
	BufferedImage imgShipsSunkV[][] = new BufferedImage[6][intThemetotal];
	BufferedImage imgShipsSunkH[][] = new BufferedImage[6][intThemetotal];
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
		g.drawImage(imgLetters[intTheme], 80, 0, null);
		g.drawImage(imgNumbers[intTheme], 0, 80, null);
		g.drawImage(imgWater, 80, 80, null);
		g.drawImage(imgBox, 720, 0, null);
		g.drawImage(imgMinimap, 960, 0, null);
		
		// Draw Ships
		if (blnStartGame == false) {
			for (int intShip=1; intShip<=5; intShip++) {
				if (intPlaced[intShip] == 0) {
					if (blnHorizontal == false) {
						g.drawImage(imgShipsV[intShip][intTheme], intDefaultPositionsV[intShip][0], intDefaultPositionsV[intShip][1], null);
					} else {
						g.drawImage(imgShipsH[intShip][intTheme], intDefaultPositionsH[intShip][0], intDefaultPositionsH[intShip][1], null);
					}
				} else if (intPlaced[intShip] == 1) {
					g.drawImage(imgShipsV[intShip][intTheme], intPositions[intShip][0], intPositions[intShip][1], null);
				} else {
					g.drawImage(imgShipsH[intShip][intTheme], intPositions[intShip][0], intPositions[intShip][1], null);
				}
			}
		} else {
			for (int intShip=1; intShip<=5; intShip++) {
				int intRow = (int)Math.floor(1.0 * (intPositions[intShip][1] - 80) / 64) + 1;
				int intCol = (int)Math.floor(1.0 * (intPositions[intShip][0] - 80) / 64) + 1;
				
				if (intPlaced[intShip] == 1) {
					g.drawImage(imgShipsMiniV[intShip][intTheme], 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
				} else if (intPlaced[intShip] == 2) {
					g.drawImage(imgShipsMiniH[intShip][intTheme], 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
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
					if (intOpponentGrid[intRow][intCol] == 11) { // Sunk Ship 1 (V)
						g.drawImage(imgShipsSunkV[1][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 12) { // Sunk Ship 1 (H)
						g.drawImage(imgShipsSunkH[1][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 21) { // Sunk Ship 2 (V)
						g.drawImage(imgShipsSunkV[2][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 22) { // Sunk Ship 2 (H)
						g.drawImage(imgShipsSunkH[2][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 31) { // Sunk Ship 3 (V)
						g.drawImage(imgShipsSunkV[3][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 32) { // Sunk Ship 3 (H)
						g.drawImage(imgShipsSunkH[3][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 41) { // Sunk Ship 4 (V) 
						g.drawImage(imgShipsSunkV[4][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 42) { // Sunk Ship 4 (H)
						g.drawImage(imgShipsSunkH[4][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 51) { // Sunk Ship 5 (V)
						g.drawImage(imgShipsSunkV[5][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 52) { // Sunk Ship 5 (H)
						g.drawImage(imgShipsSunkH[5][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 1) {
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
		if(intWinLose == 1 && blnPlayingAnimation == false) {
			g.drawImage(imgWin, 0, 150, null);
			blnGameOver = true;
		} else if(intWinLose == 2 && blnPlayingAnimation == false) {
			g.drawImage(imgLose, 0, 150, null);
			blnGameOver = true;
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
		try {
			//Same for all themes
			imgPause = ImageIO.read(new File("Assets/Sprites/BattleshipPause.png"));
			imgWin = ImageIO.read(new File("Assets/Sprites/Win.png"));
			imgLose = ImageIO.read(new File("Assets/Sprites/Lose.png"));
			imgWater = ImageIO.read(new File("Assets/Sprites/BattleshipWater.png"));
			imgBox = ImageIO.read(new File("Assets/Sprites/BattleshipBox.png"));
			imgMinimap = ImageIO.read(new File("Assets/Sprites/BattleshipMinimap.png"));
			imgMinimapHit = ImageIO.read(new File("Assets/Sprites/BattleshipMinimapHit.png"));
			imgMinimapMiss = ImageIO.read(new File("Assets/Sprites/BattleshipMinimapMiss.png"));
			imgBattleshipHit = ImageIO.read(new File("Assets/Sprites/BattleshipHit.png"));
			imgBattleshipMiss = ImageIO.read(new File("Assets/Sprites/BattleshipMiss.png"));
			
			//Battleship Theme
			imgLetters[0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/BattleshipLetters.png"));
			imgNumbers[0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/BattleshipNumbers.png"));
			
			imgShipsV[1][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShip.png"));
			imgShipsV[2][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShip.png"));
			imgShipsV[3][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSub.png"));
			imgShipsV[4][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShip.png"));
			imgShipsV[5][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShip.png"));
			imgShipsH[1][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShipH.png"));
			imgShipsH[2][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShipH.png"));
			imgShipsH[3][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubH.png"));
			imgShipsH[4][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShipH.png"));
			imgShipsH[5][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShipH.png"));
			
			imgShipsSunkV[1][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShipSunk.png"));
			imgShipsSunkV[2][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShipSunk.png"));
			imgShipsSunkV[3][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubSunk.png"));
			imgShipsSunkV[4][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShipSunk.png"));
			imgShipsSunkV[5][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShipSunk.png"));
			imgShipsSunkH[1][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShipSunkH.png"));
			imgShipsSunkH[2][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShipSunkH.png"));
			imgShipsSunkH[3][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubSunkH.png"));
			imgShipsSunkH[4][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShipSunkH.png"));
			imgShipsSunkH[5][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShipSunkH.png"));
			
			imgShipsMiniV[1][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShipMinimap.png"));
			imgShipsMiniV[2][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShipMinimap.png"));
			imgShipsMiniV[3][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubMinimap.png"));
			imgShipsMiniV[4][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShipMinimap.png"));
			imgShipsMiniV[5][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShipMinimap.png"));
			imgShipsMiniH[1][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShipMinimapH.png"));
			imgShipsMiniH[2][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShipMinimapH.png"));
			imgShipsMiniH[3][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubMinimapH.png"));
			imgShipsMiniH[4][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShipMinimapH.png"));
			imgShipsMiniH[5][0] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShipMinimapH.png"));
			
			//Ducky Theme
			imgLetters[1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/DuckyLetters.png"));
			imgNumbers[1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/DuckyNumbers.png"));
			
			imgShipsV[1][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky2TileDucklings.png"));
			imgShipsV[2][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileFamily.png"));
			imgShipsV[3][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileGay.png"));
			imgShipsV[4][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky4TileFamily.png"));
			imgShipsV[5][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky5TileFamily.png"));
			imgShipsH[1][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky2DucklingsH.png"));
			imgShipsH[2][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileFamilyH.png"));
			imgShipsH[3][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileGayH.png"));
			imgShipsH[4][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky4TileFamilyH.png"));
			imgShipsH[5][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky5TileFamilyH.png"));
			
			imgShipsSunkV[1][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky2TileDucklingsSunk.png"));
			imgShipsSunkV[2][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileFamilySunk.png"));
			imgShipsSunkV[3][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileGaySunk.png"));
			imgShipsSunkV[4][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky4TileFamilySunk.png"));
			imgShipsSunkV[5][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky5TileFamilySunk.png"));
			imgShipsSunkH[1][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky2TileDucklingsSunkH.png"));
			imgShipsSunkH[2][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileFamilySunkH.png"));
			imgShipsSunkH[3][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileGaySunkH.png"));
			imgShipsSunkH[4][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky4TileFamilySunkH.png"));
			imgShipsSunkH[5][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky5TileFamilySunkH.png"));
			
			imgShipsMiniV[1][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky2TileDucklingsMinimap.png"));
			imgShipsMiniV[2][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileFamilyMinimap.png"));
			imgShipsMiniV[3][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileGayMinimap.png"));
			imgShipsMiniV[4][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky4TileFamilyMinimap.png"));
			imgShipsMiniV[5][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky5TileFamilyMinimap.png"));
			imgShipsMiniH[1][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky2TileDucklingsMinimapH.png"));
			imgShipsMiniH[2][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileFamilyMinimapH.png"));
			imgShipsMiniH[3][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileGayMinimapH.png"));
			imgShipsMiniH[4][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky4TileFamilyMinimapH.png"));
			imgShipsMiniH[5][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky5TileFamilyMinimapH.png"));
			
			
			//Lego Theme
			imgLetters[2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/LegoLetters.png"));
			imgNumbers[2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/LegoNumbers.png"));
			
			imgShipsV[1][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego2TileRed.png"));
			imgShipsV[2][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileBlue.png"));
			imgShipsV[3][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileGreen.png"));
			imgShipsV[4][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego4TileYellow.png"));
			imgShipsV[5][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego5TilePurple.png"));
			imgShipsH[1][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego2TileRedH.png"));
			imgShipsH[2][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileBlueH.png"));
			imgShipsH[3][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileGreenH.png"));
			imgShipsH[4][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego4TileYellowH.png"));
			imgShipsH[5][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego5TilePurpleH.png"));
			
			imgShipsSunkV[1][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego2TileRedSunk.png"));
			imgShipsSunkV[2][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileBlueSunk.png"));
			imgShipsSunkV[3][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileGreenSunk.png"));
			imgShipsSunkV[4][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego4TileYellowSunk.png"));
			imgShipsSunkV[5][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego5TilePurpleSunk.png"));
			imgShipsSunkH[1][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego2TileRedSunkH.png"));
			imgShipsSunkH[2][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileBlueSunkH.png"));
			imgShipsSunkH[3][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileGreenSunkH.png"));
			imgShipsSunkH[4][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego4TileYellowSunkH.png"));
			imgShipsSunkH[5][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego5TilePurpleSunkH.png"));
			
			imgShipsMiniV[1][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego2TileRedMinimap.png"));
			imgShipsMiniV[2][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileBlueMinimap.png"));
			imgShipsMiniV[3][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileGreenMinimap.png"));
			imgShipsMiniV[4][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego4TileYellowMinimap.png"));
			imgShipsMiniV[5][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego5TilePurpleMinimap.png"));
			imgShipsMiniH[1][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego2TileRedMinimapH.png"));
			imgShipsMiniH[2][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileBlueMinimapH.png"));
			imgShipsMiniH[3][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego3TileGreenMinimapH.png"));
			imgShipsMiniH[4][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego4TileYellowMinimapH.png"));
			imgShipsMiniH[5][2] = ImageIO.read(new File("Assets/Sprites/Lego Theme/Lego5TilePurpleMinimapH.png"));
		
			
		}catch(IOException e){
			System.out.println("Error: imgShips");
		}
		
	}
}

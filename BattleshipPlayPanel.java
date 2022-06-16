// Battleship Gameplay Panel
// Authors: Louis Sun, Andy Li, Fergus Chui
// ICS 4U1
// June 16, 2022
// Version 7.27

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
	
	/**
	 * This is true if the ships are rotated horizontally. False if rotate vertically
	 */
	public boolean blnHorizontal = false;
	
	/**
	 * Stores the letters which correspond to each column on the grid. strLetters[1] = A, strLetters[2] = B, ... 
	 */
	public String strLetters[] = new String[11];
	
	/**
	 * Stores the default click area for each battleship when they are vertical and haven't been placed on the map yet
	 * For ex, top left coordinate is (intDefaultPositionsV[][0], inintDefaultPositionsV[][1]), bottom right coordinate is (intDefaultPositionsV[][2], intDefaultPositionsV[][3])
	 */
	public int intDefaultPositionsV[][] = new int[6][4];
	
	/**
	 * Stores the default click area for each battleship when they are horizontal and haven't been placed on the map yet
	 * For ex, top left coordinate is (intDefaultPositionsV[][0], inintDefaultPositionsV[][1]), bottom right coordinate is (intDefaultPositionsV[][2], intDefaultPositionsV[][3])
	 */
	public int intDefaultPositionsH[][] = new int[6][4];
	
	/**
	 * Stores the location that each of YOUR battleships should be drawn to the screen
	 * The coordinate of the battleship is (intPositions[][0], intPositions[][1])
	 */
	public int intPositions[][] = new int[6][2];
	
	/**
	 * Stores the size of each ship
	 * For ex, the size of ship 1 is intSizes[1]
	 */
	public int intSizes[] = new int[6];
	
	/**
	 * Determines the status of the ship (whether it has been placed for not)
	 * 0 - Not Placed, 1 - Placed Vertically, 2 - Placed Horizontally
	 */
	public int intPlaced[] = new int[6];
	
	/**
	 * Stores the grid representing your map of battleships (the minimap)
	 * -1 represents that your opponent has missed
	 * 10, 20, 30, 40, 50 represents that your ships 1, 2, 3, 4, 5 has been hit, respectively
	 */
	public int intYourGrid[][] = new int[11][11];
	
	/**
	 * Stores the grid representing your guesses of your opponent's ships
	 * 11 - Ship 1 has been sunk and it is vertical
	 * 12 - Ship 1 has been sunk and it is horizontal
	 * 21 - Ship 2 has been sunk and it is vertical
	 * 22 - Ship 2 has been sunk and it is horizontal
	 * 31 - Ship 3 has been sunk and it is vertical
	 * 32 - Ship 3 has been sunk and it is horizontal
	 * 41 - Ship 4 has been sunk and it is vertical
	 * 42 - Ship 4 has been sunk and it is horizontal
	 * 51 - Ship 5 has been sunk and it is vertical
	 * 52 - Ship 5 has been sunk and it is horizontal
	 * 1 - A ship has been hit (may not be sunk yet)
	 * 2 - Your guess is a miss
	 */
	public int intOpponentGrid[][] = new int[11][11];
	
	/**
	 * Represents the ship you have selected when you are dragging and dropping the ships onto the map
	 */
	public int intShipSelected = 0;
	
	/**
	 * Your HP. If this reaches 0, it means all your ships have been sunk
	 */
	public int intHealth = 17;
	
	/**
	 * True if the game has started (both players have pressed ready). False if the game has not started.
	 */
	public boolean blnStartGame = false;
	
	/**
	 * True if it is your turn to fire. False if it is not your turn
	 */
	public boolean blnYourTurn = false;
	
	/**
	 * True if you have hit your opponent's ship. False if you missed
	 */
	public boolean blnHit = false;
	
	/**
	 * The row which the bomb/explosion/splash animation should be played
	 */
	public int intAnimationRow = 0;
	
	/**
	 * The column which the bomb/explosion/splash animation should be played
	 */
	public int intAnimationCol = 0;
	
	/**
	 * The sprite which the animation is currently displaying
	 */
	public int intAnimCount = 0;
	
	/**
	 * True if an animation is currently playing. False if an animation is not currently playing
	 */
	public boolean blnPlayingAnimation = false;
	
	/**
	 * blnPlayAnimation[1] represents the bomb animation
	 * blnPlayAnimation[2] represents the explosion animation
	 * blnPlayAnimation[3] represents the splash animation
	 * 
	 * blnPlayAnimation[i] = true means that the i-th animation is currently playing
	 */
	public boolean blnPlayAnimation[] = new boolean[4];
	
	/**
	 * Represents the maximum number of sprites/frames that each animation has
	 * intMaxAnimationSprites[1] reprents the bomb animation
	 * intMaxAnimationSprites[2] reprents the explosion animation
	 * intMaxAnimationSprites[3] reprents the splash animation
	 */
	public int intMaxAnimationSprites[] = new int[4];
	
	/**
	 * Represents the number of times each ship has been hit
	 */
	public int intShipHits[] = new int[6];
	
	/**
	 * Represents the number of YOUR ships that have been sunk
	 */
	public int intShipsSunk = 0;
	
	/**
	 * 1 means that you have won, 2 means that you lost
	 */
	public int intWinLose = 0;
	
	/**
	 * True if the game is over
	 */
	public boolean blnGameOver = false;
	
	/**
	 * The row that the currently sunk ship is located at
	 */
	public int intSunkRow = 0;
	
	/**
	 * The column that the currently sunk ship is located at
	 */
	public int intSunkCol = 0;
	
	/**
	 * Used to update the value of intOpponentGrid[][]
	 */
	public int intSunkValue = 0;
	
	/**
	 * The current theme that is selected
	 * 0 - Battleship
	 * 1 - Ducky
	 * 2 - Lego
	 * 3 - User Created Theme
	 */
	public int intTheme = 0;
	
	/**
	 * The total number of themes currently available
	 */
	public int intThemetotal = 4;
	
	// Buffering Images
	/**
	 * Stores the images for the column axis (A-J)
	 */
	BufferedImage imgLetters[] = new BufferedImage[intThemetotal];
	
	/**
	 * Stores the images for the row axis (1-10)
	 */
	BufferedImage imgNumbers[] = new BufferedImage[intThemetotal];
	
	/**
	 * Stores the image for the water
	 */
	BufferedImage imgWater = null;
	
	/**
	 * Stores the image for the box where the ships are dragged from and dropped onto the map
	 */
	BufferedImage imgBox = null;
	
	/**
	 * Stores the image of your minimap
	 */
	BufferedImage imgMinimap = null;
	
	/**
	 * Stores the image of the pause button on the top left
	 */
	BufferedImage imgPause = null;
	
	/**
	 * Stores the win image
	 */
	BufferedImage imgWin = null;
	
	/**
	 * Stores the lose image
	 */
	BufferedImage imgLose = null;
	
	/**
	 * Stores the images of vertical ships (on the blue map)
	 */
	BufferedImage imgShipsV[][] = new BufferedImage[6][intThemetotal];
	
	/**
	 * Stores the images of horizontal ships (on the blue map)
	 */
	BufferedImage imgShipsH[][] = new BufferedImage[6][intThemetotal];
	
	/**
	 * Stores the images of the minimap vertical ships
	 */
	BufferedImage imgShipsMiniV[][] = new BufferedImage[6][intThemetotal];
	
	/**
	 * Stores the images of the minimap horizontal ships
	 */
	BufferedImage imgShipsMiniH[][] = new BufferedImage[6][intThemetotal];
	
	/**
	 * Stores the images of the vertical sunk ships (on the blue map)
	 */
	BufferedImage imgShipsSunkV[][] = new BufferedImage[6][intThemetotal];
	
	/**
	 * Stores the images of the horizontal sunk ships (on the blue map)
	 */
	BufferedImage imgShipsSunkH[][] = new BufferedImage[6][intThemetotal];
	
	/**
	 * Stores the image for each sprite/frame for animations
	 */
	BufferedImage imgSprite = null;
	
	/**
	 * Stores the symbol representing a minimap hit (a circle)
	 */
	BufferedImage imgMinimapHit = null;
	
	/**
	 * Stores the symbol representing a minimap miss (an X)
	 */
	BufferedImage imgMinimapMiss = null;
	
	/**
	 * Stores the symbol representing a guessed hit (a big blue circle)
	 */
	BufferedImage imgBattleshipHit = null;
	
	/**
	 * Stores the symbol repersenting a guessed miss (a big red X)
	 */
	BufferedImage imgBattleshipMiss = null;
	
	// Methods
	/**
	 * Repaints the play panel so that the gameplay visuals are updated. Draws all the assets onto the screen
	 */
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
				if (intPlaced[intShip] == 0) { // Ship has not been placed on the map yet, draw it on the wooden box/board
					if (blnHorizontal == false) {
						g.drawImage(imgShipsV[intShip][intTheme], intDefaultPositionsV[intShip][0], intDefaultPositionsV[intShip][1], null);
					} else {
						g.drawImage(imgShipsH[intShip][intTheme], intDefaultPositionsH[intShip][0], intDefaultPositionsH[intShip][1], null);
					}
				} else if (intPlaced[intShip] == 1) { // Ship has been placed vertically
					g.drawImage(imgShipsV[intShip][intTheme], intPositions[intShip][0], intPositions[intShip][1], null);
				} else { // Ship has been placed horizontally
					g.drawImage(imgShipsH[intShip][intTheme], intPositions[intShip][0], intPositions[intShip][1], null);
				}
			}
		} else { // Game has started
			for (int intShip=1; intShip<=5; intShip++) {
				int intRow = (int)Math.floor(1.0 * (intPositions[intShip][1] - 80) / 64) + 1;
				int intCol = (int)Math.floor(1.0 * (intPositions[intShip][0] - 80) / 64) + 1;
				
				if (intPlaced[intShip] == 1) { // Draw vertical ship on minimap (your ships)
					g.drawImage(imgShipsMiniV[intShip][intTheme], 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
				} else if (intPlaced[intShip] == 2) { // Draw horizontal ship on minimap (your ships)
					g.drawImage(imgShipsMiniH[intShip][intTheme], 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
				}
			}
			
			for (int intRow=1; intRow<=10; intRow++) {
				for (int intCol=1; intCol<=10; intCol++) {
					// Radar (Your Ships)
					if (intYourGrid[intRow][intCol] >= 10) { // Opponent has hit a ship which occupies this location
						g.drawImage(imgMinimapHit, 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
					} else if (intYourGrid[intRow][intCol] == -1) { // Opponent has guessed this location and missed
						g.drawImage(imgMinimapMiss, 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
					}
					
					// Map (Guessing Your Opponent's Ships)
					if (intOpponentGrid[intRow][intCol] == 11) { // Sunk Opponent Ship 1 (V)
						g.drawImage(imgShipsSunkV[1][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 12) { // Sunk Opponent Ship 1 (H)
						g.drawImage(imgShipsSunkH[1][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 21) { // Sunk Opponent Ship 2 (V)
						g.drawImage(imgShipsSunkV[2][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 22) { // Sunk Opponent Ship 2 (H)
						g.drawImage(imgShipsSunkH[2][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 31) { // Sunk Opponent Ship 3 (V)
						g.drawImage(imgShipsSunkV[3][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 32) { // Sunk Opponent Ship 3 (H)
						g.drawImage(imgShipsSunkH[3][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 41) { // Sunk Opponent Ship 4 (V) 
						g.drawImage(imgShipsSunkV[4][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 42) { // Sunk Opponent Ship 4 (H)
						g.drawImage(imgShipsSunkH[4][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 51) { // Sunk Opponent Ship 5 (V)
						g.drawImage(imgShipsSunkV[5][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 52) { // Sunk Opponent Ship 5 (H)
						g.drawImage(imgShipsSunkH[5][intTheme], (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 1) { // Hit opponent ship (Draw circle)
						g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					} else if (intOpponentGrid[intRow][intCol] == 2) { // Missed opponent ship (Draw X)
						g.drawImage(imgBattleshipMiss, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
					}
				}
			}
		}
		
		// Draw Animations
		if (blnPlayAnimation[1] == true) { // Play the dropping bomb animation
			try {
				imgSprite = ImageIO.read(new File("Assets/Sprites/BattleshipBomb"+Integer.toString(intAnimCount)+".png"));
			} catch (IOException e) {
				System.out.println("Error: bomb animation");
			}
			g.drawImage(imgSprite, (intAnimationCol - 1) * 64 + 80, (intAnimationRow - 1) * 64 + 80, null);
		} else if (blnPlayAnimation[2] == true) { // Play the explosion animation
			try {
				imgSprite = ImageIO.read(new File("Assets/Sprites/BattleshipHitExplosion"+Integer.toString(intAnimCount)+".png"));
			} catch (IOException e) {
				System.out.println("Error: bomb animation");
			}
			g.drawImage(imgSprite, (intAnimationCol - 1) * 64 + 80, (intAnimationRow - 1) * 64 + 80, null);
		} else if (blnPlayAnimation[3] == true) { // Play the splash animation
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
	/**
	 * This is the constructor of the play panel. It initializes some default values related to ship sizes and positions, and also loads all the images.
	 */
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
			
		}catch(IOException e){
			System.out.println("Error: Battleship imgs");
		}
		
		try{
			//Ducky Theme
			imgLetters[1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/DuckyLetters.png"));
			imgNumbers[1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/DuckyNumbers.png"));
			
			imgShipsV[1][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky2TileDucklings.png"));
			imgShipsV[2][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileFamily.png"));
			imgShipsV[3][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky3TileGay.png"));
			imgShipsV[4][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky4TileFamily.png"));
			imgShipsV[5][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky5TileFamily.png"));
			imgShipsH[1][1] = ImageIO.read(new File("Assets/Sprites/Ducky Theme/Ducky2TileDucklingsH.png"));
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
			
		}catch(IOException e){
			System.out.println("Error: Ducky imgs");
		}
		
		try{
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
			System.out.println("Error: Lego imgs");
		}
		
	}
}

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class BattleshipHelpPanel2 extends JPanel{
	// Properties
	Font font1 = new Font("SansSerif", Font.BOLD, 20);
	Font font2 = new Font("SansSerif", Font.BOLD, 50);
	BufferedImage imgShip = null;
	BufferedImage imgShipH = null;
	BufferedImage imgShipMini = null;
	BufferedImage imgShipMiniH = null;
	BufferedImage imgPause = null;
	BufferedImage imgLetters = null;
	BufferedImage imgNumbers = null;
	BufferedImage imgWater = null;
	BufferedImage imgBox = null;
	BufferedImage imgMinimap = null;
	BufferedImage imgSprite = null;
	BufferedImage imgBattleshipHit = null;
	BufferedImage imgBattleshipMiss = null;
	int intPositionX = 800;
	int intPositionY = 100;
	boolean blnSelected = false;
	boolean blnHorizontal = false;
	boolean blnReady = false;
	boolean blnClickHit = false;
	boolean blnClickMiss = false;
	boolean blnPlayAnimation[] = new boolean[4];
	boolean blnPlayingAnimation = false;
	int intMaxAnimationSprites[] = new int[4];
	int intGrid[][] = new int[11][11];
	int intAnimationRow = 0;
	int intAnimationCol = 0;
	int intAnimCount = 0;
	String strLetters[] = new String[11];
	
	// Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		// Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		
		g.drawImage(imgPause, 0, 0, null);
		g.drawImage(imgLetters, 80, 0, null);
		g.drawImage(imgNumbers, 0, 80, null);
		g.drawImage(imgWater, 80, 80, null);
		g.drawImage(imgBox, 720, 0, null);
		g.drawImage(imgMinimap, 960, 0, null);
		if (blnHorizontal == false) {
			if (blnReady == true) {
				int intRow = (int)Math.floor(1.0 * (intPositionY - 80) / 64) + 1;
				int intCol = (int)Math.floor(1.0 * (intPositionX - 80) / 64) + 1;
				g.drawImage(imgShipMini, 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
			} else {
				g.drawImage(imgShip, intPositionX, intPositionY, null);
			}
		} else {
			if (blnReady == true) {
				int intRow = (int)Math.floor(1.0 * (intPositionY - 80) / 64) + 1;
				int intCol = (int)Math.floor(1.0 * (intPositionX - 80) / 64) + 1;
				g.drawImage(imgShipMiniH, 960 + (intCol - 1) * 28 + 34, (intRow - 1) * 28 + 34, null);
			} else {
				g.drawImage(imgShipH, intPositionX, intPositionY, null);
			}
		}
		
		for (int intRow=1; intRow<=10; intRow++) {
			for (int intCol=1; intCol<=10; intCol++) {
				if (intGrid[intRow][intCol] == 1) {
					g.drawImage(imgBattleshipHit, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
				} else if (intGrid[intRow][intCol] == 2) {
					g.drawImage(imgBattleshipMiss, (intCol - 1) * 64 + 80, (intRow - 1) * 64 + 80, null);
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
	}
	// Constructor
	public BattleshipHelpPanel2() {
        super();
        intMaxAnimationSprites[1] = 9;
		intMaxAnimationSprites[2] = 5;
		intMaxAnimationSprites[3] = 7;
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
        try {
			imgBattleshipHit = ImageIO.read(new File("Assets/Sprites/BattleshipHit.png"));
			imgBattleshipMiss = ImageIO.read(new File("Assets/Sprites/BattleshipMiss.png"));
			imgShip = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSub.png"));
			imgShipH = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubH.png"));
			imgPause = ImageIO.read(new File("Assets/Sprites/BattleshipPause.png"));
			imgLetters = ImageIO.read(new File("Assets/Sprites/Battleship Theme/BattleshipLetters.png"));
			imgNumbers = ImageIO.read(new File("Assets/Sprites/Battleship Theme/BattleshipNumbers.png"));
			imgWater = ImageIO.read(new File("Assets/Sprites/BattleshipWater.png"));
			imgBox = ImageIO.read(new File("Assets/Sprites/BattleshipBox.png"));
			imgMinimap = ImageIO.read(new File("Assets/Sprites/BattleshipMinimap.png"));
			imgShipMini = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubMinimap.png"));
			imgShipMiniH = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSubMinimapH.png"));
		} catch (IOException e) {
			System.out.println("Error: IMAGE");
		}
    }
	
}

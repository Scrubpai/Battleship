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
	int intDefaultPositions[][] = new int[6][4];
	int intPositions[][] = new int[6][2];
	int intSizes[] = new int[6];
	int intPlaced[] = new int[6]; // 0 - Not Placed, 1 - Vertical, 2 - Horizontal
	int intYourGrid[][] = new int[10][10];
	int intOpponentGrid[][] = new int[10][10];
	
	// Buffering Images
	BufferedImage imgLetters = null;
	BufferedImage imgNumbers = null;
	BufferedImage imgWater = null;
	BufferedImage imgBox = null;
	BufferedImage imgMinimap = null;
	BufferedImage imgPause = null;
	BufferedImage imgShipsV[] = new BufferedImage[6];
	BufferedImage imgShipsH[] = new BufferedImage[6];
	
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
		for (int intShip=1; intShip<=5; intShip++) {
			if (intPlaced[intShip] == 0) {
				g.drawImage(imgShipsV[intShip], intDefaultPositions[intShip][0], intDefaultPositions[intShip][1], null);
			} else if (intPlaced[intShip] == 1) {
				g.drawImage(imgShipsV[intShip], intPositions[intShip][0], intPositions[intShip][1], null);
			} else {
				g.drawImage(imgShipsH[intShip], intPositions[intShip][0], intPositions[intShip][1], null);
			}
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
	}
	
	
	// Constructor
	public BattleshipPlayPanel(){
		super();
		intSizes[1] = 2;
		intSizes[2] = 3;
		intSizes[3] = 3;
		intSizes[4] = 4;
		intSizes[5] = 5;
		
		intDefaultPositions[1][0] = 750;
		intDefaultPositions[1][1] = 20;
		intDefaultPositions[1][2] = intDefaultPositions[1][0]+64;
		intDefaultPositions[1][3] = intDefaultPositions[1][1]+128;
		
		intDefaultPositions[2][0] = 850;
		intDefaultPositions[2][1] = 20;
		intDefaultPositions[2][2] = intDefaultPositions[2][0]+64;
		intDefaultPositions[2][3] = intDefaultPositions[2][1]+192;
		
		intDefaultPositions[3][0] = 750;
		intDefaultPositions[3][1] = 170;
		intDefaultPositions[3][2] = intDefaultPositions[3][0]+64;
		intDefaultPositions[3][3] = intDefaultPositions[3][1]+192;
		
		intDefaultPositions[4][0] = 850;
		intDefaultPositions[4][1] = 270;
		intDefaultPositions[4][2] = intDefaultPositions[4][0]+64;
		intDefaultPositions[4][3] = intDefaultPositions[4][1]+256;
		
		intDefaultPositions[5][0] = 750;
		intDefaultPositions[5][1] = 380;
		intDefaultPositions[5][2] = intDefaultPositions[5][0]+64;
		intDefaultPositions[5][3] = intDefaultPositions[5][1]+320;
		
		// Try Catch Images
		try{
			imgPause = ImageIO.read(new File("Assets/Sprites/BattleshipPause.png"));
		}catch(IOException e){
			System.out.println("Error: imgPause");
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
		}catch(IOException e){
			System.out.println("Error: imgShip1");
		}
		
	}
}

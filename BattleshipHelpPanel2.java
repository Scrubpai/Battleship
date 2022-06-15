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
	BufferedImage imgPause = null;
	BufferedImage imgLetters = null;
	BufferedImage imgNumbers = null;
	BufferedImage imgWater = null;
	BufferedImage imgBox = null;
	BufferedImage imgMinimap = null;
	int intPositionX = 800;
	int intPositionY = 300;
	boolean blnSelected = false;
	
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
		g.drawImage(imgShip, intPositionX, intPositionY, null);
	}
	// Constructor
	public BattleshipHelpPanel2() {
        super();
        
        try {
			imgShip = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSub.png"));
			imgPause = ImageIO.read(new File("Assets/Sprites/BattleshipPause.png"));
			imgLetters = ImageIO.read(new File("Assets/Sprites/Battleship Theme/BattleshipLetters.png"));
			imgNumbers = ImageIO.read(new File("Assets/Sprites/Battleship Theme/BattleshipNumbers.png"));
			imgWater = ImageIO.read(new File("Assets/Sprites/BattleshipWater.png"));
			imgBox = ImageIO.read(new File("Assets/Sprites/BattleshipBox.png"));
			imgMinimap = ImageIO.read(new File("Assets/Sprites/BattleshipMinimap.png"));
		} catch (IOException e) {
			System.out.println("Error: IMAGE");
		}
    }
	
}

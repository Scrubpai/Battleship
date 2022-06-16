// Battleship Help Panel 1
// Authors: Louis Sun, Andy Li, Fergus Chui
// ICS 4U1
// June 16, 2022
// Version 7.27

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BattleshipHelpPanel extends JPanel{
	// Properties
	Font font1 = new Font("SansSerif", Font.BOLD, 20);
	Font font2 = new Font("SansSerif", Font.BOLD, 50);
	BufferedImage imgShips[] = new BufferedImage[6]; // Ship images
		
	// Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		// Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		
		g.setFont(font2);
		g.setColor(Color.WHITE);
		g.drawString("Objective", 530, 80);
		
		g.setFont(font1);
		g.drawString("You have 5 ships. Your opponent has 5 ships.", 420, 150);
		g.drawImage(imgShips[1], 300, 220, null);
		g.drawImage(imgShips[2], 450, 220, null);
		g.drawImage(imgShips[3], 600, 220, null);
		g.drawImage(imgShips[4], 750, 220, null);
		g.drawImage(imgShips[5], 900, 220, null);
		g.drawString("Goal: guess where your opponent's ships are located and sink them all", 300, 600);	
	}
	
	// Constructor
	public BattleshipHelpPanel() {
        super();
        
        try {
			imgShips[1] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship2TileShip.png"));
			imgShips[2] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileShip.png"));
			imgShips[3] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship3TileSub.png"));
			imgShips[4] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship4TileShip.png"));
			imgShips[5] = ImageIO.read(new File("Assets/Sprites/Battleship Theme/Battleship5TileShip.png"));
		} catch (IOException e) {
			System.out.println("Error: IMAGE");
		}
    }
	
}

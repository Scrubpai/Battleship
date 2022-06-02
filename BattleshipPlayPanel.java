import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Font.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BattleshipPlayPanel extends JPanel{
	//Properties
	
	//Buffering Images
	BufferedImage imgLetters = null;
	BufferedImage imgNumbers = null;
	BufferedImage imgWater = null;
	BufferedImage imgBox = null;
	BufferedImage imgMinimap = null;
	BufferedImage imgPause = null;
	
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		
		//Draw Images
		g.drawImage(imgPause, 0, 0, null);
		g.drawImage(imgLetters, 80, 0, null);
		g.drawImage(imgNumbers, 0, 80, null);
		g.drawImage(imgWater, 80, 80, null);
		g.drawImage(imgBox, 720, 0, null);
		g.drawImage(imgMinimap, 960, 0, null);
		
		//Create Lines to split up stuff
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
	
	
	//Constructor
	public BattleshipPlayPanel(){
		super();
		
		//Try Catch Images
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
		
	}
}

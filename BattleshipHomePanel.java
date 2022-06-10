import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BattleshipHomePanel extends JPanel{
	//Properties
	//Buffered reader for the image
	BufferedImage imgBackground = null;
	BufferedImage imgLogo = null;
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		
		//Adjust coordinates of the image
		g.drawImage(imgBackground, 0, 0, null);
		g.drawImage(imgLogo, 0, 0, null);
		
		
		
	}
	
	//Constructor
	public BattleshipHomePanel(){
		super();
		try{
			//Import the background image
            imgBackground = ImageIO.read(new File("Assets/Sprites/TitleScreen.png"));
        }catch(IOException e){
            System.out.println("Error: Background Image");
        } 
        
      try{
			//Import the Logo image
            imgLogo = ImageIO.read(new File("Assets/Sprites/battleshiplogo.png"));
        }catch(IOException e){
            System.out.println("Error: Logo Image");
        } 
	}
}

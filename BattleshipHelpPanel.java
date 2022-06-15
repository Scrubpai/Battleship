import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class BattleshipHelpPanel extends BattleshipPlayPanel{
	//Properties
	//Buffered reader for the image
	BufferedImage imgHelp = null;
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1280, 720);
		
		//Adjust coordinates of the image
		g.drawImage(imgHelp, 0, 0, null);
	}
	//Constructor
	public BattleshipHelpPanel(){
        super();
        try{
			//Import the help image
            imgHelp = ImageIO.read(new File("Assets/Sprites/help.png"));
        }catch(IOException e){
            System.out.println("Error: Help Image");
        } 
    }
	
}

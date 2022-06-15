import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class BattleshipHelpPanel extends JPanel{
	//Properties
	//Buffered reader for the image
	BufferedImage imgLogo = null;	
	BufferedImage imgHelp = null;
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1280, 720);
		
		//Add Text
		g.setFont(new Font("Arial.ttf", Font.PLAIN, 30));
		g.setColor(Color.BLACK);
		g.drawString("Step 1: Drag and Drop Ships", 900, 160);
		
		//Need to add drag and drop interactivity
		
		//Adjust coordinates of the image
		g.drawImage(imgLogo, 0, -50, null);
		g.drawImage(imgHelp, 0, 230, null);
		
	}
	//Constructor
	public BattleshipHelpPanel(){
        super();
		try{
			//Import the image
			imgLogo = ImageIO.read(new File("Assets/Sprites/battleshiplogo.png"));
			imgHelp = ImageIO.read(new File("Assets/Sprites/help.png"));
        }catch(IOException e){
            System.out.println("Error: Printing Image");
        } 
        
    }
	
}

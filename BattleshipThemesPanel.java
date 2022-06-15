import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class BattleshipThemesPanel extends JPanel{
	//Properties
	//Buffered reader for the image	
	BufferedImage imgBackground = null;
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		//Background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 1280, 720);
		
		//Adjust coordinates of the image
		g.drawImage(imgBackground, 0, 0, null);
		
		//Add Text
		g.setFont(new Font("Arial.ttf", Font.PLAIN, 100));
		g.setColor(Color.WHITE);
		g.drawString("Pick Your Theme", 275, 100);
		
		
		
		
	}
	//Constructor
	public BattleshipThemesPanel(){
        super();
		try{
			//Import the image
			imgBackground = ImageIO.read(new File("Assets/Sprites/TitleScreen.png"));
        }catch(IOException e){
            System.out.println("Error: Printing Image");
        } 
        
    }
	
}

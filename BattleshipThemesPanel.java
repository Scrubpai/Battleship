import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class BattleshipThemesPanel extends JPanel{
	//Properties
	//Buffered reader for the image	
	BufferedImage imgBackground = null;
	
	//Read the file into an array
	BufferedReader themes = null;
	
	//Variables for string
	int intCount;
	int intLine = 0;
	
	String strLine = "";
	String strThemes[];
	

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
		
		//Buffer Background Image
		try{
			imgBackground = ImageIO.read(new File("Assets/Sprites/TitleScreen.png"));
		}catch(IOException e){
			System.out.println("Error: Background Img");
		}
		
		//Count how many lines in file
		//Try to open file to read 
		try{
			themes = new BufferedReader(new FileReader("themes.txt"));
		}catch(FileNotFoundException e){
			System.out.println("File not found!!!");
		}
		
		while(strLine != null){
			try{
				strLine = themes.readLine();
			}catch(IOException e){
				System.out.println("Error reading from file");
			}
			intLine++;
		}
		
		//Try to close file
		try{
			themes.close();
		}catch(IOException e){
			System.out.println("Unable to close file");
		}
		
		strThemes = new String[intLine];
		//Put lines into array
		//Try to open file to read 
		try{
			themes = new BufferedReader(new FileReader("themes.txt"));
		}catch(FileNotFoundException e){
			System.out.println("File not found!!!");
		}
		
		for(intCount = 0; intCount < intLine - 1; intCount++){
			try{
				strThemes[intCount] = themes.readLine();
			}catch(IOException e){
				System.out.println("Error reading from file");
			}
			System.out.println(strThemes[intCount]);
		}
		
		//Try to close file
		try{
			themes.close();
		}catch(IOException e){
			System.out.println("Unable to close file");
		}
        
    }
	
}

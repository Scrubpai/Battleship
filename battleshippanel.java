import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Font.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BattleshipPanel extends JPanel{
	//Properties
	
	//Methods
	public void paintComponent(Graphics g){
		//Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		
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
	public BattleshipPanel(){
		super();
		
	}
}

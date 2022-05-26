import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class battleshipgame implements ActionListener{
	//Properties
	JFrame theFrame = new JFrame("Battleship");
	battleshippanel thePanel = new battleshippanel();
	Timer theTimer = new Timer(1000/60, this);
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == theTimer){
			thePanel.repaint();
		}
	}
	
	//Constructor
	public battleshipgame(){
		thePanel.setPreferredSize(new Dimension(1280, 720));
		theFrame.setContentPane(thePanel);
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.pack();
		theFrame.setVisible(true);	
		theFrame.setResizable(false);
		theTimer.start();
	}

	//Main Method
	public static void main(String[] args){
		new battleshipgame();
	}



}

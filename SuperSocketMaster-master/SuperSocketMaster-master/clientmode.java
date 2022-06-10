import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class clientmode implements ActionListener {
	// Properties
	JFrame theframe = new JFrame("Client");
	JPanel thepanel = new JPanel();
	JButton thebutton = new JButton("Send boo!!!!");
	JTextField thefield = new JTextField("Incoming Text");
	SuperSocketMaster ssm;
	
	// Methods
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == thebutton) {
			ssm.sendText("BOOOO!!!");
		} else if (evt.getSource() == ssm) {
			String strText = ssm.readText();
			thefield.setText(strText);
		}
	}
	
	// Constructor
	public clientmode() {
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thebutton.addActionListener(this);
		thepanel.add(thebutton);
		thepanel.add(thefield);
		theframe.setContentPane(thepanel);
		theframe.pack();
		theframe.setVisible(true);
		ssm = new SuperSocketMaster("127.0.0.1", 9001, this); // Client Constructor
		ssm.connect(); // attempt to connect
	}
	
	// main method
	public static void main(String[] args) {
		new clientmode();
	}	
}

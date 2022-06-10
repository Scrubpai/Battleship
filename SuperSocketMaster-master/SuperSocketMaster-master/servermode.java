import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class servermode implements ActionListener {
	// Properties
	JFrame theframe = new JFrame("Server");
	JPanel thepanel = new JPanel();
	JButton thebutton = new JButton("Send LMAOO!!!!");
	JTextField thefield = new JTextField("Incoming Text");
	SuperSocketMaster ssm;
	
	// Methods
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == thebutton) {
			ssm.sendText("LMAOO!!!"); // sending text over the network
		} else if (evt.getSource() == ssm) { // if there is incoming data, event is triggered
			String strText = ssm.readText(); // so lets read that data
			thefield.setText(strText);
		}
	}
	
	// Constructor
	public servermode() {
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thebutton.addActionListener(this);
		thepanel.add(thebutton);
		thepanel.add(thefield);
		theframe.setContentPane(thepanel);
		theframe.pack();
		theframe.setVisible(true);
		ssm = new SuperSocketMaster(9001, this); // Server Constructor
		ssm.connect(); // attempt to connect
	}
	
	// main method
	public static void main(String[] args) {
		new servermode();
	}	
}


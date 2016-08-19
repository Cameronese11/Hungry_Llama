package src.main.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import static src.main.UI.CluedoCanvas.loadImage;

public class SetupMenu2 {
	
	
	private JComboBox selectNumPlayers;
	
	public SetupMenu2(){
		
	}


	public JPanel homePage(){
		JPanel panel = new JPanel();
		JButton done = new JButton("Done");
		panel.setLocation(100,100);
		panel.setSize(100, 100);
		panel.setBackground(Color.blue);
		String[] playerOptions = { "3", "4", "5", "6"};
		selectNumPlayers = new JComboBox(playerOptions);
		selectNumPlayers.setSelectedIndex(0);
		//selectNumPlayers.addActionListener(this);
		panel.add(selectNumPlayers, BorderLayout.NORTH);	
		panel.add(done, BorderLayout.SOUTH);
		return panel;
	}



}

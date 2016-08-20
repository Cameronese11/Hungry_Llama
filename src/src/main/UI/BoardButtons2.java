package src.main.UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardButtons2 implements ActionListener {

	private JButton suggestion;
	private JButton accusation;
	private JButton finishTurn;
	private JButton useStairway;
	
	private CluedoCanvas canvas;
	
	public BoardButtons2(CluedoCanvas canvas){
		
	}
	
	public JPanel createPanel(){
		JPanel panel = new JPanel();
		panel.setSize(100, 100);
		panel.setLocation(100, 100);
		panel.setBackground(Color.blue);
		return panel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

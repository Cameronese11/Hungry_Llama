package src.main.UI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;

import src.main.Cluedo.Game;

public class BoardButtons2 implements ActionListener {

	private JButton[] buttons;
	private CluedoCanvas canvas;
	private Game game;
	
	public BoardButtons2(Game game, CluedoCanvas canvas){
		this.game = game;
		this.canvas = canvas;
		buttons = new JButton[4];

		buttons[0] = new JButton("Accusation");
		buttons[1] = new JButton("Suggestion");
		buttons[2] = new JButton("Stairway");
		buttons[3] = new JButton("Finish Turn");
	}
	
	public JPanel createPanel(){
		JPanel panel = new JPanel(new GridLayout(2,3,5,0));
	
		for(int i = 0; i < buttons.length; i++){
			buttons[i].setActionCommand(buttons[i].getText());
			buttons[i].addActionListener(this);
			buttons[i].setSelected(false);
			panel.add(buttons[i]);
		}
		
		panel.setSize(380, 60);
		panel.setLocation(595, 525);
		panel.setOpaque(false);
		return panel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String button = e.getActionCommand();
		if(canvas.getShowCard() == null && canvas.getAccOrSugg() == 0){
			if(button.equals("Accusation"))
				canvas.accusation(true);
			if(button.equals("Suggestion"))
				canvas.accusation(false);
			if(button.equals("Stairway"))
			System.out.println("Stairway");
			if(button.equals("FinishTurn")){
				game.setCurrentPlayer(game.nextTurn());
				canvas.resetDice();
			}
			canvas.repaint();
		}
		
	}

}

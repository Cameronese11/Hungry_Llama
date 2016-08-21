package src.main.UI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import src.main.Cluedo.Game;
import src.main.Location.Location;
import src.main.Location.Room;

public class BoardButtons implements ActionListener {

	private JButton[] buttons;
	private CluedoCanvas canvas;
	private Game game;
	private boolean stairwayUsed;
	private boolean suggestionMade;
	
	public BoardButtons(Game game, CluedoCanvas canvas){
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
			if(button.equals("Accusation")){
				canvas.accusation(true);
			}else if(button.equals("Suggestion")){
				if(suggestionMade){
					JOptionPane.showMessageDialog(canvas, "Can only make once suggestion per turn");
				}else{
					if(game.getCurrentPlayer().getLocation() instanceof Room)
						canvas.accusation(false);
					else{
						JOptionPane.showMessageDialog(canvas, "Must be in a room to make a suggestion");
					}
				}
					
			}else if(button.equals("Stairway")){
				if(stairwayUsed){
					JOptionPane.showMessageDialog(canvas, "Can only use the stairway once per turn");
				}else{
				
				
					Location loc = game.getCurrentPlayer().getLocation();
					if(loc instanceof Room){
						if(!((Room) loc).getStairwayTo().equals("")){
							int n = JOptionPane.showConfirmDialog(
									canvas,
									"Are you sure you want to use the stairway to the " + ((Room) loc).getStairwayTo() + "\n" 
											+ "This will count as your Move","Are you sure?",
											JOptionPane.YES_NO_OPTION);
							System.out.println(n);
							if(n == 0){
								game.movePlayer(game.getCurrentPlayer(), game.getRoom(((Room) loc).getStairwayTo()), 0);
								stairwayUsed = true;
							}
					}else{
						JOptionPane.showMessageDialog(canvas, "Must be in a room with a stairway to use a stairway");
					}
				}else{
					JOptionPane.showMessageDialog(canvas, "Must be in a room with a stairway to use a stairway");
				}
			}
			}else if(button.equals("Finish Turn")){
				canvas.setHideHand(true);
				canvas.repaint();
				JOptionPane.showMessageDialog(
						canvas,
						game.getNextPlayer().getName() + ", your turn is next\n"
														+ " Are you ready?");
				canvas.setHideHand(false);
				game.setCurrentPlayer(game.nextTurn());
				canvas.resetDice();
				stairwayUsed = false;
				suggestionMade = false;
				
			
			}
			canvas.repaint();
		
		
	}
	public JButton getButton(int i){
		return buttons[i];
	}
	public void setSuggestionMade(Boolean b){
		suggestionMade = b;
	}
	

}

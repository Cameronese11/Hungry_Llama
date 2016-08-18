package src.main.UI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static src.main.UI.CluedoCanvas.loadImage;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import src.main.Cluedo.Game;
import src.main.GameObject.Player;
import src.main.GameObject.Weapon;
import src.main.Location.Room;

public class AccusationOrSuggestion implements ActionListener{

	private Game game;
	private JFrame frame;
	private CluedoCanvas canvas;
	private JRadioButton[] murderRooms;
	private JRadioButton[] murderWeapons;
	private JRadioButton[] murderCharacters;
	private Room room;
	private Weapon weapon;
	private Player.Character character;
	private JPanel roomPanel;
	private JPanel weaponPanel;
	private JPanel characterPanel;
	private JPanel donePanel;
	
	
	public AccusationOrSuggestion(Game game, CluedoCanvas canvas) {
		super();
		this.game = game;
		this.canvas = canvas;
		this.murderRooms = new JRadioButton[9];
		this.murderWeapons = new JRadioButton[6];
		this.murderCharacters = new JRadioButton[6];
	}
	
	
	
	public JPanel askMurderRoom(){
		roomPanel = new JPanel();
		roomPanel.setSize(new Dimension(330,210));
		roomPanel.setLocation(0, 190);
		roomPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		roomPanel.setOpaque(false);
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.WEST;
		for(int i = 0; i < murderRooms.length; i++){
			JRadioButton button = new JRadioButton(game.getRooms().get(i).getName());
			button.setForeground(Color.white);
			button.addActionListener(this);
			button.setActionCommand("r" + game.getRooms().get(i).getName());
			murderRooms[i] = button;
			roomPanel.add(button,gbc);
		}
		return roomPanel;
	}
	
	public JPanel askMurderWeapon(){
		weaponPanel = new JPanel();
		weaponPanel.setSize(new Dimension(330,140));
		weaponPanel.setLocation(330, 190);
		weaponPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		weaponPanel.setOpaque(false);
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.WEST;
		for(int i = 0; i < murderWeapons.length; i++){
			JRadioButton button = new JRadioButton(game.getWeapons().get(i).getName());
			button.setForeground(Color.white);
			button.addActionListener(this);
			button.setActionCommand("w" + game.getWeapons().get(i).getName());
			murderWeapons[i] = button;
			weaponPanel.add(button,gbc);
		}
		return weaponPanel;
	}
	
	public JPanel askMurderCharacter(){
		characterPanel = new JPanel();
		characterPanel.setSize(new Dimension(330,140));
		characterPanel.setLocation(660, 190);
		characterPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		characterPanel.setOpaque(false);
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.WEST;
		for(int i = 0; i < murderCharacters.length; i++){
			JRadioButton button = new JRadioButton(Game.getCharacterName(game.getCharacters().get(i)));
			button.setForeground(Color.white);
			button.addActionListener(this);
			button.setActionCommand("s" + Game.getCharacterName(game.getCharacters().get(i)));
			murderCharacters[i] = button;
			characterPanel.add(button,gbc);
		}
		return characterPanel;
	}
	
	public JPanel done(){
		donePanel = new JPanel();
		JButton button = new JButton("done");
		button.addActionListener(this);
		button.setActionCommand("done");
		donePanel.setSize(button.getPreferredSize());
		donePanel.setLocation(740, 400);
		donePanel.setOpaque(false);
		donePanel.add(button);
		return donePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().charAt(0) == 'r'){
			room =  game.getRoom(e.getActionCommand().substring(1, e.getActionCommand().length()));
			for(int i = 0; i < murderRooms.length; i++){
				if(!e.getActionCommand().equals(murderRooms[i].getActionCommand())){
					murderRooms[i].setSelected(false);
				}
			}
		}else if(e.getActionCommand().charAt(0) == 'w'){
			weapon =  game.getWeapon( e.getActionCommand().substring(1, e.getActionCommand().length()));
			for(int i = 0; i < murderWeapons.length; i++){
				if(!e.getActionCommand().equals(murderWeapons[i].getActionCommand())){
					murderWeapons[i].setSelected(false);
				}
			}
		}else if(e.getActionCommand().charAt(0) == 's'){
			switch(e.getActionCommand().substring(1, e.getActionCommand().length())){
				case "Miss Scarlett": character = Player.Character.MISS_SCARLETT; break;
				case "Colonel Mustard": character = Player.Character.COLONEL_MUSTARD; break;
				case "Mrs White": character = Player.Character.MRS_WHITE; break;
				case "The Reverand Green": character = Player.Character.THE_REVERAND_GREEN; break;
				case "Mrs Peacock": character = Player.Character.MRS_PEACOCK; break;
				case "Professor Plum": character = Player.Character.PROFESSOR_PLUM; break;
			}
			
			for(int i = 0; i < murderCharacters.length; i++){
				if(!e.getActionCommand().equals(murderCharacters[i].getActionCommand())){
					murderCharacters[i].setSelected(false);
				}
			}
		}else if(e.getActionCommand().equals("done")){
			System.out.println("Made an accusation(" + character + ", " + weapon.getName() + ", " + room.getName() + ")");
			System.out.println("Correct Solution(" + game.getBasement().getMurderCharacter() + ", " + game.getBasement().getMurderWeapon().getName() + ", " + game.getBasement().getMurderRoom().getName() + ")");
			boolean accusation = game.accusation(character, weapon, room);
			if(!accusation){
				canvas.remove(weaponPanel);
				canvas.remove(roomPanel);
				canvas.remove(characterPanel);
				canvas.remove(donePanel);
				canvas.revalidate();
				canvas.afterAccusation(accusation);
				
			}
					
		}	
		
		
	}
	
	

}

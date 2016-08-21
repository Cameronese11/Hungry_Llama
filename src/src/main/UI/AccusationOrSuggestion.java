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
import src.main.Location.Location;
import src.main.Location.Room;

/**
 * Class for when the player makes an Accusation or a Suggestion
 *
 */
public class AccusationOrSuggestion implements ActionListener{

	private Game game;
	private CluedoCanvas canvas;
	
	// JButtons for accusations/Suggestions
	private JRadioButton[] murderRooms;
	private JRadioButton[] murderWeapons;
	private JRadioButton[] murderCharacters;
	
	// Selected Accusation or Suggestion
	private Room room;
	private Weapon weapon;
	private Player.Character character;
	
	// JPanels to store JRadioButtons and JButton
	private JPanel roomPanel;
	private JPanel weaponPanel;
	private JPanel characterPanel;
	private JPanel donePanel;
	
	private String refute; // Refute string from suggestion, null if successful
	
	/**
	 * Construct new AccusationOrSuggestion object
	 * 
	 * @param game
	 * @param canvas
	 */
	public AccusationOrSuggestion(Game game, CluedoCanvas canvas) {
		super();
		this.game = game;
		this.canvas = canvas;
		this.murderRooms = new JRadioButton[9];
		this.murderWeapons = new JRadioButton[6];
		this.murderCharacters = new JRadioButton[6];
	}
	
	/**
	 * Constructs and returns a new JPanel that holds a 
	 * JRadioButtons for every Room
	 * 
	 * @param acc - true if player is making an accusation
	 * @return JPanel containing room radio buttons
	 */
	public JPanel askMurderRoom(Boolean acc){
		
		// get a custom JPanel
		roomPanel = getTransparentPanel(0,190,330,210);
		
		// create some constraints to ensure correct component positioning
		GridBagConstraints gbc = getCustomConstraints();
		
		// if player is in a room, then remember this room
		Location loc = game.getCurrentPlayer().getLocation();
		Room r = null;
		if(loc instanceof Room)
			r = (Room) loc;
			
		// Iterate through the  room radioButtons
		for(int i = 0; i < murderRooms.length; i++){
			JRadioButton button = new JRadioButton(game.getRooms().get(i).getName());
			
			// if player is making a suggestion
			if(r != null && !acc){
				// if the current RadioButton is for the room the player is in, then select this RadioButton
				if(r.equals(game.getRooms().get(i))) 
					button.setSelected(true);
				button.setEnabled(false); // player cant select room for a suggestion
				room = r;
			}
		
			// setup button
			button.setForeground(Color.white);
			button.addActionListener(this);
			button.setActionCommand("r" + game.getRooms().get(i).getName());
			
			// add radio button to appropriate panel and array
			murderRooms[i] = button;
			roomPanel.add(button,gbc);
		}
		
		return roomPanel;
	}
	
	/**
	 * Constructs and returns a new JPanel that holds a 
	 * JRadioButtons for every Weapon
	 * 
	 * @return - JPanel containing room radio buttons
	 */
	public JPanel askMurderWeapon(){
		
		// get a custom JPanel
		weaponPanel = getTransparentPanel(330,190,330,140);
		
		// create some constraints to ensure correct component positioning
		GridBagConstraints gbc = getCustomConstraints();
		
		// Iterate through the weapon radioButtons
		for(int i = 0; i < murderWeapons.length; i++){
			
			// create and setup radio button
			JRadioButton button = new JRadioButton(game.getWeapons().get(i).getName());
			button.setForeground(Color.white);
			button.addActionListener(this);
			button.setActionCommand("w" + game.getWeapons().get(i).getName());
			
			// add radio button to appropriate panel and array
			murderWeapons[i] = button;
			weaponPanel.add(button,gbc);
		}
		return weaponPanel;
	}
	
	/**
	 * Constructs and returns a new JPanel that holds a 
	 * JRadioButtons for every character
	 * 
	 * @return - JPanel containing character radio buttons
	 */
	public JPanel askMurderCharacter(){
		
		// get a custom JPanel
		characterPanel = getTransparentPanel(660,190,330,140);
		
		// get some constraints to ensure correct component positioning
		GridBagConstraints gbc = getCustomConstraints();
		
		// Iterate through the character radioButtons
		for(int i = 0; i < murderCharacters.length; i++){
			
			// create and setup radio button
			JRadioButton button = new JRadioButton(Game.getCharacterName(game.getCharacters().get(i)));
			button.setForeground(Color.white);
			button.addActionListener(this);
			button.setActionCommand("c" + Game.getCharacterName(game.getCharacters().get(i)));
			
			// add radio button to appropriate panel and array
			murderCharacters[i] = button;
			characterPanel.add(button,gbc);
		}
		return characterPanel;
	}
	
	public JPanel done(){
		// create and setup the JPanel
		donePanel = new JPanel();
		donePanel.setLocation(740, 400);
		donePanel.setOpaque(false);
		
		// create and setup radio button
		JButton button = new JButton("Done");
		button.addActionListener(this);
		button.setActionCommand("done");
		
		// set panel size to the size of the button
		donePanel.setSize(button.getPreferredSize());
		
		// add button to panel
		donePanel.add(button);
		
		return donePanel;
	}
	
	/**
	 * Creates some custom GridBagConstraints to ensure 
	 * that the RadioButtons a lined up appropriately
	 * 
	 * @return gbc - the gbc for the radio buttons
	 */
	public GridBagConstraints getCustomConstraints(){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.anchor = GridBagConstraints.WEST;
		
		return gbc;
	}
	
	/**
	 * Creates a custom JPanel
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * 
	 * @return - a transparent JPanel with corresponding height and location
	 */
	public JPanel getTransparentPanel(int x, int y, int width, int height){
		JPanel panel = new JPanel();
		panel.setLocation(x, y);
		panel.setSize(new Dimension(width, height));
		panel.setLayout(new GridBagLayout());
		panel.setOpaque(false);
		
		return panel;
	}
	
	/**
	 * Removes radio button JPanels
	 */
	public void removePanels(){
		canvas.remove(weaponPanel);
		canvas.remove(roomPanel);
		canvas.remove(characterPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// player has selected a room radio button
		if(e.getActionCommand().charAt(0) == 'r'){
			room =  game.getRoom(e.getActionCommand().substring(1, e.getActionCommand().length())); // remember the selected room
			for(int i = 0; i < murderRooms.length; i++) // find and select this radio button
				if(!e.getActionCommand().equals(murderRooms[i].getActionCommand()))
					murderRooms[i].setSelected(false);
		
		// player has selected a weapon radio button	
		}else if(e.getActionCommand().charAt(0) == 'w'){
			weapon =  game.getWeapon( e.getActionCommand().substring(1, e.getActionCommand().length())); // remember the selected weapon
			for(int i = 0; i < murderWeapons.length; i++) // find and select this radio button
				if(!e.getActionCommand().equals(murderWeapons[i].getActionCommand()))
					murderWeapons[i].setSelected(false);
				
		// player has selected a character radio button
		}else if(e.getActionCommand().charAt(0) == 'c'){
			// find corresponding character and remember it
			switch(e.getActionCommand().substring(1, e.getActionCommand().length())){
				case "Miss Scarlett": character = Player.Character.MISS_SCARLETT; break;
				case "Colonel Mustard": character = Player.Character.COLONEL_MUSTARD; break;
				case "Mrs White": character = Player.Character.MRS_WHITE; break;
				case "The Reverand Green": character = Player.Character.THE_REVERAND_GREEN; break;
				case "Mrs Peacock": character = Player.Character.MRS_PEACOCK; break;
				case "Professor Plum": character = Player.Character.PROFESSOR_PLUM; break;
			}
			// find and select this radio button
			for(int i = 0; i < murderCharacters.length; i++)
				if(!e.getActionCommand().equals(murderCharacters[i].getActionCommand()))
					murderCharacters[i].setSelected(false);
				
		// player has selected the 'done' button
		}else if(e.getActionCommand().equals("done")){
			
			// player is making an accusation
			if(canvas.getAccOrSugg() == 1){
				boolean accusation = false;
				// if accusation is valid
				if(character != null && weapon != null && room!= null)
					 accusation = game.accusation(character, weapon, room); 
				else{ // invalid accusation return to main screen
					canvas.add(canvas.getBoardButtonPanel());
					canvas.remove(donePanel);
					canvas.setAccOrSugg(0);
				}
				if(!accusation && canvas.getAccOrSugg() != 0)
					canvas.setAccOrSugg(2);
				else if(accusation && canvas.getAccOrSugg() != 0)
					canvas.setAccOrSugg(5);
				removePanels();	
			
			// player has made a false accusation
			}else if(canvas.getAccOrSugg() == 2){
				canvas.add(canvas.getBoardButtonPanel());
				canvas.setAccOrSugg(0);
				canvas.remove(donePanel);
				canvas.getBoardButtons().getButton(3).doClick();
		
			// player is making a suggestion
			}else if(canvas.getAccOrSugg() == 3){
				String refuted = null;
				// if suggestion is valid
				if(character != null && weapon != null && room!= null){
					refuted = game.suggestion(character, weapon, room);
					canvas.getBoardButtons().setSuggestionMade(true);
					canvas.setAccOrSugg(4);
				}else{  // invalid accusation return to main screen
					canvas.add(canvas.getBoardButtonPanel());
					canvas.remove(donePanel);
					canvas.setAccOrSugg(0);
				}
				refute = refuted;	
				removePanels();
			
			// player has made finished there suggestion
			}else if(canvas.getAccOrSugg() == 4){
				canvas.add(canvas.getBoardButtonPanel());
				canvas.setAccOrSugg(0);
				canvas.remove(donePanel);
			}
		}
		canvas.revalidate();
		canvas.repaint();
	}
	
	// Getters and Setters
	public String getRefuteMessage(){
		return refute;
	}

}

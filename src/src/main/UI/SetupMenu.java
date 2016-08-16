package src.main.UI;



import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static src.main.UI.CluedoCanvas.loadImage;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Player;
import src.main.GameObject.Player.Character;





public class SetupMenu extends javax.swing.JPanel implements ActionListener{
	
	
	private int playersLeft;
	private JComboBox selectNumPlayers;
	private JButton nextButton;
	private JTextField nameField;
	private JRadioButton[] characterButtons = new JRadioButton[6];
	private ArrayList<Integer> characterIndexes;
	private Game game;
	private CluedoFrame frame;
	private int index;
	public boolean warning = false;
	
	
	public SetupMenu(Game game, CluedoFrame frame){
		super();
		this.game = game;
		this.frame = frame;
		characterIndexes = new ArrayList<Integer>();
		homePage();
	}

	public void homePage(){
		setLayout(new GridBagLayout()); //create a Panel with a layout
		index = 0;
		String[] playerOptions = { "3", "4", "5", "6"};
		selectNumPlayers = new JComboBox(playerOptions);
		selectNumPlayers.setSelectedIndex(0);
		selectNumPlayers.addActionListener(this);
		add(selectNumPlayers);
		nextButton = new JButton("next");
		add(nextButton);
		nextButton.addActionListener(this);
		nextButton.setActionCommand("doneHome");
	}
	
	public void loadPlayerPage(){
		removeAll();
		revalidate();
		repaint();
		index++;
		GridBagConstraints c = new GridBagConstraints();
		nextButton = new JButton("next");
		nameField = new JTextField(30);
		c.gridwidth = 3;
		c.weighty = 1;
		add(nameField,c);
		addCharacterButtons();
		c.gridx = 1;
		c = new GridBagConstraints();
		c.gridx = 1;
		c.weighty = 1;
		add(nextButton,c);
		nextButton.addActionListener(this);
		repaint();
	}
	
	public void addCharacterButtons(){
		
		characterButtons[0] = new JRadioButton("Miss Scarlett");
		characterButtons[1] = new JRadioButton("Colonel Mustard");
		characterButtons[2] = new JRadioButton("Mrs White");
		characterButtons[3] = new JRadioButton("The Reverand Green");
		characterButtons[4] = new JRadioButton("Mrs Peacock");
		characterButtons[5] = new JRadioButton("Professor Plum");
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 6;
		for(int i = 0; i < characterButtons.length/2; i++){
			add(characterButtons[i],c);
			if(characterIndexes.contains(i))
				characterButtons[i].setEnabled(false);
		}
		c.gridy = 7;
		for(int i = characterButtons.length/2; i < characterButtons.length; i++){
			add(characterButtons[i],c);
			if(characterIndexes.contains(i))
				characterButtons[i].setEnabled(false);;
		}
		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, Board.WIDTH, 500);
		if(warning){
			g.setColor(Color.red);
			g.setFont(new Font("Times New Roman",Font.PLAIN, 15));
			g.drawString("Must select a character!", 440, 200);
		}
		g.setColor(Color.BLACK);
		if(index == 0){
			g.setFont(new Font("Times New Roman",Font.PLAIN, 20));
			g.drawString("How Many Players", 415, 105);
		}else if(index > 0){
			g.setFont(new Font("Times New Roman",Font.PLAIN, 20));
			g.drawString("Player " + index, 475, 15);
			g.setFont(new Font("Times New Roman",Font.PLAIN, 15));
			g.drawString("What is your name?", 440, 40);
			g.drawString("Chose your character ", 440, 100);
		
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		warning = false;
		if(e.getSource() == nextButton){
			if(e.getActionCommand().equals("doneHome")){
				nextButton1();		
			}else{
				boolean valid = false;
				for(int i = 0; i < characterButtons.length ; i++)
					if(characterButtons[i].isSelected())
						valid = true;
			
				if(valid)
					nextButton2();
				else
					warning = true;
			}
		}
		repaint();
	}
	
	public void nextButton1(){
		String selected = (String) selectNumPlayers.getSelectedItem();
		playersLeft = Integer.valueOf(selected);
		game.setNumPlayers(playersLeft);
		Game.gameState = Game.State.SETUP_PLAYER;
		loadPlayerPage();	
	}
	
	public void nextButton2(){
		String name = (String) nameField.getText();
		Player.Character character = null;
		
		for(int i = 0; i < characterButtons.length; i++){
			if(characterButtons[i].isSelected()){
				characterButtons[i].setEnabled(false);
				characterIndexes.add(i);
				switch(i){
					case 0: character = Player.Character.MISS_SCARLETT; break;
					case 1: character = Player.Character.COLONEL_MUSTARD; break;
					case 2: character = Player.Character.MRS_WHITE; break;
					case 3: character = Player.Character.THE_REVERAND_GREEN; break;
					case 4: character = Player.Character.MRS_PEACOCK; break;
					case 5: character = Player.Character.PROFESSOR_PLUM; break;
				}
			}	
		}
		
		game.addPlayer(index, character, name);
		playersLeft--;
	
		if(playersLeft == 0){
			Game.gameState = Game.State.RUNNING;
			removeAll();
			revalidate();
			repaint();
			frame.gameBoardUI();
		}else{
			loadPlayerPage();
		}
	}
}

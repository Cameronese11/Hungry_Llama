package src.main.Cards;

import static src.main.UI.CluedoCanvas.loadImage;

import java.awt.Image;

import src.main.Cluedo.Game;
import src.main.GameObject.Player.Character;

/**
 * Represents a Suspect Card in the Game. 
 * There is one room card for evey Character
 *  
 * Miss Scarlett
 * Colonel Mustard
 * Mrs. White
 * The Reverend Green
 * Mrs. Peacock
 * Professor Plum
 * 
 */
public class SuspectCard implements Card {

	private Character character;
	private Image[] imgs = new Image[3];
	
	/**
	 * Constructs a new SuspectCard object
	 * 
	 * @param c - Suspect for the Card
	 */
	public SuspectCard(Character c){
		this.character = c;
		setImages();
	}
	
	/**
	 * @return - String representing this Card
	 */
	public String toString(){
		return character.toString();
	}
	
	// Getters and Setters
	
	public Character getCharacter(){
		return character;	
	}
	
	public Image getImage(int i){
		return imgs[i];
	}
	
	public void setImages(){
		imgs[0] = loadImage("Player" + Game.getCharacterName(character) + ".jpg");
		imgs[1] = imgs[0].getScaledInstance(100, 146, 16);
		imgs[2] = imgs[0].getScaledInstance(80, 116, 16);
		
	}
	
	
}

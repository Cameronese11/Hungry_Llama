package src.main.Cards;

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
	
	/**
	 * Constructs a new SuspectCard object
	 * 
	 * @param c - Suspect for the Card
	 */
	public SuspectCard(Character c){
		this.character = c;
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
	
	
	
}

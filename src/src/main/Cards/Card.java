package src.main.Cards;

import java.awt.Image;

/**
 * Represents a card in the game
 * 
 * There are 3 types of cards
 * 
 * RoomCard
 * SuspectCard
 * Weapon Card
 * 
 */
public interface Card {
	
	/**
	 * return a String representing this card
	 * 
	 * @return - String
	 */
	public String toString();
	
	public Image getImage(int i);
}


package src.main.Cards;

import src.main.Location.Room;

/**
 * Represents a Room Card in the Game. 
 * There is one room card for evey room object
 *  
 *  Kitchen
 *  Dinning Room
 *  Lounge
 *  Hall
 *  Study
 *  Library
 *  Billiard Room
 *  Conservatory
 *  Ball Room
 * 
 * @author cameronmclachlan
 */
public class RoomCard implements Card{

	private Room room;
	
	/**
	 * Constucts a new roomCard object
	 * 
	 * @param room
	 */
	public RoomCard(Room room){
		this.room = room;
	}
	
	/**
	 * @return - String representing this card
	 */
	public String toString(){
		return "Room Card: " + room.getName();
	}
	
	// Getters and Setters
	
	public Room getRoom(){
		return room;
	}
	
	
}

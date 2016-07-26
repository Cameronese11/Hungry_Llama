package src.main.Cards;

import src.main.GameObject.Room;

public class RoomCard implements Card{

	private Room room;
	
	public RoomCard(Room room){
		this.room = room;
	}
	public Room getRoom(){
		return room;
	}
}

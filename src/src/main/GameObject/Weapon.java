package src.main.GameObject;

import src.main.Cluedo.Game;
import src.main.Location.Room;

/**
 * Represents a weapon in the game
 * 
 * @author cameronmclachlan
 *
 */
public class Weapon {

	private Game game;
	private String name;
	private String room;
	
	/**
	 * Constructs a new weapon object
	 * 
	 * @param name - name of the weapon
	 */
	public Weapon(Game game, String name){
		this.game = game;
		this.name = name;
	}
	
	// Getters and Setters
	
	public String getName(){
		return name;
	}
	
	public String getRoom(){
		return room;
	}
	

	public void setRoom(String room){
		this.room = room;
	}

	public void move(Room room){
		Room oldRoom = game.getRoom(this.room);
		oldRoom.removeWeapon(this);
		room.addWeapon(this);					
	}
	
	/**
	 *Equals method to compare to players 
	 */
	@Override
	public boolean equals(Object weapon){
		if( !(weapon instanceof Weapon) || (weapon == null))
			return false;
		
		return (((Weapon) weapon).getName().equals(getName()));
	}
}

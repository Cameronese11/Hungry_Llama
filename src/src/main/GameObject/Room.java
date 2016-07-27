package src.main.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Room on the 
 * board
 * 
 * @author cameronmclachlan
 *
 */
public class Room {

	private String name;
	
	private List<Weapon> weapons;
	private Player player;
	
	/**
	 * constructs a new Room object
	 * 
	 * @param name - name of the room
	 */
	public Room(String name){
		weapons = new ArrayList<>();
		this.name = name;
	}
	
	/**
	 * Adds a weapon to the room
	 * 
	 * @param weapon
	 */
	public void addWeapon(Weapon weapon){
		weapons.add(weapon);
	}
	
	/**
	 * Adds a player to the room
	 * 
	 * @param player
	 */
	public void addPlayer(Player player){
		this.player = player;
	}
	
	// Getters and Setters
	
	public Weapon getWeapon(){
		return null;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public String getName(){
		return name;
	}
	
	
	
	
	
}

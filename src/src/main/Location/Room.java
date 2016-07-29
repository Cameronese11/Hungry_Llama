package src.main.Location;

import java.util.ArrayList;
import java.util.List;

import src.main.GameObject.Player;
import src.main.GameObject.Weapon;

/**
 * Represents a Room on the 
 * board
 * 
 * @author cameronmclachlan
 *
 */
public class Room implements Location{

	private String name;
	
	private List<Weapon> weapons;
	private List<Player> players;
	
	/**
	 * constructs a new Room object
	 * 
	 * @param name - name of the room
	 */
	public Room(String name){
		weapons = new ArrayList<>();
		players = new ArrayList<>();
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
	 * Adds a weapon to the room
	 * 
	 * @param weapon
	 */
	public void removeWeapon(Weapon weapon){
		weapons.remove(weapon);
	}
	
	/**
	 * Adds a player to the room
	 * 
	 * @param player
	 */
	public void addPlayer(Player player){
		players.add(player);
	}
	
	/**
	 * Removes a player to the room
	 * 
	 * @param player
	 */
	public void removePlayer(Player player){
		players.remove(player);
	}
	
	// Getters and Setters
	
	public List<Weapon> getWeapons(){
		return weapons;
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public String getName(){
		return name;
	}
	
	
	public void printLocation(){
		System.out.print(name);
	}
	
	
}

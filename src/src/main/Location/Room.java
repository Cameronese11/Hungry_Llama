package src.main.Location;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import src.main.GameObject.Player;
import src.main.GameObject.Weapon;

/**
 * Represents a Room on the 
 * board
 */
public class Room implements Location{

	private String name;
	
	private List<Weapon> weapons;
	private List<Player> players;
	private String stairwayTo;
	private int x;
	private int y;
	
	/**
	 * constructs a new Room object
	 * 
	 * @param name - name of the room
	 */
	public Room(String name, String stairwayTo, int x, int y){
		weapons = new ArrayList<>();
		players = new ArrayList<>();
		this.name = name;
		this.stairwayTo = stairwayTo;
		this.x = x;
		this.y = y;
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
		if(weapons.contains(weapon))
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
		if(players.contains(player))
			players.remove(player);
	}
	
	
	/**
	 *Equals method to compare to players 
	 */
	@Override
	public boolean equals(Object room){
		if( !(room instanceof Room) || (room == null))
			return false;
		
		return (((Room) room).getName().equals(getName()));
	}
	
	public void paint(Graphics g){
		
		
	}
	// Getters and Setters
		
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	public List<Weapon> getWeapons(){
		return weapons;
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public String getName(){
		return name;
	}
	

	public String getStairwayTo() {
		return stairwayTo;
	}

	@Override
	public void paint(Graphics g, List<Tile> moveableLocations, Tile selectedTile) {
		// TODO Auto-generated method stub
		
	}
	
	
}

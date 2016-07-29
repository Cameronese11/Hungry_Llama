package src.main.GameObject;

import java.util.ArrayList;
import java.util.List;

import src.main.Location.Room;

/**
 * Represents the basement which holds the 'solution
 * '
 * @author cameronmclachlan
 *
 */
public class Basement{

	// Solution
	private Room murderRoom;
	private Player.Character murderCharacter;
	private Weapon murderWeapon;
	
	/**
	 * Constructs a new Basement object
	 * 
	 * @param r - Solution room 
	 * @param r - Solution character 
	 * @param r - Solution weapon 
	 * 
	 */
	public Basement(Room r,Player.Character character, Weapon w){
		murderRoom = r;
		murderCharacter = character;
		murderWeapon = w;
	}
	
	// Getters and Setters
	
	public Room getMurderRoom(){
		return murderRoom;
	}

	public Player.Character getMurderCharacter(){
		return murderCharacter;
	}

	public Weapon getMurderWeapon(){
		return murderWeapon;
	}
}

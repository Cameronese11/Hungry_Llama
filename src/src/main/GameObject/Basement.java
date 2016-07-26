package src.main.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Basement{

	private Room murderRoom;
	private Player.Character murderCharacter;
	private Weapon murderWeapon;
	
	public Basement(Room r,Player.Character character, Weapon w){
		murderRoom = r;
		murderCharacter = character;
		murderWeapon = w;
	}
	
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

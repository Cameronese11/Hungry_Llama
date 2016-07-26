package src.main.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Room {

	private String name;
	
	private List<Weapon> weapons;
	private Player player;
	
	public Room(String name){
		weapons = new ArrayList<>();
		this.name = name;
	}
	
	public Weapon getWeapon(){
		return null;
	}
	
	public Player getPlayer(){
		return player;
	}
	public void addWeapon(Weapon weapon){
		weapons.add(weapon);
	}
	
	public void addPlayer(Player player){
		this.player = player;
	}
	
	public String getName(){
		return name;
	}
	
}

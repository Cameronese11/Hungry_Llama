package src.main.GameObject;

import static src.main.UI.CluedoCanvas.loadImage;

import java.awt.Image;

import src.main.Cluedo.Game;
import src.main.Location.Room;

/**
 * Represents a weapon in the game
 */
public class Weapon {

	private Game game;
	private String name;
	private String room;
	private Image img;
	
	/**
	 * Constructs a new weapon object
	 * 
	 * @param name - name of the weapon
	 */
	public Weapon(Game game, String name){
		this.game = game;
		this.name = name;
		setImg();
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
	
	/**
	 * Moves a weapon to a new room
	 * 
	 * @param room - new room
	 */
	public void move(Room room){
		Room oldRoom = game.getRoom(this.room);
		oldRoom.removeWeapon(this);
		this.room = room.getName();
		room.addWeapon(this);					
	}
	
	public void setImg(){
		this.img = loadImage("Dagger.png").getScaledInstance(200, 200,0);
	}
	
	public Image getImage(){
		return img;
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

	
}

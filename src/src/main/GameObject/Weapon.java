package src.main.GameObject;

/**
 * Represents a weapon in the game
 * 
 * @author cameronmclachlan
 *
 */
public class Weapon {

	private String name;
	
	/**
	 * Constructs a new weapon object
	 * 
	 * @param name - name of the weapon
	 */
	public Weapon(String name){
		this.name = name;
	}
	
	// Getters and Setters
	
	public String getName(){
		return name;
	}
}

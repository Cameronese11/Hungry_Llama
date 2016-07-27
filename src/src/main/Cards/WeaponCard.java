package src.main.Cards;

import src.main.GameObject.Weapon;

/**
 * Represents a Weapon Card in the Game. 
 * There is one weapon card for every Weapon Object
 *  
 * Candlestick
 * Dagger 
 * Lead Pipe
 * Revolver
 * Rope
 * Spanner
 * 
 * @author cameronmclachlan
 */
public class WeaponCard implements Card{

	private Weapon weapon;
	
	/**
	 * Constructs a new WeaponCard Object
	 * 
	 * @param weapon - Weapon for Card
	 */
	public WeaponCard(Weapon weapon){
		this.weapon = weapon;
	}
	
	/**
	 * @return - String representing this card
	 */
	public String toString(){
		return "Weapon Card: " + weapon.getName();
	}
	
	// Getters and Setters
	
	public Weapon getWeapon(){
		return weapon;
	}
	
	
}

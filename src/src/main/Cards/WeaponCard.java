package src.main.Cards;

import src.main.GameObject.Weapon;

public class WeaponCard implements Card{

	private Weapon weapon;
	
	public WeaponCard(Weapon weapon){
		this.weapon = weapon;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
}

package src.main.Cards;

import src.main.GameObject.Player.Character;

public class SuspectCard implements Card {

	private Character character;
	
	public SuspectCard(Character c){
		this.character = c;
	}
	
	public Character getCharacter(){
		return character;	
	}
	
	public String toString(){
		return "Suspect Card: " + character;
	}
	
}

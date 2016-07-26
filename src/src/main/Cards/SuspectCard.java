package src.main.Cards;

public class SuspectCard implements Card {

	private Character character;
	
	public SuspectCard(Character character){
		this.character = character;
	}
	
	public Character getCharacter(){
		return character;	
	}
}

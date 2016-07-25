	package src.main.Cluedo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import src.main.GameObject.Player;
import src.main.GameObject.Player.Character;
import src.main.UI.TextClient;

/**
 * Represnts a Game of Cluedo
 * 
 * @author cameronmclachlan
 *
 */
public class Game {

	private int numPlayers;
	private List<Player> players;
	private List<Player.Character> charactersLeft;
	private List<Player.Character> characters;
	private Board board;
	private TextClient textClient;
	private int currentTurn;
	
	/**
	 * Constructs a new Game of Cluedo
	 * 
	 * @param filename - file location to construct the board
	 */
	public Game(String filename){
		board = new Board(filename);
		players = new ArrayList<Player>();
		charactersLeft = new ArrayList<>();
		characters = new ArrayList<>();
		initialiseCharacters();
		currentTurn = 1;
		textClient = new TextClient(this, board);
		textClient.Run();
	}
	
	private void initialiseCharacters() {
		charactersLeft.add(Player.Character.MISS_SCARLETT);
		charactersLeft.add(Player.Character.COLONEL_MUSTARD);
		charactersLeft.add(Player.Character.MRS_WHITE);
		charactersLeft.add(Player.Character.THE_REVERAND_GREEN);
		charactersLeft.add(Player.Character.MRS_PEACOCK);
		charactersLeft.add(Player.Character.PROFESSOR_PLUM);
		characters.add(Player.Character.MISS_SCARLETT);
		characters.add(Player.Character.COLONEL_MUSTARD);
		characters.add(Player.Character.MRS_WHITE);
		characters.add(Player.Character.THE_REVERAND_GREEN);
		characters.add(Player.Character.MRS_PEACOCK);
		characters.add(Player.Character.PROFESSOR_PLUM);

	}

	/**
	 * To clear the console an updated board can be
	 * printed
	 */
	public void clearConsole(){
		
		for(int clear = 0; clear < 100000; clear++)
		     System.out.print("\b") ;
	}
	
	/**
	 * Create a player with a random 
	 * character for every player in the game
	 */
	public Character generatePlayer(int i){
		Character character = generateCharacter();
		players.add(new Player(this, board, i, character));
		charactersLeft.remove(character);
		return character;
	}
	
	/**
	 * Create a player in the game with
	 * a specific character
	 */
	public Character addPlayer(int i, Character character){
		players.add(new Player(this, board, i, character));
		charactersLeft.remove(character);
		return character;
	}
	
	/**
	 * Returns a randam character that isn't 
	 * currently in use
	 * 
	 * @return - character
	 */
	public Character generateCharacter(){
		List<Player.Character> notInUse = charactersLeft;	
		Collections.shuffle(notInUse);
		return(notInUse.get(0));
	}
	
	
	public static void main(String[] args) {
		new Game(args[0]);
	}
	
	
	// getters and Setters
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public List<Player.Character> getCharacters(){
		return characters;
	}
	
	public List<Player.Character> getCharactersLeft(){
		return charactersLeft;
	}
	
	
	public void setNumPlayers(int num){
		this.numPlayers = num;
	}
	
	public Player getPlayer(Character character){
		for(Player p: players)
			if(p.getCharacter().equals(character))
				return p;
		return null;
	}
	
	public Player getPlayer(int num){
		for(Player p: players)
			if(p.getNum() == num)
				return p;
		return null;
	}

	
		
		
	
	
	
	

}

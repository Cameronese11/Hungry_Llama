	package src.main.Cluedo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import src.main.Cards.Card;
import src.main.Cards.RoomCard;
import src.main.Cards.SuspectCard;
import src.main.Cards.WeaponCard;
import src.main.GameObject.Basement;
import src.main.GameObject.Player;
import src.main.GameObject.Player.Character;
import src.main.Tiles.MoveTile;
import src.main.Tiles.Tile;
import src.main.GameObject.Room;
import src.main.GameObject.Weapon;
import src.main.UI.TextClient;

/**
 * Represnts a Game of Cluedo
 * 
 * @author cameronmclachlan
 *
 */
public class Game {
	
	private List<Player> players;
	private List<Room> rooms;
	private List<Weapon> weapons;
	private List<Card> deck;
	
	private List<Player.Character> characters; 	  
	private List<Player.Character> charactersLeft; // characters not yet assigned to a player
	
	private Board board;
	private TextClient textClient;
	
	private Player currentPlayer;
	private int numPlayers;
	private Basement basement;
	
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
		rooms = new ArrayList<>();
		weapons = new ArrayList<>();
		deck = new ArrayList<>();
		textClient = new TextClient(this, board);
		initialiseGame();
		textClient.Run();
	}
	
	private void initialiseGame(){
		initialiseWeapons();
		initialiseRooms();
		initialiseCharacters();
		initialiseCards();
	}
	
	private void initialiseWeapons(){
		weapons.add(new Weapon("Candlestick"));
		weapons.add(new Weapon("Dagger"));
		weapons.add(new Weapon("Lead Pipe"));
		weapons.add(new Weapon("Revolver"));
		weapons.add(new Weapon("Rope"));
		weapons.add(new Weapon("Spanner"));
	}

	private void initialiseRooms(){
		rooms.add(new Room("Kitchen"));
		rooms.add(new Room("Dinning Room"));
		rooms.add(new Room("Lounge"));
		rooms.add(new Room("Ball Room"));
		rooms.add(new Room("Hall"));
		rooms.add(new Room("Study"));
		rooms.add(new Room("Library"));
		rooms.add(new Room("Billard"));
		rooms.add(new Room("Conservatory"));
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
	 * Select a Weapon, Suspect and Room card as the Solution, 
	 * place in the basement and get the rest of the cards ready 
	 */
	private void initialiseCards(){
		List<SuspectCard> suspectCards = new ArrayList<>();
		List<RoomCard> roomCards = new ArrayList<>();
		List<WeaponCard> weaponCards = new ArrayList<>();
		
		// sort all the cards into individual piles for each card type and shuffle
		for(Character c: characters)
			suspectCards.add(new SuspectCard(c));
		for(Room r: rooms)
			roomCards.add(new RoomCard(r));
		for(Weapon w: weapons)
			weaponCards.add(new WeaponCard(w));
		Collections.shuffle(roomCards);
		Collections.shuffle(suspectCards);
		Collections.shuffle(weaponCards);
		
		// Select a card of the top of each pile to put towards the 'solution'
		Room murderRoom = roomCards.get(0).getRoom();
		Character murderCharacter = suspectCards.get(0).getCharacter();
		Weapon murderWeapon = weaponCards.get(0).getWeapon();
		
		// place the solution in the basement
		basement = new Basement(murderRoom, murderCharacter, murderWeapon);
		
		// merge the piles into one and shuffle
		for(RoomCard r: roomCards)
			deck.add(r);
		for(SuspectCard s: suspectCards)
			deck.add(s);
		for(WeaponCard w: weaponCards)
			deck.add(w);
		Collections.shuffle(deck);
		
		// remove 'solution' from deck
		deck.remove(murderWeapon);
		deck.remove(murderWeapon);
		deck.remove(murderWeapon);		
	}
	
	/**
	 * place all the players characters on their 
	 * starting tiles on the board
	 */
	public void setupPlayers(){
		for(Player p: players){
			Tile tile = p.determineStartTile();
			p.move(tile);
		}
	}
		
	/**
	 * Clear the console
	 */
	public void clearConsole(){
		
		for(int clear = 0; clear < 100000; clear++)
		     System.out.print("\b") ;
	}
	
	/**
	 * Creates a player in the game with 
	 * a random character that isn't in use
	 * 
	 * @param i - player number(1-6)
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
	 * 
	 * @param i - player number(1-6)
	 * @param character - character to assign to player
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
	
	/**
	 * Determines the player who's turn it is next
	 * 
	 * @return - player
	 */
	public Player nextTurn(){
		if(currentPlayer == null)
			currentPlayer = getPlayer(2);
		if(currentPlayer.getNum() < numPlayers)
			return getPlayer(currentPlayer.getNum() + 1);
		else
			return getPlayer(1);
	}
	
	/**
	 * roll the dice
	 * 
	 * @return int - dice roll 
	 */
	public int rollDice(){
		return (int) (Math.random() * 6);
	}
	
	public void determineMoveLocations(Player player, int DiceRoll){
		Tile Location = player.
		
	}
	
	/**
	 * deal the cards in the deck to all the players
	 * such that every player has an equal number of cards,
	 * the remaining cards are left in the deck
	 */
	public void dealCards(){
		boolean done = false;
		
		while(!done)
			if(deck.size() >= numPlayers){
				for(Player p: players){
					p.addCard(deck.get(0));
					deck.remove(0);
				}
			}else
				done = true;
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
	
	public List<Card> getDeck(){
		return deck;
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
	
	public Room getRoom(String name){
		for(Room r: rooms)
			if(r.getName().equals(name))
				return r;
		return null;
	}
	
	public Weapon getWeapon(String name){
		for(Weapon w: weapons)
			if(w.getName().equals(name))
				return w;
		return null;
	}

	public Player getCurrentPlayer(){
		return currentPlayer;
	}
	
	public void setNumPlayers(int num){
		this.numPlayers = num;
	}
	
}

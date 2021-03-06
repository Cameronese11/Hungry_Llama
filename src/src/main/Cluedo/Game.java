package src.main.Cluedo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import src.main.Cards.Card;
import src.main.Cards.RoomCard;
import src.main.Cards.SuspectCard;
import src.main.Cards.WeaponCard;
import src.main.GameObject.Basement;
import src.main.GameObject.Player;
import src.main.GameObject.Player.Character;
import src.main.Location.DoorTile;
import src.main.Location.Location;
import src.main.Location.Tile;
import src.main.Location.Room;
import src.main.GameObject.Weapon;

/**
 * Represnts a Game of Cluedo
 * 
 * @author cameronmclachlan
 * @author chethanawijesekera
 *
 */
public class Game{

	private List<Player> players; // all players that were in game from the
									// start
	private List<Player> playersOut;// all players that are currently in the game
	private List<Card> cards; // all cards
	private List<Card> deck; // leftover cards(not dealt to players)

	private List<Room> rooms;
	private List<Weapon> weapons;
	
	private Player winner;
	
	private List<Player.Character> characters; // all possible characters
	private List<Player.Character> charactersLeft; // characters not yet
													// assigned to a player
	private Board board;

	private Player currentPlayer; // player whose turn it is
	private int numPlayers; // number of players that are currently in the game
	private Basement basement; // stores the solution
	
	public static State gameState; // the current state of the game
	
	// The different states the game can be in
	public static enum State{
		RUNNING,
		SETUP_MENU,
		SETUP_PLAYER,
		OVER
	}

	/**
	 * Constructs a new Game of Cluedo
	 * 
	 * @param filename
	 *            - file location to construct the board
	 */
	public Game(Board board) {

		this.board = board;
	

		// initialise Lists
		playersOut = new ArrayList<>();
		players = new ArrayList<>();
		charactersLeft = new ArrayList<>();
		characters = new ArrayList<>();
		rooms = new ArrayList<>();
		weapons = new ArrayList<>();
		cards = new ArrayList<>();

		// initialise game objects
		initialiseWeapons();
		initialiseRooms();
		initialiseCharacters();
		deck = initialiseCards();

	}

	private void initialiseWeapons() {
		weapons.add(new Weapon(this, "Candlestick"));
		weapons.add(new Weapon(this, "Dagger"));
		weapons.add(new Weapon(this, "Lead Pipe"));
		weapons.add(new Weapon(this, "Revolver"));
		weapons.add(new Weapon(this, "Rope"));
		weapons.add(new Weapon(this, "Wrench"));
	}

	private void initialiseRooms() {
		rooms.add(new Room("Kitchen", "Study", 28, 60));
		rooms.add(new Room("Dining Room", "", 45, 269));
		rooms.add(new Room("Lounge", "Conservatory", 38, 484 ));
		rooms.add(new Room("Ball Room", "", 236, 88));
		rooms.add(new Room("Hall", "", 236, 490));
		rooms.add(new Room("Study", "Kitchen", 445, 518));
		rooms.add(new Room("Library", "", 445, 370));
		rooms.add(new Room("Billiard Room", "", 445, 238));
		rooms.add(new Room("Conservatory", "Lounge", 445, 53));
		Collections.shuffle(weapons);
		Collections.shuffle(rooms);
		for (int i = 0; i < weapons.size(); i++) {
			rooms.get(i).addWeapon(weapons.get(i));
			weapons.get(i).setRoom(rooms.get(i).getName());
		}

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
	 * Select a Weapon, Suspect and Room card as the Solution, place in the
	 * basement and get the rest of the cards ready
	 * 
	 * @return list<Card> - returns the deck
	 */
	private List<Card> initialiseCards() {
		List<SuspectCard> suspectCards = new ArrayList<>();
		List<RoomCard> roomCards = new ArrayList<>();
		List<WeaponCard> weaponCards = new ArrayList<>();
		List<Card> deck = new ArrayList<>();

		// sort all the cards into individual piles for each card type and
		// shuffle
		for (Character c : characters) {
			Card card = new SuspectCard(c);
			suspectCards.add((SuspectCard) card);
			cards.add(card);
			deck.add(card);
		}
		for (Room r : rooms) {
			Card card = new RoomCard(r);
			roomCards.add((RoomCard) card);
			cards.add(card);
			deck.add(card);
		}
		for (Weapon w : weapons) {
			Card card = new WeaponCard(w);
			weaponCards.add((WeaponCard) card);
			cards.add(card);
			deck.add(card);
		}
		Collections.shuffle(roomCards);
		Collections.shuffle(suspectCards);
		Collections.shuffle(weaponCards);
		Collections.shuffle(cards);
		Collections.shuffle(deck);

		// Select a card of the top of each pile to put towards the 'solution'
		Room murderRoom = roomCards.get(0).getRoom();
		Character murderCharacter = suspectCards.get(0).getCharacter();
		Weapon murderWeapon = weaponCards.get(0).getWeapon();

		// place the solution in the basement
		basement = new Basement(murderRoom, murderCharacter, murderWeapon);

		// remove 'solution' from deck
		deck.remove(getCard(murderWeapon));
		deck.remove(getCard(murderCharacter));
		deck.remove(getCard(murderRoom));

		return deck; // return deck to be dealt to players later
	}

	/**
	 * Moves a player in the game, either for setting up the game/test or for a
	 * players turn
	 * 
	 * @param player
	 *            - player to move
	 * @param newLocation
	 *            - the new location to move to
	 * @param roll
	 *            - the dice roll
	 * 
	 * @return - true if the move is valid
	 */
	public boolean movePlayer(Player player, Location newLocation, int roll) {
		List<Tile> moveableLocations = determineMoveLocations(player, roll);

		if (roll > 0) { // if roll > 0 must be a players turn
			if (player == currentPlayer) {
				if (newLocation instanceof Tile) {
					if (((Tile) newLocation).getPlayer() != null)
						return false; // if there is a player on the newLocation
										// tile
					if (moveableLocations.contains((Tile) newLocation))
						return currentPlayer.move(newLocation);
				} else if (newLocation instanceof Room) {

					// if moving from a room, check if it has a stairway
					if (player.getLocation() instanceof Room) {
						String stairwayTo = ((Room) player.getLocation()).getStairwayTo();

						// if it does and they wont to use it then do so
						if (stairwayTo.equals(((Room) newLocation).getName())) {
							return currentPlayer.move(newLocation);
						}
					}

					// makes sure the newLocation room has a doorTile that is
					// within moveableTiles
					for (Tile t : getTiles()) {
						if (t instanceof DoorTile) {
							if (moveableLocations.contains(t)) {
								String r = ((DoorTile) t).getRoom();
								if (r.equals(((Room) newLocation).getName())) {
									return currentPlayer.move(newLocation);
								}
							}
						}
					}
				}
			}
		} else {
			return player.move(newLocation); // isn,t a players turn, so move
												// without checking the dice
												// roll ect
		}
		return false; // must be a players turn but above conditions arnt met
						// for a valid move

	}

	/**
	 * place all the players characters on their starting tiles on the board and
	 * initialise currentPlayer
	 */
	public void setupPlayers() {
		currentPlayer = getPlayer(1);

		// add players to both lists and set to start tile
		for(Player p: players){
			Tile tile = p.determineStartTile();
			p.move((Location) tile);
		}
	}
	
	/**
	 * Returns the Letter to represent the  tiles starting character
	 * 
	 * @return letter
	 */
	public static String getCharacterName(Character character){
		switch(character){
			case MISS_SCARLETT: return"Miss Scarlett";
			case COLONEL_MUSTARD: return "Colonel Mustard";
			case MRS_WHITE: return "Mrs White";
			case THE_REVERAND_GREEN: return "The Reverand Green";
			case MRS_PEACOCK: return "Mrs Peacock";
			case PROFESSOR_PLUM: return "Professor Plum";
		}return " ";		
	}

	
	


	/**
	 * Returns a randam character that isn't currently in use
	 * 
	 * @return - character
	 */
	public Character generateCharacter() {
		List<Player.Character> notInUse = charactersLeft;
		Collections.shuffle(notInUse);
		return (notInUse.get(0));
	}

	/**
	 * Create a player in the game with a specific character
	 * 
	 * @param i
	 *            - player number(1-6)
	 * @param character
	 *            - character to assign to player
	 */
	public Character addPlayer(int i, Character character, String name) {
		players.add(new Player(this, board, i, character, name));
		charactersLeft.remove(character);
		return character;
	}

	
	
	/**
	 * Sets up the next Turn
	 * 
	 * @return - player
	 */
	public Player nextTurn() {
		Player nextPlayer = getNextPlayer();
		
		if(playersOut.contains(currentPlayer)){
			players.remove(currentPlayer);
			numPlayers--;
		}
		
		if(numPlayers == 1){
			winner = players.get(0);
			Game.gameState = Game.State.OVER;
		}
		
		return nextPlayer;
		
	}

	/**
	 * roll the dice
	 * 
	 * @return int - dice roll
	 */
	public int rollDice() {
		return (int) (Math.random() * 6) + 1;
	}

	/**
	 * Determines the move Locations for a particular player
	 * 
	 * @param player
	 *            - player to move
	 * @param diceRoll
	 *
	 * @return moveableTiles - List of tiles where the player could move too
	 */
	public List<Tile> determineMoveLocations(Player player, int diceRoll) {
		Location location = player.getLocation();
		Tile tile = null;
		List<Tile> moveableTiles = new ArrayList<>();

		// if they're moving from a tile
		if (location instanceof Tile) {
			tile = (Tile) location;
			// call the recursive method on the tile
			moveableTiles = recursiveCheck(diceRoll, tile, moveableTiles);
			// remove first tile
			moveableTiles.remove(location);

			// if they're moving from a room
		} else if (location instanceof Room) {
			// call the recursive method on all the door tiles from that room
			for (Tile t : getTiles()) {
				if (t instanceof DoorTile) {
					if (currentPlayer.getLocation().equals(getRoom(((DoorTile) t).getRoom()))) {
						moveableTiles = recursiveCheck(diceRoll, t, moveableTiles);
						// remove the first tile each time
						moveableTiles.remove(t);

					}
				}
			}
		}
		
		for(Player p: getPlayers())
			if(p.getLocation() instanceof Tile)
				moveableTiles.remove(p.getLocation());
		
		for(Player p: getPlayersOut())
			if(p.getLocation() instanceof Tile)
				moveableTiles.remove(p.getLocation());
		return moveableTiles;
	}

	/**
	 * Recursive method helper for determine move location. Checks all tiles in
	 * every direction of a tile to see if the player can move there
	 * 
	 * @param steps
	 *            - number of steps player would have left at this tile
	 * @param tile
	 *            - tile to check surrounding tiles from
	 * @param moveableTiles
	 *            - list of tiles player can move too
	 * 
	 * @return moveableTiles - list of tiles player can move too
	 */
	public List<Tile> recursiveCheck(int steps, Tile tile, List<Tile> moveableTiles) {

		// if tile is null of run out of steps then return
		if (tile == null || steps == -1)
			return moveableTiles;

		// add current tile if its not there already
		int x = tile.getX();
		int y = tile.getY();
		if (!moveableTiles.contains(tile))
			moveableTiles.add(tile);

		// call recursive method in every direction
		moveableTiles = recursiveCheck(steps - 1, board.getTile(x - 1, y), moveableTiles);
		moveableTiles = recursiveCheck(steps - 1, board.getTile(x + 1, y), moveableTiles);
		moveableTiles = recursiveCheck(steps - 1, board.getTile(x, y - 1), moveableTiles);
		moveableTiles = recursiveCheck(steps - 1, board.getTile(x, y + 1), moveableTiles);

		return moveableTiles;
	}

	/**
	 * deal the cards in the deck to all the players such that every player has
	 * an equal number of cards, the remaining cards are left in the deck
	 */
	public void dealCards() {
		boolean done = false;
		
		while (!done) {

			// only deal out cards if theres at least one for each player
			if (deck.size() >= numPlayers) {
				for(Player p: players){
					p.addCard(deck.get(0));
					deck.remove(0); // remove from deck
				}

			} else
				done = true;
		}
	}

	/**
	 * Checks a suggestion againt the rest of the players hands
	 * 
	 * @param suspect
	 *            - suspected murderer
	 * @param weapon
	 *            - suspected murder weapon
	 * @param room
	 *            - suspected murder room
	 * 
	 * @return string - string that says who refuted the suggestion, null if the
	 *         suggestion was successful
	 */
	public String suggestion(Character suspect, Weapon weapon, Room room) {

		Location location = currentPlayer.getLocation();
		if (!(location instanceof Room)) {
			return null;
		} else {
			if (!location.equals(room))
				return null;
		}

		// move weapon to the suggestion location
		weapon.move(room);
		Player player = null;
		for (Player p : players)
			if (p.getCharacter().equals(suspect))
				player = getPlayer(suspect);

		// if the suspect is in the game, move them there too
		if (player != null)
			player.move(room);

		// create a list of all the players
		// apart from the player making the suggestion
		List<Player> allPlayers = new ArrayList<>();
		allPlayers.addAll(players);
		allPlayers.addAll(playersOut);
		allPlayers.remove(currentPlayer);

		// finally search through the player hand for any matches
		for (Player p : allPlayers) {
			List<Card> hand = p.getHand();
			if (hand.contains(getCard(suspect)))
				return "Suggestion Refuted, " + p.getName() + " has " + Game.getCharacterName(suspect) + " in his/her hand";
			else if (hand.contains(getCard(weapon)))
				return "Suggestion Refuted, " + p.getName() + " has the " + weapon.getName() + " in his/her hand";
			else if (hand.contains(getCard(room)))
				return "Suggestion Refuted, " + p.getName() + " has the " + room.getName() + " in his/her hand";
		}
		return "";
	}

	/**
	 * moves a player to the corresponding room as that stairway would suggest
	 */
	public void useStairway() {
		String dest = ((Room) currentPlayer.getLocation()).getStairwayTo();
		Room destination = getRoom(dest);
		currentPlayer.move(destination);

	}

	/**
	 * Checks an accusation against the solution
	 * 
	 * @param suspect
	 *            - accused murderer
	 * @param weapon
	 *            - accused murder weapon
	 * @param room
	 *            - accused murder room
	 * 
	 * @return boolean - true if all three match
	 */
	public Boolean accusation(Character suspect, Weapon weapon, Room room) {
		if (suspect.equals(basement.getMurderCharacter()) && weapon.equals(basement.getMurderWeapon())
				&& room.equals(basement.getMurderRoom())) {
			return true;
		}
		playersOut.add(getCurrentPlayer()); // add player to removed later
		return false;
	}

	// getters and Setters

	public List<Player> getPlayers() {
		return players;
	}

	public List<Player> getPlayersOut() {
		return playersOut;
	}

	public List<Player.Character> getCharacters() {
		return characters;
	}

	public List<Player.Character> getCharactersLeft() {
		return charactersLeft;
	}

	public List<Card> getCards() {
		return cards;
	}

	public List<Card> getCardsLeft() {
		return deck;
	}

	public Card getCard(Object o) {
		for (Card c : cards) {

			if (o instanceof Character)
				if (c instanceof SuspectCard)
					if (((Character) o).equals(((SuspectCard) c).getCharacter()))
						return c;

			if (o instanceof Weapon)
				if (c instanceof WeaponCard)
					if (((Weapon) o).equals(((WeaponCard) c).getWeapon()))
						return c;

			if (o instanceof Room)
				if (c instanceof RoomCard)
					if (((Room) o).equals(((RoomCard) c).getRoom()))
						return c;
		}
		return null;
	}

	public Player getPlayer(Character character) {
		for (Player p : players)
			if (p.getCharacter().equals(character))
				return p;
		return null;
	}

	public Player getPlayer(int num) {
		for (Player p : players)
			if (p.getNum() == num)
				return p;
		return null;
	}

	public Room getRoom(String name) {
		for (Room r : rooms)
			if (r.getName().equals(name))
				return r;
		return null;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public Weapon getWeapon(String name) {
		for (Weapon w : weapons)
			if (w.getName().equals(name))
				return w;
		return null;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Basement getBasement() {
		return basement;
	}

	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}
	
	public List<Weapon> getWeapons(){
		return weapons;
	}
	
	public Player getNextPlayer(){
		int index = 0;
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).equals(currentPlayer))
				index = i;
		}
		index++;
		if(index == players.size())
				index = 0;
		
		return players.get(index);
			
	}

	public List<Tile> getTiles() {
		List<Tile> tiles = new ArrayList<>();
		for (int y = 0; y < 24; y++) {
			for (int x = 0; x < 25; x++) {
				tiles.add(board.getTile(x, y));
			}
		}
		return tiles;
	}

	public Player getWinner(){
		return winner;
	}
	
	public void setWinner(Player winner){
		this.winner = winner;
	}
	
	
	public void setNumPlayers(int num) {
		this.numPlayers = num;
	}


}

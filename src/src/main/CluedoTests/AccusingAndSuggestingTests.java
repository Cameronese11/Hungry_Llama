package src.main.CluedoTests;

import static org.junit.Assert.*;

import org.junit.Test;

import src.main.Cards.Card;
import src.main.Cards.RoomCard;
import src.main.Cards.WeaponCard;
import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Basement;
import src.main.GameObject.Player;
import src.main.GameObject.Weapon;
import src.main.Location.Location;
import src.main.Location.Room;
import src.main.Location.Tile;

/**
 * Class for all the Accusing and Suggestion tests
 * 
 * @author cameronmclachlan
 *
 */
public class AccusingAndSuggestingTests {

	@Test
	public void validAccusation() 
	// checks that a valid accusation returns true 
	{
		//Setup
		Board board = new Board("resources/board.txt");
		Game game = new Game(board);
		Player p1 = setupMockPlayer(game, board.getTile(7, 8), 1);
		Player p2 = setupMockPlayer(game, board.getTile(17, 7), 2);
		Player p3 = setupMockPlayer(game, board.getTile(6, 17), 3);
		Player p4 = setupMockPlayer(game, board.getTile(15, 15), 4);
		Player p5 = setupMockPlayer(game, board.getTile(21, 19), 5);
		game.setNumPlayers(5);
		game.dealCards();
		game.setCurrentPlayer(p1);
		Basement basement = game.getBasement();
		
		// Action / Check
		assertTrue( // make a correct accusation
				game.accusation(basement.getMurderCharacter(), basement.getMurderWeapon(), basement.getMurderRoom()));
	}

	@Test
	public void invalidAccusation()
	// checks that an invalid accusation returns false, 
	// and removes the player from the game
	{
		//Setup
		Board board = new Board("resources/board.txt");
		Game game = new Game(board);
		Player p1 = setupMockPlayer(game, board.getTile(7, 8), 1);
		Player p2 = setupMockPlayer(game, board.getTile(17, 7), 2);
		game.setNumPlayers(2);
		game.dealCards();
		game.setCurrentPlayer(p1);
		Basement basement = game.getBasement();
		Card card = game.getCard("Candlestick");
		if(p1.getHand().contains(card))
			p1.getHand().remove(card);
		p2.addCard(game.getCard("Candlestick"));
		
		assertFalse(
			game.accusation(basement.getMurderCharacter(),game.getWeapon("Candlestick"),basement.getMurderRoom()));
		
		
		// Checks
		assertTrue(game.getPlayers().size() == 1); // should now be one player left
		assertTrue(game.getPlayersOut().contains(p1)); // should contain the player who made the accusation
		assertTrue(game.getPlayersOut().size() == 1); // all players should be in "players"
	}

	

	@Test
	public void validRefutedSuggestion() 
	// Valid suggestion that is refuted,
	// Valid in that it is done within a room
	// Should be refuted
	{
		//Setup
		Board board = new Board("resources/board.txt");
		Game game = new Game(board);
		Room location = game.getRoom("Lounge");
		Player p1 = setupMockPlayer(game, location, 1,Player.Character.MISS_SCARLETT);
		Player p2 = setupMockPlayer(game, board.getTile(17, 7), 2, Player.Character.COLONEL_MUSTARD);
		game.setNumPlayers(2);
		game.dealCards();
		game.setCurrentPlayer(p1);
		p2.addCard(game.getCard("Candlestick")); // ensures suggestion will fail
		game.setCurrentPlayer(p1);
		
		// ensures candlestick must be moved into the correct room
		Weapon weapon = game.getWeapon("Candlestick"); 
		Room oldRoom = game.getRoom(weapon.getRoom());
		oldRoom.removeWeapon(weapon);
		game.getRoom("Study").addWeapon(weapon);
		
		// Check / Action
		assertFalse(game.suggestion(Player.Character.COLONEL_MUSTARD, weapon, location).equals(""));
		
		// Checks
		assertTrue(p2.getLocation().equals(location)); // player 2 must be moved into the room
		assertTrue(game.getRoom(weapon.getRoom()).equals(location)); // weapon should have moved into the room
	}
	
	@Test
	public void validUnrefutedSuggestion() 
	// Valid suggestion that is refuted,
	// Valid in that it is done within a room
	// Should be unrefuted
	{
		// Setup
		Board board = new Board("resources/board.txt");
		Game game = new Game(board);
		Basement basement = game.getBasement(); 
		Room location = basement.getMurderRoom();
		Player p1 = null;
		if(!basement.getMurderCharacter().equals(Player.Character.MISS_SCARLETT))
			p1 = setupMockPlayer(game, location, 1,Player.Character.MISS_SCARLETT);
		else
			p1 = setupMockPlayer(game, location, 1,Player.Character.MRS_WHITE);
		Player p2 = setupMockPlayer(game, board.getTile(17, 7), 2, basement.getMurderCharacter());
		game.setNumPlayers(2);
		game.dealCards();
		game.setCurrentPlayer(p1);
		
		// Check / Action
		assertTrue(game.suggestion(basement.getMurderCharacter(), basement.getMurderWeapon(), basement.getMurderRoom()).equals(""));
		
		// Checks
		assertTrue(p2.getLocation().equals(location)); // player 2 must be moved into the room
		assertTrue(game.getRoom(basement.getMurderWeapon().getRoom()).equals(location)); // weapon should have moved into the room
	}

	@Test
	public void invalidSuggestion() 
	// Invalid suggestion 
	// Invalid in that it is not done within a room
	{
		//Setup 
		Board board = new Board("resources/board.txt");
		Game game = new Game(board);
		Player p1 = setupMockPlayer(game, board.getTile(7, 8), 1);
		Player p2 = setupMockPlayer(game, board.getTile(17, 7), 2);
		game.setNumPlayers(2);
		game.dealCards();
		game.setCurrentPlayer(p1);
		Basement basement = game.getBasement();
		
		// Check / Action
		assertTrue(game.suggestion(basement.getMurderCharacter(), basement.getMurderWeapon(), basement.getMurderRoom()) == null);
		// Should be null because suggestion is invalid
	}

	@Test
	public void invalidSuggestion2() 
	// Invalid suggestion 
	// Invalid in that the room suggested is not
	// the room the player is in
	{
		// Setup
		Board board = new Board("resources/board.txt");
		Game game = new Game(board);
		Room location = game.getRoom("Lounge");
		Player p1 = setupMockPlayer(game, location, 1);
		Player p2 = setupMockPlayer(game, board.getTile(17, 7), 2);
		game.setNumPlayers(2);
		game.dealCards();
		game.setCurrentPlayer(p1);
		Basement basement = game.getBasement();
		
		// Check / Action
		assertTrue(game.suggestion(basement.getMurderCharacter(), basement.getMurderWeapon(), game.getRoom("Hall")) == null);
		// Should be null because suggestion is invalid
	}

	
	
	
	
	// Test Helper Methods //
	
	/**
	 * Adds a new player to the game at a specific location with a 
	 * particular player number
	 * 
	 * @param game
	 * @param location - player location
	 * @param playerNum 
	 * 
	 * @return - return the player
	 */
	public Player setupMockPlayer(Game game, Location location, int playerNum) {
		
		// Create player
		game.generatePlayer(playerNum);
		Player player = game.getPlayer(playerNum);
		
		// set the players location
		player.setLocation(location);
		
		// set the locations player field
		if (location instanceof Room)
			((Room) location).addPlayer(player);
		else
			((Tile) location).setPlayer(player);
		return player;
	}

	/**
	 * Adds a new player to the game at a specific location with a 
	 * particular player number
	 * 
	 * @param game
	 * @param location - player location
	 * @param playerNum 
	 * 
	 * @return - return the player
	 */
	public Player setupMockPlayer(Game game, Location location, int playerNum, Player.Character character) {
		
		// Create player
		game.addPlayer(playerNum, character);
		Player player = game.getPlayer(playerNum);
		
		// set the players location
		player.setLocation(location);
		
		// set the locations player field
		if (location instanceof Room)
			((Room) location).addPlayer(player);
		else
			((Tile) location).setPlayer(player);
		return player;
	}
	
	/**
	 * print all the rooms and the players inside them
	 * 
	 * @param game
	 */
	public void printRooms(Game game) {
		System.out.println("////////    Rooms    ////////");
		System.out.println();
		for (Room r : game.getRooms()) { // iterate through all the rooms
			System.out.println(r.getName());
			for (Player p : r.getPlayers()) { // print all the players in the room
				System.out.println("  Player: " + p.getCharacter());
			}
			System.out.println();
			for (Weapon w : r.getWeapons()) { // print all the weapons in the room
				System.out.println("  Weapon: " + w.getName());
			}
			System.out.println();
		}
		System.out.println();
	}

}


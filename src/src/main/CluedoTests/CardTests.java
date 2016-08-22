package src.main.CluedoTests;

import static org.junit.Assert.*;

import org.junit.Test;

import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Basement;
import src.main.GameObject.Player;
import src.main.Location.Location;
import src.main.Location.Room;
import src.main.Location.Tile;

/**
 * Tests for all the Cards
 */
public class CardTests {

	@Test
	public void dealPlayerCards() 
	// Deal all the cards to the players, ansures the correct 
	// number of cards are distributed to the players, and that 
	// the correct amount are left over
	{
		// Setup
		Board board = new Board("src/resources/board.txt");
		Game game = new Game(board);
		Player p1 = setupMockPlayer(game, board.getTile(7, 8), 1);
		Player p2 = setupMockPlayer(game, board.getTile(17, 7), 2);
		Player p3 = setupMockPlayer(game, board.getTile(6, 17), 3);
		Player p4 = setupMockPlayer(game, board.getTile(15, 15), 4);
		Player p5 = setupMockPlayer(game, board.getTile(21, 19), 5);
		game.setNumPlayers(5);
		
		// Precondition
		assertTrue(game.getCards().size() == 21); 
		
		// Action
		game.dealCards();
		
		// Checks
		for (Player p : game.getPlayers()) { // all players should have 3 ards each
			assertTrue(p.getHand().size() == 3);
		}
		assertTrue(game.getCards().size() == 21); // all cards should be in "Cards"
		assertTrue(game.getCardsLeft().size() == 3); // should be 3 cards left over in the deck
	}

	@Test
	public void dealSoultionCards()
	// Makes sure the soultion is placed
	// in the basement
	{
		// Setup
		Board board = new Board("src/resources/board.txt");
		Game game = new Game(board);
		Player p1 = setupMockPlayer(game, board.getTile(7, 8), 1);
		Player p2 = setupMockPlayer(game, board.getTile(17, 7), 2);
		Player p3 = setupMockPlayer(game, board.getTile(6, 17), 3);
		Player p4 = setupMockPlayer(game, board.getTile(15, 15), 4);
		Player p5 = setupMockPlayer(game, board.getTile(21, 19), 5);
		Basement basement = game.getBasement();
		game.setNumPlayers(5);
		
		// Action
		game.dealCards();
		
		// Checks
		assertTrue(basement.getMurderCharacter() != null); // check that the solution is in the basement
		assertTrue(basement.getMurderRoom() != null);
		assertTrue(basement.getMurderWeapon() != null);
	}

	@Test
	public void solutionSeperateFromHands() 
	// Deals the cards and ensures the soultion cards are 
	// seperate from the players hands
	{
		// Setup
		Board board = new Board("src/resources/board.txt");
		Game game = new Game(board);
		Player p1 = setupMockPlayer(game, board.getTile(7, 8), 1);
		Player p2 = setupMockPlayer(game, board.getTile(17, 7), 2);
		Player p3 = setupMockPlayer(game, board.getTile(6, 17), 3);
		Player p4 = setupMockPlayer(game, board.getTile(15, 15), 4);
		Player p5 = setupMockPlayer(game, board.getTile(21, 19), 5);
		Basement basement = game.getBasement();
		game.setNumPlayers(5);
		
		// Action
		game.dealCards();
		
		// Checks
		for (Player p : game.getPlayers()) { // checks every players hands for the solution cards
			if (p.getHand().contains(basement.getMurderCharacter()) || p.getHand().contains(basement.getMurderRoom())
					|| p.getHand().contains(basement.getMurderWeapon())) {
				assertFalse(true);
			}

		}

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
		game.addPlayer(playerNum, Player.Character.COLONEL_MUSTARD, "");
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
	 * Checks every tile on the bard to see if there is any players on it
	 * Assert will fail if there is a player on a tile that is not passed 
	 * in to the locations parameter
	 * 
	 * @param board
	 * @param locations - the locations where players CAN be
	 */
	public void checkTiles(Board board, Tile... locations) {
		
		// Iterate through board
		for (int x = 0; x < 24; x++) {
			for (int y = 0; y < 25; y++) {
				if (board.getTile(x, y) instanceof Tile) { // make sure current tile is a tile(not null)
					if (locations == null){ // means we can check every tile
						assertTrue(((Tile) board.getTile(x, y)).getPlayer() == null);
					}else{// means we must look through locations tiles
						boolean inLocations = false;
						for(int i = 0; i < locations.length; i++){
							if(board.getTile(x, y).equals(locations[i])){
								inLocations = true;
							}
						}
						if(inLocations == false) // cant possibly be in "locations" so can check it
							assertTrue(((Tile) board.getTile(x, y)).getPlayer() == null); // check if it is holding a player
					}
				}
			}
		}
	}


	/**
	 * Checks every room in the game to see if there is any players in it
	 * Assert will fail if there is a player in a room that is not passed 
	 * in to the location parameter
	 * 
	 * @param game
	 * @param location - location not to check
	 */
	public void checkRooms(Game game, Room location) {
		
		// iterate through all the rooms
		for (Room r : game.getRooms()) {
			if (location == null) // can check all the rooms
				assertTrue(r.getPlayers().size() == 0);
			else if (!r.equals(location)) // can check if its not the location
				assertTrue(r.getPlayers().size() == 0);
		}
	}

}

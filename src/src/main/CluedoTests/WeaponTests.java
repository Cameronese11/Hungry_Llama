package src.main.CluedoTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Player;
import src.main.GameObject.Weapon;
import src.main.Location.Room;

/**
 * Class for all the weapon tests
 * 
 * @author cameronmclachlan
 *
 */
public class WeaponTests {

	@Test
	public void validWeaponRoomToRoom() 
	// Moves a weapon from between rooms
	{
		// Setup
		Board board = new Board("resources/board.txt");
		Game game = new Game(board);
		Weapon w = game.getWeapon("Candlestick");
		Room newRoom = game.getRoom("Study");
		
		// Action
		w.move(newRoom);
		
		// Checks
		assertTrue(w.getRoom().equals(newRoom.getName()));// The weapons room field should equal the correct room
		assertTrue(newRoom.getWeapons().contains(w)); 	  // the room the weapon is in should be correct
		checkRooms(game, newRoom, w); 					  // the weapon should not be in any other room
	}

	

	// Test Helper Methods

	/**
	 * Checks every room in the game to see if there a particular weapon is in it
	 * Assert will fail if there that weapon is in a room that is not passed 
	 * in to the location parameter
	 * 
	 * @param game
	 * @param location - location not to check
	 * @param weapon - the weapon to look for
	 */
	public void checkRooms(Game game, Room location, Weapon weapon) {
		
		// iterate through all the rooms
		for (Room r : game.getRooms()) {// can check all the rooms
			if (location == null)
				assertTrue(!r.getWeapons().contains(weapon));
			else if (!r.equals(location)) // can check if its not the location
				assertTrue(!r.getWeapons().contains(weapon));
		}
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

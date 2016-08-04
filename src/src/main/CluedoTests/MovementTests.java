package src.main.CluedoTests;

import static org.junit.Assert.*;

import org.junit.Test;

import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Player;
import src.main.Location.Location;
import src.main.Location.Tile;
import src.main.Location.Room;

/**
 * tests for all the actions a player can do
 * @author cameronmclachlan
 *
 */
public class MovementTests {

	
	//// Player Movement Tests ////
	
	@Test
	public void validPlayerTileToTile()
	// Moving a player from a moveTile to another vaild moveTile 
	// e.g. its within the number of squares of the diceroll
	{
		// Setup
		Board board = new Board("resources/board.txt");
		Game game = new Game(board);
		Location oldLocation = (Location) board.getTile(17,10); // 18,11
		Location newLocation = (Location) board.getTile(9,17);  // 10,18
		Player p = setupMockPlayer(game, oldLocation);
		
		board.printBoard();
		p.move(newLocation);
		board.printBoard();
		
		assertTrue(p.getLocation().equals(newLocation));
		assertTrue(((Tile) newLocation).getPlayer().equals(p));
		
		if(newLocation instanceof Tile)
			checkTiles(board, (Tile) newLocation);
		else
			checkRooms(game, (Room) newLocation);
	}
	
	@Test
	public void invalidPlayerTileToTile(){}


	
	@Test
	public void validPlayerTileToRoom(){}
	
	@Test
	public void invalidPlayerTileToRoom(){}
	
	
	@Test
	public void validPlayerRoomToTile(){}
		
	
	@Test
	public void invalidPlayerRoomToTile(){}
	

	@Test
	public void validPlayerRoomToRoom(){}
		

	@Test
	public void invalidPlayerRoomToRoom(){}

	@Test
	public void validPlayerStairwayRoomToRoom(){}
	
	@Test
	public void invalidPlayerStairwayRoomToRoom(){}
	
	
	
	// Weapon Movement Tests
	
	@Test
	public void validWeaponRoomToRoom(){}
	
	@Test
	public void invalidWeaponToRoom(){}
	
	
	
	// TestHelperMethods
	


		public Player setupMockPlayer(Game game, Location location){
			game.addPlayer(1, Player.Character.MISS_SCARLETT);
			Player player = game.getPlayer(1);
			player.setLocation(location);
			if(location instanceof Room)
				((Room) location).addPlayer(player);
			else
				((Tile) location).setPlayer(player);
			return player;
		}
		
		public void checkTiles(Board board, Tile newLocation){
						for(int x = 0; x < 24; x++)
							for(int y = 0; y < 25; y++)
								if(board.getTile(x, y) instanceof Tile)
									if(!board.getTile(x, y).equals(newLocation))
										assert ((Tile) board.getTile(x, y)).getPlayer() == null;	
			
		}

		public void checkRooms(Game game, Room newLocation){
			for(Room r: game.getRooms()){
					if(!r.equals(newLocation))
						assertTrue(r.getPlayers().size() == 0);
			}
		}

	

	

}

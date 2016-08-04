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
		
		// Test
		p.move(newLocation);
		
		
		board.printBoard();
		
		// Check
		assertTrue(p.getLocation().equals(newLocation));
		assertTrue(((Tile) newLocation).getPlayer().equals(p));
		
		// check that the player isnt located on any other tile
		for(int x = 0; x < 24; x++)
			for(int y = 0; y < 25; y++)
				if(board.getTile(x, y) instanceof Tile)
					if(!board.getTile(x, y).equals(newLocation))
						assertTrue(((Tile) board.getTile(x, y)).getPlayer() == null);
		
		// check that the player isn't located in any other room
		for(Room r: game.getRooms()){
			if(newLocation instanceof Room)
				if(!r.equals(newLocation))
					assertTrue(r.getPlayers().size() == 0);
		}
	}
	
	@Test
	public void invalidPlayerTileToTile(){
		
	}


	
	@Test
	public void validPlayerTileToRoom(){
		// Moving a player from a moveTile to another room 
		// e.g. its within the number of squares of the diceroll
		{
			// Setup
			Board board = new Board("resources/board.txt");
			Game game = new Game(board);
			Location oldLocation = (Location) board.getTile(17,10); // 18,11
			Location newLocation = game.getRoom("Kitchen");
			Player p = setupMockPlayer(game, oldLocation);
			
			board.printBoard();
			
			// Test
			p.move(newLocation);
			
			
			board.printBoard();
			
			// Check
			assert p.getLocation().equals(newLocation);
			assert ((Tile) newLocation).getPlayer().equals(p);
			
			// check that the player isnt located on any other tile
			for(int x = 0; x < 24; x++)
				for(int y = 0; y < 25; y++)
					if(board.getTile(x, y) instanceof Tile)
						if(!board.getTile(x, y).equals(newLocation))
							assert ((Tile) board.getTile(x, y)).getPlayer() == null;	
		
			// check that the player isn't located in any other room
			for(Room r: game.getRooms()){
				if(newLocation instanceof Room)
					if(!r.equals(newLocation))
						assertTrue(r.getPlayers().size() == 0);
			}
		}
	}
	
	@Test
	public void invalidPlayerTileToRoom(){
		
	}
	
	
	@Test
	public void validPlayerRoomToTile()
		// Moving a player from a moveTile to another room 
		// e.g. its within the number of squares of the diceroll
		{
			// Setup
			Board board = new Board("resources/board.txt");
			Game game = new Game(board);
			Location oldLocation = game.getRoom("Kitchen");
			Location newLocation = (Location) board.getTile(17,10); // 18,11
			Player p = setupMockPlayer(game, oldLocation);
				
			board.printBoard();
			
			// Test
			p.move(newLocation);
					
					
			board.printBoard();
					
			// Check
			assert p.getLocation().equals(newLocation);
			assert ((Tile) newLocation).getPlayer().equals(p);
					
			// check that the player isnt located on any other tile
			for(int x = 0; x < 24; x++)
				for(int y = 0; y < 25; y++)
					if(board.getTile(x, y) instanceof Tile)
						if(!board.getTile(x, y).equals(newLocation))
							assert ((Tile) board.getTile(x, y)).getPlayer() == null;	
				
			// check that the player isn't located in any other room
			for(Room r: game.getRooms()){
				if(newLocation instanceof Room)
					if(!r.equals(newLocation))
						assertTrue(r.getPlayers().size() == 0);
				}
			}
	
	@Test
	public void invalidPlayerRoomToTile(){
		
	}
	

	@Test
	public void validPlayerRoomToRoom(){
		
	}
	@Test
	public void invalidPlayerRoomToRoom(){
		
	}

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



}

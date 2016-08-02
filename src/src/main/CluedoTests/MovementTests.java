package src.main.CluedoTests;

import org.junit.Test;

import src.main.Cluedo.Board;
import src.main.Cluedo.Game;

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
		Board board = new Board("reasources/board.txt");
		Game game = new Game(board);
		
		
		
	}
	
	@Test
	public void invalidPlayerTileToTile(){
		
	}

	
	
	
	
	
	
	
	
	@Test
	public void validPlayerTileToRoom(){
		
	}
	
	@Test
	public void invalidPlayerTileToRoom(){
		
	}
	
	
	@Test
	public void validPlayerRoomToTile(){
		
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





}

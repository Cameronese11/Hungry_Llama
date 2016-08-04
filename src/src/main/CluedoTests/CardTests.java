package src.main.CluedoTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Player;
import src.main.Location.Location;
import src.main.Location.Room;
import src.main.Location.Tile;

public class CardTests {


	
	@Test
	public void dealCards(){}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Test Helper Methods


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

package src.main.Cluedo;

import src.main.GameObject.Room;

/**
 * Represents a stairway tile
 * 
 * @author cameronmclachlan
 *
 */
public class StairTile extends Tile {

	private String roomLocation;
	private String roomDestination;
	
	/**
	 * Constructs a new stairTile object
	 * 
	 * @param x
	 * @param y
	 * @param roomLocation - room that the stairTile is in
	 */
	public StairTile(int x, int y, String roomLocation) {
		super(x, y);
		this.roomLocation = roomLocation;
		determineDestinationRoom();
	}

	/**
	 * Determine the corresponding destination for 
	 * the stairway tile
	 */
	public void determineDestinationRoom(){
		if(roomLocation == "Study")
			roomDestination = "Kitchen";
		else if(roomLocation == "Kitchen")
			roomDestination = "Study";
		else if(roomLocation == "Lounge")
			roomDestination = "Conservatory";
		else roomDestination = "Lounge";
	}
	
	/**
	 * print the tile
	 */
	public void print(){
		System.out.print("[S]");
	}
}

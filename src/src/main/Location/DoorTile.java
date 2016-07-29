package src.main.Location;

/**
 * Represents a Tile of the board which 
 * is a doorway to a room
 * 
 * @author cameronmclachlan
 */
public class DoorTile extends MoveTile implements Tile {

	private String room;
	
	/**
	 * Constructs a new DoorTile object
	 * 
	 * @param x
	 * @param y
	 * @param room - room which the doorway enters
	 */
	public DoorTile(int x, int y, String room) {
		super(x, y);
		this.room = room;
	}

	// print this tile
	public void print(){
		System.out.print("[D]");
	}
	
	public String getRoom(){
		return room;
	}
}

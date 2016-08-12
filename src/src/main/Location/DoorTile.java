package src.main.Location;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Represents a Tile of the board which 
 * is a doorway to a room
 */
public class DoorTile extends Tile{

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

	/**
	 * prints the tile
	 */
	public void paint(Graphics g){
		g.setColor(new Color(224,232,185));
		g.fillRect((x * SIZE) + 20, (y * SIZE) + 4, SIZE, SIZE);
		g.setColor(Color.black);
		g.drawRect((x * SIZE) + 20, (y * SIZE) + 4, SIZE, SIZE);
		
	}
	
	// Getters and Setters
	
	public String getRoom(){
		return room;
	}
}

package src.main.Location;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

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
	
	public void paint(Graphics g, List<Tile> moveableLocations, Point p){
		Graphics2D g2D = (Graphics2D) g;
		if(moveableLocations.contains(this)){
			g.setColor(Color.green);
			g.fillRect(xPos, yPos, SIZE, SIZE);
			g.setColor(Color.black);
			g.drawRect(xPos, yPos, SIZE, SIZE);
		}else{
			g.setColor(new Color(224,232,185));
			g.fillRect(xPos, yPos, SIZE, SIZE);
			g.setColor(Color.black);
			g.drawRect(xPos, yPos, SIZE, SIZE);
		}
		Rectangle r = new Rectangle(xPos, yPos, SIZE, SIZE);
		if(p != null){
			if(r.contains(p)){
				g2D.setStroke(new BasicStroke(2));
				g2D.setColor(Color.red);
				g2D.drawRect(xPos+2, yPos+2, SIZE-3, SIZE-3);
				g2D.setStroke(new BasicStroke(1));
			}
		}
			
		
	}
	
	// Getters and Setters
	
	public String getRoom(){
		return room;
	}
}

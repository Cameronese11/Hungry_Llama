package src.main.Location;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public interface Location {

	void paint(Graphics g, List<Tile> moveableLocations, Point p);

	/**
	 * Represents a Tile or room where a player can be stored and moved to/from
	 */
	
}

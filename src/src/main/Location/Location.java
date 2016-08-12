package src.main.Location;

import java.awt.Graphics;
import java.util.List;

public interface Location {

	void paint(Graphics g,List<Tile> moveableLocations, Tile selectedTile);

	/**
	 * Represents a Tile or room where a player can be stored and moved to/from
	 */
	
}

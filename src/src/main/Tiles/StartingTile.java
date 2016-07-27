package src.main.Tiles;


import src.main.GameObject.Player;

/**
 * Represents a tile on the board where a character 
 * starts
 * 
 * @author cameronmclachlan
 *
 */
public class StartingTile extends MoveTile implements Tile{

	private Player.Character startingCharacter;
	
	/**
	 *Constructs a new starting tile
	 * 
	 * @param x
	 * @param y
	 * @param startingCharacter
	 */
	public StartingTile(int x, int y, Player.Character startingCharacter) {
		super(x, y);
		this.startingCharacter = startingCharacter;
	}
	
	/**
	 * print the tile
	 */
	public void print(){
		if(player == null)
			System.out.print("[ ]");
		else
			System.out.print("[" + getLetter() + "]");
	}
	
	// Getters and Setters
	
	public Player.Character getCharacter(){
		return startingCharacter;
	}
}

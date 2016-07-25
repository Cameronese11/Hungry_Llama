package src.main.Cluedo;

import java.util.Scanner;

/**
 * Represents a tile on the board where a character 
 * starts
 * 
 * @author cameronmclachlan
 *
 */
public class StartingTile extends Tile {

	private String startingCharacter;
	
	/**
	 *Constructs a new starting tile
	 * 
	 * @param x
	 * @param y
	 * @param startingCharacter
	 */
	public StartingTile(int x, int y, String startingCharacter) {
		super(x, y);
		this.startingCharacter = startingCharacter;
	}

	/**
	 * Returns the first letter of the characters last name
	 * 
	 * @return letter
	 */
	public String getLetter(){
		Scanner scan = new Scanner(startingCharacter);
		String first = "";
		String last = "";
		while(last.equals("")){
			first = scan.next();
			if(!scan.hasNext())
				last = first;
		}
		return "" + last.charAt(0);
	}
	
	/**
	 * print the tile
	 */
	public void print(){
		if(startingCharacter != null)
			System.out.print("[" + getLetter() + "]");
	}
}

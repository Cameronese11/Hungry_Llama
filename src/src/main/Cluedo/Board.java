package src.main.Cluedo;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Board Class to represent the board of a Game of Cluedo
 * 
 * @author cameronmclachlan
 *
 */
public class Board {

	private Tile[][] board2D;
	
	/**
	 * Constructs a new Board Object
	 * 
	 * @param filename - file location for board.txt
	 */
	public Board(String filename){
		board2D = new Tile[24][25];
		loadBoard(filename);
	}
	
	/**
	 * Loads a new Board by reading the file and assigning the tiles to their 
	 * corresponding 2D array coordinate
	 * 
	 * @param filename - the location of the file to read
	 */
	public void loadBoard(String filename){
		
		int x = 0;
		int y = 0;
		
		try {
			
			// Scan and read file
			Scanner scan = new Scanner(new File(filename));
		    while (scan.hasNext()) {
		    	String line = scan.nextLine();
		    	Scanner sc = new Scanner(line);
		    	sc.useDelimiter(",");
		    	while(sc.hasNext()){
		    		String read = sc.next();
		    		Tile tile = null;
		    		switch(read){
		    		
		    			// Represents a particular Room Doorway
		    			case "K": tile = new DoorTile(x,y, "Kitchen"); break;
		    			case "D": tile = new DoorTile(x,y, "Dinning Room"); break;
		    			case "L": tile = new DoorTile(x,y, "Lounge"); break;
		    			case "H": tile = new DoorTile(x,y, "Hall"); break;
		    			case "s": tile = new DoorTile(x,y, "Study"); break;
		    			case "I": tile = new DoorTile(x,y, "Library"); break;
		    			case "B": tile = new DoorTile(x,y, "Billard Room"); break;
		    			case "C": tile = new DoorTile(x,y, "Conservatory"); break;
		    			case "b": tile = new DoorTile(x,y, "Ball Room"); break;
		    			case "A": tile = new DoorTile(x,y, "Basement"); break;
		    		
		    			// Represents a characters starting tile
		    			case "0": tile = new StartingTile(x,y, "Miss Scarlett"); break;
		    			case "1": tile = new StartingTile(x,y, "Colonel Mustard"); break;
		    			case "2": tile = new StartingTile(x,y, "Mrs White"); break;
		    			case "3": tile = new StartingTile(x,y, "The Reverand Green"); break;
		    			case "4": tile = new StartingTile(x,y, "Mrs Peacock"); break;
		    			case "5": tile = new StartingTile(x,y, "Professor Plum"); break;
		    		
		    			// Represents a Stairway Tile
		    			case "6": tile = new StairTile(x,y, "Kitchen"); break;
		    			case "7": tile = new StairTile(x,y, "Conservatory"); break;
		    			case "8": tile = new StairTile(x,y, "Lounge"); break;
		    			case "9": tile = new StairTile(x,y, "Study"); break;
		    		
		    			// Represents a Characters Starting Location
		    			case "S": tile = new Tile(x,y); tile.setPlayer("Miss Scarlett");break;
		    			case "M": tile = new Tile(x,y); tile.setPlayer("Colonel Mustard");break;
		    			case "W": tile = new Tile(x,y); tile.setPlayer("Mrs White");break;
		    			case "G": tile = new Tile(x,y); tile.setPlayer("The Reverand Green");break;
		    			case "P": tile = new Tile(x,y); tile.setPlayer("Mrs Peacock");break;
		    			case "p": tile = new Tile(x,y); tile.setPlayer("Professor Plum");break;
		    		
		    			// Represents a null tile
		    			case "n": tile = null; break;
		    		
		    			// Represents an ordinary Tile
		    			case "t": tile = new Tile(x,y); break;
		    		}
		    	board2D[x][y] = tile; // assign tile to corresponding board coordinate
		    	x++;
		    	}
		    y++;
		    x = 0;
		    sc.close();
		    }
		scan.close();
		} catch(IOException e){
		    e.getMessage();
		}	
	}

	/**
	 * Print board to the console
	 */
	public void printBoard(){
		
		// print top border line
		for(int i = 0; i < 72; i++)
			System.out.print("_");
		System.out.println();
		
		// loop through 2D array
		for(int y = 0; y < board2D[0].length; y++){
			for(int x = 0; x < board2D.length; x++){
				// draw tile if it exists
				if(board2D[x][y] != null)
					board2D[x][y].print();
				else // draw empty space for null tiles
					System.out.print("   ");
			// modify x according to the Room name added
			x = roomNames(x,y);	
			}
		System.out.println("|");
		}	
		
	// print bottom border line
	for(int i = 0; i < 73; i++)
		System.out.print('"');
		
	System.out.println();
	System.out.println();
	System.out.println();
	}

	
	
	/**
	 * Determines the appropriate room name to print according to the 
	 * given coordinates. Changes x accordingly
	 * 
	 * @param x
	 * @param y
	 * @return x - updated x value
	 */
	public int roomNames(int x, int y){
		
		
		if(x == 0 && y == 3){
			System.out.print(" Kitchen ");
			x = x + 3;
		}else if(x == 10 && y == 3){
			System.out.print(" Ball ");
			x = x + 2;
		}else if(x == 10 && y == 4){
			System.out.print(" Room ");
			x = x + 2;
		}else if(x == 17 && y == 3){
			System.out.print("   Conservatory");
			x = x + 5;
		}else if(x == 0 && y == 11){
			System.out.print("   Dining");
			x = x + 3;
		}else if(x == 0 && y == 12){
			System.out.print("    Room ");
			x = x + 3;
		}else if(x == 0 && y == 11){
			System.out.print("   Dining");
			x = x + 3;
		}else if(x == 9 && y == 13){
			System.out.print("   Basement ");
			x = x + 4;
		}else if(x == 18 && y == 9){
			System.out.print("   Billiard ");
			x = x + 4;
		}else if(x == 18 && y == 10){
			System.out.print("     Room");
			x = x + 3;
		}else if(x == 18 && y == 16){
			System.out.print("  Library");
			x = x + 3;
		}else if(x == 19 && y == 22){
			System.out.print("Study ");
			x = x + 2;
		}else if(x == 10 && y == 21){
			System.out.print("Hall  ");
			x = x + 2;
		}else if(x == 1 && y == 21){
			System.out.print("Lounge");
			x = x + 2;
		}
		return x;
	}
	
	
	// Getters and Setters //
	
	/**
	 * Returns the tile at a given coordinate
	 * 
	 * @param x
	 * @param y
	 * 
	 * @return tile
	 */
	public Tile getTile(int x, int y){
		return board2D[x][y];
	}
	
	
}

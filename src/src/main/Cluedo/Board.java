package src.main.Cluedo;

import static src.main.UI.CluedoCanvas.loadImage;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;

import src.main.GameObject.Player;
import src.main.Location.DoorTile;
import src.main.Location.Room;
import src.main.Location.Tile;
import src.main.Location.StartingTile;
import src.main.Location.Tile;

/**
 * Board Class to represent the board of a Game of Cluedo
 */
public class Board {

	private Tile[][] board2D;
	

	private static final Image BOARD = loadImage("scaledGameBoard2.png");
	public static final int WIDTH = BOARD.getWidth(null);
	public static final int HEIGHT = BOARD.getHeight(null) + 19;
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
		    	sc.useDelimiter(","); // To distinguish between tiles in the file
		    	while(sc.hasNext()){
		    		String read = sc.next();
		    		Tile tile = null;
		    		switch(read){
		    		
		    			// Represents a particular Room Doorway
		    			case "K": tile = new DoorTile(x,y, "Kitchen"); break;
		    			case "D": tile = new DoorTile(x,y, "Dining Room"); break;
		    			case "L": tile = new DoorTile(x,y, "Lounge"); break;
		    			case "H": tile = new DoorTile(x,y, "Hall"); break;
		    			case "s": tile = new DoorTile(x,y, "Study"); break;
		    			case "l": tile = new DoorTile(x,y, "Library"); break;
		    			case "B": tile = new DoorTile(x,y, "Billard Room"); break;
		    			case "C": tile = new DoorTile(x,y, "Conservatory"); break;
		    			case "b": tile = new DoorTile(x,y, "Ball Room"); break;
		    		
		    			// Represents a characters starting tile
		    			case "0": tile = new StartingTile(x,y, Player.Character.MISS_SCARLETT); break;
		    			case "1": tile = new StartingTile(x,y, Player.Character.COLONEL_MUSTARD); break;
		    			case "2": tile = new StartingTile(x,y, Player.Character.MRS_WHITE); break;
		    			case "3": tile = new StartingTile(x,y, Player.Character.THE_REVERAND_GREEN); break;
		    			case "4": tile = new StartingTile(x,y, Player.Character.MRS_PEACOCK); break;
		    			case "5": tile = new StartingTile(x,y, Player.Character.PROFESSOR_PLUM); break;
		    		
		    			// Represents a Characters Position on the board
		    			case "S": tile = new StartingTile(x,y, Player.Character.MISS_SCARLETT);break;
		    			case "M": tile = new StartingTile(x,y, Player.Character.COLONEL_MUSTARD);break;
		    			case "W": tile = new StartingTile(x,y, Player.Character.MRS_WHITE); break;
		    			case "G": tile = new StartingTile(x,y, Player.Character.THE_REVERAND_GREEN);break;
		    			case "P": tile = new StartingTile(x,y, Player.Character.MRS_PEACOCK);;break;
		    			case "p": tile = new StartingTile(x,y, Player.Character.PROFESSOR_PLUM);break;
		    		
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

	public void paint(Graphics g){
		g.drawImage(BOARD, 0, 0, BOARD.getWidth(null), BOARD.getHeight(null), null);
		Graphics2D g2D = (Graphics2D) g;
		paintRooms(g);
		
		
		for(int x = 0; x < 24; x++)
			for(int y = 0; y < 25; y++){
			Tile t = getTile(x,y);
			if(t != null)
				t.paint(g2D);
			}
		
	}
	
	
	public void paintRooms(Graphics g){
		int[] KitchenXS = {23,46,46,85,85,94,94,131,131,153,153,45,45,23,23,20,20,23,23,20,20,23};
		int[] KitchenYS = {31,31,28,28,31,31,28,28,31,31,159,159,138,138,118,118,92,92,81,81,54,54};
		int[] BallRoomXS = {211,211,256,256,274,275,291,291,301,301,319,319,336,336,382,382};
		int[] BallRoomYS = {182,59,59,26,21,19,17,19,19,17,19,21,26,59,59,182};
		int[] ConservatoryXS = {438,438,445,445,498,498,508,508,561,561,567,567,571,571,567,567,571,571,567,567,571,571,567,567,545,545,462,462};
		int[] ConservatoryYS = {118,30,30,27,27,30,30,27,27,30,30,38,38,55,55,66,66,79,79,89,89,106,106,114,114,138,138,118};
		int[] BillardRoomXS = {439,439,569,569,573,573,570,570,573,573,570,570,573,573,570,570,573,573,570,570};
		int[] BillardRoomYS = {299,192,192,196,196,214,214,226,226,240,240,250,250,266,266,276,276,293,293,299};
		int[] LibraryXS = {440,440,417,417,439,439,547,547,558,558,570,570,573,573,570,570,573,573,570,570,558,558,547,547};
		int[] LibraryYS = {436,414,414,354,354,331,331,354,354,351,351,354,354,378,378,388,388,413,413,416,416,413,413,436};
		int[] StudyXS = {440,440,417,417,570,570,573,573,570,570,573,573,570,570,546,546,522,522,511,511,493,493,482,482,458,458};
		int[] StudyYS = {577,555,555,492,492,514,514,527,527,537,537,551,551,577,577,580,580,577,577,580,580,577,577,580,580,577};
		int[] HallXS = {252,252,233,233,358,358,342,342};
		int[] HallYS = {583,570,570,423,423,570,570,583};
		int[] LoungeXS = {22,22,19,19,22,22,19,19,22,22,174,174,155,155,135,135,110,110,100,100,45,45};
		int[] LoungeYS = {578,554,554,517,517,506,506,468,468,446,446,554,554,578,578,580,580,578,578,580,580,578};
		int[] DiningRoomXS = {22,130,130,199,199,  22 ,22 ,18 ,18 ,22 ,22 ,18 ,18 ,22 ,22 ,18 ,18 ,22 ,22 ,18,18,22};
		int[] DiningRoomYS = {216,216,238,238,366, 366,363,363,332,332,322,322,296,296,286,286,260,260,250,250,220,220};	
		
		g.setColor(new Color(224,232,185));
		
		g.fillPolygon(KitchenXS, KitchenYS, KitchenXS.length);
		g.fillPolygon(BallRoomXS, BallRoomYS, BallRoomXS.length);
		g.fillPolygon(ConservatoryXS, ConservatoryYS, ConservatoryXS.length);
		g.fillPolygon(BillardRoomXS, BillardRoomYS, BillardRoomXS.length);
		g.fillPolygon(LibraryXS, LibraryYS, LibraryXS.length);
		g.fillPolygon(StudyXS, StudyYS, StudyXS.length);
		g.fillPolygon(HallXS, HallYS, HallXS.length);
		g.fillPolygon(LoungeXS, LoungeYS, LoungeXS.length);
		g.fillPolygon(DiningRoomXS, DiningRoomYS, DiningRoomXS.length);
		
		g.setColor(Color.black);
		g.drawString("Kitchen", 65, 45);
		g.drawString("Ball Room", 265, 45);
		g.drawString("Conservatory", 460, 45);
		g.drawString("Dining Room", 30, 235);
		g.drawString("Billard Room", 470, 210);
		g.drawString("Library", 470, 365);
		g.drawString("Study", 480, 505);
		g.drawString("Hall", 285, 460);
		g.drawString("Lounge", 65, 460);
	}
	
	// Getters and Setters //
	
	public Tile getTile(int x, int y){
		if(x<0 || x>23 || y<0 || y>24)
			return null;
		return board2D[x][y];
	}
	
	public void setTile(int x, int y, Tile tile){
		board2D[x][y] = tile;
	}
	
	public int getWidth(){
		return WIDTH;
	}
	
	public int getHeight(){
		return HEIGHT;
	}
}




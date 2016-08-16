package src.main.GameObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import static src.main.UI.CluedoCanvas.loadImage;
import src.main.Cards.Card;
import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Player.Character;
import src.main.Location.DoorTile;
import src.main.Location.Location;
import src.main.Location.Tile;
import src.main.Location.Room;
import src.main.Location.StartingTile;

/**
 * Represents a player(user) playing the game
 */
public class Player {

	private Game game;
	private Board board;
	
	private int xPos;
	private int yPos;
	private int size;
	
	private Character character;
	private String name;
	private int num;
	private Color col;
	Location location;
	private Image img;
	private List<Card> hand;
	
	/**
	 * Characters players can be in the game
	 */
	public enum Character{
		MISS_SCARLETT,
		COLONEL_MUSTARD,
		MRS_WHITE,
		THE_REVERAND_GREEN,
		MRS_PEACOCK,
		PROFESSOR_PLUM
		
	}
	
	/**
	 * Constructs a new Player with a given 
	 * character
	 * 
	 * @param g - Current game
	 * @param b - Game board
	 * @param c - Given Character
	 */
	public Player(Game g, Board b, int n, Character c, String name){
		this.game = g;
		this.board = b;
		this.name = name;
		this.num = n;
		this.character = c;
		setColorAndPicture(c);
		hand = new ArrayList<>();
		
	}	
	
	/**
	 * Determines the starting tile for this particular players character
	 * 
	 * @return tile
	 */
	public Tile determineStartTile(){
		for(int y = 0; y < 25; y++){
			for(int x = 0; x < 24; x++){
				Tile tile = board.getTile(x, y);
				if(tile instanceof StartingTile){
					if(((StartingTile) tile).getCharacter().equals(character)){
						xPos = tile.getX() * tile.getSize() + 22;
						yPos = tile.getY() * tile.getSize() + 6;
						return tile;
					}
				}
			}
		}
		return null;			
	}
	
	/**
	 *Equals method to compare to players 
	 */
	@Override
	public boolean equals(Object player){
		if( !(player instanceof Player) || (player == null))
			return false;
		
		return (((Player) player).getCharacter().equals(getCharacter()));
	}
	
	/**
	 * Adds a card to the players hand
	 * 
	 * @param c - card
	 */
	public void addCard(Card c){
		hand.add(c);
	}
	
	/**
	 * Moves the player to a new tile
	 * 
	 * @param tile - new tile to move to
	 * @return - true if the move was successful
	 */
	public boolean move(Location newLocation){
	
		
		// if the player is moving from a tile
		if(location instanceof Tile)
			((Tile) location).setPlayer(null);
		// if the player is moving from a room
		else if(location instanceof Room)
			((Room) location).removePlayer(this);
		

		// if the player is moving to a tile
		if(newLocation instanceof Tile){
			Tile tile = (Tile) newLocation;
			
			if(tile instanceof DoorTile){
				Room room = game.getRoom(((DoorTile) tile).getRoom());
				room.addPlayer(this);
				location = room;
				xPos = room.getX();
				yPos = room.getY();
			
			}else if(tile instanceof Tile){
				((Tile) tile).setPlayer(this);
				location = (Tile) tile;
				xPos = tile.getX() * tile.getSize() + 22;
				yPos = tile.getY() * tile.getSize() + 6;
			}
		
		// if the player is moving to a room(from a suggestion only)
		}else if(newLocation instanceof Room){
			((Room) newLocation).addPlayer(this);
			Room room = (Room) newLocation;
			location = room;
		}
	
	return true;
	}
	
	public void paint(Graphics g){	
		if(col.equals(Color.yellow))
			col = col.darker();
		g.setColor(col);
		if(location instanceof Tile){
			Tile t = (Tile) location;
			g.fillOval(xPos, yPos, t.getSize() - 4 , t.getSize() - 4);
			g.setColor(Color.black);
			g.drawOval(xPos, yPos, t.getSize() - 4, t.getSize() - 4 );
		}			
	}
	
	// Getters and Setters
	
	public Image getImage(){
		return img;
	}
	public Character getCharacter(){
		return character;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public Color getColor(){
		return col;
	}
	
	public int getNum(){
		return num;
	}
	
	public List<Card> getHand(){
		return hand;
	}
	
	public String getName(){
		return name;
	}
	
	public void print(){
		System.out.print("[" + getLetter() + "]");
	}
	
	// For Tests
	public void setLocation(Location location){
		this.location = location;
		if(location instanceof Room){
			Room r = (Room) location;
			xPos = r.getX();
			yPos = r.getY();
		}else{
			Tile t = (Tile) location;
			xPos = t.getX() * t.getSize() + 22;
			yPos = t.getY() * t.getSize() + 6;
			
		}
	}
	
	/**
	 * Returns the Letter to represent the  tiles starting character
	 * 
	 * @return letter
	 */
	public String getLetter(){
		switch(character){
			case MISS_SCARLETT: return"S";
			case COLONEL_MUSTARD: return "M";
			case MRS_WHITE: return "W";
			case THE_REVERAND_GREEN: return "G";
			case MRS_PEACOCK: return "P";
			case PROFESSOR_PLUM: return "p";
		}return " ";		
	}
	
	public void setColorAndPicture(Character character){
		if(character.equals(Player.Character.COLONEL_MUSTARD)){
			col = Color.yellow;
			img = loadImage("ColonelMustard.png").getScaledInstance(200, 200,0);
		}
		if(character.equals(Player.Character.MISS_SCARLETT)){
			col = Color.red;
			img = loadImage("MissScarlett.png").getScaledInstance(200, 200,0);
		}
		if(character.equals(Player.Character.MRS_PEACOCK)){
			col = Color.blue;
			img = loadImage("MrsPeacock.png").getScaledInstance(200, 200,0);
		}
		if(character.equals(Player.Character.MRS_WHITE)){
			col = Color.white;
			img = loadImage("MrsWhite.png").getScaledInstance(200, 200,0);
		}
		if(character.equals(Player.Character.PROFESSOR_PLUM)){
			col = new Color(74,74,213);
			img = loadImage("ProfessorPlum.png").getScaledInstance(200, 200,0);
		}
		if(character.equals(Player.Character.THE_REVERAND_GREEN)){
			col = Color.green;
			img = loadImage("TheReveranGreen.png").getScaledInstance(200, 200,0);
		}
		
		
							
	}

	public void paintHand(Graphics g) {
		for(int i = 0; i < 3; i++){
			g.setColor(Color.white);
			g.fillRect(660 + i*90, 254, 80, 112);
			g.setColor(Color.BLACK);
			g.drawRect(660 + i*90, 254, 80, 112);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setFont(new Font("Calibri", Font.PLAIN, 10));
			g2d.drawString(getHand().get(i).toString(), 660 + i*90, 300);
		}
		int j = 0;
		for(int i = 3; i < hand.size(); i++){
			g.setColor(Color.white);
			g.fillRect(660 + j*90, 380, 80, 112);
			g.setColor(Color.black);
			g.drawRect(660 + j*90, 380, 80, 112);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setFont(new Font("Calibri", Font.PLAIN, 10));
			g2d.drawString(getHand().get(i).toString(), 660 + j*90, 430);
			j++;
		}
		
	}
	
	
}

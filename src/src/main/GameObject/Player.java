package src.main.GameObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
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
	
	private Card selectedCard;
	
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
				xPos = 0;
				yPos = 0;
			
			}else{
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
		g.setColor(col);
		if(location instanceof Tile){
			Tile t = (Tile) location;
			g.fillOval(xPos, yPos, t.getSize() - 4 , t.getSize() - 4);
			g.setColor(Color.black);
			g.drawOval(xPos, yPos, t.getSize() - 4, t.getSize() - 4 );
		}			
	}
	
	public void paintTag(Graphics g){
		Graphics2D g2d = (Graphics2D) g; 
		String s = Game.getCharacterName(getCharacter());
		Font font = new Font("Calibri", Font.PLAIN, 15);
		g2d.setFont(font);
		Rectangle2D r = font.getStringBounds(s, g2d.getFontRenderContext());
		g.setColor(Color.lightGray);
		g.fillRect(xPos + 10, yPos - 20,(int) r.getWidth() + 10, (int )r.getHeight());
		g.setColor(Color.black);
		g.drawRect(xPos + 10, yPos - 20,(int) r.getWidth() + 10, (int )r.getHeight());
		g2d.drawString(s, xPos + 15, yPos - 6);
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
	
	public Card getSelectedCard(){
		return selectedCard;
	}
	
	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	
	public void setXPos(int x){
		xPos = x;
	}
	
	public void setYPos(int y){
		yPos = y;
	}
	
	// For Tests
	public void setLocation(Location location){
		System.out.println("setLocation");
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
	
	
	
	public void setColorAndPicture(Character character){
		if(character.equals(Player.Character.COLONEL_MUSTARD))
			col = Color.yellow.darker();
		if(character.equals(Player.Character.MISS_SCARLETT))
			col = Color.red;
		if(character.equals(Player.Character.MRS_PEACOCK))
			col = Color.blue;
		if(character.equals(Player.Character.MRS_WHITE))
			col = Color.white;
		if(character.equals(Player.Character.THE_REVERAND_GREEN))
			col = Color.green;
		if(character.equals(Player.Character.PROFESSOR_PLUM))
			col = Color.magenta.darker();
		img = loadImage(Game.getCharacterName(character) + ".png").getScaledInstance(200, 200,16);
		
		
		
							
	}

	public void paintHand(Graphics g, Point p) {
		boolean selected = false;
		List<Card> hand = getHand();
		for(int i = 0; i < 3; i++){
			Rectangle r = new Rectangle(654 + i*92, 254, 80, 116);
			Card c = hand.get(i);
			if(r.contains(p)){
				selected = true;
				selectedCard = c;
				g.drawImage(c.getImage(1),640 + i*100, 230, null);
				g.setColor(Color.BLACK);
				g.drawRect(640 + i*100, 230, 100, 146);
			}else{
				g.drawImage(c.getImage(2),650 + i*100, 254, null);
				g.setColor(Color.BLACK);
				g.drawRect(650 + i*100, 254, 80, 116);
			}
		}
		int j = 0;
		for(int i = 3; i < hand.size(); i++){
			Rectangle r = new Rectangle(654 + j*92, 380, 80, 116);
			Card c = hand.get(i);
			if(r.contains(p)){
				selected = true;
				selectedCard = c;
				g.drawImage(c.getImage(1),640 + j*100, 376, null);
				g.setColor(Color.black);
				g.drawRect(640 + j*100, 376, 100, 146);
				j++;
			}else{
				g.drawImage(c.getImage(2),650 + j*100, 390, null);
				g.setColor(Color.black);
				g.drawRect(650 + j*100, 390, 80, 116);
				j++;
			}
			
			
			if(!selected)
				selectedCard = null;
			
			
			
		}
		
	}
	
	
}

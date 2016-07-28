package src.main.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import src.main.Cards.Card;
import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.Tiles.MoveTile;
import src.main.Tiles.StartingTile;
import src.main.Tiles.Tile;

/**
 * Represents a player(user) playing the game
 * 
 * @author cameronmclachlan
 *
 */
public class Player {

	private Game game;
	private Board board;
	
	private Character character;
	private int num;
	private MoveTile currentTile;
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
	public Player(Game g, Board b, int n, Character c){
		this.game = g;
		this.board = b;
		this.num = n;
		this.character = c;
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
					if(((StartingTile) tile).getCharacter().equals(character))
						return tile;
				}
			}
		}
		return null;			
	}
	
	/**
	 *Equals method to compare to players 
	 */
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
	public boolean move(Tile tile){
		if(tile instanceof MoveTile){
			((MoveTile) tile).setPlayer(this);
			if(currentTile != null)
				currentTile.setPlayer(null);
			currentTile = (MoveTile) tile;
		}
		
		return false;
	}
	
	public Tile getCurrentTile(){
		return currentTile;
	}
	
	// Getters and Setters
	
	public Character getCharacter(){
		return character;
	}
	
	public int getNum(){
		return num;
	}
	
	public List<Card> getHand(){
		return hand;
	}
	
	
}

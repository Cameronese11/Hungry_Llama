package src.main.Cluedo;

import src.main.GameObject.Player;

/**
 * Represnts a Tile on the board
 * 
 * @author cameronmclachlan
 *
 */
public class Tile {
	
	// Tile coordinates
	private int x;
	private int y;
	
	// player at this tile, null if none
	private String player;
	
	/**
	 * Constructs a new Tile object
	 * 
	 * @param x
	 * @param y
	 */
	public Tile(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * prints the tile
	 */
	public void print(){
		System.out.print("[ ]");
	}
	
	
	
	// Getters and Setters
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	public String getPlayer(){
		return player;
	}

	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setPlayer(String player){
		this.player = player;
	}
}

package src.main.Tiles;


import java.util.Scanner;

import src.main.GameObject.Player;
import src.main.GameObject.Player.Character;

/**
 * Represnts a Tile on the board
 * 
 * @author cameronmclachlan
 *
 */
public class MoveTile implements Tile{
	
	// Tile coordinates
	protected int x;
	protected int y;
	
	// player at this tile, null if none
	protected Player player;
	
	/**
	 * Constructs a new Tile object
	 * 
	 * @param x
	 * @param y
	 */
	public MoveTile(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * prints the tile
	 */
	public void print(){
		System.out.print("[ ]");
	}
	
	/**
	 * Returns the first letter of the players last name
	 * 
	 * @return letter
	 */
	public String getLetter(){
		switch(player.getCharacter()){
			case MISS_SCARLETT: return"S";
			case COLONEL_MUSTARD: return "M";
			case MRS_WHITE: return "W";
			case THE_REVERAND_GREEN: return "G";
			case MRS_PEACOCK: return "P";
			case PROFESSOR_PLUM: return "p";
		}return " ";		
	}
	
	// Getters and Setters
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	public Player getPlayer(){
		return player;
	}

	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
}

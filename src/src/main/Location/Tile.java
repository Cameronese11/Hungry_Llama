package src.main.Location;


import java.util.Scanner;

import src.main.GameObject.Player;
import src.main.GameObject.Player.Character;

/**
 * Represents a Tile on the board
 */
public class Tile implements Location{
	
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
	public Tile(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * prints the tile
	 */
	public void print(){
		if(player == null)
			System.out.print("[ ]");
		else
			System.out.print("[" + getLetter() + "]");
	}
	
	/**
	 * prints the tile
	 */
	public void printLocation(){
			System.out.print("(" + (x+1) + "," + (y+1) + ")");
	}
	
	/**
	 * Returns the Letter to represent the tiles starting character
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

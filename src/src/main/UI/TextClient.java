
package src.main.UI;

import java.util.List;
import java.util.Scanner;

import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Player;

/**
 * Class to present the game onto print to and 
 * Receive input from the console
 * 
 * @author cameronmclachlan
 */
public class TextClient {
	
	private Game game;
	private Board board;
	
	
	/**
	 * Constructs a new text Client opject
	 * @param g - Current Game
	 * @param b - Game Board
	 */
	public TextClient(Game g, Board b){
		this.game = g;
		this.board = b;
	}
	
	/**
	 * Setup the Game 
	 */
	public void initialiseGame(){
		System.out.println("Welcome to Cluedo");
		System.out.println();
		Scanner scan = new Scanner(System.in);
		boolean done = false;
		int numPlayers = 0;
		while(!done){
			
			// Determine the number of players
			System.out.println("How many people are playing(3-6)");
			numPlayers = scan.nextInt();
			
			// keep asking until correct value is entered
			if(numPlayers <= 6 && numPlayers >= 3){
				done = true;
			}else{
				game.clearConsole();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Must be between 1 and 6 players");
				System.out.println();
				numPlayers = 0;
			}
		}
		game.setNumPlayers(numPlayers);
		int i = 1;
		while(i<=numPlayers){
			game.clearConsole();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Determine each players character

			System.out.println("Player " + i);
			System.out.println();
			System.out.println("Type the integer representing the Character you would like to use,"); 
			System.out.println("type 'r' for a random character");
			System.out.println();
			int k = game.getCharacters().size();
			
			List<Player.Character> a = game.getCharacters();
			List<Player.Character> b = game.getCharactersLeft();
			for(int j = 0; j < k; j++)
				if(b.contains(a.get(j)))
					System.out.println((j + 1) + ". " + a.get(j));
			System.out.println();
			
			String answer = scan.next();
			
			Player.Character character = null;
			if(answer.equalsIgnoreCase("r")){
				character = game.generatePlayer(i);
			}else if(answer.equals("1"))
				character = game.addPlayer(i, Player.Character.MISS_SCARLETT);
			else if(answer.equals("2"))
				character = game.addPlayer(i, Player.Character.COLONEL_MUSTARD);
			else if(answer.equals("3"))
				character = game.addPlayer(i, Player.Character.MRS_WHITE);
			else if(answer.equals("4"))
				character = game.addPlayer(i, Player.Character.THE_REVERAND_GREEN);
			else if(answer.equals("5"))
				character = game.addPlayer(i, Player.Character.MRS_PEACOCK);
			else if(answer.equals("6"))
				character = game.addPlayer(i, Player.Character.PROFESSOR_PLUM);
			else{ 
				System.out.println("invalid response, please try again");
				System.out.println();
				i--;
			}
			if(character != null){
				System.out.println("Player " + i + " is assigned " + character);
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			i++;
		}
		scan.close();
	}
	
	/**
	 * Run the Game
	 */
	public void Run(){
		initialiseGame();
		
		while(true)
			board.printBoard();
			
			
		
	}
}


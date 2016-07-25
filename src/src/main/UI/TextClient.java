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
			System.out.println("How many people are playing(Max 6)");
			numPlayers = scan.nextInt();
			
			// keep asking until correct value is entered
			if(numPlayers <= 6 && numPlayers >= 1){
				done = true;
			}else{
				game.clearConsole();
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
			int k = game.getCharactersLeft().size();
			
			List<Player.Character> p = game.getCharactersLeft();
			for(int j = 0; j < k; j++)
				System.out.println(j + ". " + p.get(j));
			System.out.println();
			
			String answer = scan.next();
			
			Player.Character character = null;
			if(answer.equalsIgnoreCase("r")){
				character = game.generatePlayer(i);
				System.out.println("Player " + i + " is assigned " + character);
			}else if(answer.equalsIgnoreCase("Miss Scarlett") || answer.equalsIgnoreCase("Miss_Scarlet"))
				character = game.addPlayer(i, Player.Character.MISS_SCARLETT);
			else if(answer.equalsIgnoreCase("Colonel Mustard") || answer.equalsIgnoreCase("Colonel_Mustard"))
				character = game.addPlayer(i, Player.Character.COLONEL_MUSTARD);
			else if(answer.equalsIgnoreCase("Mrs White") || answer.equalsIgnoreCase("Mrs_White"))
				character = game.addPlayer(i, Player.Character.MRS_WHITE);
			else if(answer.equalsIgnoreCase("The Reverand Green") || answer.equalsIgnoreCase("The Reverand_Green"))
				character = game.addPlayer(i, Player.Character.THE_REVERAND_GREEN);
			else if(answer.equalsIgnoreCase("Mrs Peacock") || answer.equalsIgnoreCase("Mrs_Peacock"))
				character = game.addPlayer(i, Player.Character.MRS_PEACOCK);
			else if(answer.equalsIgnoreCase("Professor Plum") || answer.equalsIgnoreCase("Professor_Plum"))
				character = game.addPlayer(i, Player.Character.PROFESSOR_PLUM);
			else{ 
				System.out.println("invalid response, please try again");
				System.out.println();
				i--;
			}
			if(character != null)
				System.out.println("Player " + i + " is assigned " + character);
			i++;
		}
		scan.close();
	}
	
	/**
	 * Run the Game
	 */
	public void Run(){
		
		
	}
}


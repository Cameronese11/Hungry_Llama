
package src.main.UI;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.xml.stream.events.Characters;

import src.main.Cards.Card;
import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Player;

/**
 * Class to present the game and 
 * Receive input from the console
 * 
 * @author cameronmclachlan
 */
public class TextClient {
	
	private Game game;
	private Board board;
	Scanner scan;
	
	
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
		scan = new Scanner(System.in);
		boolean done = false;
		String answer = "";
		int numPlayers = 0;
		while(!done){
						
			// Determine the number of players
			System.out.println("How many people are playing(3-6)");
			answer = scan.next();
			
			// keep asking until correct value is entered
			if(answer.equals("3")){
				numPlayers = 3;
				done = true;
			}else if(answer.equals("4")){
				numPlayers = 4;
				done = true;
			}else if(answer.equals("5")){
				numPlayers = 5;
				done = true;
			}else if(answer.equals("6")){
				numPlayers = 6;
				done = true;
			}
			else{
				game.clearConsole();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Must be between 1 and 6 players");
				System.out.println();
			}
		}
		game.setNumPlayers(numPlayers);
		int i = 1;
		while(i<=numPlayers){
			game.clearConsole();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
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
			
			done = true;
			answer = scan.next();
			Player.Character character = null;
			
			if(answer.equalsIgnoreCase("r")){
				character = game.generatePlayer(i);
			}else if(answer.equals("1")){
				if(b.contains(Player.Character.MISS_SCARLETT))
					character = game.addPlayer(i, Player.Character.MISS_SCARLETT);
				else
					done = false;
			}else if(answer.equals("2")){
				if(b.contains(Player.Character.COLONEL_MUSTARD))
					character = game.addPlayer(i, Player.Character.COLONEL_MUSTARD);
				else
					done = false;
			}else if(answer.equals("3")){
				if(b.contains(Player.Character.MRS_WHITE))
					character = game.addPlayer(i, Player.Character.MRS_WHITE);
				else
					done = false;
			}else if(answer.equals("4")){
				if(b.contains(Player.Character.THE_REVERAND_GREEN))
					character = game.addPlayer(i, Player.Character.THE_REVERAND_GREEN);
				else
					done = false;
			}else if(answer.equals("5")){
				if(b.contains(Player.Character.MRS_PEACOCK))
					character = game.addPlayer(i, Player.Character.MRS_PEACOCK);
				else
					done = false;
			}else if(answer.equals("6")){
				if(b.contains(Player.Character.PROFESSOR_PLUM))
					character = game.addPlayer(i, Player.Character.PROFESSOR_PLUM);
				else
					done = false;
			}else
				done = false;
			if(done == false){ 
				System.out.println("invalid response, please try again");
				System.out.println();
				i--;
			}
			if(character != null){
				System.out.println("Player " + i + " is assigned " + character);
				
				try {
					Thread.sleep(0000); // 3000
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			i++;
		}
		game.dealCards();
		game.setupPlayers();
		
	}
	
	/**
	 * Run the Game
	 */
	public void Run(){
		initialiseGame();
		Scanner scan = new Scanner(System.in);

			while(true){
				
				game.clearConsole();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				board.printBoard();
				
				System.out.println(game.nextTurn().getCharacter() + " It is your turn");
				System.out.println();
				System.out.println("Type 'r' when you are Ready");
				System.out.println();
			
				String input = scan.next();
				
				if(input.equals("r"))
					startTurn();
			}
			
				
	}
	public int rollDice(){
		game.clearConsole();
		int roll = game.rollDice();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return roll;
	}
	
	public void startTurn(){
		game.clearConsole();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		showTurnInfo();

		System.out.println();
		System.out.println("What would you like to do?");
		System.out.println();
		System.out.println("1. Roll the Dice");
		System.out.println();
		String input = scan.next();
		
		int roll = 0;
		if(input.equals("1")){
			roll = rollDice();
			showTurnInfo();
			System.out.println();
			System.out.println("You rolled a " + roll);
			System.out.println();
			System.out.println();
		
			String input2 = scan.next();
		
		
		
		
		}
		
		
		
	}
	
	public void showTurnInfo(){
		board.printBoard();
		System.out.println("//////////     Your Hand     //////////");
		System.out.println();
		for(Card c: game.getCurrentPlayer().getHand())
			System.out.println(c.toString());
		System.out.println();
		if(!game.getDeck().isEmpty()){
			System.out.println("//////////  Left Over Cards  //////////");
			System.out.println();
			for(Card c: game.getDeck())
				System.out.println(c.toString());
			System.out.println();
		}
		System.out.println("///////////////////////////////////////");
		
		
	}

}


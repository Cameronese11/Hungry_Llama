
package src.main.UI;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.xml.stream.events.Characters;

import src.main.Cards.Card;
import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Player;
import src.main.GameObject.Weapon;
import src.main.Location.Location;
import src.main.Location.Room;
import src.main.Location.Tile;

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
				
				System.out.println(game.getCurrentPlayer().getCharacter() + " It is your turn");
				System.out.println();
				System.out.println("Type 'r' when you are Ready");
				System.out.println();
			
				String input = scan.next();
				
				if(input.equals("r"))
					StartTurn();
				
				System.out.println();
				System.out.println();
				System.out.println("Type in the coordinate where you would like");
				System.out.println("to move with the format x,y (no brackets)");
				System.out.println();
				
				boolean done = false;
				int x = 0;
				int y = 0;
				while(done == false){
					input = scan.next();
					Scanner sc = new Scanner(input);
					sc.useDelimiter(",");
					String input1 = sc.next();
					String input2 = sc.next();
					try{
						x = Integer.valueOf(input1);
						y = Integer.valueOf(input2);
						done = true;
					}catch (NumberFormatException e){
						input = null;
						System.out.println();
						System.out.println("Invalid Input");
						System.out.println();
						System.out.println("Type in the coordinate where you would like");
						System.out.println("to move with the format x,y (no brackets)");
						System.out.println();
					}
				
				}	
				
				game.getCurrentPlayer().move((Location) board.getTile(x-1, y-1));				
				if(game.getCurrentPlayer().getLocation() instanceof Room)
					suggestion();
				
				game.setCurrentPlayer(game.nextTurn());
				
				
			}
			
				
	}
	
	
	public void StartTurn(){
		game.clearConsole();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		showTurnInfo();

		int roll = 0;
		roll = game.rollDice();
		roll = 6;
		System.out.println();
		System.out.println("You rolled a " + roll);
		System.out.println();
		System.out.println("You can move to the following locations");
		System.out.println();
		List<Tile> moveableTiles = game.determineMoveLocations(game.getCurrentPlayer(), roll);
		int count = 0;
		for(Tile t: moveableTiles){
			System.out.print("(" + (t.getX()+1) + "," + (t.getY()+1) + ") ");
			count++;
			if(count == 6){
				System.out.println();
				count = 0;
			}
		}
	}
	
	public void showTurnInfo(){
		Player currentPlayer = game.getCurrentPlayer();
		board.printBoard();
		System.out.println("///////////     Players     ///////////");
		System.out.println();
		for(Player p: game.getPlayers()){
			if(game.getCurrentPlayer().equals(p))
				System.out.print("*");
			System.out.print(p.getCharacter() + ": ");
			p.print();
			System.out.print(" ");
			p.getLocation().printLocation();
			System.out.println();
		}
		System.out.println();
		System.out.println("////////////     Rooms     ////////////");
		System.out.println();
		for(Room r: game.getRooms()){
			System.out.println(r.getName());
			for(Weapon w: r.getWeapons()){
				System.out.println("  Weapon: " + w.getName());
			}
			for(Player p: r.getPlayers())
				System.out.println("  Player: " + p.getCharacter());
			System.out.println();
		}
		
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

	public void suggestion(){
		Weapon weapon = null;
		Player suspect = null;
		
		game.clearConsole();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		showTurnInfo();
		boolean done = false;
		
		while(done == false){
			System.out.println();
			System.out.println("Would you like to make a suggestion?");
			System.out.println("y for yes, n for no");
			System.out.println();
			String input = scan.next();
			if(input.equalsIgnoreCase("y"))
				done = true;
			else if(input.equalsIgnoreCase("n"))
				done = true;
			else
				System.out.println("invalid response");
			System.out.println();
		}
		done = false;
		while (done == false){
			System.out.print("Chose your suspected murder weapon");
			System.out.println();
			System.out.println("1. Candlestick");
			System.out.println("2. Dagger");
			System.out.println("3. Lead Pipe");
			System.out.println("4. Revolver");
			System.out.println("5. Rope");
			System.out.println("6. Spanner");
			System.out.println();
			String input = scan.next();
			done = true;
			if(input.equals("1"))
				weapon = game.getWeapon("Candlestick");
			else if(input.equals("2"))
				weapon = game.getWeapon("Dagger");
			else if(input.equals("3"))
				weapon = game.getWeapon("LeadPipe");
			else if(input.equals("4"))
				weapon = game.getWeapon("Revolver");
			else if(input.equals("5"))
				weapon = game.getWeapon("Rope");
			else if(input.equals("6"))
				weapon = game.getWeapon("Spanner");
			else{
				System.out.println();
				System.out.println("invalid input");
				done = false;
			}
			System.out.println();
		}
		done = false;
		while (done == false){
			System.out.print("Chose your suspect");
			System.out.println();
			System.out.println("1. MISS_SCARLETT");
			System.out.println("2. COLONEL_MUSTARD");
			System.out.println("3. MRS_WHITE");
			System.out.println("4. THE_REVERAND_GREEN");
			System.out.println("5. MRS_PEACOCK");
			System.out.println("6. PROFESSOR_PLUM");
			System.out.println();
			String input = scan.next();
			done = true;
			if(input.equals("1"))
				suspect = game.getPlayer(Player.Character.MISS_SCARLETT);
			else if(input.equals("2"))
				suspect = game.getPlayer(Player.Character.COLONEL_MUSTARD);
			else if(input.equals("3"))
				suspect = game.getPlayer(Player.Character.MRS_WHITE);
			else if(input.equals("4"))
				suspect = game.getPlayer(Player.Character.THE_REVERAND_GREEN);
			else if(input.equals("5"))
				suspect = game.getPlayer(Player.Character.MRS_PEACOCK);
			else if(input.equals("6"))
				suspect = game.getPlayer(Player.Character.PROFESSOR_PLUM);
			else{
				System.out.println();
				System.out.println("invalid input");
				done = false;
			}if(suspect.equals(game.getCurrentPlayer())){
				System.out.println();
				System.out.println("You cant be a suspect!");
				done = false;
			}
			System.out.println();
		}
		game.suggestion(suspect,weapon,game.getCurrentPlayer().getLocation());
		
	}

}


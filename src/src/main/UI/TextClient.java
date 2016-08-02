
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
import src.main.GameObject.Player.Character;
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
	private boolean running;
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
		answer = "";
		while(!done){
						
			// Determine the number of players
			System.out.println("How many people are playing(3-6)");
			System.out.println();
			answer = scan.nextLine();
			
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
				System.out.println("Must be between 1 and 6 players!");
				System.out.println();
			}
		}
		game.setNumPlayers(numPlayers);
		
		
		int i = 1;
		// while there are still players to be assigned, then assign them a character
		while(i<=numPlayers){
			
			// clear the console, 
			game.clearConsole();
			try {
				Thread.sleep(1000); // for timing
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
			Player.Character character = null;
			done = true;
			
			// print all the unassigned characters
			for(int j = 0; j < k; j++)
				if(b.contains(a.get(j)))
					System.out.println((j + 1) + ". " + a.get(j));
			System.out.println();
			
			// Wait for user input
			answer = scan.nextLine();
			
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
				System.out.println("invalid response! please try again");
				System.out.println();
				
				try {
					
					Thread.sleep(2000); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				i--;
			}
			if(character != null){
				System.out.println("Player " + i + " is assigned " + character);
				
				
				try {
					// to give them a change to read below print statement
					Thread.sleep(3000); // 3000
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			i++;
		}
	}
		
	
	/**
	 * Run the Game
	 */
	public void Run(){
		running = true;
		Scanner scan = new Scanner(System.in);
			
		// Game loop:
			while(running){
				
				game.clearConsole();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
				
				System.out.println(game.getCurrentPlayer().getCharacter() + " It is your turn");
				System.out.println();
				System.out.println("Type anything and hit enter when you are Ready");
				System.out.println();
			
				String input = scan.next();
				input = null;
				
				game.clearConsole();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				showTurnInfo();
				
				
				boolean done = false;
				boolean suggestion = false;
				boolean stairway = false;
				boolean askSuggestion = false;
				input = null;
				List<Tile> moveableTiles = rollDice();
				while(done == false){
					System.out .println();
					System.out.println("What would you like to do?");
					System.out .println();
					System.out.println("1. Move");
					System.out.println("2. Make an Accusation");
					if(game.getCurrentPlayer().getLocation() instanceof Room){
						Room room = (Room) game.getCurrentPlayer().getLocation();
						System.out.println("3. Make a Suggestion");
						if(room.getStairwayTo() != null)
							System.out.println("4. Use Stairway to " + room.getStairwayTo());		
					}
					System.out.println();
					input = scan.next();
					if(input.equals("1")){
						done = true;
						move(moveableTiles);
					}else if(input.equals("2")){
						done = true;
						makeAccusation();
						askSuggestion = true;
						
					}else if(input.equals("3")){
						if(game.getCurrentPlayer().getLocation() instanceof Room){
							boolean fin = false;
							while(fin == false){
								System.out.println();
								System.out.println("If you make a suggestion from this room now, you cannot move this turn");
								System.out.println("are you sure you want to do this?");
								System.out.println();
								System.out.println("1. Yes");
								System.out.println("2. No");
								System.out.println();
								String input2 = scan.next();
								if(input2.equals("1")){
									done = true;
									fin = true;
									suggestion = true;
									askSuggestion = true;
								}else if(input2.equals("2")){
									fin = true;
								}else{
									System.out.println();
									System.out.println("invalid input, please try again");
								}
							}						
						}
					}else if(input.equals("4")){
						if(game.getCurrentPlayer().getLocation() instanceof Room){
							Room room = (Room) game.getCurrentPlayer().getLocation();
							if(room.getStairwayTo() != null){
								boolean fin = false;
								while(fin == false){
									System.out.println("");
									System.out.println("If you use the stairway it will count as your turn");
									System.out.println("are you sure you want to do this?");
									System.out.println();
									System.out.println("1. Yes");
									System.out.println("2. No");
									System.out.println();
									String input2 = scan.next();
									if(input2.equals("1")){
										fin = true;
										stairway = true;
										done = true;
									}else if(input2.equals("2")){
										fin = true;
									}else{
										System.out.println();
										System.out.println("invalid input, please try again");
									}
								}			
							}
						}
					}else{
					 System.out.println("invalid input, please try again");
					
					}
					
				
				}
				
				if(stairway == true){
					game.useStairway();
					game.clearConsole();
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					showTurnInfo();
					System.out.println();
					System.out.println("Board Updated...");
				
				}
				
				if(game.getCurrentPlayer().getLocation() instanceof Room)
					while(askSuggestion == false){
						System.out.println();
						System.out.println("Would you like to make a suggestion?");
						System.out.println();
						System.out.println("1. Yes");
						System.out.println("2. No");
						System.out.println();
						String input2 = scan.next();
						if(input2.equalsIgnoreCase("1")){
							suggestion = true;
							askSuggestion = true;
						}	
						else if(input2.equalsIgnoreCase("2"))
							askSuggestion = true;
						else
							System.out.println("invalid response");
						System.out.println();
					}
			
				
				
				if(suggestion == true)
					if(game.getCurrentPlayer().getLocation() instanceof Room)
						makeSuggestion();
				
				
					
				game.setCurrentPlayer(game.nextTurn());
				
				
			
			
				
			}
	}
	public void move(List<Tile> moveableTiles){
		
		System.out.println();
		System.out.println("Type in the coordinate where you would like");
		System.out.println("to move with the format x,y (no brackets)");
		System.out.println();
		
		String input;
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
				for(Tile t :moveableTiles)
					if(t.getX() == (x - 1) && t.getY() == (y - 1))
						done = true;
			}catch (NumberFormatException e){
				
			}
			if(done == false){
				input = null;
				System.out.println();
				System.out.println("Invalid Input");
				System.out.println();
				System.out.println("Type in a valid coordinate where you would like");
				System.out.println("to move with the format x,y (no brackets)");
				System.out.println();
			}
		}	
		

		game.getCurrentPlayer().move((Location) board.getTile(x-1, y-1));				
		
		
		
		
	}
		
		
		
	
	
	public void makeAccusation() {
		Weapon weapon = null;
		Character suspect = null;
		Room room = null;
		
		game.clearConsole();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		showTurnInfo();
		System.out.println();
		System.out.println("Board Updated...");
		
		
		boolean done = false;
		
		
		while (done == false){
			System.out.println();
			System.out.println("Make a Accusation, chose wisely");
			System.out.println();
			System.out.print("Chose the murder weapon");
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
				weapon = game.getWeapon("Lead Pipe");
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
				suspect = Player.Character.MISS_SCARLETT;
			else if(input.equals("2"))
				suspect = Player.Character.COLONEL_MUSTARD;
			else if(input.equals("3"))
				suspect = Player.Character.MRS_WHITE;
			else if(input.equals("4"))
				suspect = Player.Character.THE_REVERAND_GREEN;
			else if(input.equals("5"))
				suspect = Player.Character.MRS_PEACOCK;
			else if(input.equals("6"))
				suspect = Player.Character.PROFESSOR_PLUM;
			else{
				System.out.println();
				System.out.println("invalid input");
				done = false;
			}
			
			System.out.println();
		}
		done = false;
		while (done == false){
			System.out.print("Chose your murder room");
			System.out.println();
			System.out.println("1. Kitchen");
			System.out.println("2. Dinning Room");
			System.out.println("3. Lounge");
			System.out.println("4. Ball Room");
			System.out.println("5. Hall");
			System.out.println("6. Study");
			System.out.println("7. Library");
			System.out.println("8. Billard Room");
			System.out.println("9. Conservatory");
			System.out.println();
			String input = scan.next();
			done = true;
			if(input.equals("1"))
				room = game.getRoom("Kitchen");
			else if(input.equals("2"))
				room = game.getRoom("Dining Room");
			else if(input.equals("3"))
				room = game.getRoom("Lounge");
			else if(input.equals("4"))
				room = game.getRoom("Ball Room");
			else if(input.equals("5"))
				room = game.getRoom("Hall");
			else if(input.equals("6"))
				room = game.getRoom("Study");
			else if(input.equals("7"))
				room = game.getRoom("Library");
			else if(input.equals("8"))
				room = game.getRoom("Billard Room");
			else if(input.equals("9"))
				room = game.getRoom("Conservatory");
			else{
				System.out.println();
				System.out.println("invalid input");
				done = false;
			}
			System.out.println();
		}
		
		
		Boolean accusation = game.accusation(suspect,weapon,room);
		
		if(accusation){
			System.out.println("Congratulations you Win!");
			running = false;
		}else{
			System.out.println("You Lose!, The correct solution is:");
			System.out.println();
			System.out.println("MurderWeapon: " + game.getBasement().getMurderWeapon().getName());
			System.out.println("MurderRoom: " + game.getBasement().getMurderRoom().getName());
			System.out.println("Muderer: " + game.getBasement().getMurderCharacter());
			System.out.println();
			System.out.println("Press any key when your done");
			String input = scan.next();
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
		if(!game.getCardsLeft().isEmpty()){
			System.out.println("//////////  Left Over Cards  //////////");
			System.out.println();
			for(Card c: game.getCardsLeft())
				System.out.println(c.toString());
			System.out.println();
		}
		System.out.println("///////////////////////////////////////");
		
		
	}

	public List<Tile> rollDice(){
		int roll = 0;
		roll = game.rollDice();
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
		System.out.println();
		return moveableTiles;
	}
	
	
	public void makeSuggestion(){
		Weapon weapon = null;
		Character suspect = null;
		
		game.clearConsole();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		showTurnInfo();
		System.out.println();
		System.out.println("Board Updated...");
		
		
		boolean done = false;
		
		
		while (done == false){
			System.out.println();
			System.out.println("Make a Suggestion, chose wisely");
			System.out.println();
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
				weapon = game.getWeapon("Lead Pipe");
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
				suspect = Player.Character.MISS_SCARLETT;
			else if(input.equals("2"))
				suspect = Player.Character.COLONEL_MUSTARD;
			else if(input.equals("3"))
				suspect = Player.Character.MRS_WHITE;
			else if(input.equals("4"))
				suspect = Player.Character.THE_REVERAND_GREEN;
			else if(input.equals("5"))
				suspect = Player.Character.MRS_PEACOCK;
			else if(input.equals("6"))
				suspect = Player.Character.PROFESSOR_PLUM;
			else{
				System.out.println();
				System.out.println("invalid input");
				done = false;
			}
			
			System.out.println();
		}
		String Refute = game.suggestion(suspect,weapon,((Room)game.getCurrentPlayer().getLocation()));
		if(Refute == null){
			System.out.println("Well done, Suggestion was NOT refuted");
		}else{
			System.out.println(Refute);
		}
		
		System.out.println();
		System.out.println("Press any key when your done");
		String input = scan.next();
		
		
	}

}


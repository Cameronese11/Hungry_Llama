package src.main.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.main.Cluedo.Board;
import src.main.Cluedo.Game;

/**
 * 
 * Class to structure the GUI and create the window 
 *
 */
public class CluedoFrame extends JFrame{

	
	private CluedoCanvas canvas; // Canvas to draw game on
	private JPanel panelCont; // Panel to place all panels inside
	private SetupMenu setupMenu; // setup Menu to setup a game
	private String gameArgs;	// game arguments for a new instance of the game
	private MenuBar menuBar;
	private Game game;
	private Board board;
	
	/**
	 * Constructs a new CluedoFrame object and window
	 * 
	 * @param game 
	 * @param board
	 * @param gameArgs - board.txt location for creating a new board
	 */
	public CluedoFrame(Game game, Board board, String gameArgs){
		super("Cluedo Game");
		
		// initialise fields
		this.game = game;
		this.board = board;
		this.gameArgs = gameArgs;
		
		// setup Jframe
		setSize(getPreferredSize());
		setLocation(225,125);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true); 
		
		Game.gameState = Game.State.SETUP_MENU;
		
		// create components
		canvas = new CluedoCanvas(game, board, this); 
		panelCont = new JPanel();
		menuBar = new MenuBar(this);
		setupMenu = new SetupMenu(game, this);
		
		// add components
		panelCont.add(canvas);
		add(panelCont);
		
		// procced to Setup Menu
		setupMenuUI();
		
	}
	
	/**
	 * adds and displays the setupMenu 
	 */
	public void setupMenuUI(){
		panelCont.setLayout(new GridLayout(2,1)); // split container panel in half
		panelCont.add(setupMenu);					
		setJMenuBar(menuBar.createMenuBar());
		pack(); // pack together components
		repaint();
	}
	
	/**
	 * adds and displays the game board
	 */
	public void gameBoardUI(){
		panelCont.removeAll(); // clear the container panel
		panelCont.setLayout(new BorderLayout()); // change layout
		panelCont.add(canvas);
		pack(); // pack together components
		revalidate(); // revalidate the new JFrame
		
		// get game ready to start
		game.setupPlayers();
		game.dealCards();
		
		repaint();
	}
	
	
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(board.getWidth(),board.getHeight() + 25);
	}
	
	
	@Override
	public void repaint(){
		canvas.repaint();
		super.repaint();
		
	}
	
	/**
	 * Creates a new Cluedo frame and CluedoGame
	 * Called when user makes a new game from the menubar
	 */
	public void newCluedoGame(){
		Board board = new Board(gameArgs);
		Game game = new Game(board);
		CluedoFrame frame = new CluedoFrame(game, board, gameArgs);
	}
	
	/**
	 * 
	 * @param args - board.txt file location
	 */
	public static void main(String[] args){
		Board board = new Board(args[0]);
		Game game = new Game(board);
		new CluedoFrame(game, board, args[0]);
	}
	
	
	
	
}		
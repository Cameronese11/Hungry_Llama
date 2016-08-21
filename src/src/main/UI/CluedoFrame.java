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
		
		//
		canvas = new CluedoCanvas(game, board, this); 
		panelCont = new JPanel();
		
		
		
		
		menuBar = new MenuBar(this);
		panelCont.add(canvas);
		add(panelCont);
		
		Game.gameState = Game.State.SETUP_MENU;
		
		setupMenu = new SetupMenu(game, this);
		gameSetupUI();
		
	}
	
	public void gameSetupUI(){
		panelCont.setLayout(new GridLayout(2,1)); // use border layout
		panelCont.add(setupMenu);	
		setJMenuBar(menuBar.createMenuBar());
		pack();
		repaint();
	}
	
	public void gameBoardUI(){
		panelCont.removeAll();
		panelCont.setLayout(new BorderLayout());
		panelCont.add(canvas);
		pack();
		revalidate();
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
	
	public void newCluedoGame(){
		Board board = new Board(gameArgs);
		Game game = new Game(board);
		CluedoFrame frame = new CluedoFrame(game, board, gameArgs);
	}
	
	
	public static void main(String[] args){
		
		Board board = new Board(args[0]);
		Game game = new Game(board);
		CluedoFrame frame = new CluedoFrame(game, board, args[0]);
	}
	
	
	
	
}		
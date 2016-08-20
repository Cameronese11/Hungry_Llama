package src.main.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.main.Cluedo.Board;
import src.main.Cluedo.Game;

public class CluedoFrame extends javax.swing.JFrame implements java.awt.event.KeyListener{

	

	private CluedoCanvas canvas;
	private Game game;
	private Board board;
	private SetupMenu setupMenu;
	private JPanel panelCont;
	private MenuBar menuBar;
	
	
	
	public CluedoFrame(Game game, Board board){
	
		super("Cluedo Game");
		this.game = game;
		this.board = board;
		
		
		panelCont = new JPanel();
		
		
		
		
		setSize(getPreferredSize());
		setLocation(225,125);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new CluedoCanvas(game, board, this); // create canvas
		panelCont.add(canvas);
		//add(canvas, BorderLayout.CENTER); // add canvas
		setResizable(false); // prevent us from being resizeable
		setVisible(true); // make sure we are visible!
		addKeyListener(this);
		Game.gameState = Game.State.SETUP_MENU;
		menuBar = new MenuBar();
		add(panelCont);
		gameSetupUI();
		//Game.gameState = Game.State.RUNNING;
		//gameBoardUI();
	}
	
	public void gameSetupUI(){
		panelCont.setLayout(new GridLayout(2,1)); // use border layout
		setupMenu = new SetupMenu(game, this);
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
	
	
	public void keyPressed(KeyEvent e) {
		
	}
	public void keyReleased(KeyEvent e) {
			
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void repaint(){
		canvas.repaint();
		super.repaint();
		
	}
	
	
	
	
	public static void main(String[] args){
		Board board = new Board(args[0]);
		Game game = new Game(board);
		CluedoFrame frame = new CluedoFrame(game, board);
	}
	
	
	
	
}		
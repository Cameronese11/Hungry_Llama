package src.main.UI;



import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import src.main.Location.*;
import src.main.Cards.Card;
import src.main.Cluedo.Board;
import src.main.Cluedo.Game;

import src.main.GameObject.Player;

public class CluedoCanvas extends JPanel implements MouseListener, MouseMotionListener{

	private static final String IMAGE_PATH = "images/";
	private static final Image PAGE = loadImage("setupPageHome.png");
	
	
	private Game game;
	private Board board;
	
	private int mouseX;
	private int mouseY;
	
	
	private Dice dice;
	private BoardButtons boardButtonss;
	
	private List<Tile> moveableLocations;
	private Tile selectedTile;
	
	public CluedoCanvas(Game game, Board board, JFrame frame){
		this.game = game;
		this.board = board;
		moveableLocations = new ArrayList<>();
		addMouseListener(this);
		addMouseMotionListener(this);
		dice = new Dice(game, this);
		boardButtonss = new BoardButtons(game, this);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(board.getWidth(),board.getHeight());
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		}
	
	
	@Override
	public void paint(Graphics g){
		if(Game.gameState == Game.State.SETUP_MENU
				|| Game.gameState == Game.State.SETUP_PLAYER){
			g.drawImage(PAGE, 0, 0, null);
			
		}else if(Game.gameState == Game.State.RUNNING){
			board.paint(g, moveableLocations, selectedTile);
			for(Player p: game.getPlayers())
				p.paint(g);
			
			dice.paint(g);
			boardButtonss.paint(g);
			g.setColor(Color.white);
			Player p = game.getCurrentPlayer();
			p.paintHand(g);
			g.drawImage(p.getImage(), 590, 6,null);
			g.drawString(p.getName().toString(), 800, 20);
			g.drawString("It is your Turn", 800, 40);
			for(Room r: game.getRooms())
				r.paint(g, null, null);
		}
		
		
	}
	
	/**
	 * Load an image from the file system, using a given filename.
	 * 
	 * @param filename
	 * @return
	 */
	public static Image loadImage(String filename) {
		// using the URL means the image loads when stored
		// in a jar or expanded into individual files.
		java.net.URL imageURL = CluedoCanvas.class.getResource(IMAGE_PATH
				+ filename);

		try {
			Image img = ImageIO.read(imageURL);
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		Player player = game.getCurrentPlayer();
	
			if(dice.contains(new Point(getMouseX(), getMouseY()))){
				
				int roll = dice.diceClicked();
				moveableLocations = game.determineMoveLocations(player, roll);
			}else if(!moveableLocations.isEmpty()){
				for(Tile t: game.getTiles()){
					if(t != null){
						if(t.contains(getMousePosition())){
							if(moveableLocations.contains(t)){
								player.move(selectedTile);
								moveableLocations.clear();;
							}
						}
					}	
				}
			}else{
				String button = boardButtonss.contains(new Point(getMouseX(), getMouseY()));
				if(button != null){
					boardButtonss.buttonClicked(button);
				}
				
			}
			
		
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		String button = boardButtonss.contains(new Point(getMouseX(), getMouseY()));
		if(button != null)
			boardButtonss.buttonPressed(button);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		String button = boardButtonss.contains(new Point(getMouseX(), getMouseY()));
		if(button != null)
			boardButtonss.buttonReleased(button);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		selectedTile = null;
		for(Tile t: game.getTiles()){
			if(t != null)
				if(t.contains(new Point(mouseX, mouseY)))
					selectedTile = t;
				
		}
					
		//System.out.println(mouseX + ", "  + mouseY);
		repaint();	
	}

	public void resetDice(){
		dice.resetDice();
	}
	
	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}

	public Dice getDice(){
		return dice;
	}
	
	
	public List<Tile> getMoveableLocations(){
		return moveableLocations;
	}
	
	public Tile getSelectedTile(){
		return selectedTile;
	}

	


}

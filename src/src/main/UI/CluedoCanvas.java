package src.main.UI;



import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
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
import src.main.GameObject.Weapon;

public class CluedoCanvas extends JPanel implements MouseListener, MouseMotionListener{

	private static final String IMAGE_PATH = "images/";
	private static final Image PAGE = loadImage("setupPageHome.png");
	private static final Image BLACK_OPACITY = loadImage("blackOpacity.png");
	
	private Game game;
	private Board board;
	
	private int mouseX;
	private int mouseY;
	
	
	private Dice dice;
	private Card showCard;
	private BoardButtons boardButtons;
	
	private List<Tile> moveableLocations;
	private Tile selectedTile;
	
	public CluedoCanvas(Game game, Board board, JFrame frame){
		this.game = game;
		this.board = board;
		moveableLocations = new ArrayList<>();
		addMouseListener(this);
		addMouseMotionListener(this);
		dice = new Dice(game, this);
		boardButtons = new BoardButtons(game, this, frame);
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
			if(showCard != null)
				board.paint(g, moveableLocations, null);
			else
				board.paint(g, moveableLocations, new Point(mouseX, mouseY));
			
			for(Player p: game.getPlayers())
				p.paint(g);
			
			dice.paint(g);
			boardButtons.paint(g);
			g.setColor(Color.white);
			Player p = game.getCurrentPlayer();
			g.drawImage(p.getImage(), 590, 6,null);
			g.drawString(p.getName().toString(), 800, 20);
			g.drawString("It is your Turn", 800, 40);
			for(Room r: game.getRooms())
				r.paint(g, null, null);
			if(showCard != null){
				p.paintHand(g, new Point(0,0));
				g.drawImage(BLACK_OPACITY, 0, 0, 990, 590, null);
				g.drawImage(showCard.getImage(0),315, 33, null);
			}else{
				p.paintHand(g, new Point(mouseX, mouseY));
			}
			for(Player player: game.getPlayers()){
				Rectangle r = new Rectangle(player.getXPos(), player.getYPos(),19, 19);
				if(r.contains(mouseX, mouseY)){
					player.paintTag(g);
					}
			}
			for(Weapon weapon: game.getWeapons()){
				Rectangle r = new Rectangle(weapon.getXPos(), weapon.getYPos(),19, 19);
				if(r.contains(mouseX, mouseY)){
					weapon.paintTag(g);
					}
			}
			
		
		
		
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
		
		if(showCard != null){
			Rectangle r = new Rectangle(315, 33, 360, 524);
			if(!r.contains(mouseX, mouseY)){
				showCard = null;
			}
			
			
		}else{
		
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
			}else if(player.getSelectedCard() != null){
				Card c = player.getSelectedCard();
				showCard = c;
			}else{
				String button = boardButtons.contains(new Point(getMouseX(), getMouseY()));
				if(button != null){
					boardButtons.buttonClicked(button);
				}
				
			}
		}
		
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		String button = boardButtons.contains(new Point(getMouseX(), getMouseY()));
		if(button != null)
			boardButtons.buttonPressed(button);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		String button = boardButtons.contains(new Point(getMouseX(), getMouseY()));
		if(button != null)
			boardButtons.buttonReleased(button);
		
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

	public Card getShowCard(){
		return showCard;
	}


}

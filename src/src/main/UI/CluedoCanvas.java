package src.main.UI;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import src.main.Location.*;
import src.main.Cards.Card;
import src.main.Cluedo.Board;
import src.main.Cluedo.Game;
import src.main.GameObject.Basement;
import src.main.GameObject.Player;
import src.main.GameObject.Weapon;

/**
 * Represents the canvas for the cluedo 
 * game where the main elemnts are drawn
 *
 */
public class CluedoCanvas extends JPanel implements MouseListener, MouseMotionListener{

	// package location for all images
	private static final String IMAGE_PATH = "images/"; 
	
	// images the canvas requires
	private static final Image PAGE = loadImage("setupPageHome.png"); // setup menu image
	private static final Image BLACK_OPACITY = loadImage("blackOpacity.png"); // for dark background effect
	private static final Image table = loadImage("tablePattern.png"); // table pattern next to board
	
	private Game game;
	private Board board;
	
	// Mouse X and Y position
	private int mouseX;
	private int mouseY;
	
	// Component classes
	private AccusationOrSuggestion AOS;
	private Dice dice;
	private Card showCard;
	private BoardButtons boardButtons;
	private JPanel boardButtonsPanel;
		
	// Tiles to remember
	private List<Tile> moveableLocations;
	private Tile selectedTile;
	
	// Hides the currently displayed hand if true
	private boolean hideHand;
	
	public CluedoCanvas(Game game, Board board){
		this.game = game;
		this.board = board;
		
		// Setup Canvas JPanel
		setLayout(null);
		setLocation(0,0);
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);
		setOpaque(false);
		
		// initilise components
		dice = new Dice(game, this);
		moveableLocations = new ArrayList<>();
		boardButtons = new BoardButtons(game, this);
		AOS = new AccusationOrSuggestion(game, this);
		boardButtonsPanel = boardButtons.createPanel();
		
		// add to canvas panel
		add(boardButtonsPanel);
		
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(board.getWidth(),board.getHeight());
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		}
	
	/**
	 * Creates a AccusationOrSuggestion object for a accusation 
	 * or a suggestin from the user
	 * 
	 * @param acc - true if user is making an accusatio
	 * 			  - false if the user is making a suggestion
	 */
	public void accusation(Boolean acc){
		
		remove(boardButtonsPanel);// remove current components so a accisation/suggestion can be made

		// change to appropriate page
		if(acc)
			AOS.setPage(1);
		else
			AOS.setPage(3);
		
		// create and setup AOS JPrames
		JPanel room = AOS.askMurderRoom(acc);
		JPanel weapon = AOS.askMurderWeapon();
		JPanel character = AOS.askMurderCharacter();
		JPanel done = AOS.done();
		
		
				
		// add JPanels to canvas
		add(room);
		add(weapon);
		add(character);
		add(done);
		
		revalidate();
		repaint();
	}
	
	
	
	
	
	
	@Override
	public void paint(Graphics g){
		
		// Game is currently in the setupMenu
		if(Game.gameState == Game.State.SETUP_MENU
				|| Game.gameState == Game.State.SETUP_PLAYER){
			g.drawImage(PAGE, 0, 0, null);
			
		// Game is currently running
		}else if(Game.gameState == Game.State.RUNNING){
			// dont draw board hovers if on suggestion page
			if(showCard != null || AOS.getPage() > 0)
				board.paint(g, moveableLocations, null);
			else
				board.paint(g, moveableLocations, new Point(mouseX, mouseY));
			
			for(Player p: game.getPlayers())
				p.paint(g);	// Paint all the players on their tiles
			
			for(Player p: game.getPlayersOut())    // Paint players who have been 
				p.paint(g);						   // eliminated from the game on their tile
			
			paintHeading(g); // Paint title in topLeft
			dice.paint(g);	// paint the dice
			
			Player p = game.getCurrentPlayer();
			
			for(Room r: game.getRooms())
				r.paint(g, null, null); // Paint all the players and weapons in the rooms

			// Paint hand without enlargement when appropriate
			if(showCard != null || AOS.getPage() > 0){
				p.paintHand(g, new Point(0,0));
			}else{
				p.paintHand(g, new Point(mouseX, mouseY));
			}
			
			// player has clicked on a card
			if(showCard != null){
				g.drawImage(BLACK_OPACITY, 0, 0, 990, 590, null);
				g.drawImage(showCard.getImage(0),315, 33, null);
			}
			
			// Paint the player and weapon tags
			if(showCard == null && AOS.getPage() == 0){

				for(Player player: game.getPlayers()){
						Rectangle r = new Rectangle(player.getXPos(), player.getYPos(),19, 19);
						if(r.contains(mouseX, mouseY))
							player.paintTag(g); // If mouse is hovering over paint tag
				}
				for(Weapon weapon: game.getWeapons()){
					Rectangle r = new Rectangle(weapon.getXPos(), weapon.getYPos(),19, 19);
					if(r.contains(mouseX, mouseY))
						weapon.paintTag(g); // If mouse is hovering over paint tag
				}
			}
			
			// Paint the AOS page
			if(AOS != null)
				AOS.Paint(g);
			
			// Hide hand if necessary
			if(hideHand)
				g.drawImage(table, 600, 250, null);
			
			// paint components
			super.paint(g);
		
		// Game is over, declare winner
		}else if(Game.gameState == Game.State.OVER){
			gameOverScreen(g);
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
	
	/**
	 * Screen to declare the winner once the game is over
	 * 
	 * @param g
	 */
	public void gameOverScreen(Graphics g){
		g.drawImage(PAGE, 0, 0, null);
		Graphics2D g2d = (Graphics2D) g;
		Basement b = game.getBasement();
		Font font = new Font("Calibri", Font.PLAIN, 40);
		g2d.setFont(font);
		String s = "Congratulations, " + game.getWinner().getName() + " you are the Winner!";
		Rectangle2D r = font.getStringBounds(s, g2d.getFontRenderContext());
		g2d.drawString(s, 495 - (int) r.getWidth()/2, 320); // center string on page
		font = new Font("Calibri", Font.PLAIN, 20);
		g2d.setFont(font);
		s = "Correct Solution Was " +Game.getCharacterName(b.getMurderCharacter()) + ", with the " + b.getMurderWeapon().getName() + ", in the " + b.getMurderRoom().getName();
		r = font.getStringBounds(s, g2d.getFontRenderContext());
		g2d.drawString(s, 495 - (int) r.getWidth()/2, 400); // center string on page
	}
	
	/**
	 * Paints the heading on the top left of the canvas,
	 * as well as their corresponding picture
	 * 
	 * @param g - canvas graphics object
	 */
	public void paintHeading(Graphics g){
		Player p = game.getCurrentPlayer();
		g.setColor(Color.white);
		g.drawImage(p.getImage(), 590, 6,null);
		g.drawString(p.getName().toString(), 800, 20);
		g.drawString("It is your Turn", 800, 40);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(Game.gameState != Game.State.RUNNING){
			return;
		}
		
		
		// A card is currently displayed on screen
		if(showCard != null){
			Rectangle r = new Rectangle(315, 33, 360, 524);
			if(!r.contains(mouseX, mouseY)){ 
				showCard = null;		// if the player clicks outside the card
				add(boardButtonsPanel); // go back to the main screen
			}
		
		// Currently at the main game screen
		}else{
			Player player = game.getCurrentPlayer();
	
			// if player clicks on the dice and its ready to roll
			if(dice.contains(new Point(getMouseX(), getMouseY())) 
					&& dice.getState() == Dice.state.TO_ROLL){
				int roll = dice.diceClicked();
				moveableLocations = game.determineMoveLocations(player, roll);
					
			// check if a tile was clicked
			}else if(!moveableLocations.isEmpty()){
				for(Tile t: game.getTiles()){
					if(t != null){
						if(t.contains(new Point(getMouseX(), getMouseY()))){ // tile must have been clicked
							if(moveableLocations.contains(t)){ 
								 player.move(selectedTile);
								 moveableLocations.clear(); // empty until dice is rolled again
							}
						}
					}	
				}
			
			// check if a card was clicked
			}if(player.getSelectedCard() != null){ // if player was hovering over a card
				Card c = player.getSelectedCard(); // then make that the selected card
				showCard = c;
				remove(boardButtonsPanel);
			}
		}
		
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e){}

	@Override
	public void mouseReleased(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}

	@Override
	public void mouseDragged(MouseEvent e){}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		selectedTile = null;
		for(Tile t: game.getTiles()){
			if(t != null)
				if(t.contains(new Point(mouseX, mouseY)))
					selectedTile = t; // if hovering over a tile remember it
		}
		repaint();
	}

	/**
	 * resets the dice back to 0
	 */
	public void resetDice(){
		dice.resetDice();
	}
	
	// Getters and Setters
	
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
	
	public void resetMoveableLocations(){
		moveableLocations.clear();
	}
	
	public Tile getSelectedTile(){
		return selectedTile;
	}
	
	public JPanel getBoardButtonPanel(){
		return boardButtonsPanel;
	}
	
	public BoardButtons getBoardButtons(){
		return boardButtons;
	}

	public Card getShowCard(){
		return showCard;
	}
	public AccusationOrSuggestion getAOS(){
		return AOS;
	}
	public void setHideHand(Boolean b){
		hideHand = b;
	}

}
